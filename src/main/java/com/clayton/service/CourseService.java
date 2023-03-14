package com.clayton.service;

import com.clayton.dto.CourseDTO;
import com.clayton.dto.mapper.CourseMapper;
import com.clayton.exception.RecordNotFoundException;
import com.clayton.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Valid
@Service
public class CourseService {

  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;

  public CourseService(
    CourseRepository courseRepository,
    CourseMapper courseMapper
  ) {
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
  }

  // metodo listar todos
  public List<CourseDTO> list() {
    return courseRepository
      .findAll()
      .stream()
      .map(courseMapper::toDTO)
      .collect(Collectors.toList());
  }

  // listando o curso por id
  public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
    return courseRepository
      .findById(id).map(courseMapper::toDTO)
      .orElseThrow(() -> new RecordNotFoundException(id));
  }

  //create
  public CourseDTO create(@Valid @NotNull CourseDTO course) {
    return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
  }

  // update
  public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO course) {
    return courseRepository
      .findById(id)
      .map(recordFound -> {
        recordFound.setName(course.name());
        recordFound.setCategory(course.category());
        return courseMapper.toDTO(courseRepository.save(recordFound));
      })
      .orElseThrow(() -> new RecordNotFoundException(id));
  }

  //delete
  public void delete(@PathVariable @NotNull @Positive Long id) {
    courseRepository.delete(
      courseRepository
        .findById(id)
        .orElseThrow(() -> new RecordNotFoundException(id))
    );
  }
}
