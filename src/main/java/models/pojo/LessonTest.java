package models.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "lesson", "orderNum", "question", "answer", "points"})
@Deprecated
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

    @XmlElement
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonTest that = (LessonTest) o;

        if (!id.equals(that.id)) return false;
        if (!lesson.equals(that.lesson)) return false;
        if (!orderNum.equals(that.orderNum)) return false;
        if (!question.equals(that.question)) return false;
        if (!answer.equals(that.answer)) return false;
        return points != null ? points.equals(that.points) : that.points == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + lesson.hashCode();
        result = 31 * result + orderNum.hashCode();
        result = 31 * result + question.hashCode();
        result = 31 * result + answer.hashCode();
        result = 31 * result + (points != null ? points.hashCode() : 0);
        return result;
    }
}
