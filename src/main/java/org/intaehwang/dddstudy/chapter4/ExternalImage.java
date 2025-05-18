package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("EI")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExternalImage extends Image {
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
