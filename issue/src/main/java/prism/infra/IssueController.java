package prism.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prism.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/issues")
@Transactional
public class IssueController {

    @Autowired
    IssueRepository issueRepository;

    @RequestMapping(
        value = "/issues/{id}/updateissuestatus",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Issue updateIssueStatus(
        @PathVariable(value = "id") Long id,
        @RequestBody UpdateIssueStatusCommand updateIssueStatusCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /issue/updateIssueStatus  called #####");
        Optional<Issue> optionalIssue = issueRepository.findById(id);

        optionalIssue.orElseThrow(() -> new Exception("No Entity Found"));
        Issue issue = optionalIssue.get();
        issue.updateIssueStatus(updateIssueStatusCommand);

        issueRepository.save(issue);
        return issue;
    }
}
//>>> Clean Arch / Inbound Adaptor
