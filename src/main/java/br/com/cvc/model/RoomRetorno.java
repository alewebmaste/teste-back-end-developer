package br.com.cvc.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RoomRetorno {

	private Long roomID;
	private String categoryName;
	private BigDecimal totalPrice;
	private PriceDetail priceDetail;

}
