package com.aluracursos.LiteraturAlura.repository;

import com.aluracursos.LiteraturAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdiomas(String idiomas);

    @Query("SELECT l FROM Libro l ORDER BY l.numeroDeDescargas DESC LIMIT 10")
    List<Libro> findTop10ByNumeroDeDescargas();

    Optional<Libro> findByTituloIgnoreCase(String nombreLibro);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre ILIKE %:nombreAutor%")
    List<Libro> buscarLibrosPorAutor(@Param("nombreAutor") String nombreAutor);
}
