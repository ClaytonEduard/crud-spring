package com.clayton.controller;

import com.clayton.model.Course;
import com.clayton.repository.CourseRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor //construtor via lombok
public class CourseController {

  private final CourseRepository courseRepository;

  @GetMapping
  public List<Course> list() {
    return courseRepository.findAll();
  }

  //@RequestMapping(method = RequestMethod.POST)
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED) // somente para retornar o status 201
  public Course create(@RequestBody Course course) {
    return courseRepository.save(course);
    //return ResponseEntity
    //  .status(HttpStatus.CREATED)
    //  .body(courseRepository.save(course)); // somente para retornar o status 201
  }
}
