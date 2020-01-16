package com.kv.music.follow.service;

import com.kv.music.follow.request.FollowRequest;
import com.kv.music.follow.response.FollowResponse;
import com.kv.music.follow.response.FollowerResponse;

public interface FollowService {
    FollowResponse follow(FollowRequest followRequest);

    FollowResponse unfollow(FollowRequest followRequest);

    FollowerResponse getFollowersCount(String artistId);
}
