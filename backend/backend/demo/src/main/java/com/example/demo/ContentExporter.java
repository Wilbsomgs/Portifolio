package com.example.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Publica o conteúdo do site como arquivo estático (content.js).
 * O front-end carrega esse arquivo direto, sem chamar a API: o conteúdo aparece assim que a página abre,
 * mesmo com o back-end desligado, e quem acessa o site não tem como alterá-lo.
 */
@Component
public class ContentExporter {

    private static final Logger log = LoggerFactory.getLogger(ContentExporter.class);

    private final PortfolioService portfolio;
    private final ObjectMapper mapper;
    private final Path file;

    public ContentExporter(PortfolioService portfolio, ObjectMapper mapper,
                           @Value("${app.export-file}") String exportFile) {
        this.portfolio = portfolio;
        this.mapper = mapper;
        this.file = Path.of(exportFile).toAbsolutePath().normalize();
    }

    /** Regenera o arquivo com o conteúdo atual do banco. Falhas são registradas e não derrubam a API. */
    public synchronized void export() {
        try {
            var data = portfolio.build().orElse(null);
            if (data == null) {
                log.warn("Perfil ainda não cadastrado: content.js não foi gerado");
                return;
            }
            String js = "/* Gerado automaticamente pelo back-end. Não edite: altere o conteúdo pela administração. */\n"
                    + "window.PORTFOLIO = " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data) + ";\n";
            Files.createDirectories(file.getParent());
            Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
            Files.writeString(tmp, js, StandardCharsets.UTF_8);
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
            log.info("Conteúdo publicado em {}", file);
        } catch (IOException e) {
            log.error("Não foi possível gravar {}", file, e);
        }
    }
}