package br.com.medina.escolaconectada.jobs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.medina.escolaconectada.models.Materia;
import br.com.medina.escolaconectada.repository.MateriaRepository;
import br.com.medina.escolaconectada.services.IProcessMetrics;

@Configuration
@EnableScheduling
public class ProcessMetricsJob {
	Logger logger = LoggerFactory.getLogger(ProcessMetricsJob.class);
	
	private MateriaRepository materiaRepository;
	private IProcessMetrics processMetrics;
	private static final Object executingLocker = new Object();
	private static volatile boolean executing = false;
	
	public ProcessMetricsJob(MateriaRepository materiaRepository,IProcessMetrics processMetrics) {
		this.materiaRepository = materiaRepository;
		this.processMetrics = processMetrics;
	}
	
	@Scheduled(fixedDelay = 60000)
	public void processBimestreMetrics() {
		try {
			synchronized (executingLocker) {
				if (executing) {
					logger.info("Job is Busy");
					return;
				}
				executing = true;
				while(executing) {
					
					List<Materia> materias = materiaRepository.findAllUnProcessedsMaterias(false);
					if(materias.size() <= 0) {
						executing = false;
						continue;
					}
					logger.info("init mounting metrics");
					for(Materia materia : materias ) {
						materia = processMetrics.processBimestreMetrics(materia);
						materiaRepository.save(materia);
					}
					logger.info("end mounting metrics");
					executing = false;
				}
				logger.info("Job processBimestreMetrics is idle");
			}
		}catch (Exception e) {
			logger.error("error on mounting metrics {}", e);
		}

	}

}
