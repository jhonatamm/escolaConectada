package br.com.medina.escolaconectada.services;

import java.util.List;

import br.com.medina.escolaconectada.models.Escola;
import br.com.medina.escolaconectada.models.Materia;

public interface IProcessMetrics {
	
	public Materia processBimestreMetrics(Materia materia);

	public Escola processEscolaMetrics(Escola escola, List<Materia> materias);
 
}
