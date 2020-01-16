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
@Table(name = "artists", catalog = "wynk",
        uniqueConstraints = @UniqueConstraint(columnNames = "artist_id"))
public class Artists {

    @Id
    @Column(name="id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="artist_id", unique = true, nullable = false)
    private String       artistId;

    @Column(name="name")
    private String       name;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="artists_songs", joinColumns={@JoinColumn(name="artist_id", referencedColumnName="id")}
            , inverseJoinColumns={@JoinColumn(name="song_id", referencedColumnName="id")})
    private List<Songs> songsPublished;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="artists_users", joinColumns={@JoinColumn(name="artist_id", referencedColumnName="id")}
            , inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id")})
    private List<Users> followers;




}
