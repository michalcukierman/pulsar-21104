package org.acme.pulsar.processor;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.reactive.messaging.pulsar.*;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class Processor {

  private final AtomicLong inCount = new AtomicLong(0);
  @Incoming("requests-in")
  @Outgoing("dump-out")
  PulsarOutgoingMessage<String> process(PulsarIncomingMessage<String> in) {
    System.out.println(" - Processed: " + inCount.incrementAndGet());
    return PulsarOutgoingMessage.from(in);
  }
}
