package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("II")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InternalImage extends Image {

    @Override
    public String getURL() {
        return "";
    }

    @Override
    public boolean hasThumbnail() {
        return false;
    }

    @Override
    public String getThumbnailURL() {
        return "";
    }
}
