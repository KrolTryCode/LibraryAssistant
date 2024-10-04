package libraryassistant.dto;

import libraryassistant.entities.EventType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class BookEventDTO {
    private UUID id;
    private LocalDateTime eventDate;
    private EventType eventType;
    private ReaderDTO reader;
    private BookDTO book;
}