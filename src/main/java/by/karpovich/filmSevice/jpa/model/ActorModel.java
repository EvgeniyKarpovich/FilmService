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
import java.util.List;

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

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryModel placeOfBirth;

    @Column(name = "height")
    private Integer height;

    @Column(name = "awards")
    @Convert(converter = RewardForActorConverter.class)
    private List<RewardForActor> awards = new ArrayList<>();

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    private List<FilmModel> films = new ArrayList<>();

    @CreatedDate
    @Column(name = "date_of_creation", updatable = false)
    private Instant dateOfCreation;

    @LastModifiedDate
    @Column(name = "date_of_change")
    private Instant dateOfChange;
}
