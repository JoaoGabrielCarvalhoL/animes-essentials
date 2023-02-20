package br.com.carv.essentials.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AnimeInsertRequest {

    @NotBlank(message = "The anime name cannot be empty") @Size(max = 100, min = 5, message = "Maximum number of characters for this field is 100.")
    private String name;

    public AnimeInsertRequest() {
    }

    public AnimeInsertRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
