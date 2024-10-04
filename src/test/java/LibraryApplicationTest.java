import jakarta.transaction.Transactional;
import libraryassistant.LibraryApplication;
import libraryassistant.dto.BookDTO;
import libraryassistant.dto.BookEventDTO;
import libraryassistant.dto.ReaderDTO;
import libraryassistant.entities.EventType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LibraryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class LibraryApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private BookDTO book1;
    private BookDTO book2;
    private ReaderDTO reader1;
    private ReaderDTO reader2;

    @BeforeEach
    public void setUp() {
        // книга1
        BookDTO bookDto1 = new BookDTO();
        bookDto1.setTitle("abc1");
        bookDto1.setAuthor("a1");

        ResponseEntity<BookDTO> book1Response = restTemplate.postForEntity(getRootUrl() + "/books", bookDto1, BookDTO.class);
        book1 = book1Response.getBody();

        // книга2
        BookDTO bookDto2 = new BookDTO();
        bookDto2.setTitle("cbd2");
        bookDto2.setAuthor("b2");

        ResponseEntity<BookDTO> book2Response = restTemplate.postForEntity(getRootUrl() + "/books", bookDto2, BookDTO.class);
        book2 = book2Response.getBody();

        // читатель1
        ReaderDTO readerDto1 = new ReaderDTO();
        readerDto1.setFirstName("aaa_1");
        readerDto1.setSecondName("bbb_1");
        readerDto1.setBirthDate(LocalDate.parse("2003-03-03"));

        ResponseEntity<ReaderDTO> reader1Response = restTemplate.postForEntity(getRootUrl() + "/readers", readerDto1, ReaderDTO.class);
        reader1 = reader1Response.getBody();

        // читатель2
        ReaderDTO readerDto2 = new ReaderDTO();
        readerDto2.setFirstName("qqq_2");
        readerDto2.setSecondName("eee_2");
        readerDto2.setBirthDate(LocalDate.parse("2005-08-08"));

        ResponseEntity<ReaderDTO> reader2Response = restTemplate.postForEntity(getRootUrl() + "/readers", readerDto2, ReaderDTO.class);
        reader2 = reader2Response.getBody();
    }

    // ======= BookController Tests =======
    @Nested
    class BookTests {

        @Test
        @DirtiesContext
        public void testCreateBook() {
            BookDTO newBook = new BookDTO();
            newBook.setTitle("ccc3");
            newBook.setAuthor("c3");

            ResponseEntity<BookDTO> response = restTemplate.postForEntity(getRootUrl() + "/books", newBook, BookDTO.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("ccc3", response.getBody().getTitle());
        }

        @Test
        public void testGetBookById() {
            ResponseEntity<BookDTO> response = restTemplate.getForEntity(getRootUrl() + "/books/" + book1.getId(), BookDTO.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("abc1", response.getBody().getTitle());
        }

        @Test
        public void testUpdateBook() {
            BookDTO updatedBook = new BookDTO();
            updatedBook.setTitle("abc1_upd");
            updatedBook.setAuthor("a1_upd");

            restTemplate.put(getRootUrl() + "/books/" + book1.getId(), updatedBook);

            ResponseEntity<BookDTO> response = restTemplate.getForEntity(getRootUrl() + "/books/" + book1.getId(), BookDTO.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("abc1_upd", response.getBody().getTitle());
        }

        @Test
        @DirtiesContext
        public void testGetAllBooks() {
            ResponseEntity<BookDTO[]> response = restTemplate.getForEntity(getRootUrl() + "/books", BookDTO[].class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            List<BookDTO> books = List.of(response.getBody());
            assertFalse(books.isEmpty());
        }

        @Test
        public void testDeleteBook() {
            restTemplate.delete(getRootUrl() + "/books/" + book1.getId());

            ResponseEntity<BookDTO> response = restTemplate.getForEntity(getRootUrl() + "/books/" + book1.getId(), BookDTO.class);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    // ======= ReaderController Tests =======
    @Nested
    class ReaderTests {

        @Test
        public void testCreateReader() {
            ReaderDTO newReader = new ReaderDTO();
            newReader.setFirstName("xxx_3");
            newReader.setSecondName("vvv_3");
            newReader.setBirthDate(LocalDate.parse("2002-02-02"));

            ResponseEntity<ReaderDTO> response = restTemplate.postForEntity(getRootUrl() + "/readers", newReader, ReaderDTO.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("xxx_3", response.getBody().getFirstName());
        }

        @Test
        public void testGetReaderById() {
            ResponseEntity<ReaderDTO> response = restTemplate.getForEntity(getRootUrl() + "/readers/" + reader1.getId(), ReaderDTO.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("aaa_1", response.getBody().getFirstName());
        }

        @Test
        public void testUpdateReader() {
            ReaderDTO updatedReader = new ReaderDTO();
            updatedReader.setFirstName("aaa_1_upd");
            updatedReader.setSecondName("bbb_1_upd");
            updatedReader.setBirthDate(LocalDate.parse("2010-05-15"));

            restTemplate.put(getRootUrl() + "/readers/" + reader1.getId(), updatedReader);

            ResponseEntity<ReaderDTO> response = restTemplate.getForEntity(getRootUrl() + "/readers/" + reader1.getId(), ReaderDTO.class);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("aaa_1_upd", response.getBody().getFirstName());
        }

        @Test
        @DirtiesContext
        public void testGetAllReaders() {
            ResponseEntity<ReaderDTO[]> response = restTemplate.getForEntity(getRootUrl() + "/readers", ReaderDTO[].class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            List<ReaderDTO> readers = List.of(response.getBody());
            assertFalse(readers.isEmpty());
        }

        @Test
        public void testDeleteReader() {
            restTemplate.delete(getRootUrl() + "/readers/" + reader1.getId());

            ResponseEntity<ReaderDTO> response = restTemplate.getForEntity(getRootUrl() + "/readers/" + reader1.getId(), ReaderDTO.class);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        public void testGetReaderByFullName() {
            ResponseEntity<ReaderDTO> response = restTemplate.getForEntity(getRootUrl() + "/readers/search?firstName=aaa_1&secondName=bbb_1", ReaderDTO.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("aaa_1", response.getBody().getFirstName());
            assertEquals("bbb_1", response.getBody().getSecondName());
            assertEquals(LocalDate.parse("2003-03-03"), response.getBody().getBirthDate());
        }
    }

    // ======= BookEventController Tests =======
    @Nested
    class BookEventTests {

        @BeforeEach
        public void setUpEvents() {
            //событие1
            BookEventDTO event1 = new BookEventDTO();
            event1.setEventDate(LocalDateTime.now());
            event1.setEventType(EventType.BORROW);
            event1.setBook(book1);
            event1.setReader(reader1);

            restTemplate.postForEntity(getRootUrl() + "/events", event1, BookEventDTO.class);
            //событие2
            BookEventDTO event2 = new BookEventDTO();
            event2.setEventDate(LocalDateTime.of(2024,12,1,1,1,1));
            event2.setEventType(EventType.RETURN);
            event2.setBook(book2);
            event2.setReader(reader2);

            restTemplate.postForEntity(getRootUrl() + "/events", event2, BookEventDTO.class);
            //событие3
            BookEventDTO event3 = new BookEventDTO();
            event3.setEventDate(LocalDateTime.of(2024,12,1,3,1,1));
            event3.setEventType(EventType.BORROW);
            event3.setBook(book1);
            event3.setReader(reader1);

            restTemplate.postForEntity(getRootUrl() + "/events", event3, BookEventDTO.class);
        }

        @Test
        @DirtiesContext
        public void testCreateBookEvent() {
            BookEventDTO newEvent = new BookEventDTO();
            newEvent.setEventDate(LocalDateTime.now());
            newEvent.setEventType(EventType.BORROW);
            newEvent.setBook(book1);
            newEvent.setReader(reader1);

            ResponseEntity<BookEventDTO> response = restTemplate.postForEntity(getRootUrl() + "/events", newEvent, BookEventDTO.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(EventType.BORROW, response.getBody().getEventType());
        }

        @Test
        @DirtiesContext
        public void testGetMostPopularBook() {
            LocalDateTime startDate = LocalDateTime.of(2023,1,1,1,1);
            LocalDateTime endDate = LocalDateTime.now();

            ResponseEntity<BookDTO> response = restTemplate.getForEntity(
                    getRootUrl() + "/events/most-popular-book?startDate=" + startDate + "&endDate=" + endDate,
                    BookDTO.class
            );

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(book1.getTitle(), response.getBody().getTitle());
        }

        @Test
        public void testGetMostActiveReader() {
            LocalDateTime startDate = LocalDateTime.of(2023,1,1,1,1);
            LocalDateTime endDate = LocalDateTime.of(2025,1,1,1,1);

            ResponseEntity<ReaderDTO> response = restTemplate.getForEntity(
                    getRootUrl() + "/events/most-active-reader?startDate=" + startDate + "&endDate=" + endDate,
                    ReaderDTO.class
            );

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(reader1.getFirstName(), response.getBody().getFirstName());
        }

        @Test
        @DirtiesContext
        public void testGetAllEvents() {
            ResponseEntity<BookEventDTO[]> response = restTemplate.getForEntity(getRootUrl() + "/events", BookEventDTO[].class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            List<BookEventDTO> events = List.of(response.getBody());
            assertFalse(events.isEmpty());
        }
    }
}

