package com.modv.pagos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.modv.pagos.dto.PagoDto;
import com.modv.pagos.dto.RespuestaDto;
import com.modv.pagos.service.PagoService;
import com.stripe.exception.StripeException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/pagos")
@Slf4j
public class PagosController {
	private PagoService pagoService;
	
	public PagosController(@Qualifier(value = "pagoServiceImpl") PagoService pagosService) {
		this.pagoService = pagosService;
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "Realizar pago con tarjeta", notes = "Si fué realizado con éxito retorna un objeto con "
			+ "el mensaje \"El pago ha sido realizado\", de lo contrario "
			+ "se muestra el mensaje \"El pago no pudo ser realizado\"")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = RespuestaDto.class),
			@ApiResponse(code = 409, message = "Conflict", response = RespuestaDto.class)
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<?> CreatePago(@RequestBody(required = true) @Valid PagoDto pago){
		try {
			String token = pagoService.generarToken(pago.getTarjeta());
			if (pagoService.realizarPago(pago.getTotal(), token)) {
				return ResponseEntity.created(null).body(RespuestaDto.builder()
						.resultado("El pago ha sido realizado")
						.build());
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body(RespuestaDto.builder()
					.resultado("El pago no pudo ser realizado")
					.build());
		}
		catch(StripeException ex) {
			log.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(RespuestaDto.builder()
					.resultado("El pago no pudo ser realizado")
					.build());
		}
	}
}