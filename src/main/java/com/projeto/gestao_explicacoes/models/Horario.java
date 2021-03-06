package com.projeto.gestao_explicacoes.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Horario extends BaseModel{

  private DayOfWeek diaSemana;
  @DateTimeFormat(pattern = "HH:mm")
  private LocalTime horaInicio;
  @DateTimeFormat(pattern = "HH:mm")
  private LocalTime horaFim;

  @ManyToOne
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  //@JsonBackReference
  @JsonIgnore
  private Explicador explicador; // adicionado em "Explicador"

  // ****** METHODS ******

  public Horario(DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFim) {
    this.diaSemana = diaSemana;
    this.horaInicio = horaInicio;
    this.horaFim = horaFim;
  }

  /**
   * Verifica se {@code horaInicio} é maior ou igual a {@code horaFim}
   * Verifica também se existem minutos diferentes de 0 (zero)
   *
   * @return {@code true} se válido ou {@code false} se inválida
   */
  public boolean isHoraInicioAndHoraFimValid() {
    if (this.horaInicio.getMinute() != 0 || this.horaFim.getMinute() != 0) {
      return false;
    }

    if (this.horaInicio.getHour() >= this.horaFim.getHour()) {
      return false;
    }
    return true;
  }

}