package jaxbwork.jaxbwrappers;

import models.entity.Role;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 * Обертка для коллекции ролей
 */
@XmlRootElement(name = "roles")
public class RolesWrapper extends WrapperSuper<Role> {
    public RolesWrapper() {
        super();
    }

    public RolesWrapper(List<Role> objects) {
        super(objects);
    }

    public List<Role> getObjects() {
        return objects;
    }

    @XmlElement(name="role")
    public void setObjects(List<Role> objects) {
        this.objects = objects;
    }
}
