package org.intaehwang.dddstudy.chapter2;

import java.util.List;

public class DroolsRuleEngine {
    private KieContainer kContainer;

    public DroolsRuleEngine() {}

    public DroolsRuleEngine(KieContainer kContainer) {
        KieServices ks = KieServices.Factory.get();
        kContainer = ks.getKieClasspathContainer();
    }

    public void evalute(String sessionName, List<?> facts) {
        KieSession kSession = kContainer.newKieSession(sessionName);

        try {
            facts.forEach(kSession::insert);
            kSession.fireAllRules();
        } finally {
            kSession.dispose();
        }
    }
}
