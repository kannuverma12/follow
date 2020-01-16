package com.kv.music.follow.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PopularArtistResponse {
    private String artist;
    private String message;
    private String status;
}
