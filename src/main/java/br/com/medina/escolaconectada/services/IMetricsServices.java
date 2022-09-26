package br.com.medina.escolaconectada.services;

import java.util.List;

import br.com.medina.escolaconectada.models.metrics.BimestreMetrics;

public interface IMetricsServices {

	List<BimestreMetrics> listMetricsBy(String salaId, Integer bimestreCod, Integer ano, Integer materiaCod);
}
