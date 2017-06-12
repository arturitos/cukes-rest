package lv.ctco.cukesrest.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.restassured.specification.RequestSpecification;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import lv.ctco.cukesrest.internal.context.InflateContext;

@Singleton
@InflateContext
public class TemplatingFacade {

    @Inject
    RequestSpecificationFacade specification;
    @Inject
    GlobalWorldFacade world;


    public String getTemplate(RequestSpecification requestSpec) {
        return "no template";
    }

    public String processTemplate(String template) {
        return "processed template";
    }
}
