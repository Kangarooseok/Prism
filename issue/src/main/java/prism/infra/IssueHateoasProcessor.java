package prism.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import prism.domain.*;

@Component
public class IssueHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Issue>> {

    @Override
    public EntityModel<Issue> process(EntityModel<Issue> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/updateissuestatus"
                )
                .withRel("updateissuestatus")
        );

        return model;
    }
}
