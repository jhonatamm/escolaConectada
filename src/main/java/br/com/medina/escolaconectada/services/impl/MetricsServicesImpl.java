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
	public List<BimestreMetricsDTO> listMetricsBy(String salaId, Integer bimestreCod, Integer ano, Integer materiaCod) {
		Sala sala = salaRepository.findById(salaId).orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada"));
		List<Materia> materias = materiaRepository.findBySala(sala);
		Materia exempleMateria = new Materia();
		exempleMateria.setProcessed(true);
		if(bimestreCod != null) materias = materias.stream().filter(materia -> materia.getBimestre().getId() == bimestreCod).collect(Collectors.toList());
		if(ano != null && ano < 1980 && ano > 2050 ) {
			throw new ResourceNotFoundException("Entre com um ano valido 1980 a 2050");
		}
		if(ano != null ) materias = materias.stream().filter(materia -> materia.getAno().equals(ano)).collect(Collectors.toList());
		if(materiaCod != null) materias = materias.stream().filter(materia -> materia.getCodMateria().getId() == materiaCod).collect(Collectors.toList());
		 
		
		List<BimestreMetrics> bimestresMetrics = new ArrayList<BimestreMetrics>();
		
		for(Materia materia: materias) {
			bimestresMetrics.addAll( bimestreRepository.findAllByMateriaId(materia.getId()));
		}
		
		List<BimestreMetricsDTO> bimestresMetricsDTO = bimestresMetrics.stream().map(b -> new BimestreMetricsDTO(b)).collect(Collectors.toList());
		
		return bimestresMetricsDTO;
	}

	//metodo pode ser melhorado. ex aplicar filtro na query.
	@Override
	public List<EscolaMetricsDTO> listEscolaMetricsBy(String escolaId, Integer bimestreCod, Integer ano,
			Integer materiaCod) {
		//Escola escola = escolaRepository.findById(escolaId).orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada"));
		if(ano != null && ano < 1980 && ano > 2050 ) {
			throw new ResourceNotFoundException("Entre com um ano valido 1980 a 2050");
		}
		List<EscolaMetrics> escolasMetrics = escolaMetricsRepository.findByEscolaIdAndYear(escolaId,ano);

		
		if(bimestreCod != null) {
			escolasMetrics = escolasMetrics.stream().filter(e -> e.getBimestreCod() == bimestreCod.intValue()).collect(Collectors.toList());
		}
		if(materiaCod != null) {
			escolasMetrics = escolasMetrics.stream().filter(e -> e.getMateriaCod() == materiaCod.intValue()).collect(Collectors.toList());
		}
		
		return escolasMetrics.stream().map(e -> new EscolaMetricsDTO(e)).collect(Collectors.toList());
	}

}
