package br.com.medina.escolaconectada.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.medina.escolaconectada.models.Escola;
import br.com.medina.escolaconectada.models.Materia;
import br.com.medina.escolaconectada.models.enums.BimestreEnum;
import br.com.medina.escolaconectada.models.enums.MateriaEnum;
import br.com.medina.escolaconectada.models.metrics.BimestreMetrics;
import br.com.medina.escolaconectada.models.metrics.EscolaMetrics;
import br.com.medina.escolaconectada.repository.BimestreMetricsRepository;
import br.com.medina.escolaconectada.repository.EscolaMetricsRepository;
import br.com.medina.escolaconectada.services.IProcessMetrics;

@Service
public class ProcessMetricsImpl implements IProcessMetrics{
	Logger logger = LoggerFactory.getLogger(ProcessMetricsImpl.class);

	private final BimestreMetricsRepository bimestreRepository;
	private final EscolaMetricsRepository escolaMetricsRepository;
	public ProcessMetricsImpl(BimestreMetricsRepository bimestreRepository, EscolaMetricsRepository escolaMetricsRepository) {
		this.bimestreRepository = bimestreRepository;
		this.escolaMetricsRepository = escolaMetricsRepository;
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
	
	@Override
	public Escola processEscolaMetrics(Escola escola, List<Materia> materias) {
		
		try {
			System.out.println("escola "+ escola.getId());
			List<Integer> anos = materias.stream().map(m -> m.getAno()).collect(Collectors.toList());
			List<BimestreEnum> bimestre = new ArrayList<BimestreEnum>(EnumSet.allOf(BimestreEnum.class));
			List<MateriaEnum> materiasCod = new ArrayList<MateriaEnum>(EnumSet.allOf(MateriaEnum.class));
			List<EscolaMetrics> escolasMetricsToCount = new ArrayList<EscolaMetrics>();
			for(Integer ano: anos) {
				for(BimestreEnum bEnum: bimestre) {
					for(MateriaEnum mEnum: materiasCod) {
						List<Materia> localMateria = materias.stream().filter(m -> m.getBimestre().getId() == bEnum.getId() && m.getCodMateria().getId() == mEnum.getId() && m.getAno().equals(ano) ).collect(Collectors.toList());
						if(localMateria.size() == 0) {
							continue;
						}
						List<EscolaMetrics> escolasMetrics = escolaMetricsRepository.findByAnoEscolaBimestreValues(ano, bEnum.getId(), mEnum.getId(), escola.getId());
						EscolaMetrics eMetrics = new EscolaMetrics();
						Double notasMedia = 0.0;
						Double avaliacao = 0.0;
						Double presenca = 0.0;
						if(!escolasMetrics.isEmpty()) {
							eMetrics = escolasMetrics.get(0);
						}
						eMetrics.setAno(ano);
						eMetrics.setBimestreCod(bEnum.getId());
						eMetrics.setAnoBimestre((ano +"-"+ bEnum.getId()));
						eMetrics.setBimestreName(bEnum.getDescription());
						eMetrics.setEscola(escola);
						eMetrics.setMateriaCod(mEnum.getId());
						eMetrics.setMateriaDescription(mEnum.getDescription());

						
						for(Materia materia:localMateria) {
							if(materia.getAlunos().size() > 0 ) {
								
								notasMedia += materia.getAlunos().stream().map(a -> a.getNota() ).reduce(0.0, Double::sum) / materia.getAlunos().size();
								presenca += materia.getAlunos().stream().map(a -> a.getPresenca() ).reduce(0.0, Double::sum) / materia.getAlunos().size();
								avaliacao += (double) materia.getAlunos().stream().map(a -> a.getAvaliacao() ).reduce(0, Integer::sum) / materia.getAlunos().size();
							}
						}
						notasMedia = notasMedia / localMateria.size();
						presenca = presenca/ localMateria.size();
						avaliacao = avaliacao / localMateria.size();
						eMetrics.setMNota(notasMedia);
						eMetrics.setMPresenca(presenca);
						eMetrics.setMAvaliacao(avaliacao);
						eMetrics.setDtUpdated(new Date());
						escolasMetricsToCount.add(eMetrics);
						escolaMetricsRepository.save(eMetrics);
					}
				}
				escolasMetricsToCount = escolaMetricsRepository.findByEscolaIdAndYear(escola.getId(), ano);
				Double notasMedia = 0.0;
				Double avaliacao = 0.0;
				Double presenca = 0.0;
				notasMedia = escolasMetricsToCount.stream().map(e -> e.getMNota()).reduce(0.0, Double::sum) / escolasMetricsToCount.size();
				avaliacao = escolasMetricsToCount.stream().map(e -> e.getMAvaliacao()).reduce(0.0, Double::sum) / escolasMetricsToCount.size();
				presenca = escolasMetricsToCount.stream().map(e -> e.getMPresenca()).reduce(0.0, Double::sum) / escolasMetricsToCount.size();
				Map<String, Double> escolaMetrics = new HashMap<String,Double>();
				escolaMetrics.put("mediaNota", notasMedia);
				escolaMetrics.put("mediaAvaliacao", avaliacao);
				escolaMetrics.put("mediapresenca", presenca);
				Map<Integer,Map<String, Double>> mapMediasGerais = escola.getEscolaGeneralMetrics() != null ? escola.getEscolaGeneralMetrics() : new LinkedHashMap<Integer, Map<String,Double>>();
				mapMediasGerais.put(ano, escolaMetrics);
				escola.setEscolaGeneralMetrics(mapMediasGerais);
			}

			
		}catch (Exception e) {
			logger.error("Erro on processing metrics escolas {}", e);
		}
		return escola;
		
	}

}
