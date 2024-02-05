package com.csc340.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * RestApiController class for handling RESTful API requests.
 * 
 * @author Dylan Muroki
 */
@RestController
public class RestApiController {

    @GetMapping("/breaking")
    public Object getBreakingBadQuote() {
        try {
            String url = "https://api.breakingbadquotes.xyz/v1/quotes";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonResponse);

            // Print  to the console.
            System.out.println(root);

            // Parse most important info from the response.
            String quoteAuthor = root.get(0).get("author").asText();
            String quoteContent = root.get(0).get("quote").asText();
            System.out.println("Author: " + quoteAuthor);
            System.out.println("Quote: " + quoteContent);

            return root;

        } catch (JsonProcessingException ex) {
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE, null, ex);
            return "error in /breakingbadquote";
        }
    }

   @GetMapping("/basketball")
public Object getBasketballStandings() {
    try {
        // Define the API endpoint URL for basketball standings
        String url = "https://api-basketball.p.rapidapi.com/standings?league=12&season=2019-2020";

        // Set up headers for RapidAPI key
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Host", "api-basketball.p.rapidapi.com");
        headers.set("X-RapidAPI-Key", "6167f8cc1dmshab444b5314aa7c5p176ecdjsnf435e15f0845");  // Replace with your actual RapidAPI key

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        System.out.println(root);

        for (JsonNode standingNode : root.path("response").path(0)) {
            String teamName = standingNode.path("team").path("name").asText();
            String conference = standingNode.path("group").path("name").asText();

            // Print the extracted information to the console
            System.out.println("Team Name: " + teamName);
            System.out.println("Conference: " + conference);
        }

        // Return the entire JSON response
        return root;

    } catch (JsonProcessingException ex) {
        Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE, null, ex);

        return "error in /basketballStandings";
    }
}
}