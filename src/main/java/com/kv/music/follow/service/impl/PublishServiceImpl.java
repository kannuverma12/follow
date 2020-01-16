package com.kv.music.follow.service.impl;

import com.kv.music.follow.entity.Artists;
import com.kv.music.follow.entity.PlayList;
import com.kv.music.follow.entity.Songs;
import com.kv.music.follow.entity.Users;
import com.kv.music.follow.repository.PlaylistRepository;
import com.kv.music.follow.repository.SongRepository;
import com.kv.music.follow.request.PublishSongRequest;
import com.kv.music.follow.response.PublishResponse;
import com.kv.music.follow.service.PublishService;
import com.kv.music.follow.utility.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService {

    private Validator validator;
    private SongRepository songRepository;
    private PlaylistRepository playlistRepository;

    @Override
    public PublishResponse publish(PublishSongRequest publishSongRequest) {
        PublishResponse publishResponse = new PublishResponse();

        try {
            String songId = publishSongRequest.getSong();
            Songs validSong = validator.validateSong(songId);
            if (Objects.isNull(validSong)) {
                Songs songs = new Songs();
                songs.setSongId(songId);
                validSong = songRepository.save(songs);
            }
            List<String> invalidArtists = new ArrayList<>();

            Set<String> artists = publishSongRequest.getArtists();
            for (String artistId : artists) {
                Artists validArtists = validator.validateArtist(artistId);

                if (Objects.isNull(validArtists)) {
                    invalidArtists.add(artistId);
                } else {
                    addSongToFollowerPlaylist(validArtists, validSong);
                }
            }
            if (CollectionUtils.isEmpty(invalidArtists)) {
                publishResponse.setMessage("Song published against artists ");
                publishResponse.setStatus("ok");
            } else {
                publishResponse.setMessage("invalid artist(s)");
                publishResponse.setStatus("failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            publishResponse.setMessage("Invalid input parameters");
            publishResponse.setStatus("failed");
        }

        return publishResponse;
    }

    private void addSongToFollowerPlaylist(Artists validArtists, Songs song) {
        List<Users> followers = validArtists.getFollowers();
        if (!CollectionUtils.isEmpty(followers)) {
            for (Users user : followers){
                String userId = user.getUserId();
                PlayList playList = playlistRepository.findPlayListByUserId(userId);
                if (Objects.nonNull(playList)) {
                    List<Songs> songlist = playList.getSongs();
                    songlist.add(song);
                    playList.setSongs(songlist);
                    playlistRepository.save(playList);
                }
            }
        }
    }
}
