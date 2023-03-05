package es.codeurjc.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.codeurjc.backend.repository.TweetRepository;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;


@Controller
public class TweetDeleterController {
    @Autowired
    private TweetRepository tweetRepository;

    @GetMapping(value = "/tweet/delete/{id}")
    public String deleteTweet(@PathVariable Long id, HttpServletRequest req) {
        if (postShouldGetDeleted(id)) tweetRepository.deleteById(id);
        return "redirect:" + req.getHeader("referer");
    }

    private String modEndpoint = "https://mod-microservice.vercel.app/postShouldGetDeleted/";
    private Boolean postShouldGetDeleted(Long id) {
        RestTemplate api = new RestTemplate();
        String jsonStr = api.getForEntity(modEndpoint + id.toString(), String.class).getBody();
        return readIfPostShouldGetDeleted(jsonStr);
    }

    private Boolean readIfPostShouldGetDeleted(String jsonStr) {
        return Try
            .of(() -> new ObjectMapper().readTree(jsonStr))
            .map(json -> json.get("response").asBoolean())
            .getOrElse(false);
    }
}
