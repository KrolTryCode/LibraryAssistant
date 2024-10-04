package libraryassistant.repository;

import libraryassistant.entities.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReaderRepository extends JpaRepository<Reader, UUID> {
    Optional<Reader> findByFirstNameAndSecondName(String firstName, String secondName);


}
