//package prism.domain.cctv.event;
//
//import lombok.Getter;
//import lombok.Setter;
//import prism.domain.cctv.model.Cctv;
//import prism.infra.AbstractEvent;
//
//// CCTV 등록 이벤트 객체
//@Getter
//@Setter
//public class CctvRegistered extends AbstractEvent {
//
//    private Long id;
//    private String locationName;
//    private String locationAddress;
//    private String ipAddress;
//    private String hlsAddress;
//    private Float longitude;
//    private Float latitude;
//    private String status;
//
//    // 등록된 CCTV 정보로 이벤트 초기화
//    public CctvRegistered(Cctv cctv) {
//        super(cctv, "prism.cctv"); // Kafka topic 설정
//        this.id = cctv.getId();
//        this.locationName = cctv.getLocationName();
//        this.locationAddress = cctv.getLocationAddress();
//        this.ipAddress = cctv.getIpAddress();
//        this.hlsAddress = cctv.getHlsAddress();
//        this.longitude = cctv.getLongitude();
//        this.latitude = cctv.getLatitude();
//        this.status = cctv.getStatus();
//    }
//}
