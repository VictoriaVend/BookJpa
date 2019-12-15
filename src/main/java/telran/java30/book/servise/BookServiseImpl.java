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
	AutorRepository authorRepository;

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
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(long isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundExeption(isbn));

		return bookToBookDto(book);
	}

	private BookDto bookToBookDto(Book book) {

		return BookDto.builder().isbn(book.getIsbn()).title(book.getTitle())
				.publisher(book.getPublisher().getPublisherName())
				.authors(book.getAuthors().stream().map(this::autorToAutorDto).collect(Collectors.toSet())).build();
	}

	private AuthorDto autorToAutorDto(Author author) {
		return AuthorDto.builder().name(author.getName()).birthDate(author.getBirthDate()).build();
	}

	@Override
	@Transactional
	public BookDto removeBook(long isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundExeption(isbn));
		bookRepository.deleteById(isbn);
		return bookToBookDto(book);
	}

	@Override
	public Iterable<BookDto> findBooksByAuthor(String authorName) {
		
		/*
		 * Author author = authorRepository.findById(authorName).orElseThrow(()->new
		 * BookNotFoundExeption(0));
		 * 
		 * return
		 * author.getBooks().stream().map(this::bookToBookDto).collect(Collectors.toSet(
		 * ));
		 */
		return bookRepository.findBooksByAuthorsName(authorName).stream().map(this::bookToBookDto).collect(Collectors.toSet());
	}

	@Override
	public Iterable<BookDto> findBooksByPublisher(String publisherName) {
		/*
		 * Publisher publisher =
		 * publisherRepository.findById(publisherName).orElseThrow(()-> new
		 * BookNotFoundExeption(0));
		 * 
		 * return
		 * publisher.getBooks().stream().map(this::bookToBookDto).collect(Collectors.
		 * toSet());
		 */
		return bookRepository.findBooksByPublisher(publisherName).stream().map(this::bookToBookDto).collect(Collectors.toSet());
	}

	@Override
	public Iterable<AuthorDto> findBookAuthor(Long isbn) {
	Book book = bookRepository.findById(isbn).orElseThrow(()-> new BookNotFoundExeption(isbn));
		return book.getAuthors().stream().map(this::autorToAutorDto).collect(Collectors.toSet());
	}

	@Override
	public Iterable<String> findPublishersByAuthor(String authorName) {
	
		//return publisherRepository.findByBooksAuthorsName(authorName).stream().map(p->p.getPublisherName()).collect(Collectors.toSet());
		return bookRepository.findBooksByAuthorsName(authorName).stream().map(b->b.getPublisher().getPublisherName()).collect(Collectors.toSet());
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElse(null);
		if (author == null) {
			return null;
		}
		authorRepository.deleteById(authorName);
		return autorToAutorDto(author);
	}

	@Override
	@Transactional
	public BookDto updateBook(Long isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundExeption(isbn));
	book.setTitle(title);
	return bookToBookDto(book);
	}

}
