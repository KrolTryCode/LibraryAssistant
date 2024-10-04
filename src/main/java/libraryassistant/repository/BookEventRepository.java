package libraryassistant.repository;

import libraryassistant.entities.EventType;
import libraryassistant.entities.BookEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookEventRepository extends JpaRepository<BookEvent, UUID> {

    List<BookEvent> findByEventDateBetweenAndEventType(LocalDateTime startDate, LocalDateTime endDate, EventType eventType);

    List<BookEvent> findByEventDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<BookEvent> findFirstByBookIdOrderByEventDateDesc(UUID bookId);
}

