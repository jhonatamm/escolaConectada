package br.com.medina.escolaconectada.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.medina.escolaconectada.exceptions.ResourceNotFoundException;
import br.com.medina.escolaconectada.models.Cidade;
import br.com.medina.escolaconectada.repository.CidadeRepository;
import br.com.medina.escolaconectada.services.ICidadeService;

@Service
public class CidadeServiceImpl implements ICidadeService {

	private final CidadeRepository cidadeRepository;
	
	
	public CidadeServiceImpl(CidadeRepository cidadeRepository) {
		this.cidadeRepository = cidadeRepository;

	}
	
	@Override
	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}

	@Override
	public Cidade findCidadeById(String id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada"));
	}

	@Override
	public Cidade findCidadeByName(String nome) {
		List<Cidade> cidades = cidadeRepository.findByNome(nome);
		
		return cidades.isEmpty() ? null : cidades.get(0);
	}

	@Override
	public Cidade insert(Cidade cidade) {
		return cidadeRepository.save(cidade);
	}

	@Override
	public Cidade update(Cidade cidade) {
		// TODO Auto-generated method stub
		return cidadeRepository.save(cidade);
	}

	@Override
	public void deleteCidadeById(String id) {
		Cidade cidade = findCidadeById(id); 
		cidadeRepository.delete(cidade);
		
	}

}
