package telran.java30.book.servise;

import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.java30.book.dao.AutorRepository;
import telran.java30.book.dao.BookRepository;
import telran.java30.book.dao.PublisherRepository;
import telran.java30.book.dto.AuthorDto;
import telran.java30.book.dto.BookDto;
import telran.java30.book.exeption.BookNotFoundExeption;
import telran.java30.book.model.Author;
import telran.java30.book.model.Book;
import telran.java30.book.model.Publisher;

@Service
public class BookServiseImpl implements BookServise {
	@Autowired
	PublisherRepository publisherRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	AutorRepository autorRepository;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		String publisherName = bookDto.getPublisher();
		Publisher publisher = publisherRepository.findById(publisherName)
				.orElse(publisherRepository.save(new Publisher(publisherName)));

		Set<AuthorDto> authorDtos = bookDto.getAuthors();
		Set<Author> authors = authorDtos.stream()
				.map(a -> autorRepository.findById(a.getName())
						.orElse(autorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(long isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(()->  new BookNotFoundExeption(isbn));
		
		return bookToBookDto(book);
	}

	private BookDto bookToBookDto(Book book) {
		
		return BookDto.builder().isbn(book.getIsbn())
				.title(book.getTitle())
				.publisher(book.getPublisher().getPublisherName())
				.authors(book.getAuthors().stream().map(this::autorToAutorDto).collect(Collectors.toSet()))
				.build();
	}
	
	private AuthorDto autorToAutorDto(Author author) {
		return AuthorDto.builder().name(author.getName())
				.birthDate(author.getBirthDate()).build();
	}

	@Override
	@Transactional
	public BookDto removeBook(long isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> new BookNotFoundExeption(isbn));
		if(book== null) return null;
	bookRepository.deleteById(isbn);
		return bookToBookDto(book);
	}
	
	

}
