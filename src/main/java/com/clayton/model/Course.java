package com.clayton.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.clayton.enums.Category;
import com.clayton.enums.Status;
import com.clayton.enums.converters.CategoryConverter;
import com.clayton.enums.converters.StatusConverter;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id = ?") // anotacao do hibernete q posibilita buscar
                                                                      // personalizado
@Where(clause = "status = 'Ativo'") // toda vez que for realizada uma consulta será utilizada essa condicao
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonProperty("_id")
  private Long id;

  @NotBlank
  @NotNull
  @Length(min = 5, max = 100)
  @Column(length = 100, nullable = false)
  private String name;

  @NotNull
  @Column(length = 10, nullable = false)
  @Convert(converter = CategoryConverter.class)
  private Category category;

  // nova coluna para conter o historico de Soft delete
  @NotNull
  @Column(length = 10, nullable = false)
  @Convert(converter = StatusConverter.class)
  private Status status = Status.ACTIVE; // sempre que criar um novo ja virar Ativo

  // declaracao de 1 para muito
  // https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course") // faz a ligacao com a classe Lesson, se for remover um
                                                              // curso tem que remover as lissoes
  //@JoinColumn(name = "course_id") // relacionamento 1 para muitos do lado contrario
  private List<Lesson> lessons = new ArrayList<>();

}
