package models.pojo;

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
@Deprecated
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonTestResult that = (LessonTestResult) o;

        if (!id.equals(that.id)) return false;
        if (!lessonTest.equals(that.lessonTest)) return false;
        if (!user.equals(that.user)) return false;
        if (!testDateTime.equals(that.testDateTime)) return false;
        return score.equals(that.score);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + lessonTest.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + testDateTime.hashCode();
        result = 31 * result + score.hashCode();
        return result;
    }
}
