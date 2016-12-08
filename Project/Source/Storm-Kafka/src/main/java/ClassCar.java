import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class ClassCar extends BaseBasicBolt {

    private static final Logger LOG = LoggerFactory.getLogger(ClassCar.class);
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            String s = tuple.getString(0);
            System.out.println(s);
            double[] feature = fromString(s);
            Boolean check = checkClassCar(feature);
            insertIntoMongoDB(check);
            basicOutputCollector.emit(new Values("empty",check));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("context","status"));
    }

    private static double[] fromString(String string) {
        String[] strings = string.split(" ");
        double result[] = new double[strings.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.parseDouble(strings[i]);
        }
        return result;
    }

    public static void insertIntoMongoDB(Boolean check) {
        String API_KEY = "kQQiqaKTOjX4tvQQN67BaT0a0k8M9eIM";
        String DATABASE_NAME = "realtime";
        String COLLECTION_NAME = "metadata";
        String urlString = "https://api.mlab.com/api/1/databases/" +
                DATABASE_NAME + "/collections/" + COLLECTION_NAME + "?apiKey=" + API_KEY;
        LOG.info(urlString);

        StringBuilder result = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Context", "ClasscCar");
            jsonObject.put("Decision", check);
            jsonObject.put("Timestamp", System.currentTimeMillis());
            writer.write(jsonObject.toString());
            LOG.info(jsonObject.toString());
            writer.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Uploaded data to Mongo");

    }

   public Boolean checkClassCar(double[] feature) {
       if (feature[90] <= -128.0)
           if (feature[93] <= -117.0)
               if (feature[6] <= -126.0)
                   if (feature[122] <= -128.0)
                       if (feature[68] <= -57.0)
                           return true;
                       else if (feature[68] > -57.0)
                           return true;
                       else if (feature[122] > -128.0)
                           if (feature[29] <= -119.0)
                               return true;
                           else if (feature[29] > -119.0)
                               return false;
                           else if (feature[6] > -126.0)
                               if (feature[35] <= -128.0)
                                   if (feature[98] <= -122.0)
                                       return true;
                                   else if (feature[98] > -122.0)
                                       return false;
                                   else if (feature[35] > -128.0)
                                       if (feature[62] <= -128.0)
                                           return false;
                                       else if (feature[62] > -128.0)
                                           return true;
                                       else if (feature[93] > -117.0)
                                           if (feature[126] <= -128.0)
                                               if (feature[123] <= -127.0)
                                                   if (feature[43] <= -112.0)
                                                       return true;
                                                   else if (feature[43] > -112.0)
                                                       return true;
                                                   else if (feature[123] > -127.0)
                                                       if (feature[113] <= -125.0)
                                                           return true;
                                                       else if (feature[113] > -125.0)
                                                           return false;
                                                       else if (feature[126] > -128.0)
                                                           if (feature[110] <= -70.0)
                                                               if (feature[70] <= -13.0)
                                                                   return false;
                                                               else if (feature[70] > -13.0)
                                                                   return false;
                                                               else if (feature[110] > -70.0)
                                                                   if (feature[8] <= -33.0)
                                                                       return false;
                                                                   else if (feature[8] > -33.0)
                                                                       return false;
                                                                   else if (feature[90] > -128.0)
                                                                       if (feature[29] <= -128.0)
                                                                           if (feature[68] <= -119.0)
                                                                               if (feature[53] <= -128.0)
                                                                                   if (feature[20] <= -92.0)
                                                                                       return true;
                                                                                   else if (feature[20] > -92.0)
                                                                                       return false;
                                                                                   else if (feature[53] > -128.0)
                                                                                       if (feature[122] <= -26.0)
                                                                                           return false;
                                                                                       else if (feature[122] > -26.0)
                                                                                           return false;
                                                                                       else if (feature[68] > -119.0)
                                                                                           if (feature[41] <= -97.0)
                                                                                               if (feature[70] <= -128.0)
                                                                                                   return true;
                                                                                               else if (feature[70] > -128.0)
                                                                                                   return false;
                                                                                               else if (feature[41] > -97.0)
                                                                                                   if (feature[97] <= -124.0)
                                                                                                       return false;
                                                                                                   else if (feature[97] > -124.0)
                                                                                                       return false;
                                                                                                   else if (feature[29] > -128.0)
                                                                                                       if (feature[81] <= -120.0)
                                                                                                           if (feature[94] <= -113.0)
                                                                                                               if (feature[1] <= -123.0)
                                                                                                                   return false;
                                                                                                               else if (feature[1] > -123.0)
                                                                                                                   return false;
                                                                                                               else if (feature[94] > -113.0)
                                                                                                                   if (feature[3] <= -112.0)
                                                                                                                       return false;
                                                                                                                   else if (feature[3] > -112.0)
                                                                                                                       return false;
                                                                                                                   else if (feature[81] > -120.0)
                                                                                                                       if (feature[99] <= -127.0)
                                                                                                                           if (feature[123] <= -123.0)
                                                                                                                               return false;
                                                                                                                           else if (feature[123] > -123.0)
                                                                                                                               return false;
                                                                                                                           else if (feature[99] > -127.0)
                                                                                                                               if (feature[14] <= -34.0)
                                                                                                                                   return false;
                                                                                                                               else if (feature[14] > -34.0)
                                                                                                                                   return false;
       return false;
   }


}
