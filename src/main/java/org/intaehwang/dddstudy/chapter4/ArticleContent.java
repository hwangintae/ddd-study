package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleContent {
    private String content;
    private String contentType;
}
