package com.genband.util.dummy;

import com.genband.controlInterface.Application;
import com.genband.util.broker.BrokerMessagingService;
import com.genband.util.broker.BrokerType;
import com.genband.util.broker.MessagingService;
import com.genband.util.log.slf4j.GbLogger;
import com.genband.util.log.slf4j.GbLoggerFactory;

public class DummyApplication {

    private static GbLogger log = GbLoggerFactory.getGbLogger(DummyApplication.class.getName());

    public static void main(String args[]) {
        log.info("Dummy microservice is booting up...");

        Application.startControlInterface(args);

        MessagingService svc = BrokerMessagingService.getService(BrokerType.RABBITMQ);
        svc.startConsumeMessaging();
        log.info("Dummy application started successfully! ");
    }

}
