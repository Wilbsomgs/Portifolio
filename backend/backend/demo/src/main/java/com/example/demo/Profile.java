package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OrderColumn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** Dados gerais do site. Existe sempre um único registro (id = 1). */
@Entity
public class Profile {

	@Id
	public Long id = 1L;

	@Email @NotBlank
	public String email;

	public String githubUrl;

	/** Link do currículo em PDF. Vazio = o site abre a impressão para salvar como PDF. */
	public String cvUrl;

	@Column(length = 1000)
	public String about;

	@ElementCollection(fetch = FetchType.EAGER) @OrderColumn
	public List<String> roles = new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER) @OrderColumn
	public List<Skill> skills = new ArrayList<>();
}
