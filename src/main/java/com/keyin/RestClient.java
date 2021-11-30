package com.keyin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.IOException;
import java.net.HttpRetryException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class RestClient {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String request;
        System.out.println("Make a selection (1 = GET) (2 = POST) (3 = PUT) (4 = DELETE)");
        request = input.nextLine().toUpperCase();
        System.out.println();
        try {
            switch (request) {
                case "1" -> httpGetRequest();
                case "2" -> httpPostRequest();
                case "3" -> httpPutRequest();
                default -> httpDeleteRequest();
            }
        } catch (Exception e) {
            new Exception("Please select a valid number");
            e.printStackTrace();
        }
    }




    private static void httpGetRequest() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/member")).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("****" + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void httpPostRequest() throws IOException, InterruptedException {
        Map<Object, Object> info = new HashMap<>();
        info.put("firstName", "Brady");
        info.put("lastName", "Peters");
        info.put("address", "101 cannon cove");
        info.put("email", "example@hotmail.com");
        info.put("phone", "709-333-1134");


        info.put("Membership-duration", "4");
        info.put("Membership-type", "student");
        info.put("Current-tournament", "");
        info.put("Past-tournament", "");
        info.put("Future-tournament", "");
        info.put("MembershipStart-date", "01/04/2010");

        info.put("FinalStandings", "5th");
        info.put("Location", "Mile-one centre");
        info.put("Participation-members", "15");

        info.put("ts", System.currentTimeMillis());
        ObjectMapper infoMapper = new ObjectMapper();
        String stringy = infoMapper.writeValueAsString(info);
        try {
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/member"))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(stringy))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("httpPostRequest : " + responseBody);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpPutRequest() throws IOException, InterruptedException {
        Map<Object, Object> info = new HashMap<>();
        info.put("firstName", "Brady");
        info.put("lastName", "Peters");
        info.put("address", "101 cannon cove");
        info.put("email", "example@hotmail.com");
        info.put("phone", "709-333-1134");


        info.put("Membership-duration", "4");
        info.put("Membership-type", "student");
        info.put("Current-tournament", "");
        info.put("Past-tournament", "");
        info.put("Future-tournament", "");
        info.put("MembershipStart-date", "01/04/2010");


        info.put("entryFee", "$10");
        info.put("cashPrize", "$200");
        info.put("FinalStandings", "5th");
        info.put("Location", "Mile-one centre");
        info.put("Participation-members", "15");

        info.put("ts", System.currentTimeMillis());

        ObjectMapper infoMapper = new ObjectMapper();
        String stringy = infoMapper.writeValueAsString(info);
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/memberUpdate"))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(stringy))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("httpPostRequest : " + responseBody);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpDeleteRequest() throws IOException, InterruptedException {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/DeleteMember"))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-type", "application/json")
                    .DELETE()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("httpPostRequest" + responseBody);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}