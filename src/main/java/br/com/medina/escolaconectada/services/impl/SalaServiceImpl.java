package br.com.medina.escolaconectada.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.medina.escolaconectada.dto.AlunoMateriaDTO;
import br.com.medina.escolaconectada.dto.MateriaDTO;
import br.com.medina.escolaconectada.dto.SalaDTO;
import br.com.medina.escolaconectada.exceptions.ResourceNotFoundException;
import br.com.medina.escolaconectada.models.Aluno;
import br.com.medina.escolaconectada.models.Escola;
import br.com.medina.escolaconectada.models.Materia;
import br.com.medina.escolaconectada.models.Sala;
import br.com.medina.escolaconectada.models.enums.BimestreEnum;
import br.com.medina.escolaconectada.models.enums.MateriaEnum;
import br.com.medina.escolaconectada.models.enums.SerieEnum;
import br.com.medina.escolaconectada.repository.MateriaRepository;
import br.com.medina.escolaconectada.repository.SalaRepository;
import br.com.medina.escolaconectada.services.IAlunoService;
import br.com.medina.escolaconectada.services.IEscolaService;
import br.com.medina.escolaconectada.services.IMateriaService;
import br.com.medina.escolaconectada.services.ISalaService;

@Service
public class SalaServiceImpl implements ISalaService,IMateriaService,IAlunoService {
	
	private final IEscolaService escolaService;
	private final SalaRepository salaRepository;
	private final MateriaRepository materiaRepository;
	
	public SalaServiceImpl( IEscolaService escolaservice,SalaRepository salaRepository, MateriaRepository materiaRepository ) {
		this.escolaService = escolaservice;
		this.salaRepository = salaRepository;
		this.materiaRepository = materiaRepository;
	}

	@Override
	public List<SalaDTO> findAll(String escolaId) {
		Escola escola = escolaService.findById(escolaId);
		return salaRepository.findByEscola(escola).stream().map(SalaDTO::new).collect(Collectors.toList());
	}

	@Override
	public void DeleteSalasById(List<String> idsSalas) {
		idsSalas.forEach(sala -> salaRepository.deleteById(sala) );
	}
	
	public Sala findById( String id) {
		return salaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada"));
	}
	
	private Sala salaFromDTO(SalaDTO salaDTO) {
		Sala sala = new Sala();
		if(salaDTO.getId() != null) sala = salaRepository.findById(salaDTO.getId()).get();
		sala.setNome(salaDTO.getNome());
		if(salaDTO.getEscolaId() != null) sala.setEscola(escolaService.findById(salaDTO.getEscolaId()));
		sala.setSerie(SerieEnum.toEnum(salaDTO.getSerie()));
		return sala;

	}

	@Override
	public List<SalaDTO> InsertSalas(List<SalaDTO> list) {
		List<Sala> salas = list.stream().map( sala -> salaFromDTO(sala) ).collect(Collectors.toList());
		salas = salaRepository.saveAll(salas);
		return salas.stream().map(SalaDTO::new).collect(Collectors.toList());
	}

	@Override
	public SalaDTO insert(SalaDTO salaDTO) {
		Sala sala = salaFromDTO(salaDTO);
		return new SalaDTO(salaRepository.insert(sala));
	}
	
	//private Materia findMateriById(String id) {
	//	Materia materia = materiaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Materia não encontrada"));
	//	return materia;
	//}
	
	@Override
	public Materia findMateriaBySalaAndId(String id, String salaId) {
		Materia materia = materiaRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Materia não encontrada"));
		if(!salaId.equalsIgnoreCase(materia.getSala().getId())) throw new ResourceNotFoundException("Sala incorreta");
		return materia;
	}
	
	@Override
	public List<MateriaDTO> insertMaterias(String salaId, List<MateriaDTO> list) {
		Sala sala = findById(salaId);
		List<Materia> materias = list.stream().map(m -> materiaFromDTO(m,sala) ).collect(Collectors.toList());
		materias = materiaRepository.saveAll(materias);
		return materias.stream().map(MateriaDTO::new).collect(Collectors.toList());
	}
	
	@Override
	public List<MateriaDTO> findAllMateriasBySalaId(String salaId){
		Sala  sala = findById(salaId);
		return materiaRepository.findBySala(sala).stream().map(MateriaDTO::new).collect(Collectors.toList());
	}
	
	private Materia materiaFromDTO(MateriaDTO materiaDTO, Sala sala) {
		Materia materia = new Materia();
		if(materiaDTO.getId() != null) materia = findMateriaBySalaAndId(materiaDTO.getId(), materiaDTO.getSalaId());
		materia.setTotalAulas(materiaDTO.getTotalAulas());
		materia.setCodMateria(MateriaEnum.toEnum(materiaDTO.getCodMateria()));
		materia.setBimestre(BimestreEnum.toEnum(materiaDTO.getBimestre()));
		materia.setAno(materiaDTO.getAno());
		materia.setSala(sala);
		return materia;
	}
	

	@Override
	public List<AlunoMateriaDTO> insertAlunos(List<AlunoMateriaDTO> alunos, String materiaId, String salaId ){
		Materia materia = findMateriaBySalaAndId(materiaId,salaId );
		List<Aluno> list = alunos.stream().map(aluno -> alunoFromDTO(aluno)).collect(Collectors.toList());
		materia.setAlunos(list);
		materia = materiaRepository.save(materia);
		alunos = list.stream().map(aluno -> new AlunoMateriaDTO(aluno, findMateriaBySalaAndId(materiaId,salaId ))).collect(Collectors.toList());
		return alunos;
		
	}
	
	@Override
	public List<AlunoMateriaDTO> listAlunosByMateria(String materiaId, String salaId ){
		Materia materia = findMateriaBySalaAndId(materiaId,salaId );
		List<AlunoMateriaDTO> alunos = materia.getAlunos().stream().map(aluno -> new AlunoMateriaDTO(aluno, findMateriaBySalaAndId(materiaId,salaId ))).collect(Collectors.toList());
		return alunos;
		
	}
	
	private Aluno alunoFromDTO(AlunoMateriaDTO alunoDTO) {
		Aluno aluno = new Aluno();
		//aluno.setId(alunoDTO.getId());
		aluno.setAvaliacao(alunoDTO.getAvaliacao());
		aluno.setNota(alunoDTO.getNota());
		aluno.setPresenca(alunoDTO.getPresenca());
		return aluno;
	}

}
