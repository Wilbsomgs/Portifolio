package com.example.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Cria o conteúdo inicial na primeira execução. Depois disso, tudo é alterado pela API de administração. */
@Component
public class DataSeeder implements CommandLineRunner {

	private final ProfileRepository profiles;
	private final ProjectRepository projects;
	private final TimelineRepository timeline;

	public DataSeeder(ProfileRepository profiles, ProjectRepository projects, TimelineRepository timeline) {
		this.profiles = profiles;
		this.projects = projects;
		this.timeline = timeline;
	}

	private static Skill skill(String name, int level) {
		Skill s = new Skill();
		s.name = name;
		s.level = level;
		return s;
	}

	@Override
	public void run(String... args) {
		if (profiles.count() == 0) {
			Profile p = new Profile();
			p.email = "seu@email.com";
			p.githubUrl = "https://github.com/Wilbsomgs";
			p.about = "Construo aplicações web com código limpo, interfaces responsivas e foco em quem vai usar.";
			p.roles = List.of("Desenvolvedor Full Stack", "Front-end com React", "Back-end com Spring Boot");
			p.skills = List.of(skill("React", 80), skill("TypeScript", 70), skill("SCSS", 75),
					skill("Java", 60), skill("Spring Boot", 55), skill("Git e GitHub", 70));
			profiles.save(p);
		}
		if (projects.count() == 0) {
			Project p = new Project();
			p.title = "Portfólio pessoal";
			p.status = "Em andamento";
			p.summary = "Este site: projetos, currículo e contato com design system próprio.";
			p.description = "Front-end em React 19, TypeScript e Vite, com estilos em SCSS. Conteúdo gerenciado por esta API em Spring Boot.";
			p.repoUrl = "https://github.com/Wilbsomgs/repositorio";
			p.tech = List.of("React", "TypeScript", "SCSS", "Vite", "Spring Boot");
			projects.save(p);
		}
		if (timeline.count() == 0) {
			TimelineEntry exp = new TimelineEntry();
			exp.kind = TimelineEntry.Kind.EXPERIENCE;
			exp.title = "Desenvolvedor Full Stack";
			exp.organization = "Projetos pessoais";
			exp.period = "2026 – Presente";
			exp.description = "Desenvolvimento deste portfólio com React, TypeScript e Spring Boot.";
			timeline.save(exp);

			TimelineEntry edu = new TimelineEntry();
			edu.kind = TimelineEntry.Kind.EDUCATION;
			edu.title = "Seu curso ou certificação";
			edu.organization = "Sua instituição";
			edu.period = "Ano – Ano";
			edu.description = "Atualize pela API: PUT /api/admin/timeline/{id}.";
			timeline.save(edu);
		}
	}
}
