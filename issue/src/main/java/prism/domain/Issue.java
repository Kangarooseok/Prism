package prism.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import prism.IssueApplication;
import prism.domain.IssueCreated;
import prism.domain.IssueStatusUpdated;

@Entity
@Table(name = "Issue_table")
@Data
//<<< DDD / Aggregate Root
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cctvId;

    private String status;

    private String description;

    private healthCheckType healthCheckType;

    private Date createdAt;

    private Date updatedAt;

    private Date failureTime;

    private Date resolvedAt;

    public static IssueRepository repository() {
        IssueRepository issueRepository = IssueApplication.applicationContext.getBean(
            IssueRepository.class
        );
        return issueRepository;
    }

    //<<< Clean Arch / Port Method
    public void updateIssueStatus(
        UpdateIssueStatusCommand updateIssueStatusCommand
    ) {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
