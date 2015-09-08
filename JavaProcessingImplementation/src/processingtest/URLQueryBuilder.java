/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingtest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author harsha
 */
public class URLQueryBuilder {

    private static final String HOST = "http://sensor-api.localdata.com/api/v1/aggregations?";
    private final String FOV_SOURCE = "ci4xs6uhw000403zz9za2guib";
    private final String FOV_CITY = "Bangalore";
    private final String DATA_FIELDS = "temperature,light,humidity,airquality_raw,sound,dust";
    private final String DATA_RESOLUTION = "60m";
    private final String DATA_OPERATION = "mean";
    private final long ONE_DAY_MILL = 86400000;
    private final long ONE_HOUR_MILL = 3600000;

    private String from;
    private String before;
    private String fields;
    private String resolution;
    private String op;
    private String city;
    private String source;

    private final TimeZone tz;
    private final DateFormat dateFormat;

    private String URL;

    public URLQueryBuilder() {
        this.fields = this.DATA_FIELDS;
        this.resolution = this.DATA_RESOLUTION;
        this.op = this.DATA_OPERATION;
        this.city = this.FOV_CITY;
        this.source = this.FOV_SOURCE;

        tz = TimeZone.getTimeZone("IST");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(tz);

    }

    public String getDayDataByLocation(long sampleDuration) {
        this.fields = this.DATA_FIELDS;
        this.resolution = this.DATA_RESOLUTION;
        this.op = this.DATA_OPERATION;
        this.city = this.FOV_CITY;

        Calendar cal = Calendar.getInstance();
        long t = cal.getTimeInMillis();

        this.before = dateFormat.format(cal.getTime());
        this.from = dateFormat.format(new Date(t - sampleDuration * ONE_DAY_MILL));
        System.out.println(before);
        System.out.println(from);

        this.URL = URLQueryBuilder.HOST + "over.city=" + this.city + "&fields=" + fields + "&op=" + op + "&from=" + from + "&before=" + before + "&resolution=" + resolution;
        System.out.println(this.URL);
        return this.URL;
    }

    public String getDayDataBySource(long sampleDuration) throws URISyntaxException, IOException {
        this.fields = this.DATA_FIELDS;
        this.resolution = this.DATA_RESOLUTION;
        this.op = this.DATA_OPERATION;
        this.source = this.FOV_SOURCE;

        Calendar cal = Calendar.getInstance();
        long t = cal.getTimeInMillis();
        this.before = dateFormat.format(cal.getTime());
        this.from = dateFormat.format(new Date(t - sampleDuration * ONE_DAY_MILL));
        System.out.println(before);
        System.out.println(from);

        this.URL = URLQueryBuilder.HOST + "each.sources=" + source + "&fields=" + fields + "&op=" + op + "&from=" + from + "&before=" + before + "&resolution=" + resolution;
        System.out.println(this.URL);
        return this.URL;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
