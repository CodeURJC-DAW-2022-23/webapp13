package es.codeurjc.backend.controller;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            return new ResponseEntity<>(userOpt.get(), HttpStatus.OK);
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
    public ResponseEntity<User> getLoggedUser(HttpServletRequest request) {
        Optional<User> userOpt = userService.getUserBy(request);
        if (userOpt.isPresent()) 
            return ResponseEntity.ok(userOpt.get());
        else 
            return ResponseEntity.notFound().build();
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

        Optional<User> userOpt = userService.getUserBy(request);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Tweet> tweets = user.getTweets();
            if(tweets != null)
                return new ResponseEntity<>(tweets, HttpStatus.OK);
            else
                return new ResponseEntity<>(tweets, HttpStatus.NO_CONTENT);
        } else
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<Set<User>> getUserFollowers(HttpServletRequest request) {
          
        Optional<User> userOpt = userService.getUserBy(request);

        if (userOpt.isPresent()) 
            return new ResponseEntity<>(userOpt.get().getFollowers(userService), HttpStatus.OK);
        else
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<Set<User>> getUserFollowing(HttpServletRequest request) {
        
        Optional<User> userOpt = userService.getUserBy(request);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            Set<User> users = user.getFollowing();

            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else
            return ResponseEntity.notFound().build();
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
        
        Optional<User> userOpt = userService.getUserBy(request);

        if (userOpt.isPresent() && userOpt.get().hasProfilePicture()) {

            Resource file = new InputStreamResource(userOpt.get().getProfilePicture().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(userOpt.get().getProfilePicture().length()).body(file);
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

        //Only for creating non-admins
        if (!user.isAdmin()) {
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
    public ResponseEntity<Object> uploadImage(HttpServletRequest request, @RequestParam MultipartFile imageFile) throws IOException {
        Optional<User> userOpt = userService.getUserBy(request);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            URI location = fromCurrentRequest().build().toUri();
            user.setProfilePicture(
                BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()),
                userService
                );
            return ResponseEntity.created(location).build();
        } else 
            return ResponseEntity.notFound().build();
    }
}