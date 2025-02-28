package com.clinique.soap.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimestampAdapter extends XmlAdapter<String, Timestamp> {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public Timestamp unmarshal(String v) throws Exception {
        if (v == null || v.isEmpty()) {
            return null;
        }
        try {
            return new Timestamp(sdf.parse(v).getTime());
        } catch (ParseException e) {
            throw new Exception("Format de date incorrect. Attendu: yyyy-MM-dd'T'HH:mm:ss, reçu: " + v, e);
        }
    }

    @Override
    public String marshal(Timestamp v) {
        if (v == null) {
            return null;
        }
        return sdf.format(v);
    }
}
