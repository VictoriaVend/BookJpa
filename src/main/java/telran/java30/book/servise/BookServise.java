package telran.java30.book.servise;

import telran.java30.book.dto.BookDto;

public interface BookServise {
	boolean addBook(BookDto bookDto);

	BookDto findBookByIsbn(long isbn);

	BookDto removeBook(long isbn);

}
