package telran.java30.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java30.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findBooksByAuthorsName(String authorName);

	List<Book> findBooksByPublisher(String publisherName);

	@Query("select b.publisher.publisherName from Book b join b.authors a where a.name=?1")
	List<String> findPublishersByAuthorsName(String authorName);

}
