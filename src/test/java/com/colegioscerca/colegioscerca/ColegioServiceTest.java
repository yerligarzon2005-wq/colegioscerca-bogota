package com.colegioscerca.colegioscerca;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ColegioServiceTest {

    private final ColegioRepository repo = mock(ColegioRepository.class);
    private final ColegioService service = new ColegioService(repo);

    private void datos() {
        when(repo.findAll()).thenReturn(List.of(
                new Colegio("COLEGIO SAN JOSÉ", "CL 42 A 87", "URBANA",
                        "BÁSICA", "MAÑANA"),
                new Colegio("JARDÍN LA CASITA", "KR 90 A 91", "URBANA",
                        "PREESCOLAR", "COMPLETA")));
    }

    @Test
    void buscaPorNombreSinImportarMayusculasNiTildes() {
        datos();
        assertEquals(1, service.buscar("san jose").size());
    }

    @Test
    void buscaPorDireccion() {
        datos();
        assertEquals(1, service.buscar("KR 90").size());
    }

    @Test
    void sinCoincidenciasDevuelveListaVacia() {
        datos();
        assertEquals(0, service.buscar("zzz").size());
    }
}