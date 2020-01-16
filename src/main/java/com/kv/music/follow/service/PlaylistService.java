package com.kv.music.follow.service;

import com.kv.music.follow.entity.Songs;
import com.kv.music.follow.response.PlaylistResponse;

import java.util.Set;

public interface PlaylistService {
    PlaylistResponse getSongs(String userId);
}
