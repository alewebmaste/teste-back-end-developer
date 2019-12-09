package br.com.cvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.model.BuscaHotelEntrada;
import br.com.cvc.model.HotelRetorno;
import br.com.cvc.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	@Autowired
	private HotelService service;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public List<HotelRetorno> buscaHotel(Long id, BuscaHotelEntrada entrada) 
	{
	    return service.buscaHotel(id,entrada);
	}
	
	@RequestMapping(value = "avail", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	public List<HotelRetorno> buscaTodosHoteis(BuscaHotelEntrada entrada) 
	{
	    return service.buscaTodosHoteis(entrada);
	}

}
	