package prism.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import prism.domain.*;

@Component
public class AlertSubscriptionHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<AlertSubscription>> {

    @Override
    public EntityModel<AlertSubscription> process(
        EntityModel<AlertSubscription> model
    ) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/subscribetoalert"
                )
                .withRel("subscribetoalert")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/unsubscribefromalert"
                )
                .withRel("unsubscribefromalert")
        );

        return model;
    }
}
