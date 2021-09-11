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

import br.com.fiap.cadastroTutor.model.Tutor;
import br.com.fiap.cadastroTutor.repository.TutorRepository;

@RestController
@RequestMapping("/api/tutor")
public class ApiTutorController {
	
	@Autowired
	private TutorRepository repository;
	
	@GetMapping()
	@Cacheable("tutor")
	public Page<Tutor> 
	index(@RequestParam(required = false) String name, 
			@PageableDefault(size=20) Pageable pageable) {
		
		
		if(name == null) 
			return repository.findAll(pageable);
		
		return repository.findByNameLike("%"+ name +"%", pageable);
	}
	
	@PostMapping()
	@CacheEvict(value = "tutor", allEntries = true)
	public ResponseEntity<Tutor> create(@RequestBody @Valid Tutor tutor, UriComponentsBuilder uriBuilder) {
		repository.save(tutor);
		URI uri = uriBuilder.path("/api/tutor/{id}").buildAndExpand(tutor.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("{id}") 
	public ResponseEntity<Tutor> get(@PathVariable Long id) {
		return ResponseEntity.of(repository.findById(id));
	}
	
	@DeleteMapping("{id}")
	@CacheEvict(value = "tutor", allEntries = true)
	public  ResponseEntity<Tutor> delete(@PathVariable Long id) {
		Optional<Tutor> tutor = repository.findById(id);
		
		if(tutor.isEmpty()) 
			return ResponseEntity.notFound().build() ;
		
		repository.deleteById(id);
		
		return ResponseEntity.ok().build() ;
		
	}
	
	@PutMapping("{id}")
	@CacheEvict(value = "tutor", allEntries = true)
	public ResponseEntity<Tutor> update(@RequestBody Tutor newtutor, @PathVariable Long id) {
		Optional<Tutor> optional = repository.findById(id);
		
		if(optional.isEmpty()) 
			return ResponseEntity.notFound().build() ;
		Tutor tutor = optional.get();
		tutor.setName(newtutor.getName());
		tutor.setEmail(newtutor.getEmail());
		tutor.setPass(newtutor.getPass());
		
		repository.save(tutor);
		
		return ResponseEntity.ok(tutor);
	} 

}
