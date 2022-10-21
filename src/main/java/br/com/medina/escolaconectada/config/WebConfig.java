package br.com.medina.escolaconectada.config;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	//configuração temporaria para desabilitar cross para estas origens: caso for desenvolver local,  descomentar a linha localhost com a porta a ser usada
    	String[] allowedOrigins = {
    			"http://localhost:3000",
    			"https://transcendent-valkyrie-713170.netlify.app"
    			//,"localhost:8081"
    	};
        registry.addMapping("/**").allowedOrigins(allowedOrigins)
        		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        //registry.addMapping("forward:/swagger-ui/index.html").allowedOrigins("*");
    }
    
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/GMT+3"));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> 
            jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Etc/GMT+3"));
    }

}