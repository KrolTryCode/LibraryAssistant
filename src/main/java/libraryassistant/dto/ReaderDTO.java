package libraryassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDTO {
    private UUID id;
    private String firstName;
    private String secondName;
    private LocalDate birthDate;
}
