package com.exchangeratesnbu.serviсe;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class ServiceForConnection {
    public HttpURLConnection makeConnection(HttpURLConnection connection, String query, String requestMethod) {
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            connection.setRequestMethod(requestMethod);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        connection.setUseCaches(false);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }


    public void disconnectConnection(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }
}