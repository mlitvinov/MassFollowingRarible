package com.rare;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Rarible {
    private static final List<String> list = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        loadAddresses();
        subscribe();
    }

    private static void subscribe() throws Exception {
        while (list.size() != 0) {
            String id = list.get(list.size() - 1);
            System.out.println("Subscribing to " + id);
            sendRequest("https://api-mainnet.rarible.com/users/" + id + "/follow");
            System.out.println("Removing from the list " + id);
            list.remove(id);
            System.out.println("Checking the rest of the addresses " + list.size());
        }
        System.out.println("The list is empty");
    }


    private static void loadAddresses() {
        try (BufferedReader br = new BufferedReader(new FileReader("addresses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendRequest(String url) throws Exception {
        String token = "";

        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("x-auth-token", token);
        connection.setRequestProperty("content-type", "application/json");

        try (OutputStream output = connection.getOutputStream()) {
            output.write("{}".getBytes(StandardCharsets.UTF_8));
        }
        try {
            InputStream response = connection.getInputStream();
            System.out.println(response);
        } catch (IOException e) {
            System.err.println("Connection not available " + e);
        }
    }
}

