package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class MemberId implements Serializable {
    private String id;
}
