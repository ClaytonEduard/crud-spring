package com.clayton.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.clayton.dto.CourseDTO;
import com.clayton.dto.mapper.CourseMapper;
import com.clayton.exception.RecordNotFoundException;
import com.clayton.model.Course;
import com.clayton.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Valid
@Service
public class CourseService {

  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;

  public CourseService(
      CourseRepository courseRepository,
      CourseMapper courseMapper) {
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
  public CourseDTO findById(@NotNull @Positive Long id) {
    return courseRepository
        .findById(id).map(courseMapper::toDTO)
        .orElseThrow(() -> new RecordNotFoundException(id));
  }

  // create
  public CourseDTO create(@Valid @NotNull CourseDTO course) {
    return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
  }

  // update
  public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
    return courseRepository
        .findById(id)
        .map(recordFound -> {
          Course course = courseMapper.toEntity(courseDTO);
          recordFound.setName(courseDTO.name());
          recordFound.setCategory(this.courseMapper.convertCategoryValue(courseDTO.category()));
          // recordFound.setLessons(course.getLessons());
          recordFound.getLessons().clear();
          course.getLessons().forEach(recordFound.getLessons()::add); // exemplo em lambida
          //course.getLessons().forEach(lesson -> recordFound.getLessons().add(lesson)); // forma normal

          return courseMapper.toDTO(courseRepository.save(recordFound));
        })
        .orElseThrow(() -> new RecordNotFoundException(id));
  }

  // delete
  public void delete(@NotNull @Positive Long id) {
    courseRepository.delete(
        courseRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
  }
}
