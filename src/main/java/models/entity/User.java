package models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jaxbwork.jaxbadapters.DateAdapter;
import models.pojo.UserPOJO;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by Mordr on 16.02.2017.
 * Сущность, отражающая пользователя системы
 */
@XmlRootElement
@XmlType(propOrder = {"id", "firstName", "surname", "patronymic", "birthday", "sex", "email", "password"})
@Entity
public class User implements Serializable {
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
    private Long version;

    public User() {

    }

    public User(String firstName, String surname, String patronymic, Date birthday, String sex, String email, String password) {
        this.firstName = firstName;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.sex = sex;
        this.email = email;
        this.password = password;
    }

    public User(UserPOJO userPOJO) {
        this.id = userPOJO.getId();
        this.firstName = userPOJO.getFirstName();
        this.surname = userPOJO.getSurname();
        this.patronymic = userPOJO.getPatronymic();
        this.birthday = userPOJO.getBirthday();
        this.sex = userPOJO.getSex();
        this.email = userPOJO.getEmail();
        this.password = userPOJO.getPassword();
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

    @Column(unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(cascade={CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy="author")
    @JsonBackReference
    public List<Course> getCoursesAuthor() {
        return coursesAuthor;
    }

    @XmlTransient
    public void setCoursesAuthor(List<Course> coursesAuthor) {
        this.coursesAuthor = coursesAuthor;
    }

    @OneToMany(cascade={CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy="user")
    @JsonBackReference
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    @XmlTransient
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
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

        User user = (User) o;

        return id.equals(user.id) && (firstName != null ? firstName.equals(user.firstName) :
                user.firstName == null) && (surname != null ? surname.equals(user.surname) :
                user.surname == null) && (patronymic != null ? patronymic.equals(user.patronymic) :
                user.patronymic == null) && (birthday != null ? birthday.equals(user.birthday) :
                user.birthday == null) && (sex != null ? sex.equals(user.sex) :
                user.sex == null) && email.equals(user.email) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
