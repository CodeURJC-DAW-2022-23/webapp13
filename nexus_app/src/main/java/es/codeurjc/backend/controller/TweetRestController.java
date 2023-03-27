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

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.TweetService;
import es.codeurjc.backend.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


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
    @GetMapping("/{username}/tweets")
    public ResponseEntity<List<Tweet>> getTweetsByUsername(@PathVariable String username) {
        Optional<User> userOpt = userService.getUserBy(username);
        if (userOpt.isPresent()) {
            List<Tweet> tweets = userOpt.get().getTweets();
            return new ResponseEntity<>(tweets, HttpStatus.OK);
        } else 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    @JsonView(User.Alt.class)
    @GetMapping("/me")
    public ResponseEntity<List<Tweet>> getTweetsOfCurrentUser(HttpServletRequest request) {
        
        Optional<User> userOpt = userService.getUserBy(request);
        List<Tweet> tweets = userOpt.get().getTweets();
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
        Page<Tweet> pagedTweets= tweetService.getPageOfTweets(page);
        List<Tweet> listedTweets = pagedTweets.toList();
       return new ResponseEntity<>(listedTweets, HttpStatus.OK);
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
        Optional<Tweet> tweetOpt = tweetService.getTweetById(id);
        if (tweetOpt.isPresent()) 
            return new ResponseEntity<>(tweetOpt.get(), HttpStatus.OK);
        else 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Set<User>> getLikesByTweetId(@PathVariable String id) {
        Optional<Tweet> tweetOpt = tweetService.getTweetById(id);
        if (tweetOpt.isPresent()) {
            Tweet tweet = tweetOpt.get();
            //this is not going to work with current configuration of JsonIgnores
            Set<User> users = tweet.getLikes();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Tweet> deleteTweet(@RequestParam long id) {

        Optional <Tweet> tweetOpt = tweetService.getTweetById(id);
        if(tweetOpt.isPresent()) {
            tweetService.delete(tweetOpt.get());
            return new ResponseEntity<>(null,HttpStatus.OK);
        } else
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }

}
