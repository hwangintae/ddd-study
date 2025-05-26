package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    private String imageType;

    @Column(name = "image_path")
    private String path;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_time")
    private Date uploadTime;

    public Image(String path) {
        this.path = path;
        this.uploadTime = new Date();
    }

    public String getPath() {
        return path;
    }

    public boolean hasThumbnail() {
        return imageType.equals("II");
    }

    public Image(String imageType, String path, Date uploadTime) {
        this.imageType = imageType;
        this.path = path;
        this.uploadTime = uploadTime;
    }
}
