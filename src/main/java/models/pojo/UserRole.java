package models.pojo;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Mordr on 18.02.2017.
 * Сущность, отражающая какие права есть у конкретного пользователя
 */
@XmlRootElement
@XmlType(propOrder = {"id", "user", "role"})
@Entity
public class UserRole {
    private Integer id;
    private User user;
    private Role role;

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
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

    @JoinColumn(nullable = false)
    @ManyToOne(cascade={CascadeType.MERGE}, fetch= FetchType.EAGER)
    @JsonManagedReference
    public User getUser() {
        return user;
    }

    @XmlElement
    public void setUser(User user) {
        this.user = user;
    }

    @JoinColumn(nullable = false)
    @ManyToOne(cascade={CascadeType.MERGE}, fetch= FetchType.EAGER)
    @JsonManagedReference
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

        return id.equals(userRole.id) && user.equals(userRole.user) && role.equals(userRole.role);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}
