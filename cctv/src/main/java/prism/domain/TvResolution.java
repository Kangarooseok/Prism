package prism.domain;

import prism.domain.Dispatched;
import prism.domain.WarningTriggered;
import prism.domain.SituationResolved;
import prism.CctvApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;


@Entity
@Table(name="TvResolution_table")
@Data

//<<< DDD / Aggregate Root
public class TvResolution  {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
private Long id;    
    
    
private String cctvId;    
    
    
private String status;    
    
    
private String healthCheckId;    
    
    
private Date createdAt;    
    
    
private Date updatedAt;

    @PostPersist
    public void onPostPersist(){

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        prism.external.UpdateIssueStatusCommand updateIssueStatusCommand = new prism.external.UpdateIssueStatusCommand();
        // mappings goes here
        CctvApplication.applicationContext.getBean(prism.external.IssueService.class)
            .updateIssueStatus(/* get???(), */ updateIssueStatusCommand);



        Dispatched dispatched = new Dispatched(this);
        dispatched.publishAfterCommit();


        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        prism.external.UpdateIssueStatusCommand updateIssueStatusCommand = new prism.external.UpdateIssueStatusCommand();
        // mappings goes here
        CctvApplication.applicationContext.getBean(prism.external.IssueService.class)
            .updateIssueStatus(/* get???(), */ updateIssueStatusCommand);



        WarningTriggered warningTriggered = new WarningTriggered(this);
        warningTriggered.publishAfterCommit();


        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        prism.external.UpdateIssueStatusCommand updateIssueStatusCommand = new prism.external.UpdateIssueStatusCommand();
        // mappings goes here
        CctvApplication.applicationContext.getBean(prism.external.IssueService.class)
            .updateIssueStatus(/* get???(), */ updateIssueStatusCommand);



        SituationResolved situationResolved = new SituationResolved(this);
        situationResolved.publishAfterCommit();

    
    }

    public static TvResolutionRepository repository(){
        TvResolutionRepository tvResolutionRepository = CctvApplication.applicationContext.getBean(TvResolutionRepository.class);
        return tvResolutionRepository;
    }



//<<< Clean Arch / Port Method
    public void dispatch(DispatchCommand dispatchCommand){
        
        //implement business logic here:
        

        prism.external.TvResolutionQuery tvResolutionQuery = new prism.external.TvResolutionQuery();
        // tvResolutionQuery.set??()        
          = TvResolutionApplication.applicationContext
            .getBean(prism.external.Service.class)
            .tvResolution(tvResolutionQuery);

    }
//>>> Clean Arch / Port Method
//<<< Clean Arch / Port Method
    public void triggerWarning(TriggerWarningCommand triggerWarningCommand){
        
        //implement business logic here:
        


    }
//>>> Clean Arch / Port Method
//<<< Clean Arch / Port Method
    public void resolveSituation(ResolveSituationCommand resolveSituationCommand){
        
        //implement business logic here:
        


    }
//>>> Clean Arch / Port Method



}
//>>> DDD / Aggregate Root
