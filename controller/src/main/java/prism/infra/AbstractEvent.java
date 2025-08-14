//package prism.infra;
//
//import lombok.Getter;
//import lombok.Setter;
//import prism.domain.cctv.model.Cctv;
//
//// Kafka 이벤트 공통 추상 클래스
//@Getter
//@Setter
//public abstract class AbstractEvent {
//
//    protected String topic;          // 전송 대상 Kafka 토픽
//    protected String eventType;      // 이벤트 타입 (클래스명)
//    protected long timestamp;        // 이벤트 발생 시간 (밀리초)
//
//    public AbstractEvent(Cctv cctv, String topic) {
//        this.topic = topic;
//        this.eventType = this.getClass().getSimpleName(); // 예: CctvRegistered
//        this.timestamp = System.currentTimeMillis();
//    }
//}
