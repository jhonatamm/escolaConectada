package br.com.medina.escolaconectada.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.medina.escolaconectada.models.Materia;
import br.com.medina.escolaconectada.models.Sala;

@Repository
public interface MateriaRepository extends MongoRepository<Materia, String> {

	List<Materia> findBySala(Sala sala);
}
