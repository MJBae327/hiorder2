package hiorder.infra;

import hiorder.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class MenuHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Menu>> {

    @Override
    public EntityModel<Menu> process(EntityModel<Menu> model) {
        return model;
    }
}
