package br.com.medina.escolaconectada.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.medina.escolaconectada.util.Utils;

@Component
@Order(1)
public class RequestResponseFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(RequestResponseFilter.class);
	
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		try {
			logger.info("Logging  Filter Request  {} : {}", req.getMethod(), req.getRequestURI());
			if(req.getMethod().equals("GET")) {
				chain.doFilter(request, response);
			}
			else {
				String token = req.getHeader("APIKEY");
				String secret = req.getHeader("SECRET");
				if(token == null || secret == null ) {
					logger.debug("API KEY e SECRET não encontrados");
					HttpServletResponse resp = (HttpServletResponse) response;
					resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
					resp.setHeader("Content-Type", "application/json");
					resp.getOutputStream().write(
							"{\"error\": \"por favor insira a APIKEY e o SECRET no header  EX: (APIKEY - 1234) e (SECRET - 4321)\"}"
									.getBytes());
				}else {
					String passCheck = Utils.instance.DecryptPassword("lVlThq3IWvtyK176lMO2iX1PyxTrMFe05O1S7KUbI0OfXbGaAYZ1nltQvITyJm7xHjJRGg/ozmhEbe+L8r7exrP59pBkvD3SPRKQPjOcPSs=", secret);
					System.out.println("APIKEY is" + passCheck);
					if(passCheck.equals(token)) {
						logger.info("AUTENTICATED");
						chain.doFilter(request, response);
					} else {
						logger.debug("API KEY e SECRET incorretas");
						HttpServletResponse resp = (HttpServletResponse) response;
						resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
						resp.setHeader("Content-Type", "application/json");
						resp.getOutputStream().write(
								"{\"error\": \"Credenciais incorretas. verifique APIKEY e o SECRET no header  EX: (APIKEY - 1234) e (SECRET - 4321)\"}"
										.getBytes());
					}
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Email do usuario não encontrado, favor inserir no header");
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			resp.setHeader("Content-Type", "application/json");
			resp.getOutputStream().write(
					"{\"error\": \"Credenciais incorretas. verifique APIKEY e o SECRET no header  EX: (APIKEY - 1234) e (SECRET - 4321)\"}"
							.getBytes());
		}
		
		
		
		// TODO Auto-generated method stub
		
	}

}
