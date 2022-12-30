package com.modv.pagos.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.modv.pagos.dto.TarjetaDto;
import com.modv.pagos.service.PagoService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;

@Service(value = "pagoServiceImpl")
public class PagoServiceImpl implements PagoService {

	@Value("${stripe.apiKey}")
	private String apiKey;
	
	@Override
	public String generarToken(TarjetaDto tarjeta) throws StripeException {
		Stripe.apiKey = apiKey;

		Map<String, Object> card = new HashMap<>();
		card.put("number", tarjeta.getNumero());
		card.put("exp_month", tarjeta.getMes());
		card.put("exp_year", tarjeta.getYear());
		card.put("cvc", tarjeta.getCvc());
		Map<String, Object> params = new HashMap<>();
		params.put("card", card);

		Token token = Token.create(params);
		
		return token.getId();
	}

	@Override
	public boolean realizarPago(double total, String token) throws StripeException {
		Stripe.apiKey = apiKey;
		
		Map<String, Object> params = new HashMap<>();
		params.put("amount", (int) (total * 100));
		params.put("currency", "usd");
		params.put("source", token);
		params.put(
		  "description",
		  "Pago de compra realizada en el sitio Mercado online (MOD-V)"
		);

		Charge charge = Charge.create(params);
		return charge.getPaid();
	}
}