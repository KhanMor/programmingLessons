package jaxbwork.jaxbwrappers;

import models.LessonTestResult;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 */
@XmlRootElement(name = "lessonTestResults")
public class LessonTestResultsWrapper extends WrapperSuper<LessonTestResult> {
    public LessonTestResultsWrapper() {
        super();
    }

    public LessonTestResultsWrapper(List<LessonTestResult> objects) {
        super(objects);
    }

    public List<LessonTestResult> getObjects() {
        return objects;
    }

    @XmlElement(name="lessonTestResult")
    public void setObjects(List<LessonTestResult> objects) {
        this.objects = objects;
    }
}
