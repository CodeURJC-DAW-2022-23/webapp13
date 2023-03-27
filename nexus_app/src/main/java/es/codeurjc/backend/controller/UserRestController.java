package es.codeurjc.backend.controller;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.TweetService;
import es.codeurjc.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    //Find specific user
    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the user",
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
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> optional = userService.getUserById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    //GET all users
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the users",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = User.class)
            )}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content
        )
    })
    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    //Logged user
    @Operation(summary = "Get logged user")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User found.",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = User.class)
            )}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content
        )
    })
    @GetMapping("/me")
    public ResponseEntity<User> userLoged(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            return ResponseEntity.ok(userService.getUserByEmail(principal.getName()).orElseThrow());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get User Tweets")
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
    @GetMapping("/me/tweets")
    public ResponseEntity<List<Tweet>> getUserTweets(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            User user = userService.getUserBy(principal.getName()).orElseThrow();
            List<Tweet> tweets = new ArrayList<>();
            tweets = user.getTweets();//should be userService.getTweetsByUsername
            if(tweets==null)
                return new ResponseEntity<>(tweets, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tweets, HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //Followers of current user
    @Operation(summary = "Get User followers")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = User.class)
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
    @GetMapping("/me/followers")
    public ResponseEntity<List<User>> getUserFollowers(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {//not logged or something
            User user = userService.getUserBy(principal.getName()).orElseThrow();
            List<User> users = new ArrayList<>();
            users = (List<User>) userService.getFollowers(user);
            if(users==null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    //list of following of the current user
    @Operation(summary = "Get User following list")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = User.class)
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
    @GetMapping("/me/following")
    public ResponseEntity<List<User>> getUserFollowing(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {//not logged or something
            User user = userService.getUserBy(principal.getName()).orElseThrow();
            List<User> users = new ArrayList<>();
            users = (List<User>) user.getFollowing();
            if(users==null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get user profile picture")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found the exercise",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = User.class)
            )}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content
        )
    })
    @GetMapping("/me/image")
    public ResponseEntity<Object> downloadImage(HttpServletRequest request) throws SQLException {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserBy(principal.getName()).orElseThrow();

        if (user.hasProfilePicture()) {

            Resource file = new InputStreamResource(user.getProfilePicture().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(user.getProfilePicture().length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //POST new user
    @Operation(summary = "Post new user")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = User.class)
            )}
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content = @Content
        )
    })
    @PostMapping("/users/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (!user.isAdmin()) {//not allowed to create admins
            userService.save(user);
            URI location = fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getUsername()).toUri();
            return ResponseEntity.created(location).body(user);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Upload profile picture
    @Operation(summary = "POST a user profile picture")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation=User.class)
            )}
        ),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid id supplied",
            content = @Content
        )
    })
    @PostMapping("/me/image")
    public ResponseEntity<Object> uploadMyImage(HttpServletRequest request, @RequestParam MultipartFile imageFile) throws IOException {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            User user = userService.getUserBy(principal.getName()).orElseThrow();
            URI location = fromCurrentRequest().build().toUri();
            user.setProfilePicture(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
            userService.save(user);
            return ResponseEntity.created(location).build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}