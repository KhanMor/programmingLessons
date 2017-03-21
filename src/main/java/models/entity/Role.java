package models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import models.pojo.RolePOJO;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 * Сушность, отражающая роли, которые могут быть
 * присвоенны пользователю системы
 */
@XmlRootElement
@XmlType(propOrder = {"id", "role", "description"})
@Entity
public class Role implements Serializable {
    private Integer id;
    private String role;
    private String description;
    private List<UserRole> userRoles;
    private Long version;

    public Role() {
    }

    public Role(RolePOJO rolePOJO) {
        this.id = rolePOJO.getId();
        this.role = rolePOJO.getRole();
        this.description = rolePOJO.getDescription();
        this.version = rolePOJO.getVersion();
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

    @Column(unique = true, nullable = false)
    public String getRole() {
        return role;
    }

    @XmlElement
    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade={CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy="role")
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

        Role role1 = (Role) o;

        return id.equals(role1.id) && role.equals(role1.role) &&
                (description != null ? description.equals(role1.description) : role1.description == null);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
