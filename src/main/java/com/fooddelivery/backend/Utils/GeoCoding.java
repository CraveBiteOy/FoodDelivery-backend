package com.fooddelivery.backend.Utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.backend.Exception.BadResultException;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Request.OrderRequest;
import com.fooddelivery.backend.Models.Request.RestaurantRequest;

@Component
public class GeoCoding {
    @Value("${GEOCODING_RESOURCE}")
    private String GEOCODING_RESOURCE;

    @Value("${API_KEY}")
	private String API_KEY;
    

    public RestaurantRequest geoLocationEncode(RestaurantRequest request) {
       try {
        HttpClient httpClient = HttpClient.newHttpClient();
        String address = request.getAddress() + " ," + request.getZipcode() + " " + request.getCity()  + " , finland";
        String addressEncode = URLEncoder.encode(address, "UTF-8");
        String uriRequest = GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&q=" + addressEncode;

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(uriRequest)).timeout(Duration.ofMillis(2000)).build();

        String res = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println(res);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resJson = mapper.readTree(res);
        JsonNode items = resJson.get("items");
        if(items != null) {
            for(JsonNode item : items) {
                JsonNode location = item.get("position");
                Double latitude = location.get("lat") != null ? location.get("lat").asDouble() : null;
                Double longitude = location.get("lng") != null ? location.get("lng").asDouble() : null;
                System.out.println("longitude: " + longitude + " latitude: " + latitude);
                if(latitude != null) {
                    // request.setLatitude(Long.valueOf(latitude));
                     request.setLatitude(latitude);
                }
                if(longitude != null) {
                    // request.setLongitude(Long.valueOf(longitude));
                    request.setLongitude(longitude);
                }
            }
        }


        return request;
       } catch (InterruptedException ex) {
        throw new BadResultException(ex.getMessage());
       
       } catch (IOException ex) {
        throw new BadResultException(ex.getMessage());
       }
        
    }

    public OrderRequest geoLocationEncode(OrderRequest request) {
       try {
        HttpClient httpClient = HttpClient.newHttpClient();
        String address = request.getAddress() + " ," + request.getZipcode() + " " + request.getCity()  + " , finland";
        String addressEncode = URLEncoder.encode(address, "UTF-8");
        String uriRequest = GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&q=" + addressEncode;

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(uriRequest)).timeout(Duration.ofMillis(2000)).build();

        String res = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println(res);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resJson = mapper.readTree(res);
        JsonNode items = resJson.get("items");
        if(items != null) {
            for(JsonNode item : items) {
                JsonNode location = item.get("position");
                Double latitude = location.get("lat") != null ? location.get("lat").asDouble() : null;
                Double longitude = location.get("lng") != null ? location.get("lng").asDouble() : null;
                System.out.println("longitude: " + longitude + " latitude: " + latitude);
                if(latitude != null) {
                    // request.setLatitude(Long.valueOf(latitude));
                     request.setToLatitude(latitude);
                }
                if(longitude != null) {
                    // request.setLongitude(Long.valueOf(longitude));
                    request.setToLongitude(longitude);
                }
            }
        }


        return request;
       } catch (InterruptedException ex) {
        throw new BadResultException(ex.getMessage());
       
       } catch (IOException ex) {
        throw new BadResultException(ex.getMessage());
       }
        
    }

    public Users geoLocationEncode(Users user, String address, String zipcode, String city) {
       try {
        HttpClient httpClient = HttpClient.newHttpClient();
        String addressStr = address + " ," + zipcode + " " + city  + " , finland";
        String addressEncode = URLEncoder.encode(addressStr, "UTF-8");
        String uriRequest = GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&q=" + addressEncode;

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(uriRequest)).timeout(Duration.ofMillis(2000)).build();

        String res = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println(res);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resJson = mapper.readTree(res);
        JsonNode items = resJson.get("items");
        if(items != null) {
            for(JsonNode item : items) {
                JsonNode location = item.get("position");
                Double latitude = location.get("lat") != null ? location.get("lat").asDouble() : null;
                Double longitude = location.get("lng") != null ? location.get("lng").asDouble() : null;
                System.out.println("longitude: " + longitude + " latitude: " + latitude);
                if(latitude != null) {
                    // request.setLatitude(Long.valueOf(latitude));
                     user.setLatitude(latitude);
                }
                if(longitude != null) {
                    // user.setLongitude(Long.valueOf(longitude));
                    user.setLongitude(longitude);
                }
            }
        }


        return user;
       } catch (InterruptedException ex) {
        throw new BadResultException(ex.getMessage());
       
       } catch (IOException ex) {
        throw new BadResultException(ex.getMessage());
       }
        
    }
}
