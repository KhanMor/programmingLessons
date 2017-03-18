package models.pojo;

import jaxbwork.jaxbadapters.DateAdapter;
import models.entity.User;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Date;
import java.util.List;

/**
 * Created by Mordr on 16.02.2017.
 * Сущность, отражающая пользователя системы
 */
@XmlRootElement
@XmlType(propOrder = {"id", "firstName", "surname", "patronymic", "birthday", "sex", "email", "password"})
public class UserPOJO {
    private Integer id;
    private String firstName;
    private String surname;
    private String patronymic;
    private Date birthday;
    private String sex;
    private String email;
    private String password;
    private List<CoursePOJO> coursesAuthor;
    private List<UserRolePOJO> userRoles;
    private Long version;

    public UserPOJO() {

    }

    public UserPOJO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.surname = user.getSurname();
        this.patronymic = user.getPatronymic();
        this.birthday = user.getBirthday();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.password = user.getPassword();
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

    public List<CoursePOJO> getCoursesAuthor() {
        return coursesAuthor;
    }

    @XmlTransient
    public void setCoursesAuthor(List<CoursePOJO> coursesAuthor) {
        this.coursesAuthor = coursesAuthor;
    }

    public List<UserRolePOJO> getUserRoles() {
        return userRoles;
    }

    @XmlTransient
    public void setUserRoles(List<UserRolePOJO> userRoles) {
        this.userRoles = userRoles;
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

        UserPOJO userPOJO = (UserPOJO) o;

        if (id != null ? !id.equals(userPOJO.id) : userPOJO.id != null) return false;
        if (firstName != null ? !firstName.equals(userPOJO.firstName) : userPOJO.firstName != null) return false;
        if (surname != null ? !surname.equals(userPOJO.surname) : userPOJO.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(userPOJO.patronymic) : userPOJO.patronymic != null) return false;
        if (birthday != null ? !birthday.equals(userPOJO.birthday) : userPOJO.birthday != null) return false;
        if (sex != null ? !sex.equals(userPOJO.sex) : userPOJO.sex != null) return false;
        if (email != null ? !email.equals(userPOJO.email) : userPOJO.email != null) return false;
        return password != null ? password.equals(userPOJO.password) : userPOJO.password == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
