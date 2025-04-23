package org.intaehwang.dddstudy.chapter2;

import org.intaehwang.dddstudy.chapter1.Money;
import org.intaehwang.dddstudy.chapter1.OrderLine;

import java.util.Arrays;
import java.util.List;

public class DroolsRuleEngine implements RuleDiscounter {
    private KieContainer kContainer;

    public DroolsRuleEngine() {}

    public DroolsRuleEngine(KieContainer kContainer) {
        KieServices ks = KieServices.Factory.get();
        kContainer = ks.getKieClasspathContainer();
    }

    public void evalute(String sessionName, List<?> facts) {
        KieSession kSession = kContainer.newKieSession(sessionName);
    }

    @Override
    public Money applyRules(Customer customer, List<OrderLine> orderLines) {
        KieSession kSession = kContainer.newKieSession("discountSession");

        MutableMoney money = new MutableMoney(0);
        List<Object> facts = Arrays.asList(customer, money);

        facts.addAll(orderLines);

        try {
            facts.forEach(kSession::insert);
            kSession.fireAllRules();
        } finally {
            kSession.dispose();
        }

        return money.toImmutableMoney();

    }
}
