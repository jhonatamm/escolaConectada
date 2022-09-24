package br.com.medina.escolaconectada.services;

import java.util.List;

import br.com.medina.escolaconectada.dto.EscolaDTO;
import br.com.medina.escolaconectada.models.Escola;


public interface IEscolaService {
	
	public List<EscolaDTO> findAll(String cidadeId);
	
	public Escola findById(String escolaId);

	public List<EscolaDTO> InsertEscolas(List<EscolaDTO> list);
	
	public void DeleteEscolasById(List<String> idsEscolas);
	

}
