package models.pojo;

import models.entity.UserRole;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Mordr on 18.02.2017.
 * Сущность, отражающая какие права есть у конкретного пользователя
 */
@XmlRootElement
@XmlType(propOrder = {"id", "user", "role"})
public class UserRolePOJO {
    private Integer id;
    private UserPOJO user;
    private RolePOJO role;
    private Long version;

    public UserRolePOJO() {
    }

    public UserRolePOJO(UserPOJO user, RolePOJO role) {
        this.user = user;
        this.role = role;
    }

    public UserRolePOJO(UserRole userRole) {
        this.user = new UserPOJO(userRole.getUser());
        this.role = new RolePOJO(userRole.getRole());
    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public UserPOJO getUser() {
        return user;
    }

    @XmlElement
    public void setUser(UserPOJO user) {
        this.user = user;
    }

    public RolePOJO getRole() {
        return role;
    }

    @XmlElement
    public void setRole(RolePOJO role) {
        this.role = role;
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

        UserRolePOJO that = (UserRolePOJO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return role != null ? role.equals(that.role) : that.role == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
