package com.codewithsam.coursesapi.controller;

import com.codewithsam.coursesapi.model.CourseInstance;
import com.codewithsam.coursesapi.repository.CourseInstanceRepository;
import com.codewithsam.coursesapi.service.CourseInstanceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/instances")
public class CourseInstanceController {
    private final CourseInstanceService courseInstanceService;

    public CourseInstanceController(CourseInstanceService courseInstanceService) {
        this.courseInstanceService = courseInstanceService;
    }

    @PostMapping
    public ResponseEntity<?> createInstance(@RequestBody CourseInstance instance) {
        try {
            CourseInstance createdInstance = courseInstanceService.createInstance(instance);
            return new ResponseEntity<>(createdInstance, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<CourseInstance>> getAllInstances() {
        List<CourseInstance> allCourseInstances = courseInstanceService.getAllInstances();
        return ResponseEntity.ok(allCourseInstances);
    }

    // Get all instances in a given year and semester
    // /year/semester
    @GetMapping("/{year}/{semester}")
    public ResponseEntity<List<CourseInstance>> getInstanceByYearAndSemester(@PathVariable int year, @PathVariable int semester) {
        List<CourseInstance> courseInstances = courseInstanceService.getInstancesByYearAndSemester(year, semester);
        return new ResponseEntity<>(courseInstances, HttpStatus.OK);
    }

    // Get a specific instance
    // /year/semester/courseId
    @GetMapping("/{year}/{semester}/{courseId}")
    public ResponseEntity<?> getInstance(@PathVariable Long courseId, @PathVariable int year, @PathVariable int semester) {
        try {
            CourseInstance instance = courseInstanceService.getInstance(courseId, year, semester);
            return new ResponseEntity<>(instance, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{year}/{semester}/{courseId}")
    public ResponseEntity<?> deleteInstance(@PathVariable Long courseId, @PathVariable int year, @PathVariable int semester) {
        try {
            courseInstanceService.deleteInstance(courseId, year, semester);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
