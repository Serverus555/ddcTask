package me.serverus.ddcTask.model;

import javax.persistence.*;

@Entity
@Table(name="news")
public class News {

    public News() {

    }

    public News(String name, String shortDescription, String description, NewsType newsType) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.type = newsType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private String shortDescription;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name="type_id")
    private NewsType type;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NewsType getType() {
        return type;
    }

    public void setType(NewsType type) {
        this.type = type;
    }
}
