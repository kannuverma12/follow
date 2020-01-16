package com.kv.music.follow.service;

import com.kv.music.follow.request.PublishSongRequest;
import com.kv.music.follow.response.PublishResponse;

public interface PublishService {
    PublishResponse publish(PublishSongRequest publishSongRequest);
}
