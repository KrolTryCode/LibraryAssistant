package libraryassistant.mapper;

import libraryassistant.dto.BookDTO;
import libraryassistant.dto.BookEventDTO;
import libraryassistant.dto.ReaderDTO;
import libraryassistant.entities.Book;
import libraryassistant.entities.BookEvent;
import libraryassistant.entities.Reader;
import libraryassistant.exeption.custom.BookNotFoundException;
import libraryassistant.exeption.custom.ReaderNotFoundException;
import libraryassistant.repository.BookRepository;
import libraryassistant.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Mapper {
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public Book toBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        return book;
    }

    public BookDTO toBookDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        return dto;
    }


    public Reader toReader(ReaderDTO readerDTO) {
        Reader reader = new Reader();
        reader.setId(readerDTO.getId());
        reader.setFirstName(readerDTO.getFirstName());
        reader.setSecondName(readerDTO.getSecondName());
        reader.setBirthDate(readerDTO.getBirthDate());
        return reader;
    }

    public ReaderDTO toReaderDTO(Reader reader) {
        ReaderDTO dto = new ReaderDTO();
        dto.setId(reader.getId());
        dto.setFirstName(reader.getFirstName());
        dto.setSecondName(reader.getSecondName());
        dto.setBirthDate(reader.getBirthDate());
        return dto;
    }


    public BookEvent toBookEvent(BookEventDTO bookEventDTO) {
        BookEvent bookEvent = new BookEvent();
        bookEvent.setId(bookEventDTO.getId());
        bookEvent.setEventDate(bookEventDTO.getEventDate());
        bookEvent.setEventType(bookEventDTO.getEventType());
        bookEvent.setReader(readerRepository.findById(bookEventDTO.getReader().getId())
                .orElseThrow(() -> new ReaderNotFoundException("Читатель с id " + bookEventDTO.getReader().getId() + " не найден")));
        bookEvent.setBook(bookRepository.findById(bookEventDTO.getBook().getId())
                .orElseThrow(() -> new BookNotFoundException("Книга с id " + bookEventDTO.getBook().getId() + " не найдена")));
        return bookEvent;
    }

    public BookEventDTO toBookEventDTO(BookEvent bookEvent) {
        BookEventDTO dto = new BookEventDTO();
        dto.setId(bookEvent.getId());
        dto.setEventDate(bookEvent.getEventDate());
        dto.setEventType(bookEvent.getEventType());
        dto.setReader(new ReaderDTO(bookEvent.getReader().getId(), bookEvent.getReader().getFirstName(), bookEvent.getReader().getSecondName(), bookEvent.getReader().getBirthDate()));
        dto.setBook(new BookDTO(bookEvent.getBook().getId(), bookEvent.getBook().getTitle(), bookEvent.getBook().getAuthor()));
        return dto;
    }
}
