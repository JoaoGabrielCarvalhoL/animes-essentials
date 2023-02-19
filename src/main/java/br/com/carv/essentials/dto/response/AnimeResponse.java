package br.com.carv.essentials.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

@JsonPropertyOrder({"id", "name"})
public class AnimeResponse extends RepresentationModel<AnimeResponse> {

    @JsonProperty(value = "id")
    private Long key;
    private String name;

    public AnimeResponse() {
    }

    public AnimeResponse(Long key, String name) {
        this.key = key;
        this.name = name;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
