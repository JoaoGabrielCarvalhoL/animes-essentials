package br.com.carv.essentials.dto.response;

public class AnimeResponseClient {

    private Long id;
    private String name;

    public AnimeResponseClient() {
    }

    public AnimeResponseClient(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setKey(Long key) {
        this.id = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AnimeResponseClient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
