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
    private String email;
    private String location1;
    private String location2;
    private String location3;


    public StarLocation(String sub, String email, String location1) {
        this.sub = sub;
        this.email = email;
        this.location1 = location1;
    }

    public StarLocation() {
    }
}

