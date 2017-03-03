package jaxbwork.jaxbwrappers;

import jaxbwork.XmlConverter;

import java.util.List;

/**
 * Created by Mordr on 19.02.2017.
 * Обертка-суперкласс для других оберток
 */
public class WrapperSuper<T> {
    protected List<T> objects;

    public WrapperSuper() {
    }

    public WrapperSuper(List<T> objects) {
        this.objects = objects;
    }

    public void xmlMarshall(String filename) {
        XmlConverter xmlConverter = new XmlConverter();
        xmlConverter.marshallObject(this, this.getClass(), filename);
    }

    public Object xmlUnmarshall(String filename) {
        XmlConverter xmlConverter = new XmlConverter();
        Object object = xmlConverter.unmarshallObject(this.getClass(), filename);
        return object;
    }
}
