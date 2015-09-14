/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingtest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author harsha
 */
public class CityData implements Comparable {

    private Double temperature;
    private Double light;
    private Double humidity;
    private Double airquality_raw;
    private Double sound;
    private Double dust;
    private String timestamp;
    private String source;
    private String city;

    public CityData(Double temperature, Double light, Double humidity, Double airquality_raw, Double sound, Double dust, String timestamp, String source, String city) {
        this.temperature = temperature;
        this.light = light;
        this.humidity = humidity;
        this.airquality_raw = airquality_raw;
        this.sound = sound;
        this.dust = dust;
        this.timestamp = timestamp;
        this.source = source;
        this.city = city;
    }

    public CityData() {
        this.temperature = 0.0;
        this.light = 0.0;
        this.humidity = 0.0;
        this.airquality_raw = 0.0;
        this.sound = 0.0;
        this.dust = 0.0;
        this.timestamp = null;
        this.source = null;
        this.city = null;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setLight(Double light) {
        this.light = light;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public void setAirquality_raw(Double airquality_raw) {
        this.airquality_raw = airquality_raw;
    }

    public void setSound(Double sound) {
        this.sound = sound;
    }

    public void setDust(Double dust) {
        this.dust = dust;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Date getTimestampasDate() throws ParseException {
        return getDate(this.timestamp);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        String data = "";
        data = data + getTimestamp() + getCity() + getSource();
        return data;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getLight() {
        return light;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getAirquality_raw() {
        return airquality_raw;
    }

    public Double getSound() {
        return sound;
    }

    public Double getDust() {
        return dust;
    }

    private Date getDate(String dateString) throws ParseException {
        //TimeZone tz = TimeZone.getTimeZone("IST"); 
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'");
        //dateFormat.setTimeZone(tz);
        Date date = dateFormat.parse(dateString);
        return date;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof CityData)) {
            return false;
        }
        CityData temp = (CityData) o;
        Date thisDate = null;
        Date tempDate = null;
        try {
            thisDate = getDate(this.timestamp);
            tempDate = getDate(temp.timestamp);
        } catch (ParseException ex) {
            Logger.getLogger(CityData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempDate.equals(thisDate);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.temperature);
        hash = 97 * hash + Objects.hashCode(this.light);
        hash = 97 * hash + Objects.hashCode(this.humidity);
        hash = 97 * hash + Objects.hashCode(this.airquality_raw);
        hash = 97 * hash + Objects.hashCode(this.sound);
        hash = 97 * hash + Objects.hashCode(this.dust);
        hash = 97 * hash + Objects.hashCode(this.timestamp);
        return hash;
    }

    @Override
    public int compareTo(Object t) {
        CityData temp = (CityData) t;
        Date thisDate = null;
        Date tempDate = null;
        try {
            thisDate = getDate(this.timestamp);
            tempDate = getDate(temp.timestamp);
        } catch (ParseException ex) {
            Logger.getLogger(CityData.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (thisDate.equals(tempDate)) {
            return 0;
        } else if (thisDate.before(tempDate)) {
            return -1;
        } else if (thisDate.after(tempDate)) {
            return 1;
        } else {
            System.out.println("Error in comparision");
            return -9; //ERROR
        }
    }
}
