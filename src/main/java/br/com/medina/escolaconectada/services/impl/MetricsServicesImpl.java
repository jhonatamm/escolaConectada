package br.com.medina.escolaconectada.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.medina.escolaconectada.exceptions.ResourceNotFoundException;
import br.com.medina.escolaconectada.models.Materia;
import br.com.medina.escolaconectada.models.Sala;
import br.com.medina.escolaconectada.models.metrics.BimestreMetrics;
import br.com.medina.escolaconectada.repository.BimestreMetricsRepository;
import br.com.medina.escolaconectada.repository.MateriaRepository;
import br.com.medina.escolaconectada.repository.SalaRepository;
import br.com.medina.escolaconectada.services.IMetricsServices;

@Service
public class MetricsServicesImpl implements IMetricsServices {
	
	private final SalaRepository salaRepository;
	private final MateriaRepository materiaRepository;
	private final BimestreMetricsRepository bimestreRepository;
	
	public MetricsServicesImpl(SalaRepository salaRepository, MateriaRepository materiaRepository,BimestreMetricsRepository bimestreRepository ) {
		this.salaRepository = salaRepository;
		this.materiaRepository = materiaRepository;
		this.bimestreRepository =  bimestreRepository;
	}

	@Override
	public List<BimestreMetrics> listMetricsBy(String salaId, Integer bimestreCod, Integer ano, Integer materiaCod) {
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
		
		return bimestresMetrics;
	}

}
