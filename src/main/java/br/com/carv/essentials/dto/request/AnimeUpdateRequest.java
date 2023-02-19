package br.com.carv.essentials.dto.request;

public class AnimeUpdateRequest {

    private Long id;
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
