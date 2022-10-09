package br.com.medina.escolaconectada.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.medina.escolaconectada.models.metrics.EscolaMetrics;

@Repository
public interface EscolaMetricsRepository extends MongoRepository<EscolaMetrics, String> {

	@Query("{$and:[ {'ano' : ?0} , {'bimestreCod': ?1} , {'materiaCod': ?2 },{'escola.id':?3} ] }")
	List<EscolaMetrics> findByAnoEscolaBimestreValues(Integer ano, Integer bimestreCod, Integer materiaCod,
			String escolaId);

	@Query("{ 'escola.id' : ?0 }")
	List<EscolaMetrics> findByEscolaId(String escolaId);
	
	@Query("{$and:[ {'escola.id' : ?0} ,{'ano':?1} ] }")
	List<EscolaMetrics> findByEscolaIdAndYear(String escolaId,Integer ano);

}
