package org.acme.pulsar.processor;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.policies.data.RetentionPolicies;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class Producer {

  @ConfigProperty(name = "pulsar.admin.serviceUrl")
  private String serviceUrl;
  @ConfigProperty(name = "mp.messaging.outgoing.requests-out.topic")
  private String requestsOutTopic;

  private final AtomicLong outCount = new AtomicLong(0);
  private PulsarAdmin admin;


 
  @PostConstruct
  void init() throws PulsarClientException, PulsarAdminException {
    admin = PulsarAdmin.builder()
      .serviceHttpUrl(serviceUrl)
      .build();

    // if (!admin.topics().getList("public/default", TopicDomain.persistent).contains(requestsOutTopic)) {
      // admin.topics().createPartitionedTopic(requestsOutTopic, 3, null);
      // System.out.println("Pulsar topic: " + requestsOutTopic + " created");
      admin.topicPolicies().setRetention(requestsOutTopic, new RetentionPolicies(-1, -1));
      System.out.println("Pulsar retention on topic: " + requestsOutTopic + " set");
    // }
  }

  @PreDestroy
  void cleanup() {
    if (admin != null) {
      admin.close();
    }
  }

  @Outgoing("requests-out")
  public Multi<String> produce() {
    return Multi.createBy().repeating()
        .uni(() -> Uni
            .createFrom()
            .item(() -> RandomStringUtils.randomAlphabetic(30_000))
            .onItem()
            .invoke(() -> System.out.println("+ Produced: " + outCount.incrementAndGet()))
        )
        .atMost(1_000_000);
  }
}
