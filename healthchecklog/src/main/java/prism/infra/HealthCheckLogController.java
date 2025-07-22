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
// @RequestMapping(value="/healthCheckLogs")
@Transactional
public class HealthCheckLogController {

    @Autowired
    HealthCheckLogRepository healthCheckLogRepository;

    @RequestMapping(
        value = "/healthCheckLogs/{id}/schedulehealthcheck",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public HealthCheckLog scheduleHealthCheck(
        @PathVariable(value = "id") String id,
        @RequestBody ScheduleHealthCheckCommand scheduleHealthCheckCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /healthCheckLog/scheduleHealthCheck  called #####"
        );
        Optional<HealthCheckLog> optionalHealthCheckLog = healthCheckLogRepository.findById(
            id
        );

        optionalHealthCheckLog.orElseThrow(() ->
            new Exception("No Entity Found")
        );
        HealthCheckLog healthCheckLog = optionalHealthCheckLog.get();
        healthCheckLog.scheduleHealthCheck(scheduleHealthCheckCommand);

        healthCheckLogRepository.save(healthCheckLog);
        return healthCheckLog;
    }

    @RequestMapping(
        value = "/healthCheckLogs/{id}/cancelhealthcheckschedule",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public HealthCheckLog cancelHealthCheckSchedule(
        @PathVariable(value = "id") String id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /healthCheckLog/cancelHealthCheckSchedule  called #####"
        );
        Optional<HealthCheckLog> optionalHealthCheckLog = healthCheckLogRepository.findById(
            id
        );

        optionalHealthCheckLog.orElseThrow(() ->
            new Exception("No Entity Found")
        );
        HealthCheckLog healthCheckLog = optionalHealthCheckLog.get();
        healthCheckLog.cancelHealthCheckSchedule();

        healthCheckLogRepository.delete(healthCheckLog);
        return healthCheckLog;
    }
}
//>>> Clean Arch / Inbound Adaptor
