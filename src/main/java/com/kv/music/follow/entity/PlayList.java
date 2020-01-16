package com.kv.music.follow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "playlist", catalog = "wynk",
        uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class PlayList {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "user_id")
    private String       userId;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="playlist_songs", joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")}
            , inverseJoinColumns={@JoinColumn(name="song_id", referencedColumnName="id")})
    private List<Songs> songs;


}
