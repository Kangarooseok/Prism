package prism.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import prism.domain.user.model.User;

@Component
public class UserHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<User>> {

    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/userregister")
                .withRel("userregister")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/userupdate")
                .withRel("userupdate")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/userdelete")
                .withRel("userdelete")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/assignusertoteam"
                )
                .withRel("assignusertoteam")
        );

        return model;
    }
}
