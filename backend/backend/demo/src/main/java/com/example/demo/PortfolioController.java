package com.example.demo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/** API pública: somente leitura. É o que o site consome. */
@RestController
@RequestMapping("/api")
public class PortfolioController {

	public record PortfolioResponse(Profile profile, List<Project> projects,
			List<TimelineEntry> experience, List<TimelineEntry> education) {
	}

	private final ProfileRepository profiles;
	private final ProjectRepository projects;
	private final TimelineRepository timeline;

	public PortfolioController(ProfileRepository profiles, ProjectRepository projects, TimelineRepository timeline) {
		this.profiles = profiles;
		this.projects = projects;
		this.timeline = timeline;
	}

	@GetMapping("/portfolio")
	public PortfolioResponse portfolio() {
		Profile profile = profiles.findById(1L)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil ainda não cadastrado"));
		return new PortfolioResponse(profile,
				projects.findByPublishedTrueOrderByIdDesc(),
				timeline.findByKindOrderByIdAsc(TimelineEntry.Kind.EXPERIENCE),
				timeline.findByKindOrderByIdAsc(TimelineEntry.Kind.EDUCATION));
	}
}
