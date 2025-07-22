package prism.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import prism.NetworkApplication;
import prism.domain.NetworkAlarmRaised;
import prism.domain.NetworkDispatched;
import prism.domain.NetworkIssueResolved;

@Entity
@Table(name = "NetworkAction_table")
@Data
//<<< DDD / Aggregate Root
public class NetworkAction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cctvId;

    private actionType actionType;

    private String status;

    private String performedBy;

    private String result;

    private Date createdAt;

    private Date updatedAt;

    @PostPersist
    public void onPostPersist() {
        NetworkDispatched networkDispatched = new NetworkDispatched(this);
        networkDispatched.publishAfterCommit();

        NetworkAlarmRaised networkAlarmRaised = new NetworkAlarmRaised(this);
        networkAlarmRaised.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        prism.external.UpdateIssueStatusCommand updateIssueStatusCommand = new prism.external.UpdateIssueStatusCommand();
        // mappings goes here
        NetworkApplication.applicationContext
            .getBean(prism.external.IssueService.class)
            .updateIssueStatus(/* get???(), */updateIssueStatusCommand);

        NetworkIssueResolved networkIssueResolved = new NetworkIssueResolved(
            this
        );
        networkIssueResolved.publishAfterCommit();
    }

    public static NetworkActionRepository repository() {
        NetworkActionRepository networkActionRepository = NetworkApplication.applicationContext.getBean(
            NetworkActionRepository.class
        );
        return networkActionRepository;
    }

    //<<< Clean Arch / Port Method
    public void dispatchToSite(DispatchToSiteCommand dispatchToSiteCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void raiseNetworkAlarm(
        RaiseNetworkAlarmCommand raiseNetworkAlarmCommand
    ) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void resolveNetworkIssue(
        ResolveNetworkIssueCommand resolveNetworkIssueCommand
    ) {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
