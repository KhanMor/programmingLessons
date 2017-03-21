package models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Mordr on 16.02.2017.
 * Сущность отражающая курсы информационной ссистемы
 */
@XmlRootElement
@XmlType(propOrder = {"id", "author", "name", "duration"})
@Entity
public class Course implements Serializable {
    private Integer id;
    private User author;
    private String name;
    private Double duration;
    private List<Lesson> lessons;
    private Long version;

    public Course() {
    }

    public Course(User author, String name, Double duration) {
        this.author = author;
        this.name = name;
        this.duration = duration;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JoinColumn(nullable = false)
    @ManyToOne(cascade={CascadeType.MERGE}, fetch= FetchType.LAZY)
    @JsonManagedReference
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

    @OneToMany(cascade={CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy="course")
    @JsonBackReference
    public List<Lesson> getLessons() {
        return lessons;
    }

    @XmlTransient
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        return id.equals(course.id) && author.equals(course.author) && (name != null ? name.equals(course.name) :
                course.name == null) && (duration != null ? duration.equals(course.duration) :
                course.duration == null);
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
