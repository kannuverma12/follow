package com.kv.music.follow.service;

import com.kv.music.follow.response.PopularArtistResponse;
import com.kv.music.follow.response.PopularSongResponse;

public interface PopularityService {
    PopularSongResponse getMostPopularSong();
    PopularArtistResponse getMostPopularArtist();
}
