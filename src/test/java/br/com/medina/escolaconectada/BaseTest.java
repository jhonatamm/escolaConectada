package br.com.medina.escolaconectada;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;

import br.com.medina.escolaconectada.services.IAlunoService;
import br.com.medina.escolaconectada.services.ICidadeService;
import br.com.medina.escolaconectada.services.IEscolaService;
import br.com.medina.escolaconectada.services.IMateriaService;
import br.com.medina.escolaconectada.services.IMetricsServices;
import br.com.medina.escolaconectada.services.ISalaService;


@AutoConfigureMockMvc
@WebMvcTest
public class BaseTest {
	

	
	@MockBean
	protected ICidadeService cidadeSvc;
	
	@MockBean 
	protected IEscolaService escolaSvc;
	
	@MockBean
	protected IMetricsServices metricsSvc;
	
	@MockBean
	protected ISalaService salaSvc;
	
	@MockBean
	protected IMateriaService materiaSvc;
	
	@MockBean
	protected IAlunoService alunoSvc;
	
	protected static String readFile(String path) {
		String content = "";
		try {
			Path filePath = Path.of(	new ClassPathResource(
					path, 
					BaseTest.class.getClassLoader()).getFile().getAbsolutePath());
			content = Files.readString(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
