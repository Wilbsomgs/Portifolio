package com.example.demo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

/** API de administração: exige login (ver SecurityConfig). Único jeito de alterar o conteúdo do site. */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final ProfileRepository profiles;
	private final ProjectRepository projects;
	private final TimelineRepository timeline;
	private final ContentExporter exporter;

	public AdminController(ProfileRepository profiles, ProjectRepository projects, TimelineRepository timeline,
						   ContentExporter exporter) {
		this.profiles = profiles;
		this.projects = projects;
		this.timeline = timeline;
		this.exporter = exporter;
	}

	/** Depois de cada alteração, regenera o content.js que o site lê. */
	private <T> T published(T saved) {
		exporter.export();
		return saved;
	}

	private static ResponseStatusException notFound() {
		return new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado");
	}

	// Informações gerais (e-mail, GitHub, texto de apresentação, cargos e skills)
	@PutMapping("/profile")
	public Profile updateProfile(@Valid @RequestBody Profile profile) {
		profile.id = 1L;
		return published(profiles.save(profile));
	}

	// Projetos
	@GetMapping("/projects")
	public List<Project> listProjects() {
		return projects.findAll();
	}

	@PostMapping("/projects")
	@ResponseStatus(HttpStatus.CREATED)
	public Project createProject(@Valid @RequestBody Project project) {
		project.id = null;
		return published(projects.save(project));
	}

	@PutMapping("/projects/{id}")
	public Project updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
		if (!projects.existsById(id)) throw notFound();
		project.id = id;
		return published(projects.save(project));
	}

	@DeleteMapping("/projects/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProject(@PathVariable Long id) {
		if (!projects.existsById(id)) throw notFound();
		projects.deleteById(id);
		exporter.export();
	}

	// Experiência e educação
	@GetMapping("/timeline")
	public List<TimelineEntry> listTimeline() {
		return timeline.findAll();
	}

	@PostMapping("/timeline")
	@ResponseStatus(HttpStatus.CREATED)
	public TimelineEntry createTimeline(@Valid @RequestBody TimelineEntry entry) {
		entry.id = null;
		return published(timeline.save(entry));
	}

	@PutMapping("/timeline/{id}")
	public TimelineEntry updateTimeline(@PathVariable Long id, @Valid @RequestBody TimelineEntry entry) {
		if (!timeline.existsById(id)) throw notFound();
		entry.id = id;
		return published(timeline.save(entry));
	}

	@DeleteMapping("/timeline/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTimeline(@PathVariable Long id) {
		if (!timeline.existsById(id)) throw notFound();
		timeline.deleteById(id);
		exporter.export();
	}

	/** Regenera o content.js manualmente (útil depois de restaurar o banco). */
	@PostMapping("/publish")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void publish() {
		exporter.export();
	}

	@GetMapping("/profile")
	public Profile getProfile() {
		return profiles.findById(1L).orElseThrow(AdminController::notFound);
	}
}