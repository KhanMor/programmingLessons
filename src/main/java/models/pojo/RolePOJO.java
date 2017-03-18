package models.pojo;

import models.entity.Role;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by Mordr on 18.02.2017.
 * Сушность, отражающая роли, которые могут быть
 * присвоенны пользователю системы
 */
@XmlRootElement
@XmlType(propOrder = {"id", "role", "description"})
public class RolePOJO {
    private Integer id;
    private String role;
    private String description;
    private List<UserRolePOJO> userRoles;
    private Long version;

    public RolePOJO() {
    }

    public RolePOJO(Role role) {
        this.id = role.getId();
        this.role = role.getRole();
        this.description = role.getDescription();
        this.version = role.getVersion();
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

        RolePOJO rolePOJO = (RolePOJO) o;

        if (id != null ? !id.equals(rolePOJO.id) : rolePOJO.id != null) return false;
        if (role != null ? !role.equals(rolePOJO.role) : rolePOJO.role != null) return false;
        return description != null ? description.equals(rolePOJO.description) : rolePOJO.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
