package es.codeurjc.backend.controller;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.TweetService;
import es.codeurjc.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class TweetReportController {
    @Autowired
    private TweetService tweetService;

    @RequestMapping("/report/{id}")
    public String reportTweet(
        Model model, HttpSession session, @PathVariable Long id
    ) {
        Optional<User> reporter = UserService.getUserFrom(session);
        if (reporter.isEmpty()) return "redirect:/login";
        tweetService.addReportToTweet(reporter.get(), id);
        return "redirect:/u/" + reporter.get().getUsername();
    }

}
