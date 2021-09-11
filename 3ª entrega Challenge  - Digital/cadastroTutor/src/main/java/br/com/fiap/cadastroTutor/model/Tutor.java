package br.com.fiap.cadastroTutor.model;

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
@Table(name="TUTOR")
@SequenceGenerator(name="tutor", sequenceName = "SQ_TUTOR", allocationSize = 1)
public class Tutor {
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutor")
	private Long Id;

	@NotBlank(message = "Campo Nome obrigatório")
	private String name;

	@NotBlank(message = "Insira um email válido")
	private String email;

	private String pass;


}
