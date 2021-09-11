package br.com.fiap.cadastroTutor.controller.api;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.cadastroTutor.model.Paciente;
import br.com.fiap.cadastroTutor.repository.PacienteRepository;


@RestController
@RequestMapping("/api/paciente")
public class ApiPacienteController {

	@Autowired
	private PacienteRepository repository;

	@GetMapping()
	@Cacheable("paciente")
	public Page<Paciente> index(@RequestParam(required = false) String name,
			@PageableDefault(size = 20) Pageable pageable) {

		if (name == null)
			return repository.findAll(pageable);

		return repository.findByNameLike("%" + name + "%", pageable);
	}

	@PostMapping()
	@CacheEvict(value = "paciente", allEntries = true)
	public ResponseEntity<Paciente> create(@RequestBody @Valid Paciente paciente, UriComponentsBuilder uriBuilder) {
		repository.save(paciente);
		URI uri = uriBuilder.path("/api/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping("{id}")
	public ResponseEntity<Paciente> get(@PathVariable Long id) {
		return ResponseEntity.of(repository.findById(id));
	}

	@DeleteMapping("{id}")
	@CacheEvict(value = "paciente", allEntries = true)
	public ResponseEntity<Paciente> delete(@PathVariable Long id) {
		Optional<Paciente> paciente = repository.findById(id);

		if (paciente.isEmpty())
			return ResponseEntity.notFound().build();

		repository.deleteById(id);

		return ResponseEntity.ok().build();

	}

	@PutMapping("{id}")
	@CacheEvict(value = "paciente", allEntries = true)
	public ResponseEntity<Paciente> update(@RequestBody Paciente newpaciente, @PathVariable Long id) {
		Optional<Paciente> optional = repository.findById(id);

		if (optional.isEmpty())
			return ResponseEntity.notFound().build();
		Paciente paciente = optional.get();
		paciente.setName(newpaciente.getName());
		paciente.setSexo(newpaciente.getSexo());
		paciente.setDataNascimento(newpaciente.getDataNascimento());

		repository.save(paciente);

		return ResponseEntity.ok(paciente);
	}

}
