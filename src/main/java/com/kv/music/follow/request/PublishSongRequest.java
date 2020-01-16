package com.kv.music.follow.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishSongRequest {

    @NotEmpty
    private  String      song;

    @NotEmpty
    private Set<String> artists;

}
