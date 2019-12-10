package telran.java30.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java30.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
