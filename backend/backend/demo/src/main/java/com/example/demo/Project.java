package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OrderColumn;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Project {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@NotBlank
	public String title;

	public String status = "Planejado";

	@Column(length = 500)
	public String summary;

	@Column(length = 4000)
	public String description;

	public String repoUrl;

	/** Só projetos publicados aparecem no site (RN01). */
	public boolean published = true;

	@ElementCollection(fetch = FetchType.EAGER) @OrderColumn
	public List<String> tech = new ArrayList<>();
}
