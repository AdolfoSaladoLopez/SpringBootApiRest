package com.adolfo.springbootapirest.controllers;

import com.adolfo.springbootapirest.models.Libro;
import com.adolfo.springbootapirest.models.LibroString;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.adolfo.springbootapirest.repositories.LibroRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/biblioteca")
public class LibroController {

    @Autowired
    LibroRepository repo;

    @GetMapping()
    public List<Libro> getLibros() {
        return repo.findAll();
    }

    @GetMapping("/libros")
    public List<LibroString> traerLibrosString() {
        List<Libro> libros = repo.findAll();
        List<LibroString> librosString = new ArrayList<>();

        libros.forEach(
                libro -> {
                    var uno = new LibroString();
                    uno.setId(libro.getId());
                    uno.setTitulo(libro.getTitulo());

                    librosString.add(uno);
                }
        );

        return librosString;
    }


    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Libro> getLibroTitulo(@PathVariable String titulo) {
        var libro = repo.findByTitulo(titulo);

        if (libro.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(libro.get(), HttpStatus.OK);
        }
    }

    @GetMapping("/categoria/{categoria}")
    public List<Libro> getLibrosCategoria(@PathVariable String categoria) {
        return repo.findByCategoria(categoria);
    }

    @GetMapping("/autor/{nombre}")
    public List<Libro> getLibroAutor(@PathVariable String nombre) {
        return repo.findByAutor(nombre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> get(@PathVariable Long id) {
        if (repo.existsById(id)) {
            return new ResponseEntity<>(repo.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Libro> post(@RequestBody Libro input) {
        repo.save(input);
        return new ResponseEntity<>(input, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Libro> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            Libro borrada = repo.getById(id);
            repo.deleteById(id);
            return new ResponseEntity<Libro>(borrada, HttpStatus.ACCEPTED);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarEjemplo(@PathVariable("id") Long id, @RequestBody Libro libro) {
        Libro recuperado = repo.getById(id);

        recuperado.setId(id);
        recuperado.setTitulo(libro.getTitulo());
        recuperado.setAutor(libro.getAutor());
        recuperado.setCategoria(libro.getCategoria());
        recuperado.setIsbn(libro.getIsbn());
        recuperado.setEdicion(libro.getEdicion());

        repo.save(recuperado);
        
        return new ResponseEntity<>(libro, HttpStatus.OK);
    }
}
