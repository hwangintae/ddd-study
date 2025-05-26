package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SecondaryTable(
        name = "article_content",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id")
)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "content",
                    column = @Column(table = "article_content", name = "content")),
            @AttributeOverride(
                    name = "contentType",
                    column = @Column(table = "article_content", name = "content_type")
            )
    })
    private ArticleContent articleContent;
}
