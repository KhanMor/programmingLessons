package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by Mordr on 18.02.2017.
 */
@XmlRootElement
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

    @XmlTransient
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
}
