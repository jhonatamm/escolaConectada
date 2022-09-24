package br.com.medina.escolaconectada.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.medina.escolaconectada.dto.EscolaDTO;
import br.com.medina.escolaconectada.services.IEscolaService;

@RestController
@RequestMapping("/api/escola")
public class EscolaController {
	Logger logger = LoggerFactory.getLogger(EscolaController.class);
	
	IEscolaService escolaService;
	public EscolaController(IEscolaService  escolaService) {
		this.escolaService = escolaService;
	}
	
	@GetMapping
	public ResponseEntity<List<EscolaDTO>> getAllById(@RequestParam(name = "cidadeId", required = true, defaultValue = "") String cidadeId) {
		logger.info("Buscando todas as escolas");
		if(cidadeId.isEmpty()) {
			throw new IllegalArgumentException("id da cidade requerido");
		}
		return ResponseEntity.ok(escolaService.findAll(cidadeId));
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<EscolaDTO>> insertAllEscola(@RequestBody List<EscolaDTO> escolasDTO) {
		logger.info("Inserindo todas as escolas");
		return ResponseEntity.ok(escolaService.InsertEscolas(escolasDTO));
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody List<String> idsEscolas){
		logger.info("deletendo todas as escolas");
		escolaService.DeleteEscolasById(idsEscolas);
		return ResponseEntity.noContent().build();
	}

}
