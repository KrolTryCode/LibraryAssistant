package libraryassistant.service;

import libraryassistant.dto.ReaderDTO;
import libraryassistant.entities.Reader;
import libraryassistant.exeption.custom.ReaderAlreadyExistException;
import libraryassistant.exeption.custom.ReaderNotFoundException;
import libraryassistant.mapper.Mapper;
import libraryassistant.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final Mapper mapper;

    public ReaderDTO createReader(ReaderDTO readerDTO) {
        if (readerRepository.findByFirstNameAndSecondName(readerDTO.getFirstName(), readerDTO.getSecondName()).isPresent()) {
            throw new ReaderAlreadyExistException("Такой читатель уже существует");
        }
        Reader reader = mapper.toReader(readerDTO);
        return mapper.toReaderDTO(readerRepository.save(reader));
    }

    public ReaderDTO findReaderById(UUID id) {
        return mapper.toReaderDTO(readerRepository.findById(id)
                .orElseThrow(() -> new ReaderNotFoundException("Читатель с id " + id + " не найден")));
    }

    public ReaderDTO findReaderByFullName(String firstName, String secondName) {
        return mapper.toReaderDTO(readerRepository.findByFirstNameAndSecondName(firstName, secondName)
                .orElseThrow(() -> new ReaderNotFoundException("Читатель с именем " + firstName + " " + secondName + " не найден")));
    }

    public ReaderDTO updateReader(UUID id, ReaderDTO updatedReaderDTO) {
        return readerRepository.findById(id).map(reader -> {
            reader.setFirstName(updatedReaderDTO.getFirstName());
            reader.setSecondName(updatedReaderDTO.getSecondName());
            reader.setBirthDate(updatedReaderDTO.getBirthDate());
            return mapper.toReaderDTO(readerRepository.save(reader));
        }).orElseThrow(() -> new ReaderNotFoundException("Читатель с id " + id + " не найден"));
    }

    public List<ReaderDTO> findAllReaders() {
        return readerRepository.findAll().stream().map(mapper::toReaderDTO).toList();
    }

    public void deleteReaderById(UUID id) {
        if (!readerRepository.existsById(id)) {
            throw new ReaderNotFoundException("Читатель с id " + id + " не найден");
        }
        readerRepository.deleteById(id);
    }
}
