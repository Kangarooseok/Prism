package prism.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// Kafka 메시지 수신 핸들러
@Service
@Slf4j
public class PolicyHandler {

    // 'prism.cctv' 토픽으로 수신된 메시지를 처리
    @KafkaListener(topics = "prism.cctv", groupId = "prism")
    public void onMessage(String message) {
        log.info("📥 Received Kafka message: {}", message);
        // JSON 파싱 및 후처리 로직 가능
    }
}