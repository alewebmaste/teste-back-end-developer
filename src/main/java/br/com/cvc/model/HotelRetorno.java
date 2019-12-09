package br.com.cvc.model;

import java.util.List;

import lombok.Data;

@Data

public class HotelRetorno {
	private Long id;
	private String cityName;
	private List<RoomRetorno> rooms;

}
