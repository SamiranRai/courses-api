package com.codewithsam.coursesapi.repository;

import com.codewithsam.coursesapi.model.Course;
import com.codewithsam.coursesapi.model.CourseInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseInstanceRepository extends JpaRepository<CourseInstance, Long> {
    // To prevent duplicate course instances
    boolean existsByCourseAndYearAndSemester(com.codewithsam.coursesapi.model.Course course, int year, int semester);

    // To list all courses in a given semester
    List<CourseInstance> findByYearAndSemester(int year, int semester);

    // To get a single course instance
    Optional<CourseInstance> findByCourseIdAndYearAndSemester(Long courseId, int year, int semester);

    boolean existsByCourse(Course course);
}