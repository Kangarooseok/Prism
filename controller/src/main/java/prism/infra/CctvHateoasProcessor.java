package prism.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import prism.domain.*;

@Component
public class CctvHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Cctv>> {

    @Override
    public EntityModel<Cctv> process(EntityModel<Cctv> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/registercctv")
                .withRel("registercctv")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/modifycctv")
                .withRel("modifycctv")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/deletecctv")
                .withRel("deletecctv")
        );

        return model;
    }
}
