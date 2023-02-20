package br.com.carv.essentials.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AnimeUpdateRequest {

    @NotBlank(message = "The id is required for update requests.")
    private Long id;
    @NotBlank(message = "The anime name cannot be empty.") @Size(max = 100, min = 5, message = "Maximum number of characters for this field is 100.")
    private String name;

    public AnimeUpdateRequest() {
    }

    public AnimeUpdateRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
