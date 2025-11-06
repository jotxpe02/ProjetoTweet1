package com.empresa.SpringTweet.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.empresa.SpringTweet.model.Tweet;
import com.empresa.SpringTweet.model.User;
import com.empresa.SpringTweet.repository.TweetRepository;
import com.empresa.SpringTweet.repository.UserRepository;

/**
 * Camada de serviço para operações relacionadas a Tweets.
 */
@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public Tweet create(Tweet tweet) {
        tweet.setPostTime(new Date());
        if (tweet.getTweetUser() != null && tweet.getTweetUser().getId() != null) {
            Integer userId = tweet.getTweetUser().getId();
            Optional<User> u = userRepository.findById(userId);
            u.ifPresent(tweet::setTweetUser);
        }
        return tweetRepository.save(tweet);
    }

    public List<Tweet> listAll() {
        return tweetRepository.findAll();
    }

    public Optional<Tweet> getById(Integer id) {
        return tweetRepository.findById(id);
    }

    public Optional<Tweet> update(Integer id, Tweet updated) {
        return tweetRepository.findById(id).map(existing -> {
            if (updated.getContent() != null) existing.setContent(updated.getContent());
            if (updated.getTweetUser() != null && updated.getTweetUser().getId() != null) {
                Optional<User> u = userRepository.findById(updated.getTweetUser().getId());
                u.ifPresent(existing::setTweetUser);
            }
            return tweetRepository.save(existing);
        });
    }

    public boolean delete(Integer id) {
        if (!tweetRepository.existsById(id)) return false;
        tweetRepository.deleteById(id);
        return true;
    }
}
