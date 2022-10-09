package br.com.medina.escolaconectada.services;

import java.util.List;

import br.com.medina.escolaconectada.dto.BimestreMetricsDTO;
import br.com.medina.escolaconectada.dto.EscolaMetricsDTO;

public interface IMetricsServices {

	List<BimestreMetricsDTO> listMetricsBy(String salaId, Integer bimestreCod, Integer ano, Integer materiaCod);
	List<EscolaMetricsDTO> listEscolaMetricsBy(String escolaId, Integer bimestreCod, Integer ano, Integer materiaCod);
}
