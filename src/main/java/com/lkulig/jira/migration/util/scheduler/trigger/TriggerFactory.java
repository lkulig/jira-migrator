package com.lkulig.jira.migration.util.scheduler.trigger;

import com.lkulig.jira.migration.util.scheduler.exception.TriggerCreationException;
import org.quartz.CronTrigger;
import java.text.ParseException;

public class TriggerFactory {

    public static CronTrigger create(String name, String expression) throws TriggerCreationException {
        try {
            CronTrigger trigger = new CronTrigger();
            trigger.setName(name);
            trigger.setCronExpression(expression);
            return trigger;
        } catch (ParseException e) {
            throw new TriggerCreationException(e);
        }
    }
}
