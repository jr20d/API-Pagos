package com.modv.pagos.dto;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class PagoDto {
	@NotNull(message = "El campo total no puede ser nulo")
	@DecimalMin(value = "0.01", message = "El valor no puede ser igual a cero")
	private Double total;
	@NotNull(message = "Debe ingresar los datos de la tarjeta")
	@Valid
	private TarjetaDto tarjeta;
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public TarjetaDto getTarjeta() {
		return tarjeta;
	}
	public void setTarjeta(TarjetaDto tarjeta) {
		this.tarjeta = tarjeta;
	}
}