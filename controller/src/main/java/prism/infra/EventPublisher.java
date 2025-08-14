//package prism.infra;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class EventPublisher {
//
//    private final KafkaProducerService kafkaProducer;
//    private final ObjectMapper objectMapper;
//
//    public void publish(AbstractEvent event) {
//        try {
//            String json = objectMapper.writeValueAsString(event);
//            kafkaProducer.send(event.getTopic(), json);
//            log.info("Event published to Kafka topic [{}]: {}", event.getTopic(), json);
//        } catch (Exception e) {
//            log.error("Kafka 메시지 전송 실패: {}", e.getMessage(), e);
//            throw new RuntimeException(e);
//        }
//    }
//}
