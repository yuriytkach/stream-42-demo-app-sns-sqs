package com.yuriytkach.demo.aws.messaging.demosnssqs.sqs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsConfiguration {

  @Bean
  SqsTemplate sqsTemplate(final SqsAsyncClient sqsAsyncClient) {
    return SqsTemplate.newTemplate(sqsAsyncClient);
  }

}
