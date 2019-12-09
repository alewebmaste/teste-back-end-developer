package br.com.cvc.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PriceDetail {

	private BigDecimal pricePerDayAdult;
	private BigDecimal pricePerDayChild;

}
