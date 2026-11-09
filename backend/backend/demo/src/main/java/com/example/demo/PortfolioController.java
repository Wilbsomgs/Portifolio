package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/** API pública, somente leitura. O site não depende dela: lê o content.js gerado pelo ContentExporter. */
@RestController
@RequestMapping("/api")
public class PortfolioController {

	private final PortfolioService service;

	public PortfolioController(PortfolioService service) {
		this.service = service;
	}

	@GetMapping("/portfolio")
	public PortfolioService.PortfolioResponse portfolio() {
		return service.build()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil ainda não cadastrado"));
	}
}