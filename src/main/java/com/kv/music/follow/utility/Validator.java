package com.kv.music.follow.utility;

import com.kv.music.follow.entity.Artists;
import com.kv.music.follow.entity.PlayList;
import com.kv.music.follow.entity.Songs;
import com.kv.music.follow.entity.Users;
import com.kv.music.follow.repository.ArtistRepository;
import com.kv.music.follow.repository.PlaylistRepository;
import com.kv.music.follow.repository.SongRepository;
import com.kv.music.follow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Validator {

    private UserRepository userRepository;
    private ArtistRepository artistRepository;
    private PlaylistRepository playlistRepository;
    private SongRepository songRepository;

    public Users validateUser(String userId) {
        return userRepository.findUserByUserId(userId);
    }

    public Artists validateArtist(String artistId) {
        return artistRepository.findArtistByArtistId(artistId);
    }

    public PlayList validatePlaylist(String userId) {
        return playlistRepository.findPlayListByUserId(userId);
    }

    public Songs validateSong(String songId) {
        return songRepository.findSongBySongId(songId);
    }
}
