package br.com.medina.escolaconectada.services;

import java.util.List;

import br.com.medina.escolaconectada.dto.MateriaDTO;
import br.com.medina.escolaconectada.models.Materia;

public interface IMateriaService {

	Materia findMateriaBySalaAndId(String id, String salaId);

	List<MateriaDTO> insertMaterias(String salaId, List<MateriaDTO> list);

	List<MateriaDTO> findAllMateriasBySalaId(String salaId);

}
