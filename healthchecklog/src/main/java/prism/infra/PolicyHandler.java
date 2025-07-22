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
    HealthCheckLogRepository healthCheckLogRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CctvRegistered'"
    )
    public void wheneverCctvRegistered_ScheduleHealthCheckOnCctvRegisterOrUpdate(
        @Payload CctvRegistered cctvRegistered
    ) {
        CctvRegistered event = cctvRegistered;
        System.out.println(
            "\n\n##### listener ScheduleHealthCheckOnCctvRegisterOrUpdate : " +
            cctvRegistered +
            "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CctvModified'"
    )
    public void wheneverCctvModified_ScheduleHealthCheckOnCctvRegisterOrUpdate(
        @Payload CctvModified cctvModified
    ) {
        CctvModified event = cctvModified;
        System.out.println(
            "\n\n##### listener ScheduleHealthCheckOnCctvRegisterOrUpdate : " +
            cctvModified +
            "\n\n"
        );
        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CctvDeleted'"
    )
    public void wheneverCctvDeleted_RemoveHealthCheckScheduleOnCctvDeleted(
        @Payload CctvDeleted cctvDeleted
    ) {
        CctvDeleted event = cctvDeleted;
        System.out.println(
            "\n\n##### listener RemoveHealthCheckScheduleOnCctvDeleted : " +
            cctvDeleted +
            "\n\n"
        );
        // Sample Logic //

    }
}
//>>> Clean Arch / Inbound Adaptor
