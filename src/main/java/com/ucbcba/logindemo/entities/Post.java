package com.ucbcba.logindemo.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @ManyToOne
    private Category category;

    private String text;

    private String link;

    private Integer likes = 0;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private
    List<Comment> comments;


    @ManyToOne
    private User user;

    public Post() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
