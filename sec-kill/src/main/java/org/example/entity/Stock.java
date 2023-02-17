package org.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private long number;

    @Column(name = "version")
    private long version;

    public Stock(String name , long number){
        this.name = name;
        this.number = number;
        this.version =0;
    }
}
