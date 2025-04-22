package org.intaehwang.dddstudy.chapter2;

public class KieServices {

    public record Factory() {
        public static KieServices get() {
            return new KieServices();
        }
    }

    public KieContainer getKieClasspathContainer() {
        return new KieContainer();
    }
}
