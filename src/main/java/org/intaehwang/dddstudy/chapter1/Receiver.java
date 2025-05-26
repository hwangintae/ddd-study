package org.intaehwang.dddstudy.chapter1;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receiver {
    @Column(name = "receiver_name")
    private String name;

    @Column(name = "receiver_phone")
    private String phoneNumber;

    public Receiver(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (this == other) return true;
        if (! (other instanceof Receiver)) return false;
        Receiver that = (Receiver) other;
        return this.name.equals(that.name) && this.phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.phoneNumber == null) ? 0 : this.phoneNumber.hashCode());
        return result;
    }
}
