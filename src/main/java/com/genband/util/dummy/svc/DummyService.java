package com.genband.util.dummy.svc;

import com.genband.util.broker.BrokerMessagingService;
import com.genband.util.broker.BrokerType;
import com.genband.util.broker.MessagingService;
import com.genband.util.broker.model.Message;
import com.genband.util.log.slf4j.GbLogger;
import com.genband.util.log.slf4j.GbLoggerFactory;

public class DummyService {
    private static GbLogger log = GbLoggerFactory.getGbLogger(DummyService.class.getName());
    private static DummyService instance = null;
    private static Object mutex = new Object();
    private MessagingService svc;

    private DummyService() {
        log.info("Dummy Service Loaded...");
        svc = BrokerMessagingService.getService(BrokerType.RABBITMQ);
    }

    public static DummyService getInstance() {
        if (null == instance)
            synchronized (mutex) {
                if (null == instance)
                    instance = new DummyService();
            }
        return instance;
    }

    public void handleUnroutedMessage(Message message) {
        log.info("Now will serving subscriber: " + message.getMessageParams().getSubscriber());
        svc.bindRoutingKey(message.getMessageParams().getSubscriber());
    }
}
