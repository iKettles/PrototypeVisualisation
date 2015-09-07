/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processingtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author harsha
 */
public class CitySense {

    URLQueryBuilder queryBuilder;

    public CitySense() {
        queryBuilder = new URLQueryBuilder();
    }

    private CloseableHttpResponse executeRequest(String URL) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(URL);
        System.out.println(httpget.getURI());
        CloseableHttpResponse response = httpclient.execute(httpget);
        return response;
    }

    public ArrayList<CityData> getDayDataByLocation() throws IOException {
        String query = queryBuilder.getDayDataByLocation(2); //Over 2 days
        CloseableHttpResponse response = this.executeRequest(query);
        HttpEntity entity = response.getEntity();
        ArrayList<CityData> dataPoints = null;

        if (entity != null) {
            try ( // A Simple JSON Response Read
                    InputStream instream = entity.getContent()) {
                String result = convertStreamToString(instream);
                // now you have the string representation of the HTML request
                //System.out.println("RESPONSE: " + result);

                JSONParser parser = new JSONParser();
                try {
                    Object obj = parser.parse(result);
                    JSONObject jobj = (JSONObject) obj;
                    //System.out.println(jobj.entrySet());
                    Object dataObject = jobj.get("data");
                    JSONArray array = (JSONArray) dataObject;
                    //System.out.println(array.get(0));
                    dataPoints = decodeJsonData(array);
                    System.out.println((dataPoints.size()));
                } catch (ParseException pe) {
                    System.out.println("position: " + pe.getPosition());
                    System.out.println(pe);
                }
            }
        }
        return dataPoints;
    }

    public ArrayList<CityData> getDayDataBySource() throws URISyntaxException, IOException {
        String query = queryBuilder.getDayDataBySource(2); //Over 2 days
        CloseableHttpResponse response = this.executeRequest(query);
        HttpEntity entity = response.getEntity();
        ArrayList<CityData> dataPoints = null;

        if (entity != null) {
            try ( // A Simple JSON Response Read
                    InputStream instream = entity.getContent()) {
                String result = convertStreamToString(instream);
                // now you have the string representation of the HTML request
                //System.out.println("RESPONSE: " + result);

                JSONParser parser = new JSONParser();
                try {
                    Object obj = parser.parse(result);
                    JSONObject jobj = (JSONObject) obj;
                    //System.out.println(jobj.entrySet());
                    Object dataObject = jobj.get("data");
                    JSONArray array = (JSONArray) dataObject;
                    //System.out.println(array.get(0));
                    dataPoints = decodeJsonData(array);
                    System.out.println((dataPoints.size()));
                } catch (ParseException pe) {
                    System.out.println("position: " + pe.getPosition());
                    System.out.println(pe);
                }
            }
        }
        return dataPoints;
    }

    private ArrayList<CityData> decodeJsonData(JSONArray array) {
        ArrayList<CityData> dataPoints = new ArrayList<>();
        for (Object array1 : array) {
            JSONObject arrayItem = (JSONObject) array1;
            Double temp = Double.parseDouble(arrayItem.get("temperature").toString());
            Double light = Double.parseDouble(arrayItem.get("light").toString());
            Double humidity = Double.parseDouble(arrayItem.get("humidity").toString());
            Double airQuality = Double.parseDouble(arrayItem.get("airquality_raw").toString());
            Double sound = Double.parseDouble(arrayItem.get("sound").toString());
            Double dust = Double.parseDouble(arrayItem.get("dust").toString());
            CityData tempData = new CityData(temp, light, humidity, airQuality, sound, dust, (String) arrayItem.get("timestamp"), (String) arrayItem.get("source"), (String) arrayItem.get("city"));
            dataPoints.add(tempData);
        }
        return dataPoints;
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        line = reader.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = reader.readLine();
        }
        is.close();
        return sb.toString();
    }

//    public static void main(String args[]) {
//        CitySense cs = new CitySense();
//        try {
//            cs.getDayDataByLocation();
//        } catch (IOException ex) {
//            Logger.getLogger(CitySense.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
