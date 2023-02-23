package br.com.carv.essentials.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "tb_anime")
public class Anime extends GenericEntity implements Serializable {

    @Column(nullable = false, length = 100)
    @NotBlank(message = "The anime name cannot be empty") @Size(max = 100, min = 5, message = "Maximum number of characters for this field is 100.")
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
