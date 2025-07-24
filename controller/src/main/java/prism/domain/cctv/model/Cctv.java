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
    private String status;
    private Date createdAt;
    private Date updatedAt;

    // 등록 처리 로직
    public void registerCctv(RegisterCctvCommand command) {
        this.locationName = command.getLocationName();
        this.locationAddress = command.getLocationAddress();
        this.ipAddress = command.getIpAddress();
        this.hlsAddress = command.getHlsAddress();
        this.longitude = command.getLongitude();
        this.latitude = command.getLatitude();
        this.status = command.getStatus();
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // 수정 처리 로직
    public void modifyCctv(ModifyCctvCommand command) {
        this.locationName = command.getLocationName();
        this.locationAddress = command.getLocationAddress();
        this.ipAddress = command.getIpAddress();
        this.hlsAddress = command.getHlsAddress();
        this.longitude = command.getLongitude();
        this.latitude = command.getLatitude();
        this.status = command.getStatus();
        this.updatedAt = new Date();
    }

    // 삭제 처리 로직 (필요에 따라 soft delete로 변경 가능)
    public void deleteCctv() {
        this.status = "DELETED"; // 또는 그냥 삭제 처리: 서비스/컨트롤러에서 repository.delete(this)
        this.updatedAt = new Date();
    }
}
