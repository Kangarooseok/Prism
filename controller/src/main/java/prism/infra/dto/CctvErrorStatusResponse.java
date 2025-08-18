package prism.infra.dto;

import lombok.*;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CctvErrorStatusResponse {
    private Long cctvId;
    private String status;        // ACTIVE/WARNING/ERROR 등
    private Instant occurredAt;   // 최신 발생(기록) 시각
}
