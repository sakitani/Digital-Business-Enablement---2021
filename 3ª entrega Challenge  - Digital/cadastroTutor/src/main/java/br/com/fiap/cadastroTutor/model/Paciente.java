package br.com.fiap.cadastroTutor.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "PACIENTE")
@SequenceGenerator(name = "paciente", sequenceName = "SQ_PACIENTE", allocationSize = 1)
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paciente")
	private Long Id;

	@NotBlank(message = "Campo Nome obrigatório")
	private String name;

	@NotBlank(message = "Insira informação válida")
	private String sexo;

	private Calendar dataNascimento;

}
