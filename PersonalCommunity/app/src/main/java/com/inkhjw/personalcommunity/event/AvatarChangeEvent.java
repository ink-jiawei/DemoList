package com.inkhjw.personalcommunity.event;

public class AvatarChangeEvent {
    public boolean updateAvatar;

    public AvatarChangeEvent(boolean updateAvatar) {
        this.updateAvatar = updateAvatar;
    }
}
