package br.com.fiap.cadastroTutor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.cadastroTutor.model.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
	
	Page<Tutor> findByNameLike(String title, Pageable pageable);

}
