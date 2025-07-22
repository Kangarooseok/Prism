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
    IssueRepository issueRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='IcmpFailureDetected'"
    )
    public void wheneverIcmpFailureDetected_CreateIssueFromFailure(
        @Payload IcmpFailureDetected icmpFailureDetected
    ) {
        IcmpFailureDetected event = icmpFailureDetected;
        System.out.println(
            "\n\n##### listener CreateIssueFromFailure : " +
            icmpFailureDetected +
            "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HlsFailureDetected'"
    )
    public void wheneverHlsFailureDetected_CreateIssueFromFailure(
        @Payload HlsFailureDetected hlsFailureDetected
    ) {
        HlsFailureDetected event = hlsFailureDetected;
        System.out.println(
            "\n\n##### listener CreateIssueFromFailure : " +
            hlsFailureDetected +
            "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ResourceFailureDetected'"
    )
    public void wheneverResourceFailureDetected_CreateIssueFromFailure(
        @Payload ResourceFailureDetected resourceFailureDetected
    ) {
        ResourceFailureDetected event = resourceFailureDetected;
        System.out.println(
            "\n\n##### listener CreateIssueFromFailure : " +
            resourceFailureDetected +
            "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='NetworkAlarmRaised'"
    )
    public void wheneverNetworkAlarmRaised_CreateIssueFromFailure(
        @Payload NetworkAlarmRaised networkAlarmRaised
    ) {
        NetworkAlarmRaised event = networkAlarmRaised;
        System.out.println(
            "\n\n##### listener CreateIssueFromFailure : " +
            networkAlarmRaised +
            "\n\n"
        );
        // Sample Logic //

    }
}
//>>> Clean Arch / Inbound Adaptor
