package utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Log implements Filter {

    public static final Logger LOG = LoggerFactory.getLogger(Log.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec, FilterContext ctx) {

        Response response = ctx.next(requestSpec, responseSpec);

        if (response.statusCode() != HttpStatus.SC_OK) {

            LOG.error("\nRequest method:      " + requestSpec.getMethod()
                    + "\nRequest URI:         " + requestSpec.getURI()
                    + "\nHeaders:             " + requestSpec.getHeaders()
                    + "\nRequest Body:        " + requestSpec.getBody()
                    + "\nResponse StatusLine: " + response.getStatusLine()
                    + "\nResponse Headers:    " + response.getHeaders()
                    + "\nResponse Body:\n" + response.getBody().prettyPrint());
        } else {
            LOG.info("\nRequest method:      " + requestSpec.getMethod()
                    + "\nRequest URI:         " + requestSpec.getURI()
                    + "\nHeaders:             " + requestSpec.getHeaders()
                    + "\nRequest Body:        " + requestSpec.getBody()
                    + "\nResponse StatusLine: " + response.getStatusLine()
                    + "\nResponse Body:\n" + response.getBody().prettyPrint());
        }

        return response;
    }
}