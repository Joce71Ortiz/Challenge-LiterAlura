package com.aluracursos.LiteraturAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();
    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.getNombre();
        this.fechaDeNacimiento = datosAutor.getFechaDeNacimiento();
        this.fechaDeFallecimiento = datosAutor.getFechaDeFallecimiento();
    }

    @Override
    public String toString() {
        return "Autor{" +
                "Id=" + Id +
                ", nombre='" + nombre + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                ", fechaDeFallecimiento=" + fechaDeFallecimiento +
                ", libros=" + libros +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeMuerte) {
        this.fechaDeFallecimiento = fechaDeMuerte;
    }
}

