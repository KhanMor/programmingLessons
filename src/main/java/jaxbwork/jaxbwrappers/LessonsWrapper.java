package jaxbwork.jaxbwrappers;

import models.pojo.Lesson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 * Обертка для коллекции уроков
 */
@XmlRootElement(name = "lessons")
public class LessonsWrapper extends WrapperSuper<Lesson> {
    public LessonsWrapper() {
        super();
    }

    public LessonsWrapper(List<Lesson> objects) {
        super(objects);
    }

    public List<Lesson> getObjects() {
        return objects;
    }

    @XmlElement(name="lesson")
    public void setObjects(List<Lesson> objects) {
        this.objects = objects;
    }
}
