package models;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "orderNum", "theme", "duration", "content", "scoreToPass", "lessonTests"})
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

    @XmlTransient
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

    @XmlElementWrapper
    @XmlElement(name = "lessonTest")
    public void setLessonTests(List<LessonTest> lessonTests) {
        this.lessonTests = lessonTests;
    }
}
