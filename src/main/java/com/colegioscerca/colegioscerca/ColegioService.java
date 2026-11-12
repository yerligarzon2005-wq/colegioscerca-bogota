package com.colegioscerca.colegioscerca;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
public class ColegioService {

    private final ColegioRepository repository;

    public ColegioService(ColegioRepository repository) {
        this.repository = repository;
    }

    // Primeros colegios, para mostrar algo al abrir la página
    public List<Colegio> listar(int max) {
        return repository.findAll().stream().limit(max).toList();
    }

    // Busca el texto dentro del nombre o la dirección, sin importar mayúsculas ni tildes
    public List<Colegio> buscar(String texto) {
        if (texto == null || texto.isBlank()) {
            return List.of();
        }
        String q = normalizar(texto);
        return repository.findAll().stream()
                .filter(c -> normalizar(c.nombre()).contains(q)
                        || normalizar(c.direccion()).contains(q))
                .limit(100)
                .toList();
    }

    private static String normalizar(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase()
                .trim();
    }
}