package jaxbwork;

import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Mordr on 19.02.2017.
 */
public class XmlConverter {
    private static final Logger logger = Logger.getLogger(XmlConverter.class);

    public void marshallObject(Object object, Class clazz, String filename) {
        try {
            File file = new File(filename);
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object, file);
        } catch (JAXBException e) {
            logger.error(e);
        }
    }
    public Object unmarshallObject(Class clazz, String filename) {
        try {
            File file = new File(filename);
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object object = jaxbUnmarshaller.unmarshal(file);
            return object;
        } catch (JAXBException e) {
            logger.error(e);
        }
        return null;
    }
}
