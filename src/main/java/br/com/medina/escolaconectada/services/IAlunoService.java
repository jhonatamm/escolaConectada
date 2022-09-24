package br.com.medina.escolaconectada.services;

import java.util.List;

import br.com.medina.escolaconectada.dto.AlunoMateriaDTO;

public interface IAlunoService {

	List<AlunoMateriaDTO> insertAlunos(List<AlunoMateriaDTO> alunos, String materiaId, String salaId);

	List<AlunoMateriaDTO> listAlunosByMateria(String materiaId, String salaId);

}
