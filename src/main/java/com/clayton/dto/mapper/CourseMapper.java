package com.clayton.dto.mapper;

import com.clayton.dto.CourseDTO;
import com.clayton.dto.LessonDTO;
import com.clayton.enums.Category;
import com.clayton.model.Course;
import com.clayton.model.Lesson;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

  // criar um curso e retornar um dto
  public CourseDTO toDTO(Course course) {
    if (course == null) {
      return null;
    }

    List<LessonDTO> lessons = course.getLessons()
        .stream()
        .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
        .collect(Collectors.toList());
    return new CourseDTO(
        course.getId(),
        course.getName(),
        course.getCategory().getValue(), lessons);
  }

  // transformar curso em cursoDto
  public Course toEntity(CourseDTO courseDTO) {
    if (courseDTO == null) {
      return null;
    }

    Course course = new Course();
    if (courseDTO.id() != null) {
      course.setId(courseDTO.id());
    }
    course.setName(courseDTO.name());
    course.setCategory(convertCategoryValue(courseDTO.category()));

    List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
      var lesson = new Lesson();
      lesson.setId(lessonDTO.id());
      lesson.setName(lessonDTO.name());
      lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
      lesson.setCourse(course);
      return lesson;
    }).collect(Collectors.toList());
    course.setLessons(lessons);
    return course;
  }

  // metod convert for Category
  public Category convertCategoryValue(String value) {
    if (value == null) {
      return null;
    }
    // expressão switch parecido com o switch case do java
    return switch (value) {
      case "Front-end" -> Category.FRONT_END;
      case "Back-end" -> Category.BACK_END;
      default -> throw new IllegalArgumentException(
          "Categoria inválida: " + value);
    };
  }
}
