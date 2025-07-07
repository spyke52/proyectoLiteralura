package com.alura.literalura.service;

import com.alura.literalura.model.DatosGutendex;
import com.alura.literalura.model.DatosLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GutendexService {
    private static final String API_URL = "https://gutendex.com/books/?search=";

    @Autowired
    private ConsumoAPI consumoAPI;
    @Autowired
    private ConvierteDatos conversor;

    public Optional<DatosLibro> buscarLibroPorTitulo(String titulo) {
        String url = API_URL + titulo.replace(" ", "+");
        String json = consumoAPI.obtenerDatos(url);
        DatosGutendex datos = conversor.obtenerDatos(json, DatosGutendex.class);

        if (datos.results().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(datos.results().get(0));
    }
}