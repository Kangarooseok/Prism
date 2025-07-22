package prism.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import prism.domain.*;

@Component
public class NetworkActionHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<NetworkAction>> {

    @Override
    public EntityModel<NetworkAction> process(
        EntityModel<NetworkAction> model
    ) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/dispatchtosite")
                .withRel("dispatchtosite")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/raisenetworkalarm"
                )
                .withRel("raisenetworkalarm")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/resolvenetworkissue"
                )
                .withRel("resolvenetworkissue")
        );

        return model;
    }
}
