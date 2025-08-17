package prism.domain.cctv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import prism.domain.cctv.command.ModifyCctvCommand;
import prism.domain.cctv.command.RegisterCctvCommand;
import prism.domain.group.model.CctvGroup;

import java.time.Instant;

@Entity
@Table(name = "cctv")
@Getter
@Setter
@SQLDelete(sql = "UPDATE cctv SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL") // 조회 시 기본적으로 살아있는 레코드만
public class Cctv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CCTV가 속한 그룹 (지연 로딩, 순환 참조 방지)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private CctvGroup group;

    private String locationName;
    private String locationAddress;
    private String ipAddress;
    private String hlsAddress;
    private Float longitude;
    private Float latitude;

    @Column(length = 20)
    private String status; // 예: ACTIVE / INACTIVE 등

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @PrePersist
    void prePersist() {
        final Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
        if (status == null) status = "ACTIVE";
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }

    // 등록 커맨드를 기반으로 CCTV 정보 세팅
    public void registerCctv(RegisterCctvCommand command) {
        this.locationName = command.getLocationName();
        this.locationAddress = command.getLocationAddress();
        this.ipAddress = command.getIpAddress();
        this.hlsAddress = command.getHlsAddress();
        this.longitude = command.getLongitude();
        this.latitude = command.getLatitude();
        // 그룹 설정은 외부에서 setGroup() 호출로 처리
    }

    // 수정 커맨드를 기반으로 CCTV 정보 업데이트
    public void modifyCctv(ModifyCctvCommand command) {
        this.locationName = command.getLocationName();
        this.locationAddress = command.getLocationAddress();
        this.ipAddress = command.getIpAddress();
        this.hlsAddress = command.getHlsAddress();
        this.longitude = command.getLongitude();
        this.latitude = command.getLatitude();
        // 그룹 설정은 외부에서 setGroup() 호출로 처리
        // updatedAt은 @PreUpdate에서 자동 갱신
    }
}
