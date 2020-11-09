package cz.burjanova.forecast.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_location")
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String sub;
    private String location;

}

