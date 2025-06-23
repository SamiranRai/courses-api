package com.codewithsam.coursesapi.service;

import com.codewithsam.coursesapi.model.Course;
import com.codewithsam.coursesapi.repository.CourseInstanceRepository;
import com.codewithsam.coursesapi.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseInstanceRepository courseInstanceRepository;

    public CourseService(CourseRepository courseRepository, CourseInstanceRepository courseInstanceRepository) {
        this.courseRepository = courseRepository;
        this.courseInstanceRepository = courseInstanceRepository;
    }

    // CREATE A NEW COURSE WITH PRE-REQUISITE VALIDATION
    public Course createCourse(Course course) {
        List<Course> validPrereqs = new ArrayList<>();

        for (Course prereq: course.getPrerequisites()) {
            Course found = courseRepository.findById(prereq.getId()).orElseThrow(() -> new EntityNotFoundException("Prerequisite Course ID " + prereq.getId() + " does not exist"));
            // ADD
            validPrereqs.add(found);
        }
        // UPDATE
        course.setPrerequisites(validPrereqs);
        // SAVE
        return courseRepository.save(course);
    }

    // GET ALL COURSE WITH THEIR PRE-REQUEST
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // GET COURSE BY ID
    public Course getCourseById(Long id) {
        return  courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course ID " + id + " does not exist"));
    }

    // DELETE THE COURSE IF IT'S NOT USED AS PRE-REQUESIT FOUND
    @Transactional
    public void deleteCourse(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course ID " + courseId + " does not exist"));

        boolean usedInInstance = courseInstanceRepository.existsByCourse(course);
        if (usedInInstance) {
            throw new DataIntegrityViolationException("Course has active instances and cannot be deleted.");
        }

        boolean isUsedAsPrerequsites = courseRepository.findAll().stream()
                .anyMatch(c -> c.getPrerequisites().stream()
                        .anyMatch(p -> p.getId().equals(courseId)));

        if (isUsedAsPrerequsites) {
        throw new DataIntegrityViolationException("Course ID " + courseId + " cannot be deleted as it is a prerequisite for other courses.");
        }

        // FINALLY DELETE
        courseRepository.delete(course);
    }

    }
