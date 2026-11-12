package com.colegioscerca.colegioscerca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Repository
public class ColegioRepository {

    private final List<Colegio> colegios;

    // Al arrancar, lee data/colegios.json y lo convierte en objetos Colegio
    public ColegioRepository(ObjectMapper mapper) throws IOException {
        try (InputStream in = new ClassPathResource("data/colegios.json").getInputStream()) {
            List<Fila> filas = mapper.readValue(in, new TypeReference<List<Fila>>() {});
            this.colegios = filas.stream().map(Fila::aColegio).toList();
        }
    }

    public List<Colegio> findAll() {
        return colegios;
    }

    // Solo se toman 5 campos del JSON; el resto (teléfono, correo, etc.) se ignora
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Fila(String nombreestablecimiento, String direccion, String zona,
                       String niveles, String jornada) {
        Colegio aColegio() {
            return new Colegio(limpiar(nombreestablecimiento), limpiar(direccion),
                    limpiar(zona), limpiar(niveles), limpiar(jornada));
        }
        private static String limpiar(String s) {
            return s == null ? "" : s.trim();
        }
    }
}