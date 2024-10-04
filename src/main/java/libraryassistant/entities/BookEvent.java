package libraryassistant.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "events")
public class BookEvent {
    @Id
    @UuidGenerator
    private UUID id;

    //один читатель может учавствовать в нескольких ивентах
    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    //одна книга может учавствовать в нескольких ивентах
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime eventDate;

    @Enumerated(EnumType.STRING)
    private EventType eventType; // "BORROW" или "RETURN"
}
