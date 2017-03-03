package jaxbwork.jaxbwrappers;

import models.pojo.UserRole;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Mordr on 21.02.2017.
 * Обертка для коллекции ролей пользователей
 */
@XmlRootElement(name = "userRoles")
public class UserRolesWrapper extends WrapperSuper<UserRole>{
    public UserRolesWrapper() {
        super();
    }

    public UserRolesWrapper(List<UserRole> objects) {
        super(objects);
    }

    public List<UserRole> getObjects() {
        return objects;
    }

    @XmlElement(name="userRole")
    public void setObjects(List<UserRole> objects) {
        this.objects = objects;
    }
}
