package models.pojo;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 */
@XmlRootElement
@XmlType(propOrder = {"id", "role", "description"})
public class Role {
    private Integer id;
    private String role;
    private String description;
    private List<UserRole> userRoles;

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    @XmlTransient
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        if (!id.equals(role1.id)) return false;
        if (!role.equals(role1.role)) return false;
        return description != null ? description.equals(role1.description) : role1.description == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
