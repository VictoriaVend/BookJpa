package telran.java30.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java30.book.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {

}
