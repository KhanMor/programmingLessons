package models;

import jaxbwork.jaxbadapters.TimestampAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;

/**
 * Created by Mordr on 18.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "lessonTest", "user", "testDateTime", "score"})
public class LessonTestResult {
    private Integer id;
    private LessonTest lessonTest;
    private User user;
    private Timestamp testDateTime;
    private Integer score;

    public LessonTestResult() {
    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public LessonTest getLessonTest() {
        return lessonTest;
    }

    @XmlElement
    public void setLessonTest(LessonTest lessonTest) {
        this.lessonTest = lessonTest;
    }

    public User getUser() {
        return user;
    }

    @XmlElement
    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTestDateTime() {
        return testDateTime;
    }

    @XmlElement
    @XmlJavaTypeAdapter(TimestampAdapter.class)
    public void setTestDateTime(Timestamp testDateTime) {
        this.testDateTime = testDateTime;
    }

    public Integer getScore() {
        return score;
    }

    @XmlElement
    public void setScore(Integer score) {
        this.score = score;
    }
}
