package telran.java30.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java30.book.model.Author;

public interface AutorRepository extends JpaRepository<Author, String> {

}
