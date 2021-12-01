package utils.api;

import endpoints.EndPoints;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.Log;

public class Specification {

    public static RequestSpecification getRequestSpecification() {

        return new RequestSpecBuilder()
                .setBaseUri(EndPoints.BASE_URI)
                .setContentType(ContentType.JSON)
                .addFilter(new Log())
                .build();
    }
}
