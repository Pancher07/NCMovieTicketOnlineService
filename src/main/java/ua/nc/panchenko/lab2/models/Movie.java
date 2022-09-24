package ua.nc.panchenko.lab2.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "lab2_pdv_movie")
@Data //lombok аннотация: генерирует геттеры, сеттеры, equals, hashCode код методы
@NoArgsConstructor//lombok аннотация: конструктор без аргументов
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
 /* private String country;
    private String year;
    private String starring;
    private String producer;
    private String screenwriter;
    private int duration;
    private String description;*/
}