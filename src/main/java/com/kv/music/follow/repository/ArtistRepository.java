package com.kv.music.follow.repository;

import com.kv.music.follow.entity.Artists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artists, Long> {
    Artists findArtistByArtistId(String artistId);
}
