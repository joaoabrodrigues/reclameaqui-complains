package br.com.reclameaqui.complaints.config;

import br.com.reclameaqui.complaints.model.Complaint;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.validation.Validator;

@Configuration
public class RepositoryRestConfig extends RepositoryRestMvcConfiguration {

    private final Validator validator;

    @Autowired
    public RepositoryRestConfig(ApplicationContext context, ObjectFactory<ConversionService> conversionService, Validator validator) {
        super(context, conversionService);
        this.validator = validator;
    }

    @Override
    public RepositoryRestConfiguration repositoryRestConfiguration() {
        RepositoryRestConfiguration repositoryRestConfiguration = super.repositoryRestConfiguration();
        return repositoryRestConfiguration.exposeIdsFor(Complaint.class);
    }

    @Override
    public ValidatingRepositoryEventListener validatingRepositoryEventListener(ObjectFactory<PersistentEntities> entities) {
        ValidatingRepositoryEventListener validatingListener = super.validatingRepositoryEventListener(entities);
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
        return validatingListener;
    }
}
