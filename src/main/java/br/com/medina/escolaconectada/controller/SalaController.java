package br.com.medina.escolaconectada.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.medina.escolaconectada.dto.AlunoMateriaDTO;
import br.com.medina.escolaconectada.dto.MateriaDTO;
import br.com.medina.escolaconectada.dto.SalaDTO;
import br.com.medina.escolaconectada.services.IAlunoService;
import br.com.medina.escolaconectada.services.IMateriaService;
import br.com.medina.escolaconectada.services.ISalaService;

@RestController
@RequestMapping("/api/salas")
public class SalaController {
	Logger logger = LoggerFactory.getLogger(SalaController.class);
	
	ISalaService  salaService;
	IMateriaService materiaService;
	IAlunoService alunoService;
	public SalaController(ISalaService  salaService,IMateriaService materiaService, IAlunoService alunoService ) {
		this.salaService = salaService;
		this.materiaService = materiaService;
		this.alunoService = alunoService;
	}
	
	@GetMapping
	public ResponseEntity<List<SalaDTO>> getAllById(@RequestParam(name = "escolaID", required = true, defaultValue = "") String escolaID) {
		logger.info("Buscando todas as salas by escola");
		if(escolaID.isEmpty()) {
			throw new IllegalArgumentException("id da escola requerido");
		}
		return ResponseEntity.ok(salaService.findAll(escolaID));
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<SalaDTO>> insertAllSala(@RequestBody List<SalaDTO> salasDTO) {
		logger.info("Inserindo todas as salas");
		return ResponseEntity.ok(salaService.InsertSalas(salasDTO));
	}
	
	@PostMapping(value = "/sala", produces = "application/json", consumes = "application/json")
	public ResponseEntity<SalaDTO> insertSala(@RequestBody SalaDTO salaDTO) {
		logger.info("Inserindo todas as salas");
		return ResponseEntity.ok(salaService.insert(salaDTO));
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody List<String> idsEscolas){
		logger.info("deletendo todas as salas");
		salaService.DeleteSalasById(idsEscolas);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{salaId}")
	public ResponseEntity<List<MateriaDTO>> getAllMateriasBySalaId(@PathVariable String salaId) {
		logger.info("Buscando todas as materias by sala");
		if(salaId.isEmpty()) {
			throw new IllegalArgumentException("id da sala requerido");
		}
		
		return ResponseEntity.ok(materiaService.findAllMateriasBySalaId(salaId));
	}
	
	@PostMapping(value = "/{salaId}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<MateriaDTO>> insertAllMateriasBySala(@PathVariable String salaId, @RequestBody List<MateriaDTO> materiasDTO) {
		logger.info("Inserindo todas as materias salas");
		return ResponseEntity.ok(materiaService.insertMaterias(salaId, materiasDTO));
	}
	
	@GetMapping(value = "/{salaId}/{materiaId}")
	public ResponseEntity<List<AlunoMateriaDTO>> getAllAlunosByMateriasBySalaId(@PathVariable String salaId, @PathVariable String materiaId ) {
		logger.info("Buscando todos os alunos");
		if(salaId.isEmpty()) {
			throw new IllegalArgumentException("id da sala requerido");
		}
		
		return ResponseEntity.ok(alunoService.listAlunosByMateria(materiaId, salaId));
	}
	
	@PostMapping(value = "/{salaId}/{materiaId}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<AlunoMateriaDTO>> insertAllAlunosByMateriasBySala(@PathVariable String salaId, @PathVariable String materiaId, @RequestBody List<AlunoMateriaDTO> alunos) {
		logger.info("Inserindo todas os alunos na  materias salas");
		return ResponseEntity.ok(alunoService.insertAlunos(alunos, materiaId, salaId));
	}
	

}
