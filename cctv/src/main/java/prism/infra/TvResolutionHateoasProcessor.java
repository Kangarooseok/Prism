package prism.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import prism.domain.*;

@Component
public class TvResolutionHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<TvResolution>> {

    @Override
    public EntityModel<TvResolution> process(EntityModel<TvResolution> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/dispatch")
                .withRel("dispatch")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/triggerwarning")
                .withRel("triggerwarning")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/resolvesituation"
                )
                .withRel("resolvesituation")
        );

        return model;
    }
}
