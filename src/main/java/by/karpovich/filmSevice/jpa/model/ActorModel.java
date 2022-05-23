package by.karpovich.filmSevice.jpa.model;

import by.karpovich.filmSevice.jpa.converter.RewardForActorConverter;
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
@Table(name = "actors")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class ActorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private Instant dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryModel placeOfBirth;

    @Column(name = "height", nullable = false)
    private Integer height;

//    private byte[] image;

    @Column(name = "awards")
    @Convert(converter = RewardForActorConverter.class)
    private Set<RewardForActor> awards = new HashSet<>();

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    private List<FilmModel> films = new ArrayList<>();

    @CreatedDate
    @Column(name = "date_of_creation", updatable = false)
    private Instant dateOfCreation;

    @LastModifiedDate
    @Column(name = "date_of_change")
    private Instant dateOfChange;
}