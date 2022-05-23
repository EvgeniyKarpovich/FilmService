package by.karpovich.filmSevice.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Films")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class FilmModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenreForFilm genre;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private DirectorModel director;

    @Column(name = "duration_in_minutes", nullable = false)
    private int durationInMinutes;

    @Column(name = "rating_IMDB", nullable = false)
    private double ratingIMDB;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryModel country;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ACTORS_FILMS",
            joinColumns = {@JoinColumn(name = "film_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id", referencedColumnName = "id")})
    private Set<ActorModel> actors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MUSICS_FILMS",
            joinColumns = {@JoinColumn(name = "film_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "music_id", referencedColumnName = "id")})
    private List<MusicModel> musics = new ArrayList<>();

    @Column(name = "description", nullable = false)
    private String description;

//    private byte[] image;

    @Column(name = "age_Limit", nullable = false)
    private int ageLimit;

    @CreatedDate
    @Column(name = "date_of_creation", updatable = false)
    private Instant dateOfCreation;

    @LastModifiedDate
    @Column(name = "date_of_change")
    private Instant dateOfChange;

}
