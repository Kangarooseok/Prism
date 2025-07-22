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
    NetworkActionRepository networkActionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='IssueCreated'"
    )
    public void wheneverIssueCreated_NotifyManagerOnUnresolvedIssue(
        @Payload IssueCreated issueCreated
    ) {
        IssueCreated event = issueCreated;
        System.out.println(
            "\n\n##### listener NotifyManagerOnUnresolvedIssue : " +
            issueCreated +
            "\n\n"
        );
        // Comments //
        //NotifyManagerOnUnresolvedIssue

        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='WarningTriggered'"
    )
    public void wheneverWarningTriggered_AutoDispatchOnCriticalAlert(
        @Payload WarningTriggered warningTriggered
    ) {
        WarningTriggered event = warningTriggered;
        System.out.println(
            "\n\n##### listener AutoDispatchOnCriticalAlert : " +
            warningTriggered +
            "\n\n"
        );
        // Comments //
        //알람이 '심각' 등급이면 자동 출동을 수행.
        // Trigger: NotificationSent 또는 IssueCreated의 alertType 또는 severityLevel
        //

        // Sample Logic //

    }
}
//>>> Clean Arch / Inbound Adaptor
