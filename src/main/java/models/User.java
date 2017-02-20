package models;

import jaxbwork.jaxbadapters.DateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Date;
import java.util.List;

/**
 * Created by Mordr on 16.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "firstName", "surname", "patronymic", "birthday", "sex", "email", "password", "userRoles"})
public class User {
    private Integer id;
    private String firstName;
    private String surname;
    private String patronymic;
    private Date birthday;
    private String sex;
    private String email;
    private String password;
    private List<Course> coursesAuthor;
    private List<UserRole> userRoles;
    private List<LessonTestResult> lessonTestResults;

    public User() {

    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    @XmlElement(type = String.class)
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    @XmlElement
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    @XmlElement
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    @XmlElement
    @XmlJavaTypeAdapter(DateAdapter.class)
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    @XmlElement
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Course> getCoursesAuthor() {
        return coursesAuthor;
    }

    @XmlTransient
    public void setCoursesAuthor(List<Course> coursesAuthor) {
        this.coursesAuthor = coursesAuthor;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    @XmlElementWrapper
    @XmlElement(name = "userRole")
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<LessonTestResult> getLessonTestResults() {
        return lessonTestResults;
    }

    @XmlTransient
    public void setLessonTestResults(List<LessonTestResult> lessonTestResults) {
        this.lessonTestResults = lessonTestResults;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coursesAuthor=" + coursesAuthor +
                ", userRoles=" + userRoles +
                '}';
    }
}
