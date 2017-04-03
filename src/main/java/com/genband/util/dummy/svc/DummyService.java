package com.genband.util.dummy.svc;

import com.genband.util.broker.BrokerMessagingService;
import com.genband.util.broker.BrokerType;
import com.genband.util.broker.MessagingService;
import com.genband.util.broker.model.Message;
import com.genband.util.broker.util.MessageUtils;
import com.genband.util.log.slf4j.GbLogger;
import com.genband.util.log.slf4j.GbLoggerFactory;

public class DummyService {
    private static GbLogger log = GbLoggerFactory.getGbLogger(DummyService.class.getName());
    private static DummyService instance = null;
    private static Object mutex = new Object();
    private MessagingService svc;
    private MessageUtils util;

    private DummyService() {
        log.info("Dummy Service Loaded...");
        svc = BrokerMessagingService.getService(BrokerType.RABBITMQ);
        util = MessageUtils.getInstance();
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
        log.info("Now will serving new subscriber: " + message.getMessageParams().getSubscriber());
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        log.info("Unrouted Message: " + message.getMessageBody());
        log.info("Type: " + message.getMessageParams().getType());
        log.info("Transcation-ID: " + message.getMessageParams().getTransactionId());
        log.info("Message-ID: " + message.getMessageParams().getMessageId());
        log.info("Message-Params: " + message.getMessageParams().toString());
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        svc.bindRoutingKey(message.getMessageParams().getSubscriber());
        /**
         * Send Response Message, No Matter What
         */
        Message responseMessage = util.buildMessage("This is the auto response for ROUTED",
                message.getMessageParams().getSubscriber(), message.getMessageParams().getOriginatingMS());
        try {
            svc.sendResponse(message.getMessageParams().getTransactionId(), responseMessage, "local-site",
                    message.getMessageParams().getCalledMessageQueue());
        } catch (Exception e) {
            log.error("Message sent failure: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleRoutedMessage(Message message) {
        log.info("Routed subscriber: " + message.getMessageParams().getSubscriber());
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        log.info("Message: " + message.getMessageBody());
        log.info("Type: " + message.getMessageParams().getType());
        log.info("Transcation-ID: " + message.getMessageParams().getTransactionId());
        log.info("Message-ID: " + message.getMessageParams().getMessageId());
        log.info("Message-Params: " + message.getMessageParams().toString());
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Message responseMessage = util.buildMessage("This is the auto response for ROUTED",
                message.getMessageParams().getSubscriber(), message.getMessageParams().getOriginatingMS());
        try {
            svc.sendResponse(message.getMessageParams().getTransactionId(), responseMessage, "local-site",
                    message.getMessageParams().getCalledMessageQueue());
        } catch (Exception e) {
            log.error("Message sent failure: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
