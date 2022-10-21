package br.com.medina.escolaconectada.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.medina.escolaconectada.dto.BimestreMetricsDTO;
import br.com.medina.escolaconectada.dto.EscolaMetricsDTO;
import br.com.medina.escolaconectada.exceptions.ResourceNotFoundException;
import br.com.medina.escolaconectada.models.Materia;
import br.com.medina.escolaconectada.models.Sala;
import br.com.medina.escolaconectada.models.metrics.BimestreMetrics;
import br.com.medina.escolaconectada.models.metrics.EscolaMetrics;
import br.com.medina.escolaconectada.repository.BimestreMetricsRepository;
import br.com.medina.escolaconectada.repository.EscolaMetricsRepository;
import br.com.medina.escolaconectada.repository.EscolaRepository;
import br.com.medina.escolaconectada.repository.MateriaRepository;
import br.com.medina.escolaconectada.repository.SalaRepository;
import br.com.medina.escolaconectada.services.IMetricsServices;

@Service
public class MetricsServicesImpl implements IMetricsServices {
	
	@SuppressWarnings("unused")
	private final EscolaRepository escolaRepository;
	private final SalaRepository salaRepository;
	private final MateriaRepository materiaRepository;
	private final BimestreMetricsRepository bimestreRepository;
	private final EscolaMetricsRepository escolaMetricsRepository;
	
	public MetricsServicesImpl(SalaRepository salaRepository, MateriaRepository materiaRepository,BimestreMetricsRepository bimestreRepository ,EscolaRepository escolaRepository, EscolaMetricsRepository escolaMetricsRepository) {
		this.salaRepository = salaRepository;
		this.materiaRepository = materiaRepository;
		this.bimestreRepository =  bimestreRepository;
		this.escolaRepository = escolaRepository;
		this.escolaMetricsRepository = escolaMetricsRepository;
	}

	@Override
	public List<BimestreMetricsDTO> listMetricsBy(List<String> escolaIds,List<Integer> series, Integer ano, List<Integer> materiaCods) {
		//Sala sala = salaRepository.findById(salaId).orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada"));
		
		if(series.isEmpty()) {
			throw new IllegalArgumentException("envie o valor da serie");
		}
		
		
		List<Sala> salas = new ArrayList<Sala>();
		for(String escola: escolaIds) {
			for(Integer serie: series) {
				salas.addAll(salaRepository.findBySerieEscola(escola, serie));
			}
		}
		if(salas.isEmpty()) throw new ResourceNotFoundException("Salas não encontradas");
		
		List<Materia> materias  = new ArrayList<Materia>();
		for(Sala sala: salas) {
			materias.addAll(materiaRepository.findBySala(sala));
		}
		if(materias.isEmpty()) throw new ResourceNotFoundException("Materias não encontrada");
;
		//if(bimestreCod != null) materias = materias.stream().filter(materia -> materia.getBimestre().getId() == bimestreCod).collect(Collectors.toList());
		if(ano != null && ano < 1980 && ano > 2050 ) {
			throw new IllegalArgumentException("Entre com um ano valido 1980 a 2050");
		}
		
		if(ano != null ) materias = materias.stream().filter(materia -> materia.getAno().equals(ano)).collect(Collectors.toList());
		
		if(materiaCods != null) {
			for(Integer materiaCod:materiaCods ) {
				if(materiaCod != null) materias = materias.stream().filter(materia -> materia.getCodMateria().getId() == materiaCod).collect(Collectors.toList());
			}
		}

		
		 
		
		List<BimestreMetrics> bimestresMetrics = new ArrayList<BimestreMetrics>();
		
		for(Materia materia: materias) {
			bimestresMetrics.addAll( bimestreRepository.findAllByMateriaId(materia.getId()));
		}
		
		List<BimestreMetricsDTO> bimestresMetricsDTO = bimestresMetrics.stream().map(b -> new BimestreMetricsDTO(b)).collect(Collectors.toList());
		
		return bimestresMetricsDTO;
	}

	//metodo pode ser melhorado. ex aplicar filtro na query.
	@Override
	public List<EscolaMetricsDTO> listEscolaMetricsBy(List<String> escolaIds, Integer bimestreCod, Integer ano,
			List<Integer> materiaCods) {
		//Escola escola = escolaRepository.findById(escolaId).orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada"));
		if(ano != null && ano < 1980 && ano > 2050 ) {
			throw new ResourceNotFoundException("Entre com um ano valido 1980 a 2050");
		}
		List<EscolaMetrics> escolasMetrics = new ArrayList<EscolaMetrics>();
		for(String escolaId: escolaIds) {
			escolasMetrics.addAll(escolaMetricsRepository.findByEscolaIdAndYear(escolaId,ano));
		}
		
		if(bimestreCod != null) {
			escolasMetrics = escolasMetrics.stream().filter(e -> e.getBimestreCod() == bimestreCod.intValue()).collect(Collectors.toList());
		}
		if(materiaCods != null && materiaCods.size() > 0) {
			List<EscolaMetrics> escolasMetricsLocal = new ArrayList<EscolaMetrics>();
			for(Integer materiaCod : materiaCods) {
				escolasMetricsLocal.addAll(escolasMetrics.stream().filter(e -> e.getMateriaCod() == materiaCod.intValue()).collect(Collectors.toList()));
			}
			escolasMetrics = escolasMetricsLocal;
		}
		
		return escolasMetrics.stream().map(e -> new EscolaMetricsDTO(e)).collect(Collectors.toList());
	}

}
