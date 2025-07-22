package prism.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import prism.config.kafka.KafkaProcessor;
import prism.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    NotificationRepository notificationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='IssueCreated'"
    )
    public void wheneverIssueCreated_SendAlertToSubscribers(
        @Payload IssueCreated issueCreated
    ) {
        IssueCreated event = issueCreated;
        System.out.println(
            "\n\n##### listener SendAlertToSubscribers : " +
            issueCreated +
            "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='IssueStatusUpdated'"
    )
    public void wheneverIssueStatusUpdated_SendAlertToSubscribers(
        @Payload IssueStatusUpdated issueStatusUpdated
    ) {
        IssueStatusUpdated event = issueStatusUpdated;
        System.out.println(
            "\n\n##### listener SendAlertToSubscribers : " +
            issueStatusUpdated +
            "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='NetworkDispatched'"
    )
    public void wheneverNetworkDispatched_SendAlertToSubscribers(
        @Payload NetworkDispatched networkDispatched
    ) {
        NetworkDispatched event = networkDispatched;
        System.out.println(
            "\n\n##### listener SendAlertToSubscribers : " +
            networkDispatched +
            "\n\n"
        );
        // Sample Logic //

    }
}
//>>> Clean Arch / Inbound Adaptor
