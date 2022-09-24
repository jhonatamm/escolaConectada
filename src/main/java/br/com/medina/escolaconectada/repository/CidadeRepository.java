package br.com.medina.escolaconectada.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.medina.escolaconectada.models.Cidade;

@Repository
public interface CidadeRepository extends MongoRepository<Cidade, String> {

	List<Cidade> findByNome(String nome);
}
