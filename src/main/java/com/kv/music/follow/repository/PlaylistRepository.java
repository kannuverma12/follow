package com.kv.music.follow.repository;

import com.kv.music.follow.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlaylistRepository extends JpaRepository<PlayList, Long> {
    PlayList findPlayListByUserId(String userId);

}
