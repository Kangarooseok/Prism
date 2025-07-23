package prism.domain.cctv.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import prism.domain.*;

//test
@Entity
@Table(name = "Cctv_table")
@Data
//<<< DDD / Aggregate Root
public class Cctv {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String locationName;

    private String locationAddress;

    private String ipAddress;

    private String hlsAddress;

    private Float longitude;

    private Float latitude;

    private Date createdAt;

    private Date updatedAt;

    private String status;

    @PostPersist
    public void onPostPersist() {
        CctvRegistered cctvRegistered = new CctvRegistered(this);
        cctvRegistered.publishAfterCommit();

        CctvModified cctvModified = new CctvModified(this);
        cctvModified.publishAfterCommit();

        CctvDeleted cctvDeleted = new CctvDeleted(this);
        cctvDeleted.publishAfterCommit();
    }

    public static CctvRepository repository() {
        CctvRepository cctvRepository = ControllerApplication.applicationContext.getBean(
            CctvRepository.class
        );
        return cctvRepository;
    }

    //<<< Clean Arch / Port Method
    public void registerCctv(RegisterCctvCommand registerCctvCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void modifyCctv(ModifyCctvCommand modifyCctvCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void deleteCctv() {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
