package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE a.nombre = :nombre")
    Optional<Autor> findByNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :anio AND (a.fallecimiento IS NULL OR a.fallecimiento > :anio)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") int anio);

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE LOWER(a.nombre) LIKE LOWER(concat('%', :nombre,'%'))")
    List<Autor> findByNombreContaining(@Param("nombre") String nombre);
}