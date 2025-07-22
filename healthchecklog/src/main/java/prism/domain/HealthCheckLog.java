package prism.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import prism.HealthchecklogApplication;
import prism.domain.HealthCheckScheduled;
import prism.domain.HlsFailureDetected;
import prism.domain.HlsHealthChecked;
import prism.domain.IcmpFailureDetected;
import prism.domain.IcmpHealthChecked;
import prism.domain.ResourceChecked;
import prism.domain.ResourceFailureDetected;

@Entity
@Table(name = "HealthCheckLog_table")
@Data
//<<< DDD / Aggregate Root
public class HealthCheckLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String cctvId;

    private Date timestamp;

    private Float icmpLatencyMs;

    private Float hlsResponseMs;

    private Float cpuPercent;

    private Float memoryPercent;

    private Float diskPercent;

    private Long uptimeSeconds;

    private String icmpStatus;

    private String hlsStatus;

    private String resourceStatus;

    private Boolean faultDetected;

    private Date createdAt;

    @PostPersist
    public void onPostPersist() {
        IcmpHealthChecked icmpHealthChecked = new IcmpHealthChecked(this);
        icmpHealthChecked.publishAfterCommit();

        HlsHealthChecked hlsHealthChecked = new HlsHealthChecked(this);
        hlsHealthChecked.publishAfterCommit();

        ResourceChecked resourceChecked = new ResourceChecked(this);
        resourceChecked.publishAfterCommit();

        IcmpFailureDetected icmpFailureDetected = new IcmpFailureDetected(this);
        icmpFailureDetected.publishAfterCommit();

        HlsFailureDetected hlsFailureDetected = new HlsFailureDetected(this);
        hlsFailureDetected.publishAfterCommit();

        ResourceFailureDetected resourceFailureDetected = new ResourceFailureDetected(
            this
        );
        resourceFailureDetected.publishAfterCommit();

        HealthCheckScheduled healthCheckScheduled = new HealthCheckScheduled(
            this
        );
        healthCheckScheduled.publishAfterCommit();
    }

    public static HealthCheckLogRepository repository() {
        HealthCheckLogRepository healthCheckLogRepository = HealthchecklogApplication.applicationContext.getBean(
            HealthCheckLogRepository.class
        );
        return healthCheckLogRepository;
    }

    //<<< Clean Arch / Port Method
    public void scheduleHealthCheck(
        ScheduleHealthCheckCommand scheduleHealthCheckCommand
    ) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void cancelHealthCheckSchedule() {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
