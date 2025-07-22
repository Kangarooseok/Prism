package prism.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "issue", url = "${api.url.issue}")
public interface IssueService {
    @RequestMapping(
        method = RequestMethod.PUT,
        path = "/issues/{id}/updateissuestatus"
    )
    public void updateIssueStatus(
        @PathVariable("id") Long id,
        @RequestBody UpdateIssueStatusCommand updateIssueStatusCommand
    );

    @RequestMapping(
        method = RequestMethod.PUT,
        path = "/issues/{id}/updateissuestatus"
    )
    public void updateIssueStatus(
        @PathVariable("id") Long id,
        @RequestBody UpdateIssueStatusCommand updateIssueStatusCommand
    );

    @RequestMapping(
        method = RequestMethod.PUT,
        path = "/issues/{id}/updateissuestatus"
    )
    public void updateIssueStatus(
        @PathVariable("id") Long id,
        @RequestBody UpdateIssueStatusCommand updateIssueStatusCommand
    );
}
