package lv.ctco.cukesrest.jmeter.mapper;

import io.restassured.http.Header;
import lv.ctco.cukesrest.jmeter.function.WebAddHeader;

public class WebAddHeaderMapper {

    public WebAddHeader map(Header header) {
        WebAddHeader result = new WebAddHeader();
        result.setName(header.getName());
        result.setValue(header.getValue());

        return result;
    }
}
