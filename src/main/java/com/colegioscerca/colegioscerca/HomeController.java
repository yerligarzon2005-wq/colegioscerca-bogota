package com.colegioscerca.colegioscerca;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final ColegioService service;

    public HomeController(ColegioService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q, Model model) {
        List<Colegio> resultados;
        String mensaje = null;

        if (q == null) {                       // primera visita
            resultados = service.listar(20);
        } else if (q.isBlank()) {              // búsqueda vacía
            resultados = List.of();
            mensaje = "Escribe un nombre o una dirección para buscar.";
        } else {                               // búsqueda normal
            resultados = service.buscar(q);
            if (resultados.isEmpty()) {
                mensaje = "No se encontraron colegios para '" + q + "'.";
            }
        }

        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("colegios", resultados);
        model.addAttribute("mensaje", mensaje);
        return "index";
    }
}