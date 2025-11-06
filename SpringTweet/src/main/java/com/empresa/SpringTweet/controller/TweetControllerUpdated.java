package com.empresa.SpringTweet.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empresa.SpringTweet.model.Tweet;
import com.empresa.SpringTweet.service.TweetService;

@RestController
@RequestMapping("/api/tweets")
public class TweetControllerUpdated {

    private final TweetService tweetService;

    public TweetControllerUpdated(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet) {
        Tweet created = tweetService.create(tweet);
        return ResponseEntity.created(URI.create("/api/tweets/" + created.getId())).body(created);
    }

    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweetById(@PathVariable Integer id) {
        return tweetService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tweet> updateTweet(@PathVariable Integer id, @RequestBody Tweet updated) {
        return tweetService.update(id, updated).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Integer id) {
        boolean deleted = tweetService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
