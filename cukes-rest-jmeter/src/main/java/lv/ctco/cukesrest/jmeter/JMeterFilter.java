package lv.ctco.cukesrest.jmeter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lv.ctco.cukesrest.CukesOptions;
import lv.ctco.cukesrest.CukesRuntimeException;
import lv.ctco.cukesrest.internal.context.GlobalWorldFacade;
import lv.ctco.cukesrest.jmeter.function.InitializeConcatFunction;
import lv.ctco.cukesrest.jmeter.function.InitializeGenerateRandomStringFunction;
import lv.ctco.cukesrest.jmeter.function.InitializeGetUrlFunction;
import lv.ctco.cukesrest.jmeter.function.InitializeSaveBoundedValueFunction;
import lv.ctco.cukesrest.jmeter.function.JMeterFunction;
import lv.ctco.cukesrest.jmeter.function.WebCustomRequest;
import lv.ctco.cukesrest.jmeter.mapper.WebCustomRequestMapper;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@Singleton
public class JMeterFilter implements Filter {

    @Inject
    WebCustomRequestMapper mapper;

    @Inject
    GlobalWorldFacade globalWorldFacade;

    private List<JMeterFunction> initializationFunctions = Arrays.asList(
        new InitializeSaveBoundedValueFunction(),
        new InitializeGenerateRandomStringFunction(),
        new InitializeGetUrlFunction(),
        new InitializeConcatFunction()
    );
    private JMeterAction action;
    private JMeterTransaction trx;

    @Before
    public void beforeScenario(Scenario scenario) {
        createJMeterTransaction(scenario.getName());
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {
        WebCustomRequest request = mapper.map(requestSpec);
        trx.addFunction(request);
        boolean blockRequests = globalWorldFacade.getBoolean(CukesOptions.JMETER_FILTER_BLOCKS_REQUESTS);
        if (blockRequests) {
            Response response = Mockito.mock(Response.class);
            Mockito.when(response.then()).thenReturn(Mockito.mock(ValidatableResponse.class));
            return response;
        }
        return ctx.next(requestSpec, responseSpec);
    }

    public void createJMeterAction() {
        action = new JMeterAction();
    }

    public JMeterTransaction getTrx() {
        return trx;
    }

    public void createJMeterTransaction(String name) {
        trx = new JMeterTransaction();
        trx.setName(name);
        trx.setTrxFlag("transactionStatus");
        if (action != null) {
            action.addTransaction(trx);
        }
    }

    public void dump(OutputStream out) {
        try {
            for (JMeterFunction function : initializationFunctions) {
                out.write(function.format().getBytes());
            }
            if (action != null) out.write(action.format().getBytes());
        } catch (IOException e) {
            throw new CukesRuntimeException(e);
        }
    }
}
