package com.alurachallenge.literalura.principal;

import com.alurachallenge.literalura.models.Author;
import com.alurachallenge.literalura.models.Book;
import com.alurachallenge.literalura.models.BookData;
import com.alurachallenge.literalura.models.LibreryData;
import com.alurachallenge.literalura.repository.IBookRepository;
import com.alurachallenge.literalura.services.ConvertData;
import com.alurachallenge.literalura.services.RequestAPI;
import org.aspectj.apache.bcel.Repository;
import org.aspectj.apache.bcel.classfile.Module;
import org.hibernate.resource.transaction.backend.jta.internal.synchronization.ExceptionMapper;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner reader = new Scanner(System.in);
    private RequestAPI request = new RequestAPI();
    private ConvertData convert = new ConvertData();
    private IBookRepository repositoryBook;
    private final String URL_BASE ="https://gutendex.com/books/";

    private List<Book> book;
    public Principal(IBookRepository repository) {
        this.repositoryBook = repository;
    }

    public void show(){
        var swich = true;

        while(swich){
            var menu = """
                    ##############################
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos apartir de una fecha
                    5 - Listar libros por idioma
                    
                    0. Salir
                    ##############################
                    """;

            System.out.println(menu);
            var option = reader.nextInt();
            reader.nextLine();

            switch (option){
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listAuthorsByDate();
                    break;
                case 5:
                    listBookByLanguage();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación!");
                    swich = false;
                    break;
                default:
                    System.out.println("Opcion inválida, verifica por favor");
            }
        }
    }



    private LibreryData getData() {
        try{
            // Buscar libros por titulos
            System.out.println("Escribe el título del libro que deseas buscar:");
            String title = reader.nextLine();

            System.out.println(true);

            String url = URL_BASE + "?search=" + title.replace(" ", "%20");
            var json = request.getData(url);

            var librery = convert.getData(json, LibreryData.class);

            return librery;
        }catch (Exception e){
            throw e;
        }
    }
    private void searchBookByTitle() {
        try{
            var data = getData().data();

            List<Book> book = data.stream()
                    .map(e -> new Book(e))
                    .collect(Collectors.toList());

            System.out.println(book.toString());

            List<Author> authors = data.stream()
                    .flatMap(d -> d.author().stream()
                            .map(e -> new Author(e)))
                    .collect(Collectors.toList());

            book.get(0).setAuthors(authors.get(0));

            repositoryBook.save(book.get(0));

        }catch (Exception e){
            System.out.println("Ha ocurrido un error al almacenar el libro en la base de datos!");
        }
    }
    private void listRegisteredBooks() {
        book = repositoryBook.findAll();

        if (book.isEmpty()){
            System.out.println("Aun no se encuentran libros registrados!");
        }else{
            book.forEach(System.out::println);
        }
    }
    private void listRegisteredAuthors() {
        List<Author> authors = repositoryBook.findByAuthor();

        if (authors.isEmpty()){
            System.out.println("Aun no se encuentran autores registrados!");
        }else{
            authors.forEach(System.out::println);
        }
    }
    private void listAuthorsByDate() {
        System.out.println("Ingrese el año del que desea conocer los autores vivos: ");
        var age = reader.nextInt();

        List<Author> aliveAuthors = repositoryBook.getAliveAuthors(age);

        List<Author> filterAuthor = aliveAuthors.stream()
                .filter(a -> a.getDeathYear() > 0)
                .collect(Collectors.toList());

        if (filterAuthor.isEmpty()){
            System.out.println("No hemos encontrado autores vivos en el año seleccionado!");
        }else{
            filterAuthor.forEach(System.out::println);
        }

    }

    private void listBookByLanguage() {
        List<Book> foundBooks = repositoryBook.findAll();

        List<String> lenguajes = foundBooks.stream()
                .map(l -> l.getLanguages())
                .distinct()
                .toList();

        System.out.println("Escribe el idioma de las opciones que tenemos: ");
        lenguajes.forEach(System.out::println);

        var lenguage = reader.nextLine();


        List<Book> filterBook = foundBooks.stream()
                .filter( b -> b.getLanguages().contains(lenguage))
                .collect(Collectors.toList());

        if(filterBook.isEmpty()){
            System.out.println("No hemos encontrado  ningún libro con el idioma seleccionado!!");
        }else{
            filterBook.forEach(System.out::println);
        }

    }
}
