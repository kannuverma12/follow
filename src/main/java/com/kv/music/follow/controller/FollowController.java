package com.kv.music.follow.controller;

import com.kv.music.follow.request.FollowRequest;
import com.kv.music.follow.request.PublishSongRequest;
import com.kv.music.follow.response.*;
import com.kv.music.follow.service.impl.FollowServiceImpl;
import com.kv.music.follow.service.impl.PlaylistServiceImpl;
import com.kv.music.follow.service.impl.PopularityServiceImpl;
import com.kv.music.follow.service.impl.PublishServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/wynk")
@Validated
@AllArgsConstructor
public class FollowController {

    private FollowServiceImpl   followService;
    private PublishServiceImpl  publishService;
    private PlaylistServiceImpl playlistService;
    private PopularityServiceImpl popularityService;

    @PostMapping("/follow")
    public @ResponseBody FollowResponse follow(@RequestBody @Valid
                        @NotNull FollowRequest followRequest) {
        return followService.follow(followRequest);
    }

    @PostMapping("/unfollow")
    public @ResponseBody FollowResponse unfollow(@RequestBody @Valid
                        @NotNull FollowRequest followRequest) {
        return followService.unfollow(followRequest);
    }

    @PostMapping("/publish")
    public @ResponseBody PublishResponse publish(@RequestBody @Valid
                        @NotNull PublishSongRequest publishSongRequest) {
        return publishService.publish(publishSongRequest);
    }

    @GetMapping("/playlist")
    public @ResponseBody PlaylistResponse playlist(@RequestParam("user") @NotBlank String userId) {
        return playlistService.getSongs(userId);
    }

    @GetMapping("/popular/song")
    public @ResponseBody PopularSongResponse popularSong() {

        return popularityService.getMostPopularSong();
    }

    @GetMapping("/popular/artist")
    public @ResponseBody PopularArtistResponse popularArtist() {
        return popularityService.getMostPopularArtist();
    }

    @GetMapping("/follow/count")
    public @ResponseBody FollowerResponse followCount(
            @RequestParam("artist") @NotBlank String artistId) {
        return followService.getFollowersCount(artistId);
    }
}
