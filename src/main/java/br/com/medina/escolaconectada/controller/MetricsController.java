package br.com.medina.escolaconectada.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.medina.escolaconectada.dto.BimestreMetricsDTO;
import br.com.medina.escolaconectada.dto.EscolaMetricsDTO;
import br.com.medina.escolaconectada.services.IMetricsServices;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController  {
	Logger logger = LoggerFactory.getLogger(MetricsController.class);
	
	private IMetricsServices metricsService;
	public MetricsController(IMetricsServices metricsService) {
		this.metricsService = metricsService;
	}
	
	
	@GetMapping(value = "/sala")
	public ResponseEntity<List<BimestreMetricsDTO>> getAllById(@RequestParam(name = "salaId", required = true, defaultValue = "") String salaId,
			@RequestParam(name = "bimestreCod", required = false) Integer bimestreCod,
			@RequestParam(name = "ano", required = false ) Integer ano,
			@RequestParam(name = "materiaCod", required = false) Integer materiaCod
			
			) {
		logger.info("Buscando metricas do bimestre");
		if(salaId.isEmpty()) {
			throw new IllegalArgumentException("id da Sala requerido");
		}
		return ResponseEntity.ok(metricsService.listMetricsBy(salaId, bimestreCod, ano, materiaCod));
	}
	
	@GetMapping(value = "/escola")
	public ResponseEntity<List<EscolaMetricsDTO>> getAllByEscola(@RequestParam(name = "escolaId", required = true, defaultValue = "") String escolaId,
			@RequestParam(name = "bimestreCod", required = false) Integer bimestreCod,
			@RequestParam(name = "ano", required = false ) Integer ano,
			@RequestParam(name = "materiaCod", required = false) Integer materiaCod
			
			) {
		logger.info("Buscando metricas do bimestre");
		if(escolaId.isEmpty()) {
			throw new IllegalArgumentException("id da Escola requerido");
		}
		return ResponseEntity.ok(metricsService.listEscolaMetricsBy(escolaId, bimestreCod, ano, materiaCod));
	}

}
