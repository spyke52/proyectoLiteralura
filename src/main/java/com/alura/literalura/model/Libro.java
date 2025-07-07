package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private Integer descargas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    public Libro() {}

    public Libro(String titulo, String idioma, Integer descargas) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
    }

    public void agregarAutor(Autor autor) {
        autores.add(autor);
        autor.getLibros().add(this);
    }


    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) {
        if (idioma != null && !idioma.matches("^[a-z]{2}$")) {
            throw new IllegalArgumentException("Idioma debe ser código de 2 letras");
        }
        this.idioma = idioma;
    }

    public Integer getDescargas() { return descargas; }
    public void setDescargas(Integer descargas) { this.descargas = descargas; }

    public List<Autor> getAutores() { return autores; }
    public void setAutores(List<Autor> autores) { this.autores = autores; }

    @Override
    public String toString() {
        return titulo + " (" + idioma + ") - " + descargas + " descargas";
    }
}