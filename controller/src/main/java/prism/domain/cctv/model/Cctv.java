package prism.domain.cctv.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import prism.domain.cctv.command.ModifyCctvCommand;
import prism.domain.cctv.command.RegisterCctvCommand;

import java.util.Date;

@Entity
@Table(name = "cctv")
@Getter
@Setter
public class Cctv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationName;
    private String locationAddress;
    private String ipAddress;
    private String hlsAddress;
    private Float longitude;
    private Float latitude;

    private String status; // ✅ 여기에 status 필드 추가

    private Date createdAt;
    private Date updatedAt;

    public void registerCctv(RegisterCctvCommand command) {
        this.locationName = command.getLocationName();
        this.locationAddress = command.getLocationAddress();
        this.ipAddress = command.getIpAddress();
        this.hlsAddress = command.getHlsAddress();
        this.longitude = command.getLongitude();
        this.latitude = command.getLatitude();
        this.status = "ACTIVE"; // ✅ 등록 시 status 기본값 설정
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public void modifyCctv(ModifyCctvCommand command) {
        this.locationName = command.getLocationName();
        this.locationAddress = command.getLocationAddress();
        this.ipAddress = command.getIpAddress();
        this.hlsAddress = command.getHlsAddress();
        this.longitude = command.getLongitude();
        this.latitude = command.getLatitude();
        this.updatedAt = new Date();
    }
}
