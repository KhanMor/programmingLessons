package models.pojo;

import models.entity.Lesson;

import javax.xml.bind.annotation.*;

/**
 * Created by Mordr on 18.02.2017.
 * Сущность, отражающая уроки входящие  в курс
 */
@XmlRootElement
@XmlType(propOrder = {"id", "course", "orderNum", "theme", "duration", "content"})
public class LessonPOJO {
    private Integer id;
    private CoursePOJO course;
    private Integer orderNum;
    private String theme;
    private Double duration;
    private String content;
    private Long version;

    public LessonPOJO() {

    }

    public LessonPOJO(Lesson lesson) {
        this.id = lesson.getId();
        this.orderNum = lesson.getOrderNum();
        this.theme = lesson.getTheme();
        this.duration = lesson.getDuration();
        this.content = lesson.getContent();
        this.version = lesson.getVersion();
    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public CoursePOJO getCourse() {
        return course;
    }

    @XmlElement
    public void setCourse(CoursePOJO course) {
        this.course = course;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    @XmlElement(type = Integer.class)
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

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

        LessonPOJO that = (LessonPOJO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orderNum != null ? !orderNum.equals(that.orderNum) : that.orderNum != null) return false;
        if (theme != null ? !theme.equals(that.theme) : that.theme != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderNum != null ? orderNum.hashCode() : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
