package prism.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PolicyHandler {

    @KafkaListener(topics = "prism.cctv", groupId = "prism")
    public void onMessage(String message) {
        log.info("📥 Received Kafka message: {}", message);
        // JSON 파싱 및 후처리 로직 가능
    }
}
