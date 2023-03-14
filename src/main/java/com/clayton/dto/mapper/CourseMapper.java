package com.clayton.dto.mapper;

import com.clayton.dto.CourseDTO;
import com.clayton.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

  //criar um curso e retornar um dto
  public CourseDTO toDTO(Course course) {
    if (course == null) {
      return null;
    }
    return new CourseDTO(
      course.getId(),
      course.getName(),
      course.getCategory()
    );
  }

  //transformar curso em cursoDto
  public Course toEntity(CourseDTO courseDTO) {
    if (courseDTO == null) {
      return null;
    }

    Course course = new Course();
    if (courseDTO.id() != null) {
      course.setId(courseDTO.id());
    }
    course.setName(courseDTO.name());
    course.setCategory(courseDTO.category());
    course.setStatus("Ativo");
    return course;
  }
}
