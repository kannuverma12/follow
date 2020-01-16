package com.kv.music.follow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "songs", catalog = "wynk",
        uniqueConstraints = {@UniqueConstraint(columnNames = "song_id"),
                @UniqueConstraint(columnNames = "id")})
public class Songs {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Long id;

    @Column(name="song_id", unique = true, nullable = false)
    private String songId;

    @Column(name="name")
    private String name;

}
