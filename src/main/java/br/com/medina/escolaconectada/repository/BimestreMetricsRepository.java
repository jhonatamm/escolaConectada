package br.com.medina.escolaconectada.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.medina.escolaconectada.models.metrics.BimestreMetrics;

@Repository
public interface BimestreMetricsRepository extends MongoRepository<BimestreMetrics, String> {
	
	@Query("{ 'materia.id' : ?0 }")
	List<BimestreMetrics> findAllByMateriaId(String materiaId);
}
