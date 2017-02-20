package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "orderNum", "question", "answer", "points"})
public class LessonTest {
    private Integer id;
    private Lesson lesson;
    private Integer orderNum;
    private String question;
    private String answer;
    private Integer points;
    private List<LessonTestResult> lessonTestResults;

    public LessonTest() {
    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    @XmlTransient
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    @XmlElement
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getQuestion() {
        return question;
    }

    @XmlElement
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    @XmlElement
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getPoints() {
        return points;
    }

    @XmlElement
    public void setPoints(Integer points) {
        this.points = points;
    }

    public List<LessonTestResult> getLessonTestResults() {
        return lessonTestResults;
    }

    @XmlTransient
    public void setLessonTestResults(List<LessonTestResult> lessonTestResults) {
        this.lessonTestResults = lessonTestResults;
    }
}
