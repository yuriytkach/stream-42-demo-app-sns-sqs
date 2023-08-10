package com.yuriytkach.demo.aws.messaging.demosnssqs.sns;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SnsConfiguration {

  private final SnsClient snsClient;

  @PostConstruct
  void subscribeToTopic() {
    log.info("Subscribing to SNS topic...");
    snsClient.subscribe(SubscribeRequest.builder()
      .topicArn("arn:aws:sns:us-east-1:000000000000:sns-topic")
      .protocol("http")
      .endpoint("http://localhost:8080/snsConsumer")
      .build());
    log.info("Subscribed to SNS topic.");
  }
}
