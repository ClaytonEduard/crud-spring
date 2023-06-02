package com.clayton;

import com.clayton.model.Course;
import com.clayton.model.Lesson;
import com.clayton.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.clayton.enums.Category;

@SpringBootApplication
public class CrudSpringApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrudSpringApplication.class, args);
  }

  @Bean
  CommandLineRunner initDatabase(CourseRepository courseRepository) {
    return args -> {
      courseRepository.deleteAll();
      Course c = new Course();
      c.setName("Angular com Spring");
      c.setCategory(Category.FRONT_END);
      Lesson l = new Lesson();
      l.setName("Introdução");
      l.setYoutubeUrl("deu certo");
      l.setCourse(c);
      c.getLessons().add(l);

      courseRepository.save(c);
    };
  }
}
