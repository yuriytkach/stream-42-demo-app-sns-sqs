package com.yuriytkach.demo.aws.messaging.demosnssqs;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventController {

  private final SqsTemplate sqsTemplate;
  private final SnsTemplate snsTemplate;

  @PostMapping(value = "/event", consumes = MediaType.APPLICATION_JSON_VALUE)
  public String publishEvent(@RequestBody final Event event) {

    if (event.id() > 10) {
      log.info("Sending to sns...");
      snsTemplate.convertAndSend("arn:aws:sns:us-east-1:000000000000:sns-topic", event);
      return "SNS OK";
    }

    log.info("Sending event: {}", event);

    final SendResult<Event> result;
    if (event.id() % 2 == 0) {
      result = sqsTemplate.send(to -> to
        .payload(event)
        .queue("queue2.fifo")
        .header("my-header", "hello-world")
        .messageGroupId("group1")
        .messageDeduplicationId(String.valueOf(event.id()))
      );
    } else {
      result = sqsTemplate.send("queue1", event);
    }

    return result.messageId().toString();
  }

}
