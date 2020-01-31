package com.css.pos.view.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;


public class WSUtils {

    public void getAllOrgs() {
        try {
            URL url = new URL(AppConfig.getProperty("orgservice"));
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", AppConfig.getProperty("AuthorizationKey"));
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    /** if it necessarry get url verfication */
                    //return HttpsURLConnection.getDefaultHostnameVerifier().verify("your_domain.com", session);
                    return true;
                }
            });
            conn.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
            System.out.println("output is " + output);
            //            JSONObject xx = new JSONObject(output);
            //            JSONArray json = xx.getJSONArray("items");
            //            for (int i = 0; i < json.length(); i++) {
            //                JSONObject ff = json.getJSONObject(i);
            //                System.out.println(ff.get("OrganizationId") + " " + ff.get("OrganizationCode") + " " +
            //                                   ff.get("OrganizationName") + " " + ff.get("ManagementBusinessUnitId"));
            //            }


            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
