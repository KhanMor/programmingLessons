package models.pojo;

import models.entity.Course;
import models.entity.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Mordr on 16.02.2017.
 * Сущность отражающая курсы информационной ссистемы
 */
@XmlRootElement
@XmlType(propOrder = {"id", "author", "name", "duration"})
public class CoursePOJO {
    private Integer id;
    private UserPOJO author;
    @Size(min = 2, max = 100)
    private String name;
    @Max(value = 9999)
    @Min(value = 0)
    private Double duration;
    private List<LessonPOJO> lessons;
    private Long version;

    public CoursePOJO() {
    }

    public CoursePOJO(Course course) {
        this.id = course.getId();
        User author = course.getAuthor();
        if (author != null) {
            this.author = new UserPOJO(author);
        }
        this.name = course.getName();
        this.duration = course.getDuration();
        this.version = course.getVersion();
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

    public UserPOJO getAuthor() {
        return author;
    }

    @XmlElement
    public void setAuthor(UserPOJO author) {
        this.author = author;
    }

    public Double getDuration() {
        return duration;
    }

    @XmlElement
    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public List<LessonPOJO> getLessons() {
        return lessons;
    }

    @XmlTransient
    public void setLessons(List<LessonPOJO> lessons) {
        this.lessons = lessons;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoursePOJO that = (CoursePOJO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return duration != null ? duration.equals(that.duration) : that.duration == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }
}
