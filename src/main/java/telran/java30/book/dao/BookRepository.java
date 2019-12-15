package telran.java30.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import telran.java30.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findBooksByAuthorsName(String authorName);

	List<Book> findBooksByPublisher(String publisherName);

	//@Query("select b.publisher.name from Book b where b.authors:?1")
	//List<String> findPublishersByAuthorsName(String authorName);

}
