package org.intaehwang.dddstudy.chapter10;

import java.util.HashMap;

public class Order {

    public void cancel() {
        Events.raise(new HashMap<>());
    }
}
