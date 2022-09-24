package br.com.medina.escolaconectada.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.medina.escolaconectada.dto.EscolaDTO;
import br.com.medina.escolaconectada.exceptions.ResourceNotFoundException;
import br.com.medina.escolaconectada.models.Cidade;
import br.com.medina.escolaconectada.models.Escola;
import br.com.medina.escolaconectada.repository.EscolaRepository;
import br.com.medina.escolaconectada.services.ICidadeService;
import br.com.medina.escolaconectada.services.IEscolaService;

@Service
public class EscolaServiceImpl implements IEscolaService {
	
	private final EscolaRepository escolaRepository;
	private final ICidadeService cidadeService;
	
	public EscolaServiceImpl(EscolaRepository escolaRepository,ICidadeService cidadeService ) {
		this.escolaRepository = escolaRepository;
		this.cidadeService = cidadeService;
	}

	@Override
	public List<EscolaDTO> findAll(String cidadeId) {
		Cidade cidade = cidadeService.findCidadeById(cidadeId);
		return escolaRepository.findByCidade(cidade).stream().map(EscolaDTO::new).collect(Collectors.toList());
	}

	@Override
	public List<EscolaDTO> InsertEscolas(List<EscolaDTO> list) {
		List<Escola> escolas = list.stream().map( escola -> escolaFromDTO(escola) ).collect(Collectors.toList());
		escolas = escolaRepository.saveAll(escolas);
		return escolas.stream().map(EscolaDTO::new).collect(Collectors.toList());
	}

	@Override
	public void DeleteEscolasById(List<String> idsEscolas) {
		idsEscolas.forEach(escola -> escolaRepository.deleteById(escola) );
	}
	
	@Override
	public Escola findById(String escolaId) {
		return escolaRepository.findById(escolaId).orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada"));
	}
	
	public Escola escolaFromDTO(EscolaDTO escolaDTO) {
		Escola escola = new Escola();
		if(escolaDTO.getId() != null) escola = escolaRepository.findById(escolaDTO.getId()).get();
		escola.setNome(escolaDTO.getNome());
		if(escolaDTO.getCidadeId() != null) escola.setCidade(cidadeService.findCidadeById(escolaDTO.getCidadeId()));
		return escola;

	}
}
