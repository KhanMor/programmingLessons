package jaxbwork.jaxbwrappers;

import models.pojo.Course;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 * Обертка для коллекции курсов
 */
@XmlRootElement(name = "courses")
public class CoursesWrapper extends WrapperSuper<Course> {
    public CoursesWrapper() {
        super();
    }

    public CoursesWrapper(List<Course> objects) {
        super(objects);
    }

    public List<Course> getObjects() {
        return objects;
    }

    @XmlElement(name="course")
    public void setObjects(List<Course> objects) {
        this.objects = objects;
    }
}
