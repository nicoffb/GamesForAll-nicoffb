package com.salesianostriana.gamesforall.platform;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PlatformDTO {

    private Long id;
    private String platformName;


    public static PlatformDTO of(Platform platform) {

        return PlatformDTO.builder()
                .id(platform.getId())
                .platformName(platform.getPlatformName())
                .build();
    }

    public Platform toPlatform (){
        return Platform.builder()
                .id(id)
                .platformName(platformName)
                .build();
    }

}