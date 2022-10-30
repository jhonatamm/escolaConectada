package br.com.medina.escolaconectada.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.medina.escolaconectada.models.Cidade;
import br.com.medina.escolaconectada.services.ICidadeService;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {
	
	Logger logger = LoggerFactory.getLogger(CidadeController.class);
	
	ICidadeService cidadeService;
	public CidadeController(ICidadeService cidadeService) {
		this.cidadeService = cidadeService;
	}
	
	@GetMapping
	public ResponseEntity<List<Cidade>> getAll() {
		logger.info("Buscando todas as cidades");
		return ResponseEntity.ok(cidadeService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cidade> findById(
			@PathVariable(name = "id", required = true) String id) {
		logger.info("Encontrando cidade pelo id: " + id);
		return ResponseEntity.ok().body(cidadeService.findCidadeById(id));
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<Cidade> findByNome(
			@PathVariable(name = "nome", required = true) String nome) {
		logger.info("Encontrando cidade pelo id: " + nome);
		return ResponseEntity.ok().body(cidadeService.findCidadeByName(nome));
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<Cidade> create(@RequestBody Cidade cidade) {
		logger.info("Criando cliente");
		if (cidade == null || cidade.getNome().isEmpty()) {
			throw new IllegalArgumentException("Nome necessario");
		}
		Cidade newCidade = cidadeService.update(cidade);
		//URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCidade.getId()).toUri();
		//logger.info("Criando cliente URL: " + uri);
		return ResponseEntity.ok(newCidade);
	}

	
	
}
