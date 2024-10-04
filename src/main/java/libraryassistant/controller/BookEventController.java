package libraryassistant.controller;

import libraryassistant.dto.BookDTO;
import libraryassistant.dto.BookEventDTO;
import libraryassistant.dto.ReaderDTO;
import libraryassistant.entities.EventType;
import libraryassistant.service.BookEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class BookEventController {
    private final BookEventService bookEventService;

    /**
     * Создание события.
     *
     * @param event DTO события для создания.
     * @return Ответ с созданным событием и статусом 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<BookEventDTO> createEvent(@RequestBody BookEventDTO event) {
        return new ResponseEntity<>(bookEventService.createEvent(event), HttpStatus.CREATED);
    }

    /**
     * Создание события по именам.
     *
     * @param firstName Имя читателя.
     * @param secondName Фамилия читателя.
     * @param bookTitle Название книги.
     * @param eventType Тип события (BORROW или RETURN).
     * @return Ответ с созданным событием и статусом 201 (CREATED).
     * Пример запроса:
     * curl -X POST "http://localhost:8080/events/create?firstName=name1&secondName=name12&bookTitle=book1&eventType=BORROW"
     * curl -X POST "http://localhost:8080/events/create?firstName=name1&secondName=name12&bookTitle=book1&eventType=RETURN"
     */
    @PostMapping("/create")
    public ResponseEntity<BookEventDTO> createBookEvent(@RequestParam String firstName,
                                                        @RequestParam String secondName,
                                                        @RequestParam String bookTitle,
                                                        @RequestParam EventType eventType) {
        return new ResponseEntity<>(bookEventService.createBookEvent(firstName, secondName, bookTitle, eventType), HttpStatus.CREATED);
    }

    /**
     * Поиск самой популярной книги за период.
     *
     * @param startDate Дата начала периода.
     * @param endDate Дата конца периода.
     * @return Ответ с самой популярной книгой за указанный период и статусом 200 (OK).
     * Пример запроса:
     * curl -X GET "http://localhost:8080/events/most-popular-book?startDate=2023-01-01T00:00:00&endDate=2024-12-31T23:59:59"
     */
    @GetMapping("/most-popular-book")
    public ResponseEntity<BookDTO> getMostPopularBook(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return new ResponseEntity<>(bookEventService.findMostPopularBook(startDate, endDate), HttpStatus.OK);
    }

    /**
     * Поиск самого активного читателя за все время.
     *
     * @param startDate Дата начала периода.
     * @param endDate Дата конца периода.
     * @return Ответ с самым активным читателем за указанный период и статусом 200 (OK).
     * Пример запроса:
     *  curl -X GET "http://localhost:8080/events/most-active-reader?startDate=2023-01-01T00:00:00&endDate=2024-12-31T23:59:59"
     */
    @GetMapping("/most-active-reader")
    public ResponseEntity<ReaderDTO> getMostActiveReader(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return new ResponseEntity<>(bookEventService.findMostActiveReader(startDate, endDate), HttpStatus.OK);
    }

    /**
     * Поиск всех событий.
     *
     * @return Ответ с списком всех событий и статусом 200 (OK).
     * Пример запроса:
     * curl -X GET http://localhost:8080/events
     */
    @GetMapping
    public ResponseEntity<List<BookEventDTO>> getAllEvents() {
        return new ResponseEntity<>(bookEventService.findAllEvents(), HttpStatus.OK);
    }
}


