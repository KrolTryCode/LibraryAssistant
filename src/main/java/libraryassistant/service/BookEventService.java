package libraryassistant.service;

import jakarta.persistence.EntityNotFoundException;
import libraryassistant.dto.BookDTO;
import libraryassistant.dto.BookEventDTO;
import libraryassistant.dto.ReaderDTO;
import libraryassistant.entities.Book;
import libraryassistant.entities.BookEvent;
import libraryassistant.entities.EventType;
import libraryassistant.entities.Reader;
import libraryassistant.exeption.custom.BookAlreadyBorrowedException;
import libraryassistant.mapper.Mapper;
import libraryassistant.repository.BookEventRepository;
import libraryassistant.repository.BookRepository;
import libraryassistant.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookEventService {

    private final BookEventRepository bookEventRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final Mapper mapper;

    public boolean isBookBorrowed(UUID bookId) {
        Optional<BookEvent> lastEvent = bookEventRepository.findFirstByBookIdOrderByEventDateDesc(bookId);

        return lastEvent.isPresent() && lastEvent.get().getEventType() == EventType.BORROW;
    }

    public BookEventDTO createBookEvent(String firstName, String secondName, String bookTitle, EventType eventType) {
        Reader reader = readerRepository.findByFirstNameAndSecondName(firstName, secondName)
                .orElseThrow(() -> new EntityNotFoundException("Данный читатель не найден: " + firstName + " " + secondName));
        Book book = bookRepository.findByTitle(bookTitle)
                .orElseThrow(() -> new EntityNotFoundException("Данная книга не найдена: " + bookTitle));

        if (eventType == EventType.BORROW && isBookBorrowed(book.getId())) {
            throw new BookAlreadyBorrowedException("Книга уже взята и не была возвращена");
        }

        if (eventType == EventType.RETURN && !isBookBorrowed(book.getId())) {
            throw new BookAlreadyBorrowedException("Книга не была взята ранее, возврат невозможен");
        }

        BookEvent bookEvent = new BookEvent();
        bookEvent.setReader(reader);
        bookEvent.setBook(book);
        bookEvent.setEventDate(LocalDateTime.now());
        bookEvent.setEventType(eventType);

        return mapper.toBookEventDTO(bookEventRepository.save(bookEvent));
    }

    public BookEventDTO createEvent(BookEventDTO eventDTO) {
        BookEvent event = mapper.toBookEvent(eventDTO);
        BookEvent savedEvent = bookEventRepository.save(event);
        return mapper.toBookEventDTO(savedEvent);
    }

    public List<BookEventDTO> findAllEvents() {
        return bookEventRepository.findAll().stream().map(mapper::toBookEventDTO).toList();
    }

    public BookDTO findMostPopularBook(LocalDateTime startDate, LocalDateTime endDate) {
        List<BookEvent> bookEvents = bookEventRepository.findByEventDateBetweenAndEventType(startDate, endDate, EventType.BORROW);
        return bookEvents.stream()
                .collect(Collectors.groupingBy(BookEvent::getBook, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor()))
                .orElse(null);
    }

    public ReaderDTO findMostActiveReader(LocalDateTime startDate, LocalDateTime endDate) {
        List<BookEvent> bookEvents = bookEventRepository.findByEventDateBetween(startDate, endDate);
        return bookEvents.stream()
                .collect(Collectors.groupingBy(BookEvent::getReader, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(reader -> new ReaderDTO(reader.getId(), reader.getFirstName(), reader.getSecondName(), reader.getBirthDate()))
                .orElse(null);
    }
}
