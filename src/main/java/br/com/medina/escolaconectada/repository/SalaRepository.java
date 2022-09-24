package br.com.medina.escolaconectada.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.medina.escolaconectada.models.Escola;
import br.com.medina.escolaconectada.models.Sala;

@Repository
public interface SalaRepository extends MongoRepository<Sala, String> {

	List<Sala> findByEscola(Escola escola);
}
