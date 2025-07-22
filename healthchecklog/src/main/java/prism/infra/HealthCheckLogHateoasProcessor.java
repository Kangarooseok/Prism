package prism.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import prism.domain.*;

@Component
public class HealthCheckLogHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<HealthCheckLog>> {

    @Override
    public EntityModel<HealthCheckLog> process(
        EntityModel<HealthCheckLog> model
    ) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/schedulehealthcheck"
                )
                .withRel("schedulehealthcheck")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/cancelhealthcheckschedule"
                )
                .withRel("cancelhealthcheckschedule")
        );

        return model;
    }
}
