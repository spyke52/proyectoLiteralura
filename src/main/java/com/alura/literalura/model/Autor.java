package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {}

    public Autor(String nombre, Integer nacimiento, Integer fallecimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.fallecimiento = fallecimiento;
    }

    // Getters y setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getNacimiento() { return nacimiento; }
    public void setNacimiento(Integer nacimiento) {
        if (fallecimiento != null && nacimiento != null && nacimiento > fallecimiento) {
            throw new IllegalArgumentException("Año de nacimiento no puede ser mayor que fallecimiento");
        }
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() { return fallecimiento; }
    public void setFallecimiento(Integer fallecimiento) {
        if (nacimiento != null && fallecimiento != null && fallecimiento < nacimiento) {
            throw new IllegalArgumentException("Año de fallecimiento no puede ser menor que nacimiento");
        }
        this.fallecimiento = fallecimiento;
    }

    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }

    @Override
    public String toString() {
        return nombre + " (" + nacimiento + "-" + fallecimiento + ")";
    }
}