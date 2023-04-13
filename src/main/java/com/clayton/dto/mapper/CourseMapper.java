package com.clayton.dto.mapper;

import com.clayton.dto.CourseDTO;
import com.clayton.enums.Category;
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
      course.getCategory().getValue()
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
    //TODO: use o mapper for Category
    course.setCategory(convertCategoryValue(courseDTO.category()));
    return course;
  }

  // metod convert for Category
  public Category convertCategoryValue(String value) {
    if (value == null) {
      return null;
    }
    //expressão switch parecido com o switch case do java
    return switch (value) {
      case "Front-end" -> Category.FRONT_END;
      case "Back-end" -> Category.BACK_END;
      default -> throw new IllegalArgumentException(
        "Categoria inválida: " + value
      );
    };
  }
}
