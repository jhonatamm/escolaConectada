package br.com.medina.escolaconectada.services;

import java.util.List;

import br.com.medina.escolaconectada.models.Cidade;


public interface ICidadeService {
	
	public List<Cidade> findAll();

	public Cidade findCidadeById(String id);
	
	public Cidade findCidadeByName(String name);
	
	public Cidade insert(Cidade cidadeDTO);
	
	public Cidade update(Cidade cidadeDTO);	
	
	public void deleteCidadeById(String id);

}
