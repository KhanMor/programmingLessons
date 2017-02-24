package models.pojo;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Mordr on 16.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "author", "name", "duration", "price", "certified"})
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

    @XmlTransient
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (!id.equals(course.id)) return false;
        if (!author.equals(course.author)) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        return duration != null ? duration.equals(course.duration) : course.duration == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }
}
