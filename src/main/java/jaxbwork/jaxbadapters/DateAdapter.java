package jaxbwork.jaxbadapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Mordr on 16.02.2017.
 * Преобразование даты в строку и обратно для JAXB
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date unmarshal(String v) throws Exception {
        synchronized (dateFormat) {
            java.util.Date date = dateFormat.parse(v);
            return new Date(date.getTime());
        }
    }

    @Override
    public String marshal(Date v) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.format(v);
        }
    }
}
