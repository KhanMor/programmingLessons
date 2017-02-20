package models;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Mordr on 16.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "author", "name", "duration", "price", "certified", "lessons"})
public class Course {
    private Integer id;
    private User author;
    private String name;
    private Double duration;
    private Double price;
    private boolean certified;
    private List<Lesson> lessons;

    public Course() {
    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    @XmlElement
    public void setAuthor(User author) {
        this.author = author;
    }

    public Double getDuration() {
        return duration;
    }

    @XmlElement
    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isCertified() {
        return certified;
    }

    @XmlElement
    public void setCertified(boolean certified) {
        this.certified = certified;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    @XmlElementWrapper
    @XmlElement(name = "lesson")
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
