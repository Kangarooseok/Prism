package prism.domain.cctv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import prism.domain.cctv.command.ModifyCctvCommand;
import prism.domain.cctv.command.RegisterCctvCommand;
import prism.domain.group.model.CctvGroup;

import java.util.Date;

@Entity
@Table(name = "cctv")
@Getter
@Setter
public class Cctv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CCTV가 속한 그룹 (지연 로딩, 순환 참조 방지)
    // 트러블슈팅 작성 예정
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
    private String status;
    private Date createdAt;
    private Date updatedAt;

    // 등록 커맨드를 기반으로 CCTV 정보 세팅
    public void registerCctv(RegisterCctvCommand command) {
        this.locationName = command.getLocationName();
        this.locationAddress = command.getLocationAddress();
        this.ipAddress = command.getIpAddress();
        this.hlsAddress = command.getHlsAddress();
        this.longitude = command.getLongitude();
        this.latitude = command.getLatitude();
        this.status = "ACTIVE";
        this.createdAt = new Date();
        this.updatedAt = new Date();

        if (command.getGroupId() != null) {
            CctvGroup group = new CctvGroup();
            group.setId(command.getGroupId());
            this.group = group;
        }
    }

    // 수정 커맨드를 기반으로 CCTV 정보 업데이트
    public void modifyCctv(ModifyCctvCommand command) {
        this.locationName = command.getLocationName();
        this.locationAddress = command.getLocationAddress();
        this.ipAddress = command.getIpAddress();
        this.hlsAddress = command.getHlsAddress();
        this.longitude = command.getLongitude();
        this.latitude = command.getLatitude();
        this.updatedAt = new Date();

        if (command.getGroupId() != null) {
            CctvGroup group = new CctvGroup();
            group.setId(command.getGroupId());
            this.group = group;
        }
    }
}
