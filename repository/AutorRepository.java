package com.aluracursos.LiteraturAlura.repository;

import com.aluracursos.LiteraturAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :anio AND a.fechaDeFallecimiento >= :anio")
    List<Autor> buscarAutoresVivosPorAnio(Integer anio);

    List<Autor> findByNombreContainingIgnoreCase(String nombreAutor);
}
