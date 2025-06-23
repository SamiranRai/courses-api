package com.codewithsam.coursesapi.service;

import com.codewithsam.coursesapi.model.Course;
import com.codewithsam.coursesapi.model.CourseInstance;
import com.codewithsam.coursesapi.repository.CourseInstanceRepository;
import com.codewithsam.coursesapi.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseInstanceService {
    private final CourseInstanceRepository instanceRepository;
    private final CourseRepository courseRepository;

    public CourseInstanceService(CourseInstanceRepository instanceRepository, CourseRepository courseRepository) {
        this.instanceRepository = instanceRepository;
        this.courseRepository = courseRepository;
    }

    // CREATE AN INSTANCES
    public CourseInstance createInstance(CourseInstance instance) {
        if (instance.getCourse() == null || instance.getCourse().getId() == null) {
            throw new IllegalArgumentException("Course ID must be provided.");
        }

        Long courseId = instance.getCourse().getId();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course ID " + courseId + " not found."));

        boolean alreadyExists = instanceRepository.existsByCourseAndYearAndSemester(course, instance.getYear(), instance.getSemester());
        if (alreadyExists) {
            throw new DataIntegrityViolationException("Course instance already exists for year " + instance.getYear() + ", semester " + instance.getSemester());
        }
        instance.setCourse(course);
        return  instanceRepository.save(instance);
    }

    // GENERAL GET ALL INSTANCES ENDPOINT  - /API/INSTANCES
    public List<CourseInstance> getAllInstances() {
        return instanceRepository.findAll();
    }

    // GET ALL INSTANCES FOR GIVEN YEAR AND SEMESTER
    public List<CourseInstance> getInstancesByYearAndSemester(int year, int semester) {
        return instanceRepository.findByYearAndSemester(year, semester);
    }

    // GET SPECIFIC COURSE INSTANCE
    public CourseInstance getInstance(Long courseId, int year, int semester) {
        return instanceRepository.findByCourseIdAndYearAndSemester(courseId, year, semester).orElseThrow(() -> new EntityNotFoundException("Instance not found for course ID " + courseId + " in " + year + ", semester " + semester));
    }

    public void deleteInstance(Long courseId, int year, int semester) {
        CourseInstance instance = instanceRepository.findByCourseIdAndYearAndSemester(courseId, year, semester).orElseThrow(() -> new EntityNotFoundException("Instance not found to delete."));
        // if everything OK
        instanceRepository.delete(instance);
    }

}
