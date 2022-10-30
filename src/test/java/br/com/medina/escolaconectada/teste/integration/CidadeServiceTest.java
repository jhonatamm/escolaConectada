package br.com.medina.escolaconectada.teste.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import br.com.medina.escolaconectada.controller.CidadeController;
import br.com.medina.escolaconectada.models.Cidade;
import br.com.medina.escolaconectada.repository.CidadeRepository;
import br.com.medina.escolaconectada.services.ICidadeService;
import br.com.medina.escolaconectada.services.impl.CidadeServiceImpl;
import lombok.extern.slf4j.Slf4j;

@DataMongoTest
@Slf4j
@TestPropertySource(properties = "spring.mongodb.embedded.version=4.0.6")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CidadeServiceTest {
	
	
		@Autowired private CidadeRepository cidaderepo;

	
		 
		
		
    	@DisplayName("given object to save"
            + " when save object using MongoDB template"
            + " then object is saved")
        @Test
        public void test(@Autowired MongoTemplate mongoTemplate) {
            // given
            DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

            // when
            mongoTemplate.save(objectToSave, "collection");

            // then
            assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                .containsOnly("value");
        }
    	
    	
    	
        @Test
        public void saveCity(@Autowired MongoTemplate mongoTemplate) {
        	log.info("## inicio teste 02");
            // given
            DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();
            
            Cidade c =  new Cidade();
            c.setEstado("RJ");
            c.setNome("Campos");
            c = cidaderepo.insert(c);
            log.info("## inicio teste 02 c" + c);
            
            ICidadeService cidadeSvc = new CidadeServiceImpl(cidaderepo);
            CidadeController cidadeCtrl = new CidadeController(cidadeSvc);
            
            log.info("## inicio teste 02 listc ss" +  cidadeCtrl.findById("63534403a07f8a50746c271d"));
            
            log.info("## inicio teste 02 listc" +  cidadeSvc.findAll());
            // when
            //mongoTemplate.save(objectToSave, "collection");
            // then
            assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                .containsOnly("value");
        }
        
}
