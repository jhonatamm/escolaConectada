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
	public ResponseEntity<List<BimestreMetricsDTO>> getAllById(@RequestParam(name = "escolaIds", required = true, defaultValue = "") List<String> escolaIds,
			@RequestParam(name = "seriesIds", required = false) List<Integer> seriesIds,
			@RequestParam(name = "ano", required = false ) Integer ano,
			@RequestParam(name = "materiaCods", required = false) List<Integer> materiaCods
			
			) {
		logger.info("Buscando metricas do bimestre");
		if(escolaIds.isEmpty()) {
			throw new IllegalArgumentException("id da Sala requerido");
		}
		return ResponseEntity.ok(metricsService.listMetricsBy(escolaIds, seriesIds, ano, materiaCods));
	}
	
	@GetMapping(value = "/escola")
	public ResponseEntity<List<EscolaMetricsDTO>> getAllByEscola(@RequestParam(name = "escolaIds", required = true, defaultValue = "") List<String> escolaIds,
			@RequestParam(name = "bimestreCod", required = false) Integer bimestreCod,
			@RequestParam(name = "ano", required = false ) Integer ano,
			@RequestParam(name = "materiaCods", required = false) List<Integer> materiaCods
			
			) {
		logger.info("Buscando metricas do bimestre");
		if(escolaIds.isEmpty()) {
			throw new IllegalArgumentException("id da Escola requerido");
		}
		return ResponseEntity.ok(metricsService.listEscolaMetricsBy(escolaIds, bimestreCod, ano, materiaCods));
	}

}
