package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private final GutendexService gutendexService;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    @Autowired
    public Principal(GutendexService gutendexService,
                     LibroRepository libroRepository,
                     AutorRepository autorRepository) {
        this.gutendexService = gutendexService;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(teclado.nextLine());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Ingresa un número válido");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("""
            \n=== MENÚ LITERALURA ===
            1 - Buscar libro por título
            2 - Listar libros registrados
            3 - Listar autores registrados
            4 - Buscar autores vivos en un año
            5 - Listar libros por idioma
            6 - Top 10 libros más descargados
            0 - Salir
            """);
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> buscarLibroPorTitulo();
            case 2 -> listarLibrosRegistrados();
            case 3 -> listarAutoresRegistrados();
            case 4 -> buscarAutoresVivos();
            case 5 -> listarLibrosPorIdioma();
            case 6 -> listarTop10Libros();
            case 0 -> System.out.println("👋 ¡Hasta pronto!");
            default -> System.out.println("⚠️ Opción no válida");
        }
    }

    @Transactional
    private void buscarLibroPorTitulo() {
        System.out.println("\n🔍 Ingrese el título del libro:");
        var titulo = teclado.nextLine().trim();

        if (titulo.isEmpty()) {
            System.out.println("❌ El título no puede estar vacío");
            return;
        }

        gutendexService.buscarLibroPorTitulo(titulo)
                .ifPresentOrElse(
                        this::procesarLibroEncontrado,
                        () -> System.out.println("❌ No se encontró el libro '" + titulo + "'")
                );
    }

    private void procesarLibroEncontrado(DatosLibro datosLibro) {
        if (libroRepository.existsByTituloIgnoreCase(datosLibro.titulo())) {
            System.out.println("⚠️ El libro ya está registrado");
            return;
        }

        var libro = guardarLibro(datosLibro);
        mostrarResultadoLibro(libro);
    }

    private Libro guardarLibro(DatosLibro datosLibro) {
        var idioma = datosLibro.idiomas().isEmpty() ? "en" : datosLibro.idiomas().get(0);
        var libro = new Libro(datosLibro.titulo(), idioma, datosLibro.descargas());

        datosLibro.autores().forEach(datosAutor -> {
            var autor = autorRepository.findByNombre(datosAutor.nombre())
                    .orElseGet(() -> autorRepository.save(
                            new Autor(datosAutor.nombre(), datosAutor.nacimiento(), datosAutor.fallecimiento())
                    ));
            libro.agregarAutor(autor);
        });

        return libroRepository.save(libro);
    }

    private void mostrarResultadoLibro(Libro libro) {
        System.out.println("\n✅ Libro registrado exitosamente:");
        System.out.println("📖 Título: " + libro.getTitulo());
        System.out.println("🌐 Idioma: " + libro.getIdioma().toUpperCase());
        System.out.println("⬇️ Descargas: " + libro.getDescargas());
        System.out.println("✍️ Autor(es): " + libro.getAutores().stream()
                .map(Autor::getNombre)
                .collect(Collectors.joining(", ")));
    }

    @Transactional(readOnly = true)
    private void listarLibrosRegistrados() {
        var libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("\n📭 No hay libros registrados");
            return;
        }

        System.out.println("\n📚 Libros registrados (" + libros.size() + "):");
        libros.forEach(this::mostrarLibroResumen);
    }

    private void mostrarLibroResumen(Libro libro) {
        System.out.println("\n• " + libro.getTitulo());
        System.out.println("  🌐 Idioma: " + libro.getIdioma().toUpperCase());
        System.out.println("  ⬇️ Descargas: " + libro.getDescargas());
        System.out.println("  ✍️ Autor(es): " + libro.getAutores().stream()
                .map(Autor::getNombre)
                .collect(Collectors.joining(", ")));
    }

    @Transactional(readOnly = true)
    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("\n📭 No hay autores registrados");
            return;
        }

        System.out.println("\n👤 Autores registrados (" + autores.size() + "):");
        autores.forEach(this::mostrarAutorResumen);
    }

    private void mostrarAutorResumen(Autor autor) {
        System.out.println("\n• " + autor.getNombre());
        System.out.println("  🎂 Nacimiento: " +
                (autor.getNacimiento() != null ? autor.getNacimiento() : "Desconocido"));
        System.out.println("  ✝️ Fallecimiento: " +
                (autor.getFallecimiento() != null ? autor.getFallecimiento() : "Desconocido"));
        System.out.println("  📚 Libros: " + autor.getLibros().stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", ")));
    }

    @Transactional(readOnly = true)
    private void buscarAutoresVivos() {
        System.out.println("\n📅 Ingrese el año para buscar autores vivos:");
        try {
            var anio = Integer.parseInt(teclado.nextLine());
            var autores = autorRepository.findAutoresVivosEnAnio(anio);

            if (autores.isEmpty()) {
                System.out.println("\n🔍 No se encontraron autores vivos en " + anio);
                return;
            }

            System.out.println("\n👤 Autores vivos en " + anio + ":");
            autores.forEach(autor -> System.out.println("• " + autor.getNombre() +
                    " (" + autor.getNacimiento() + "-" + autor.getFallecimiento() + ")"));
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Ingresa un año válido");
        }
    }

    @Transactional(readOnly = true)
    private void listarLibrosPorIdioma() {
        System.out.println("\n🌐 Ingrese el código de idioma (2 letras):");
        var idioma = teclado.nextLine().toLowerCase().trim();

        if (!idioma.matches("[a-z]{2}")) {
            System.out.println("❌ Error: Código de idioma inválido");
            return;
        }

        var libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("\n📭 No hay libros en idioma '" + idioma + "'");
            return;
        }

        System.out.println("\n📚 Libros en " + idioma.toUpperCase() + ":");
        libros.forEach(libro -> System.out.println("• " + libro.getTitulo()));
    }

    @Transactional(readOnly = true)
    private void listarTop10Libros() {
        var topLibros = libroRepository.findTop10ByOrderByDescargasDesc();
        if (topLibros.isEmpty()) {
            System.out.println("\n📭 No hay libros registrados");
            return;
        }

        System.out.println("\n🏆 Top 10 libros más descargados:");
        for (int i = 0; i < topLibros.size(); i++) {
            var libro = topLibros.get(i);
            System.out.println((i + 1) + ". " + libro.getTitulo() +
                    " (" + libro.getDescargas() + " descargas)");
        }
    }
}