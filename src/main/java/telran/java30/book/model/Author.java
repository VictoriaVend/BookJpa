package telran.java30.book.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "name" })
@Entity
public class Author implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	String name;
	@ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
	Set<Book> books;
	LocalDate birthDate;

	public Author(String name, LocalDate birthDate) {
		this.name = name;
		this.birthDate = birthDate;
	}

}
