package com.aluracursos.LiteraturAlura.principal;

import com.aluracursos.LiteraturAlura.model.*;
import com.aluracursos.LiteraturAlura.repository.AutorRepository;
import com.aluracursos.LiteraturAlura.repository.LibroRepository;
import com.aluracursos.LiteraturAlura.service.ConsumoAPI;
import com.aluracursos.LiteraturAlura.service.ConvierteDatos;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1- Buscar libro por titulo
                    2- Mostrar libros registrados
                    3- Mostrar autores registrados
                    4- Mostrar autores vivos en un determinado año
                    5- Mostrar libros por idioma
                    6- Top 10 libros más descargados
                    7- Generar estadísticas de descargas
                    8- Buscar autor por nombre
                    9- Buscar libros por autor
                    0- Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorAnio();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 6:
                    top10LibrosMasDescargados();
                    break;
                case 7:
                    mostrarEstadisticasDescargas();
                    break;
                case 8:
                    buscarAutorPorNombre();
                    break;
                case 9:
                    buscarLibrosPorAutor();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación....");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
            System.out.println("Escribe el nombre del libro que deseas buscar: ");
            var nombreLibro = teclado.nextLine();

        Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(nombreLibro);

        if (libroExistente.isPresent()) {
            System.out.println("----- Libro ya registrado -----");
            System.out.println(libroExistente.get());
            System.out.println("---------------------------");
        } else {
            var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
            var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

            if (datosBusqueda != null && !datosBusqueda.getResultados().isEmpty()) {
                DatosLibro datosLibro = datosBusqueda.getResultados().get(0);

                // Creamos un objeto Libro a partir de los datos de la API
                Libro libro = new Libro(datosLibro);

                // Guardamos el libro en la base de datos
                libroRepository.save(libro);

                System.out.println("----- Libro encontrado y guardado -----");

                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idiomas: " + libro.getIdiomas());
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                System.out.println("Sinopsis: " + libro.getSinopsis());
                System.out.println("---------------------------");
            } else {
                System.out.println("Libro no encontrado.");
            }
        }
    }
    private void mostrarLibrosRegistrados(){
        List<Libro> libros = libroRepository.findAll();
        System.out.println("----- Libros Registrados -----");
        libros.forEach(System.out::println);
        System.out.println("---------------------------");
    }

    private void mostrarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void mostrarAutoresVivosPorAnio() {
        System.out.println("Escribe el año: ");
        var anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresVivos = autorRepository.buscarAutoresVivosPorAnio(anio);
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            autoresVivos.forEach(System.out::println);
        }
    }
    private void mostrarLibrosPorIdioma() {
        System.out.println("Escribe el idioma para buscar los libros (ej. es, en, fr): ");
        var idioma = teclado.nextLine();

        List<Libro> librosPorIdioma = libroRepository.findByIdiomas(idioma);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idioma + ".");
        } else {
            librosPorIdioma.forEach(System.out::println);
        }
    }

    private void top10LibrosMasDescargados() {
        System.out.println("----- Top 10 libros más descargados -----");
        List<Libro> top10Libros = libroRepository.findTop10ByNumeroDeDescargas();
        top10Libros.forEach(System.out::println);
        System.out.println("---------------------------------------");
    }

    private void mostrarEstadisticasDescargas() {
        // Get all books from the database
        List<Libro> libros = libroRepository.findAll();

        // Collect statistics from the download counts
        DoubleSummaryStatistics stats = libros.stream()
                .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));

        // Display the statistics
        System.out.println("----- Estadísticas de descargas -----");
        System.out.println("Libros registrados: " + stats.getCount());
        System.out.println("Media de descargas: " + stats.getAverage());
        System.out.println("Máximo de descargas: " + stats.getMax());
        System.out.println("Mínimo de descargas: " + stats.getMin());
        System.out.println("-------------------------------------");
    }

    private void buscarAutorPorNombre() {
        System.out.println("Escribe el nombre del autor que deseas buscar:");
        var nombreAutor = teclado.nextLine();

        List<Autor> autoresEncontrados = autorRepository.findByNombreContainingIgnoreCase(nombreAutor);

        if (autoresEncontrados.isEmpty()) {
            System.out.println("No se encontraron autores con ese nombre.");
        } else {
            autoresEncontrados.forEach(System.out::println);
        }
    }

    private void buscarLibrosPorAutor() {
        System.out.println("Escribe el nombre del autor para buscar sus libros:");
        var nombreAutor = teclado.nextLine();

        List<Libro> librosEncontrados = libroRepository.buscarLibrosPorAutor(nombreAutor);

        if (librosEncontrados.isEmpty()) {
            System.out.println("No se encontraron libros para ese autor.");
        } else {
            System.out.println("----- Libros del autor '" + nombreAutor + "' -----");
            librosEncontrados.forEach(System.out::println);
            System.out.println("---------------------------------------");
        }
    }
}