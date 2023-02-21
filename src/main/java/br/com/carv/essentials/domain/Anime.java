package br.com.carv.essentials.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_anime")
public class Anime extends GenericEntity implements Serializable {

    @Column(nullable = false, length = 100)
    private String name;

    public Anime() {
    }

    public Anime(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "name='" + name + '\'' +
                '}';
    }
}
