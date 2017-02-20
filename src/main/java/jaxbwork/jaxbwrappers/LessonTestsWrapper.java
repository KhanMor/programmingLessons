package jaxbwork.jaxbwrappers;

import models.LessonTest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 */
@XmlRootElement(name = "lessonTests")
public class LessonTestsWrapper extends WrapperSuper<LessonTest> {
    public LessonTestsWrapper() {
        super();
    }

    public LessonTestsWrapper(List<LessonTest> objects) {
        super(objects);
    }

    public List<LessonTest> getObjects() {
        return objects;
    }

    @XmlElement(name="lessonTest")
    public void setObjects(List<LessonTest> objects) {
        this.objects = objects;
    }
}