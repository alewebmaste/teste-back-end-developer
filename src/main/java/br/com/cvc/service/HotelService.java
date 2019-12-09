package br.com.cvc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.cvc.model.BuscaHotelEntrada;
import br.com.cvc.model.Hotel;
import br.com.cvc.model.HotelRetorno;
import br.com.cvc.model.PriceDetail;
import br.com.cvc.model.RoomRetorno;

@Service
public class HotelService {

	@Value("${url}")
	private String url;

	@Value("${urlById}")
	private String urlById;

	public List<HotelRetorno> buscaHotel(Long id, BuscaHotelEntrada entrada) {
		final String uri = urlById + id;
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Hotel[]> response = restTemplate.getForEntity(uri, Hotel[].class);
		Hotel[] hoteis = response.getBody();

		List<Hotel> hoteisList = Arrays.asList(hoteis);

		List<HotelRetorno> listHotelRetorno = new ArrayList<HotelRetorno>();

		converter(entrada, hoteisList, listHotelRetorno);

		return listHotelRetorno;

	}

	public List<HotelRetorno> buscaTodosHoteis(BuscaHotelEntrada entrada) {
		final String uri = url + entrada.getCityCode();
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Hotel[]> response = restTemplate.getForEntity(uri, Hotel[].class);
		Hotel[] hoteis = response.getBody();

		List<Hotel> hoteisList = Arrays.asList(hoteis);

		List<HotelRetorno> listHotelRetorno = new ArrayList<HotelRetorno>();

		converter(entrada, hoteisList, listHotelRetorno);

		return listHotelRetorno;

	}

	private void converter(BuscaHotelEntrada entrada, List<Hotel> hoteisList, List<HotelRetorno> listHotelRetorno) {
		hoteisList.parallelStream().forEach(h -> {

			HotelRetorno retorno = new HotelRetorno();
			retorno.setId(h.getId());
			retorno.setCityName(h.getCityName());

			List<RoomRetorno> roomsRetornoList = new ArrayList<RoomRetorno>();

			h.getRooms().parallelStream().forEach(r -> {

				RoomRetorno roomRetorno = new RoomRetorno();

				roomRetorno.setRoomID(r.getRoomID());
				roomRetorno.setCategoryName(r.getCategoryName());

				PriceDetail priceDetail = new PriceDetail();

				priceDetail.setPricePerDayAdult(
						r.getPrice().getAdult().divide(new BigDecimal(0.7), 2, RoundingMode.HALF_UP));
				priceDetail.setPricePerDayChild(
						r.getPrice().getChild().divide(new BigDecimal(0.7), 2, RoundingMode.HALF_UP));

				Long totalDias = ChronoUnit.DAYS.between(entrada.getCheckin(), entrada.getCheckout());
				Long qtdAdultos = entrada.getQtdAdultos();
				Long qtdCriancas = entrada.getQtdCriancas();

				BigDecimal valorTotalAdulto = priceDetail.getPricePerDayAdult().multiply(new BigDecimal(totalDias));
				valorTotalAdulto = valorTotalAdulto.multiply(new BigDecimal(qtdAdultos));

				BigDecimal valorTotalCrianca = priceDetail.getPricePerDayChild().multiply(new BigDecimal(totalDias));
				valorTotalCrianca = valorTotalCrianca.multiply(new BigDecimal(qtdCriancas));

				BigDecimal totalPrice = valorTotalAdulto.add(valorTotalCrianca);

				roomRetorno.setTotalPrice(totalPrice);

				roomRetorno.setPriceDetail(priceDetail);

				roomsRetornoList.add(roomRetorno);

			});

			retorno.setRooms(roomsRetornoList);

			listHotelRetorno.add(retorno);

		});
	}

}
