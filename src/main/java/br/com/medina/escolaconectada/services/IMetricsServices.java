package br.com.medina.escolaconectada.services;

import java.util.List;

import br.com.medina.escolaconectada.dto.BimestreMetricsDTO;
import br.com.medina.escolaconectada.dto.EscolaMetricsDTO;

public interface IMetricsServices {

	List<BimestreMetricsDTO> listMetricsBy(List<String> escolaIds,List<Integer> seriesIds, Integer ano, List<Integer> materiaCods);
	List<EscolaMetricsDTO> listEscolaMetricsBy(List<String> escolaId, Integer bimestreCod, Integer ano, List<Integer> materiaCods);
}
