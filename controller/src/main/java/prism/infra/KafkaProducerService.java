package prism.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// Kafka 메시지 발행 서비스
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // 지정한 토픽에 메시지 전송
    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
        log.info("Kafka 메시지 발행 완료. topic = {}, message = {}", topic, message);
    }
}
