package telran.java30.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java30.book.model.Author;
import telran.java30.book.model.Book;

public interface AutorRepository extends JpaRepository<Author, String> {
 
}
