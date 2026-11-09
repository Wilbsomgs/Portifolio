package com.example.demo;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Entrega o currículo em PDF (arquivo em src/main/resources/static) para o botão "Baixar currículo".
 * Responde com "Content-Disposition: attachment", o que faz o navegador baixar o arquivo em vez de abri-lo.
 * Rota pública e somente leitura (liberada no SecurityConfig).
 */
@RestController
@RequestMapping("/api")
public class CurriculumController {

    private final Resource cv;
    private final String downloadName;

    public CurriculumController(@Value("${app.cv-file}") String cvFile,
                                @Value("${app.cv-download-name}") String downloadName) {
        this.cv = new ClassPathResource(cvFile);
        this.downloadName = downloadName;
    }

    @GetMapping("/Curriculo-Wilbsom-Goncalves-da-Silva")
    public ResponseEntity<Resource> download() {
        if (!cv.exists()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currículo não encontrado");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(downloadName, StandardCharsets.UTF_8).build().toString())
                .body(cv);
    }
}