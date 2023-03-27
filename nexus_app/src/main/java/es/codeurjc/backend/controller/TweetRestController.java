package es.codeurjc.backend.controller;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.web.JsonPath;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.TweetService;
import es.codeurjc.backend.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import static org.springframework.web.util.UriComponentsBuilder.newInstance;

@RestController
@RequestMapping("/api/tweets")
public class TweetRestController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;
    
    
    @Operation(summary = "Get tweets by user id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Tweet.class)
            )}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content = @Content
        )
    })
    @JsonView(User.class)
    @GetMapping("/{id}/tweets")
    public ResponseEntity<List<Tweet>> getTweetsByUserId(@PathVariable String id) {
        Optional<User> optional = userService.getUserById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            List<Tweet> tweets = new ArrayList<>();
            tweets = tweetService.getTweetsByUser(user);
            //tweets = user.getTweets();
            if(tweets==null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tweets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get tweets of the current user")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Tweet.class)
            )}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content = @Content
        )
    })
    @JsonView(User.class)
    @GetMapping("/me")
    public ResponseEntity<List<Tweet>> getTweetsOfCurrentUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserBy(principal.getName()).orElseThrow();
        List<Tweet> tweets = new ArrayList<>();
        tweetService.getTweetsByUser(user);
        if(tweets==null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    //All Tweets
    @Operation(summary = "Get all tweets")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found recepies",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Tweet.class)
            )}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content
        )
    })
    @GetMapping("")
    public ResponseEntity<List<Tweet>> getTweets(@RequestParam int page) {
        Page<Tweet> tweets= tweetService.getPageOfTweets(page);
        List<Tweet> aux= tweets.toList();
       return new ResponseEntity<>(aux, HttpStatus.OK);
    }

    //Find specific tweet
    @Operation(summary = "Get tweet by id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the tweet",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Tweet.class)
                )}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "not found",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweetById(@PathVariable String id) {
        Optional<Tweet> optional = tweetService.getTweetById(id);
        if (optional.isPresent()) {
            Tweet tweet = optional.get();
            return new ResponseEntity<>(tweet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Find specific tweet
    @Operation(summary = "Get likes of a tweet by id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the tweet",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = User.class)
                )}
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "not found",
            content = @Content
        )
    })
    @GetMapping("/{id}/likes")
    public ResponseEntity<List<User>> getLikesByTweetId(@PathVariable String id) {
        Optional<Tweet> optional = tweetService.getTweetById(id);
        if (optional.isPresent()) {
            Tweet tweet = optional.get();
            //this is not goin to work with current configuration of JsoIgnores
            List<User> users = (List<User>) tweet.getLikes();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //POST new tweet
    @Operation(summary = "Post new tweet")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Tweet.class)
            )}
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content = @Content
        )
    })
    @PostMapping("/")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tweet> postTweet(@RequestBody Tweet tweet) {
        try{
            tweetService.save(tweet);
            URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(tweet.getId()).toUri();
            return ResponseEntity.created(location).body(tweet);
        }catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete tweet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Tweet.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ids supplied",
                    content = @Content
            )
    })
    @DeleteMapping("")
    public ResponseEntity<Tweet> deleteTweet(@RequestParam Long id) {
        if(id != null) {
            if(tweetService.getTweetById(id).isPresent()) {
                Optional<Tweet> optional = tweetService.getTweetById(id);
                Tweet tweet = optional.get();
                tweetService.delete(tweet);
            }
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
