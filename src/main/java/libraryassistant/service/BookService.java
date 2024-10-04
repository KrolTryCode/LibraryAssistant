package libraryassistant.service;

import libraryassistant.dto.BookDTO;
import libraryassistant.entities.Book;
import libraryassistant.exeption.custom.BookAlreadyExistException;
import libraryassistant.exeption.custom.BookNotFoundException;
import libraryassistant.mapper.Mapper;
import libraryassistant.repository.BookRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final Mapper mapper;

    public BookDTO createBook(BookDTO bookDTO) {
        if (bookRepository.findByTitle(bookDTO.getTitle()).isPresent()) {
            throw new BookAlreadyExistException("Такая книга уже существует");
        }
        Book book = mapper.toBook(bookDTO);
        System.out.println(book.getAuthor());
        System.out.println(book.getTitle());
        return mapper.toBookDTO(bookRepository.save(book));
    }

    public BookDTO findBookById(UUID id) {
        return mapper.toBookDTO(bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с id " + id + " не найдена")));

    }

    public BookDTO updateBook(UUID id, BookDTO updatedBookDTO) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBookDTO.getTitle());
            book.setAuthor(updatedBookDTO.getAuthor());
            return mapper.toBookDTO(bookRepository.save(book));
        }).orElseThrow(() -> new BookNotFoundException("Книга с id " + id + " не найдена"));
    }

    public void deleteBookById(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Книга с id " + id + " не найдена");
        }
        bookRepository.deleteById(id);
    }

    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream().map(mapper::toBookDTO).toList();
    }
}
