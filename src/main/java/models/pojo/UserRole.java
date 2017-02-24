package models.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Mordr on 18.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "user", "role"})
public class UserRole {
    private Integer id;
    private User user;
    private Role role;

    public UserRole() {
    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    @XmlElement
    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    @XmlElement
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (!id.equals(userRole.id)) return false;
        if (!user.equals(userRole.user)) return false;
        return role.equals(userRole.role);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}
