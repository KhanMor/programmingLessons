package jaxbwork.jaxbwrappers;

import models.pojo.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 * Обертка для коллекции пользователей
 */
@XmlRootElement(name = "users")
public class UsersWrapper extends WrapperSuper<User>{
    public UsersWrapper() {
        super();
    }

    public UsersWrapper(List<User> objects) {
        super(objects);
    }

    public List<User> getObjects() {
        return objects;
    }

    @XmlElement(name="user")
    public void setObjects(List<User> objects) {
        this.objects = objects;
    }
}
