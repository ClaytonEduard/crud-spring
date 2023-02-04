package com.clayton.service;

import com.clayton.model.Course;
import com.clayton.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Valid
@Service
public class CourseService {

  private final CourseRepository courseRepository;

  public CourseService(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public List<Course> list() {
    return courseRepository.findAll();
  }

  // listando o curso por id
  public Optional<Course> findById(@PathVariable @NotNull @Positive Long id) {
    return courseRepository.findById(id);
  }

  //create
  public Course create(@Valid Course course) {
    return courseRepository.save(course);
  }

  // update
  public Optional<Course> update(
    @NotNull @Positive Long id,
    @Valid Course course
  ) {
    return courseRepository
      .findById(id)
      .map(recordFound -> {
        recordFound.setName(course.getName());
        recordFound.setCategory(course.getCategory());
        return courseRepository.save(recordFound);
      });
  }

  //delete
  public boolean delete(@PathVariable @NotNull @Positive Long id) {
    return courseRepository
      .findById(id)
      .map(recordFound -> {
        courseRepository.deleteById(id);
        return true;
      })
      .orElse(false);
  }
}
