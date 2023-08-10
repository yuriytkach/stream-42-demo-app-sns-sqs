package com.yuriytkach.demo.aws.messaging.demosnssqs.sns;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuriytkach.demo.aws.messaging.demosnssqs.Event;

import io.awspring.cloud.sns.annotation.endpoint.NotificationMessageMapping;
import io.awspring.cloud.sns.annotation.endpoint.NotificationSubscriptionMapping;
import io.awspring.cloud.sns.annotation.endpoint.NotificationUnsubscribeConfirmationMapping;
import io.awspring.cloud.sns.annotation.handlers.NotificationMessage;
import io.awspring.cloud.sns.annotation.handlers.NotificationSubject;
import io.awspring.cloud.sns.handlers.NotificationStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/snsConsumer")
public class NotificationFromSnsController {

  @NotificationSubscriptionMapping
  public void handleSubscriptionMessage(NotificationStatus status) {
    log.info("Subscribe to SNS...");
    //We subscribe to start receive the message
    status.confirmSubscription();
  }

  @NotificationMessageMapping
  public void handleNotificationMessage(@NotificationSubject String subject, @NotificationMessage Event message) {
    log.info("SNS CONSUMER: Received message {} with subject {}", message, subject);
  }

  @NotificationUnsubscribeConfirmationMapping
  public void handleUnsubscribeMessage(NotificationStatus status) {
    log.info("Re-subscribe to SNS...");
    //e.g. the client has been unsubscribed and we want to "re-subscribe"
    status.confirmSubscription();
  }

}
