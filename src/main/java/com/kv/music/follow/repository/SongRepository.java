package com.kv.music.follow.repository;


import com.kv.music.follow.entity.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Songs, Long> {
    Songs findSongBySongId(String songId);
}
