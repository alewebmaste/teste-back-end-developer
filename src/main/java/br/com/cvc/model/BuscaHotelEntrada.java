package br.com.cvc.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BuscaHotelEntrada {

	private Long id;
	
	private Long cityCode;
	
	@DateTimeFormat(pattern = "ddMMyyyy")
	private LocalDate checkin;
	@DateTimeFormat(pattern = "ddMMyyyy")
	private LocalDate checkout;
	
	private Long qtdAdultos;
	private Long qtdCriancas;

}
