package models.pojo;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "course", "orderNum", "theme", "duration", "content", "scoreToPass"})
public class Lesson {
    private Integer id;
    private Course course;
    private Integer orderNum;
    private String theme;
    private Double duration;
    private String content;
    private Integer scoreToPass;
    private List<LessonTest> lessonTests;

    public Lesson() {

    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    @XmlElement
    public void setCourse(Course course) {
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

    public Integer getScoreToPass() {
        return scoreToPass;
    }

    @XmlElement(type = Integer.class)
    public void setScoreToPass(Integer scoreToPass) {
        this.scoreToPass = scoreToPass;
    }

    public List<LessonTest> getLessonTests() {
        return lessonTests;
    }

    @XmlTransient
    public void setLessonTests(List<LessonTest> lessonTests) {
        this.lessonTests = lessonTests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;

        Lesson lesson = (Lesson) o;

        if (!id.equals(lesson.id)) return false;
        if (!course.equals(lesson.course)) return false;
        if (!orderNum.equals(lesson.orderNum)) return false;
        if (!theme.equals(lesson.theme)) return false;
        if (duration != null ? !duration.equals(lesson.duration) : lesson.duration != null) return false;
        if (content != null ? !content.equals(lesson.content) : lesson.content != null) return false;
        return scoreToPass.equals(lesson.scoreToPass);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + course.hashCode();
        result = 31 * result + orderNum.hashCode();
        result = 31 * result + theme.hashCode();
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + scoreToPass.hashCode();
        return result;
    }
}
