package com.kv.music.follow.service.impl;

import com.kv.music.follow.entity.Artists;
import com.kv.music.follow.entity.PlayList;
import com.kv.music.follow.repository.ArtistRepository;
import com.kv.music.follow.repository.PlaylistRepository;
import com.kv.music.follow.response.PopularArtistResponse;
import com.kv.music.follow.response.PopularSongResponse;
import com.kv.music.follow.service.PopularityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PopularityServiceImpl implements PopularityService {

    private PlaylistRepository playlistRepository;
    private ArtistRepository artistRepository;

    @Override
    public PopularSongResponse getMostPopularSong() {
        PopularSongResponse popularSongResponse = new PopularSongResponse();

        List<PlayList> playLists = playlistRepository.findAll();
        Map<String, Long> songsCount =
                playLists.stream().flatMap(playList -> playList.getSongs().stream())
                        .collect(Collectors
                                .groupingBy(song -> song.getSongId(), Collectors.counting()));


        String mostPopularSong =
                Collections.max(songsCount.entrySet(), Map.Entry.comparingByValue()).getKey();

        if (Objects.nonNull(mostPopularSong)) {
            popularSongResponse.setSong(mostPopularSong);
        } else {
            popularSongResponse.setMessage("invalid input parameter");
            popularSongResponse.setStatus("failed");
        }
        return popularSongResponse;

    }

    public PopularArtistResponse getMostPopularArtist() {
        PopularArtistResponse popularArtistResponse = new PopularArtistResponse();

        List<Artists> artists = artistRepository.findAll();
        Map<String, Integer> artistFollowerCount =
                artists.stream().collect(Collectors.toMap(artist -> artist.getArtistId(),
                        artist -> artist.getFollowers().size()));


        String mostPopularArtist =
                Collections.max(artistFollowerCount.entrySet(), Map.Entry.comparingByValue()).getKey();

        if (Objects.nonNull(mostPopularArtist)) {
            popularArtistResponse.setArtist(mostPopularArtist);
        } else {
            popularArtistResponse.setMessage("invalid input parameter");
            popularArtistResponse.setStatus("failed");
        }
        return popularArtistResponse;
    }

}
