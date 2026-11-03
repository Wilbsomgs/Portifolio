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

	public AdminController(ProfileRepository profiles, ProjectRepository projects, TimelineRepository timeline) {
		this.profiles = profiles;
		this.projects = projects;
		this.timeline = timeline;
	}

	private static ResponseStatusException notFound() {
		return new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado");
	}

	// Informações gerais (e-mail, GitHub, texto de apresentação, cargos e skills)
	@PutMapping("/profile")
	public Profile updateProfile(@Valid @RequestBody Profile profile) {
		profile.id = 1L;
		return profiles.save(profile);
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
		return projects.save(project);
	}

	@PutMapping("/projects/{id}")
	public Project updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
		if (!projects.existsById(id)) throw notFound();
		project.id = id;
		return projects.save(project);
	}

	@DeleteMapping("/projects/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProject(@PathVariable Long id) {
		if (!projects.existsById(id)) throw notFound();
		projects.deleteById(id);
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
		return timeline.save(entry);
	}

	@PutMapping("/timeline/{id}")
	public TimelineEntry updateTimeline(@PathVariable Long id, @Valid @RequestBody TimelineEntry entry) {
		if (!timeline.existsById(id)) throw notFound();
		entry.id = id;
		return timeline.save(entry);
	}

	@DeleteMapping("/timeline/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTimeline(@PathVariable Long id) {
		if (!timeline.existsById(id)) throw notFound();
		timeline.deleteById(id);
	}
}
