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
    TvResolutionRepository tvResolutionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='NotificationSent'"
    )
    public void wheneverNotificationSent_AutoDispatchOnCriticalAlert(
        @Payload NotificationSent notificationSent
    ) {
        NotificationSent event = notificationSent;
        System.out.println(
            "\n\n##### listener AutoDispatchOnCriticalAlert : " +
            notificationSent +
            "\n\n"
        );
        // Sample Logic //

    }
}
//>>> Clean Arch / Inbound Adaptor
