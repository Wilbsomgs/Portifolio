package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Item de experiência profissional ou de educação. */
@Entity
public class TimelineEntry {

	public enum Kind { EXPERIENCE, EDUCATION }

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@NotNull @Enumerated(EnumType.STRING)
	public Kind kind;

	@NotBlank
	public String title;

	public String organization;

	public String period;

	@Column(length = 1000)
	public String description;
}
