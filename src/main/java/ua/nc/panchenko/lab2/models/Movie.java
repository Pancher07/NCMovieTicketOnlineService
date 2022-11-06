package ua.nc.panchenko.lab2.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lab2_pdv_movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotEmpty(message = "Title shouldn`t empty")
    private String title;
    @Column
    @NotEmpty(message = "Genre shouldn`t empty")
    private String genre;
    @Column
    @NotEmpty(message = "Country shouldn`t empty")
    private String country;
    @Column(name = "movie_language")
    @NotEmpty(message = "Language shouldn`t empty")
    private String movieLanguage;
    @Column
    @NotEmpty(message = "Starring shouldn`t empty")
    private String starring;
    @Column(name = "directed_by")
    @NotEmpty(message = "\"Directed by\" shouldn`t empty")
    private String directedBy;
    @Column(name = "screenplay_by")
    @NotEmpty(message = "\"Screenplay by\" shouldn`t empty")
    private String screenplayBy;
    @Column
    @NotEmpty(message = "Duration shouldn`t empty")
    private String duration;
    @Column
    @NotEmpty(message = "Description shouldn`t empty")
    private String description;

    @OneToMany(mappedBy = "movieOfSessions")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Session> sessions;
}