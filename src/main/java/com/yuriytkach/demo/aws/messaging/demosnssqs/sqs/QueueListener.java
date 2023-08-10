package com.yuriytkach.demo.aws.messaging.demosnssqs.sqs;

import org.springframework.stereotype.Service;

import com.yuriytkach.demo.aws.messaging.demosnssqs.Event;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueListener {

  @SqsListener(value = "queue1", maxConcurrentMessages = "1", maxMessagesPerPoll = "1")
  public void listenQueue1(final Event event) {
    log.info("RECEIVED-1 event: {}", event);
    if ("throw".equals(event.name())) {
      throw new RuntimeException("Exception thrown on purpose");
    }
    if ("wait".equals(event.name())) {
      log.info("Waiting...");
      try {
        Thread.sleep(70000);
      } catch (final InterruptedException e) {
        e.printStackTrace();
      }
      log.info("Wait for 70 seconds - done");
    }
  }

  @SqsListener("dlq")
  public void listenDlq(final Event event) {
    log.info("RECEIVED-DLQ event: {}", event);
  }

  @SqsListener("${app.queue2-name}")
  public void listenQueue2(final Event event) {
    log.info("RECEIVED-2 event: {}", event);
  }

  @SqsListener("queue-from-sns")
  public void listenQueueFromSns(final String event) {
    log.info("RECEIVED-From-Sns event: {}", event);
  }

}
