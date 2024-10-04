package libraryassistant.controller;

import libraryassistant.dto.BookDTO;
import libraryassistant.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * Создание книги.
     *
     * @param bookDTO DTO книги для создания.
     * @return Ответ с созданной книгой и статусом 201 (CREATED).
     * Пример запроса:
     * curl -X POST http://localhost:8080/books -H "Content-Type: application/json" -d "{\"title\":\"book1\", \"author\":\"author1\"}"
     */
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    /**
     * Обновление книги.
     *
     * @param id Идентификатор книги для обновления.
     * @param updatedBookDTO DTO с обновленными данными книги.
     * @return Ответ с обновленной книгой и статусом 200 (OK).
     * Пример запроса:
     * curl -X PUT http://localhost:8080/books/<input id> -H "Content-Type: application/json" -d "{\"title\":\"book1_upd\", \"author\":\"author1\"}"
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable UUID id, @RequestBody BookDTO updatedBookDTO) {
        return new ResponseEntity<>(bookService.updateBook(id, updatedBookDTO), HttpStatus.OK);
    }

    /**
     * Поиск книги по ID.
     *
     * @param id Идентификатор книги для поиска.
     * @return Ответ с найденной книгой и статусом 200 (OK).
     * Пример запроса:
     * curl -X GET http://localhost:8080/books/{input id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID id) {
        return new ResponseEntity<>(bookService.findBookById(id), HttpStatus.OK);
    }

    /**
     * Поиск всех книг.
     *
     * @return Ответ с списком всех книг и статусом 200 (OK).
     * Пример запроса:
     * curl -X GET http://localhost:8080/books
     */
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }


    /**
     * Удаление книги по ID.
     *
     * @param id Идентификатор книги для удаления.
     * @return Ответ со статусом 204 (NO_CONTENT).
     * Пример запроса:
     * curl -X DELETE http://localhost:8080/books/{input id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable UUID id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

