package libraryassistant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "books")
public class Book {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<BookEvent> bookEvents;
}
