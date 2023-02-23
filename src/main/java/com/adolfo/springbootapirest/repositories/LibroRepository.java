package com.adolfo.springbootapirest.repositories;

import com.adolfo.springbootapirest.models.Libro;
import com.adolfo.springbootapirest.models.LibroString;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String titulo);

    List<Libro> findByCategoria(String categoria);

    List<Libro> findByAutor(String autor);


}
