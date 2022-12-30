package com.modv.pagos.service;

import com.modv.pagos.dto.TarjetaDto;
import com.stripe.exception.StripeException;

public interface PagoService {
	String generarToken(TarjetaDto tarjeta) throws StripeException;
	boolean realizarPago(double total, String token) throws StripeException;
}