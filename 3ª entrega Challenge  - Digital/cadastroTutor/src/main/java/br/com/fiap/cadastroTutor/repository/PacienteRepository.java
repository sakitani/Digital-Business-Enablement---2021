package br.com.fiap.cadastroTutor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.cadastroTutor.model.Paciente;
import br.com.fiap.cadastroTutor.model.Tutor;


public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	Page<Paciente> findByNameLike(String title, Pageable pageable);

}
