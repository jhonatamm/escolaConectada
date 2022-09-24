package br.com.medina.escolaconectada.services;

import java.util.List;

import br.com.medina.escolaconectada.dto.SalaDTO;


public interface ISalaService {
	
	public List<SalaDTO> findAll(String escolaId);

	public List<SalaDTO> InsertSalas(List<SalaDTO> list);
	
	public SalaDTO insert(SalaDTO salaDTO);
	
	public void DeleteSalasById(List<String> idsSalas);
	

}
