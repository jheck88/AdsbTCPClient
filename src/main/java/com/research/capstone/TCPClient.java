package com.research.capstone;

import org.json.JSONArray;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

class TCPClient {
    public static void main(String argv[]) throws Exception {
        TCPClient tcpClient = new TCPClient();

        while (true) {
            tcpClient.run();
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        }
    }

    private void run() {

        URL url = null;
        HttpURLConnection con = null;
        BufferedReader in = null;
        try {
            url = new URL("https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json?lat=39.849312&lng=-112.008113&fDstL=0&fDstU=250&trFmt=sa");

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            List<Map> listOfMaps = parseAndPackage(content);

            storeInRedis(listOfMaps);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            con.disconnect();

        }
    }

    private void storeInRedis(List<Map> listOfMaps) {
        //Connecting to Redis server on localhost
        Jedis jedis = new Jedis("localhost");
        LocalDateTime minutes = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");

        String key = "aircraft-list";


        jedis.lpush(key, listOfMaps.toString());

//        List<String> items = jedis.lrange(key, 0, -1);
    }

    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private List<Map> parseAndPackage(StringBuffer content) {
        List<Map> acListOfObjects = new ArrayList<>();
        try {

            String jsonInString = content.toString();

            JSONObject jsonObj = new JSONObject(jsonInString);

            JSONArray acListJsonArray = (JSONArray) jsonObj.get("acList");

            JSONObject obj = acListJsonArray.getJSONObject(0);

            for (Object jsonObject : acListJsonArray) {
                JSONObject keySetObject = (JSONObject) jsonObject;

                Map<String, Object> mapObject = keySetObject.toMap();

                acListOfObjects.add(mapObject);
            }


        } catch (Exception e) {
            throw e;
        }

        return acListOfObjects;
    }
}