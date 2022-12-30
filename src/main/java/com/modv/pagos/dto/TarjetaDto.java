package com.modv.pagos.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TarjetaDto {
	@NotBlank(message = "Debe ingresar el numero de la tarjeta")
	private String numero;
	@NotNull(message = "Debe ingresar el mes de vencimiento de la tarjeta")
	@Min(value = 1)
	private Integer mes;
	@NotNull(message = "Debe ingresar el año de vencimiento de la tarjeta")
	@Min(value = 1)
	private Integer year;
	@NotBlank(message = "Debe ingresar el código de seguridad de la tarjeta (CVC)")
	private String cvc;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
}