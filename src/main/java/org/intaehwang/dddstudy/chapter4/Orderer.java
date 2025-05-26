package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class Orderer {
    private MemberId memberId;

    private String name;
}
