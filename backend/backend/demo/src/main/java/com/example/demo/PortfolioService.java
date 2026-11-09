package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/** Monta o conteúdo público do site. Usado pela API e pela geração do content.js. */
@Service
public class PortfolioService {

    public record PortfolioResponse(Profile profile, List<Project> projects,
                                    List<TimelineEntry> experience, List<TimelineEntry> education) {
    }

    private final ProfileRepository profiles;
    private final ProjectRepository projects;
    private final TimelineRepository timeline;

    public PortfolioService(ProfileRepository profiles, ProjectRepository projects, TimelineRepository timeline) {
        this.profiles = profiles;
        this.projects = projects;
        this.timeline = timeline;
    }

    public Optional<PortfolioResponse> build() {
        return profiles.findById(1L).map(profile -> new PortfolioResponse(profile,
                projects.findByPublishedTrueOrderByIdDesc(),
                timeline.findByKindOrderByIdAsc(TimelineEntry.Kind.EXPERIENCE),
                timeline.findByKindOrderByIdAsc(TimelineEntry.Kind.EDUCATION)));
    }
}