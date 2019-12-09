package br.com.cvc.model;

import java.util.List;

import lombok.Data;

@Data
public class Hotel {
	
	private Long id;
	private String name;
	private Long cityCode;
	private String cityName;
	private List<Room> rooms;	

}
