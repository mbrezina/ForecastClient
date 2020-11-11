package cz.burjanova.forecast.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "star_location")
public class StarLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String sub;
    private String location;

}

