package com.kv.music.follow.service.impl;

import com.kv.music.follow.entity.PlayList;
import com.kv.music.follow.entity.Songs;
import com.kv.music.follow.response.PlaylistResponse;
import com.kv.music.follow.service.PlaylistService;
import com.kv.music.follow.utility.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private Validator validator;

    @Override public PlaylistResponse getSongs(String userId) {

        PlaylistResponse playlistResponse = new PlaylistResponse();
        try {
            PlayList validPlaylist = validator.validatePlaylist(userId);
            if (Objects.nonNull(validPlaylist)) {
                List<Songs> playlistSongs = validPlaylist.getSongs();
                List<String> songIds = playlistSongs.stream().map(s -> s.getSongId()).collect(
                        Collectors.toList());
                playlistResponse.setSongs(new HashSet<>(songIds));
            }
        }catch (Exception e) {
            e.printStackTrace();
            playlistResponse.setMessage("Invalid input parameters");
            playlistResponse.setStatus("failed");
        }
        return playlistResponse;
    }
}
