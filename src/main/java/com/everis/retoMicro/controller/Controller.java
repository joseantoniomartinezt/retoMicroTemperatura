package com.everis.retoMicro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class Controller {
	
	private Counter counterMostrarCelsius,counterMostrarFaren,counterConvertirFarenCelsius,counterConvertirCelsiusFaren;
	
	private final static Logger logger= LoggerFactory.getLogger(Controller.class);
	

	public Controller(MeterRegistry registry) {
		this.counterMostrarCelsius = Counter.builder("invocaciones.mostrarCelsius").description("Invocaciones totales mostrarCelsius").register(registry);
		this.counterMostrarFaren = Counter.builder("invocaciones.mostrarFaren").description("Invocaciones totales mostrarFaren").register(registry);
		this.counterConvertirFarenCelsius = Counter.builder("invocaciones.convertirFarenCelsius").description("Invocaciones totales convertirFarenCelsius").register(registry);
		this.counterConvertirCelsiusFaren = Counter.builder("invocaciones.convertirCelsiusFaren").description("Invocaciones totales convertirCelsiusFaren").register(registry);
	}
	
	@Value("${some.valueCelsius}")
	private int gradosCelsius;
	
	@Value("${some.valueFaren}")
	private int gradosFaren;
	
	@GetMapping(path = "/gradosCelsius")
	public String gradosCelsius() {
		counterMostrarCelsius.increment();
		logger.info("Mostrar Grados Celsius");
		
		return "La temperatura actual en grados Celsius es de: " + this.gradosCelsius;
	}
	
	@GetMapping(path = "/gradosFaren")
	public String gradosFaren() {
		counterMostrarFaren.increment();
		logger.info("Mostrar Grados Fahrenheit");
		
		return "La temperatura actual en grados Fahrenheit: " + this.gradosFaren;
	}
	
	@GetMapping(path = "/convertirFarenCelsius")
	@ResponseBody()
	public String convertirFarenCelsius(@RequestParam double temperaturaFaren) {
		counterConvertirFarenCelsius.increment();
		logger.info("Conversor Fahrenheit a Celsius");
		
		double temperaturaCelsius = ((temperaturaFaren - 32) * 5) / 9;
		
		return "La temperatura en Fahrenheit es: " + temperaturaFaren + " y la temperatura en Celsius es: " + temperaturaCelsius;
	}
	
	@GetMapping(path = "/convertirCelsiusFaren")
	@ResponseBody
	public String convertirCelsiusFaren(@RequestParam double temperaturaCelsius) {
		counterConvertirCelsiusFaren.increment();
		logger.info("Conversor Celsius a Fahrenheit");
		
		double temperaturaFaren = temperaturaCelsius * 1.8 + 32;
		
		return "La temperatura en Celsius es: " + temperaturaCelsius + " y la temperatura en Fahrenheit es: " + temperaturaFaren;
	}
	
	
}
