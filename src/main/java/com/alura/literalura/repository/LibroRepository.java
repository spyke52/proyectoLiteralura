package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String titulo);
    boolean existsByTitulo(String titulo);
    boolean existsByTituloIgnoreCase(String titulo); // Método añadido
    List<Libro> findByIdioma(String idioma);
    Optional<Libro> findByTituloIgnoreCase(String titulo);
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findTop10ByOrderByDescargasDesc();
}