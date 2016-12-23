package com.example.seedcommando_2.payhistory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mayur on 21/12/16.
 */

public class Myhtttp {
   // String result;
    public String fetchData() {
        System.out.print("STARTING");
        HttpURLConnection httpcon;
        String url = "http://sikam.slingapp.in:8080/sling/device/getpaymenthistory";
        String data = "{\"userName\":\"div.yansh\"}";
        String result = null;

        try {
            //Connect
            httpcon = (HttpURLConnection) ((new URL(url).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Accept", "application/json");
            httpcon.setRequestMethod("POST");
            httpcon.connect();
            System.out.print("CONNECTED");
            //Write
            OutputStream os = httpcon.getOutputStream();
            System.out.print("responce");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.close();
            os.close();

            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            System.out.print("result");
            result = sb.toString();
            System.out.print(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }


}



