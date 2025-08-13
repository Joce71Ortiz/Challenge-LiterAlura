package com.aluracursos.LiteraturAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String titulo;
    private String idiomas;
    private Integer numeroDeDescargas;
    @Column(length = 2000)
    private String sinopsis;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autor().get(0));
        this.idiomas = datosLibro.idiomas().get(0);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
        if (datosLibro.summaries() != null && !datosLibro.summaries().isEmpty()) {
            this.sinopsis = datosLibro.summaries().get(0);
        }
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return """
            ----- Libro encontrado y guardado -----
            Título: %s
            Autor: %s
            Idiomas: %s
            Número de descargas: %s
            Sinopsis: %s
            ---------------------------
            """.formatted(titulo, autor.getNombre(), idiomas, numeroDeDescargas, sinopsis);
    }
}