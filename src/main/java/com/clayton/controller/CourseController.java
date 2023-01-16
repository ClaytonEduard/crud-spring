package com.clayton.controller;

import com.clayton.model.Course;
import com.clayton.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import lombok.AllArgsConstructor;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor //construtor via lombok
public class CourseController {

  private final CourseRepository courseRepository;

  @GetMapping
  public List<Course> list() {
    return courseRepository.findAll();
  }

  // listando o curso por id
  @GetMapping("/{id}")
  public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id) {
    return courseRepository
      .findById(id)
      .map(recordFound -> ResponseEntity.ok().body(recordFound))
      .orElse(ResponseEntity.notFound().build());
  }

  //@RequestMapping(method = RequestMethod.POST)
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED) // somente para retornar o status 201
  public Course create(@RequestBody @Valid Course course) {
    return courseRepository.save(course);
    //return ResponseEntity
    //  .status(HttpStatus.CREATED)
    //  .body(courseRepository.save(course)); // somente para retornar o status 201
  }

  @PutMapping("/{id}")
  public ResponseEntity<Course> update(
    @PathVariable @NotNull @Positive Long id,
    @RequestBody @Valid Course course
  ) {
    return courseRepository
      .findById(id)
      .map(recordFound -> {
        recordFound.setName(course.getName());
        recordFound.setCategory(course.getCategory());
        Course updated = courseRepository.save(recordFound);
        return ResponseEntity.ok().body(updated);
      })
      .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
    return courseRepository
      .findById(id)
      .map(recordFound -> {
        courseRepository.deleteById(id);
        return ResponseEntity.noContent().<Void>build();
      })
      .orElse(ResponseEntity.notFound().build());
  }
}
