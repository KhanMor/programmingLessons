package jaxbwork.jaxbadapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by Mordr on 19.02.2017.
 */
public class TimestampAdapter extends XmlAdapter<String, Timestamp> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Timestamp unmarshal(String v) throws Exception {
        synchronized (dateFormat) {
            java.util.Date date = dateFormat.parse(v);
            return new Timestamp(date.getTime());
        }
    }

    @Override
    public String marshal(Timestamp v) throws Exception {
        synchronized (dateFormat) {
            return dateFormat.format(v);
        }
    }
}
