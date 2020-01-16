package com.kv.music.follow.service.impl;

import com.kv.music.follow.entity.Artists;
import com.kv.music.follow.entity.PlayList;
import com.kv.music.follow.entity.Songs;
import com.kv.music.follow.entity.Users;
import com.kv.music.follow.repository.ArtistRepository;
import com.kv.music.follow.repository.PlaylistRepository;
import com.kv.music.follow.repository.UserRepository;
import com.kv.music.follow.request.FollowRequest;
import com.kv.music.follow.response.FollowResponse;
import com.kv.music.follow.response.FollowerResponse;
import com.kv.music.follow.service.FollowService;
import com.kv.music.follow.utility.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

    private Validator validator;
    private UserRepository userRepository;
    private ArtistRepository artistRepository;
    private PlaylistRepository playlistRepository;

    @Override
    public FollowResponse follow(FollowRequest followRequest) {

        FollowResponse followResponse = new FollowResponse();
        try {
            String userId = followRequest.getUser();
            Users validUsers = validator.validateUser(userId);

            if (Objects.isNull(validUsers)) {
                Users users = new Users();
                users.setUserId(userId);

                validUsers = userRepository.save(users);
            }

            List<String> artists = followRequest.getArtist();
            String artistNames = "";

            for (int i=0; i< artists.size(); i++ ) {
                String artistId = artists.get(i);
                if (i != 0)
                    artistNames += ",";
                artistNames += artistId;

                Artists validArtists = validator.validateArtist(artistId);
                if (Objects.isNull(validArtists)) {
                    Artists artist = new Artists();
                    artist.setArtistId(artistId);
                    validArtists = artistRepository.save(artist);
                }

                List<Users> followers
                        = validArtists.getFollowers();

                if (CollectionUtils.isEmpty(followers)) {
                    followers = new ArrayList<>();
                }
                followers.add(validUsers);
                validArtists.setFollowers(followers);

                addArtistSongsToUserPlaylist(userId, validArtists);
            }
            followResponse.setMessage(userId + " started following " + artistNames);
            followResponse.setStatus("ok");
        } catch (Exception e) {
            e.printStackTrace();
            followResponse.setMessage("Invalid input parameters");
            followResponse.setStatus("failed");
        }

        return followResponse;
    }

    private void addArtistSongsToUserPlaylist(String userId, Artists validArtists) {
        PlayList validPlaylist = validator.validatePlaylist(userId);
        if (Objects.isNull(validPlaylist)) {
            PlayList playList = new PlayList();
            playList.setUserId(userId);
            validPlaylist = playlistRepository.save(playList);
        }

        List<Songs> playlistSongs = validPlaylist.getSongs();
        if (CollectionUtils.isEmpty(playlistSongs)) {
            playlistSongs = new ArrayList<>();
        }
        if (Objects.nonNull(validArtists.getSongsPublished())) {
            playlistSongs.addAll(validArtists.getSongsPublished());
        }
        validPlaylist.setSongs(playlistSongs);
    }

    @Override
    public FollowResponse unfollow(FollowRequest followRequest) {
        FollowResponse followResponse = new FollowResponse();
        try {
            String userId = followRequest.getUser();
            Users validUsers = validator.validateUser(userId);

            if (Objects.isNull(validUsers)) {
                followResponse.setStatus("failed");
                followResponse.setMessage("invalid user");
                return followResponse;
            }

            List<String> artists = followRequest.getArtist();
            String artistNames = "";
            List<String> invalidArtists = new ArrayList<>();
            for (int i=0; i< artists.size(); i++ ) {
                String artistId = artists.get(i);
                if (i != 0)
                    artistNames += ",";
                artistNames += artistId;

                Artists validArtists = validator.validateArtist(artistId);
                if (Objects.isNull(validArtists)) {
                    invalidArtists.add(artistId);
                } else {
                    List<Users> followers = validArtists.getFollowers();

                    if (!CollectionUtils.isEmpty(followers)) {
                        if (followers.contains(userId)) {
                            followers.remove(userId);
                        }
                    }
                    validArtists.setFollowers(followers);
                    removeArtistSongsFromUserPlaylist(userId, validArtists);
                }
            }
            if (CollectionUtils.isEmpty(invalidArtists)) {
                followResponse.setMessage(userId + " stopped following " + artistNames);
                followResponse.setStatus("ok");
            } else {
                followResponse.setMessage("invalid artist(s)");
                followResponse.setStatus("failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            followResponse.setMessage("Invalid input parameters");
            followResponse.setStatus("failed");
        }
        return followResponse;
    }

    @Override
    public FollowerResponse getFollowersCount(String artistId) {
        FollowerResponse followerResponse = new FollowerResponse();
        Artists validArtist = validator.validateArtist(artistId);
        if (Objects.nonNull(validArtist) && !CollectionUtils.isEmpty(validArtist.getFollowers())) {
            followerResponse.setArtist(artistId);
            followerResponse.setCount(validArtist.getFollowers().size());
        } else {
            followerResponse.setMessage("Invalid input");
            followerResponse.setStatus("Failed");
        }
        return followerResponse;
    }

    private void removeArtistSongsFromUserPlaylist(String userId, Artists validArtists) {
        PlayList validPlaylist = validator.validatePlaylist(userId);

        if (Objects.nonNull(validPlaylist)) {
            List<Songs> songs = validPlaylist.getSongs();
            if (!CollectionUtils.isEmpty(songs)) {
                List<Songs> artistSongs = validArtists.getSongsPublished();
                if (Objects.nonNull(artistSongs)) {
                    songs.removeAll(artistSongs);
                }
            }
            validPlaylist.setSongs(songs);

        }


    }
}
