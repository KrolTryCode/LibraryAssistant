package libraryassistant.controller;

import libraryassistant.dto.ReaderDTO;
import libraryassistant.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;

    /**
     * Создание читателя.
     *
     * @param readerDTO DTO читателя для создания.
     * @return Ответ с созданным читателем и статусом 201 (CREATED).
     * Пример запроса:
     * curl -X POST http://localhost:8080/readers -H "Content-Type: application/json" -d "{\"firstName\":\"name1\", \"secondName\":\"name11\", \"birthDate\":\"2000-01-01\"}"
     */
    @PostMapping
    public ResponseEntity<ReaderDTO> addReader(@RequestBody ReaderDTO readerDTO) {
        return new ResponseEntity<>(readerService.createReader(readerDTO), HttpStatus.CREATED);
    }

    /**
     * Обновление читателя.
     *
     * @param id Идентификатор читателя для обновления.
     * @param updatedReaderDTO DTO с обновленными данными читателя.
     * @return Ответ с обновленным читателем и статусом 200 (OK).
     * Пример запроса:
     * curl -X PUT http://localhost:8080/readers/{input id} -H "Content-Type: application/json" -d "{\"firstName\":\"name1\", \"secondName\":\"name12\", \"birthDate\":\"2000-01-01\"}"
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReaderDTO> updateReader(@PathVariable UUID id, @RequestBody ReaderDTO updatedReaderDTO) {
        return new ResponseEntity<>(readerService.updateReader(id, updatedReaderDTO), HttpStatus.OK);
    }

    /**
     * Поиск читателя по ID.
     *
     * @param id Идентификатор читателя для поиска.
     * @return Ответ с найденным читателем и статусом 200 (OK).
     * Пример запроса:
     * curl -X GET http://localhost:8080/readers/{input id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReaderDTO> getReaderById(@PathVariable UUID id) {
        return new ResponseEntity<>(readerService.findReaderById(id), HttpStatus.OK);
    }

    /**
     * Поиск читателя по имени.
     *
     * @param firstName Имя читателя.
     * @param secondName Фамилия читателя.
     * @return Ответ с найденным читателем и статусом 200 (OK).
     * Пример запроса:
     * curl -X GET "http://localhost:8080/readers/search?firstName=name1&secondName=name12"
     */
    @GetMapping("/search")
    public ResponseEntity<ReaderDTO> getReaderByFullName(@RequestParam String firstName, @RequestParam String secondName) {
        return new ResponseEntity<>(readerService.findReaderByFullName(firstName, secondName), HttpStatus.OK);
    }

    /**
     * Поиск всех читателей.
     *
     * @return Ответ с списком всех читателей и статусом 200 (OK).
     * Пример запроса:
     * curl -X GET http://localhost:8080/readers
     */
    @GetMapping
    public ResponseEntity<List<ReaderDTO>> getAllReaders() {
        return new ResponseEntity<>(readerService.findAllReaders(), HttpStatus.OK);
    }

    /**
     * Удаление читателя по ID.
     *
     * @param id Идентификатор читателя для удаления.
     * @return Ответ со статусом 204 (NO_CONTENT).
     * Пример запроса:
     * curl -X DELETE http://localhost:8080/readers/{input id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReaderById(@PathVariable UUID id) {
        readerService.deleteReaderById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
