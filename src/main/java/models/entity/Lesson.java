package models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import models.pojo.LessonPOJO;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Created by Mordr on 18.02.2017.
 * Сущность, отражающая уроки входящие  в курс
 */
@XmlRootElement
@XmlType(propOrder = {"id", "course", "orderNum", "theme", "duration", "content"})
@Entity
public class Lesson implements Serializable {
    private Integer id;
    private Course course;
    private Integer orderNum;
    private String theme;
    private Double duration;
    private String content;
    private Long version;

    public Lesson() {

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

    @JoinColumn(nullable = false)
    @ManyToOne(cascade={CascadeType.MERGE}, fetch= FetchType.LAZY)
    @JsonManagedReference
    public Course getCourse() {
        return course;
    }

    @XmlElement
    public void setCourse(Course course) {
        this.course = course;
    }

    @Column(nullable = false)
    public Integer getOrderNum() {
        return orderNum;
    }

    @XmlElement(type = Integer.class)
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Column(nullable = false)
    public String getTheme() {
        return theme;
    }

    @XmlElement(type = String.class)
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Double getDuration() {
        return duration;
    }

    @XmlElement(type = Double.class)
    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    @XmlElement(type = String.class)
    public void setContent(String content) {
        this.content = content;
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
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        return id.equals(lesson.id) && course.equals(lesson.course) && orderNum.equals(lesson.orderNum) &&
                theme.equals(lesson.theme) && (duration != null ? duration.equals(lesson.duration) :
                lesson.duration == null) && (content != null ? content.equals(lesson.content) : lesson.content == null);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + course.hashCode();
        result = 31 * result + orderNum.hashCode();
        result = 31 * result + theme.hashCode();
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
