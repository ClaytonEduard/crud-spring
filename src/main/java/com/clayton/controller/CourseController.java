package com.clayton.controller;

import com.clayton.model.Course;
import com.clayton.repository.CourseRepository;
import com.clayton.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

  private final CourseService courseService;

  public CourseController(
    CourseService courseService
  ) {
    this.courseService = courseService;
  }

  @GetMapping
  public @ResponseBody List<Course> list() {
    return courseService.list();
  }

  // listando o curso por id
  @GetMapping("/{id}")
  public ResponseEntity<Course> findById(
    @PathVariable @NotNull @Positive Long id
  ) {
    return courseService
      .findById(id)
      .map(recordFound -> ResponseEntity.ok().body(recordFound))
      .orElse(ResponseEntity.notFound().build());
  }

  //@RequestMapping(method = RequestMethod.POST)
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED) // somente para retornar o status 201
  public Course create(@RequestBody @Valid Course course) {
    return courseService.create(course);
    //return ResponseEntity
    //  .status(HttpStatus.CREATED)
    //  .body(courseRepository.save(course)); // somente para retornar o status 201
  }

  @PutMapping("/{id}")
  public ResponseEntity<Course> update(
    @PathVariable @NotNull @Positive Long id,
    @RequestBody @Valid Course course
  ) {
    return courseService
      .update(id, course)
      .map(recordFound -> ResponseEntity.ok().body(recordFound))
      .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
    if (courseService.delete(id)) {
      return ResponseEntity.noContent().<Void>build();
    }
    return ResponseEntity.notFound().build();
  }
}
