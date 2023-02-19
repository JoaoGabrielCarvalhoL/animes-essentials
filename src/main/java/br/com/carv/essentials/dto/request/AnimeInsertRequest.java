package br.com.carv.essentials.dto.request;

public class AnimeInsertRequest {

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
