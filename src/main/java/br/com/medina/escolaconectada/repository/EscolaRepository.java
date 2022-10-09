package br.com.medina.escolaconectada.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.medina.escolaconectada.models.Cidade;
import br.com.medina.escolaconectada.models.Escola;

@Repository
public interface EscolaRepository extends MongoRepository<Escola, String> {

	List<Escola> findByNome(String nome);
	List<Escola> findByCidade(Cidade cidade);
	
	@Query("{ 'processed' : ?0 }")
	List<Escola>  findAllUnProcessedsEscola(boolean unProcessed);
}
