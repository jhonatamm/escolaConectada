package br.com.medina.escolaconectada.services.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.medina.escolaconectada.models.Materia;
import br.com.medina.escolaconectada.models.metrics.BimestreMetrics;
import br.com.medina.escolaconectada.repository.BimestreMetricsRepository;
import br.com.medina.escolaconectada.services.IProcessMetrics;

@Service
public class ProcessMetricsImpl implements IProcessMetrics{
	Logger logger = LoggerFactory.getLogger(ProcessMetricsImpl.class);

	private final BimestreMetricsRepository bimestreRepository;
	public ProcessMetricsImpl(BimestreMetricsRepository bimestreRepository) {
		this.bimestreRepository = bimestreRepository;
	}
	
	@Override
	public Materia processBimestreMetrics(Materia materia) {
		try {
			BimestreMetrics bMetrics = materia.getBMetrics() != null ?  materia.getBMetrics() : new BimestreMetrics();
			Double notasMedia = materia.getAlunos().stream().map(a -> a.getNota() ).reduce(0.0, Double::sum) / materia.getAlunos().size();
			Double presenca = materia.getAlunos().stream().map(a -> a.getPresenca() ).reduce(0.0, Double::sum) / materia.getAlunos().size();
			Double avaliacao = (double) materia.getAlunos().stream().map(a -> a.getAvaliacao() ).reduce(0, Integer::sum) / materia.getAlunos().size();
			bMetrics.setDtUpdated(new Date());
			bMetrics.setMPresenca(presenca);
			bMetrics.setMAvaliacao(avaliacao);
			bMetrics.setMNota(notasMedia);
			bMetrics.setMateria(materia);
			bimestreRepository.save(bMetrics);
			materia.setBMetrics(bMetrics);
			materia.setProcessed(true);
		}catch (Exception e) {
			logger.error("Erro on processing metrics {}", e);
		}
		
		return materia;
	}

}
