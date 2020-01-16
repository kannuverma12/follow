package com.kv.music.follow.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistResponse {
    private Set<String> songs;
    private String message;
    private String status;
}
