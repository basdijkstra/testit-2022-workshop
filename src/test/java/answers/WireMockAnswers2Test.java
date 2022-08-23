package answers;

import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9876)
public class WireMockAnswers2Test {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://localhost").
            setPort(9876).
            build();
    }

    public void setupStubExercise201() {

        /************************************************
         * Create a stub that will respond to all POST
         * requests to /requestLoan
         * with HTTP status code 503 and a status message
         * equal to 'Loan processor service unavailable'
         *
         * Have a look at https://wiremock.org/docs/stubbing/
         * under 'Setting the response status message'
         * for an example of how to do this
         ************************************************/

        stubFor(post(urlEqualTo("/requestLoan"))
            .willReturn(aResponse()
                .withStatus(503)
                .withStatusMessage("Loan processor service unavailable")
            ));
    }

    public void setupStubExercise202() {

        /************************************************
         * Create a stub that will respond to a POST request
         * to /requestLoan, but only if this request contains
         * a header 'speed' with value 'slow'.
         *
         * Respond with status code 200, but only after a
         * fixed delay of 3000 milliseconds.
         ************************************************/

        stubFor(post(urlEqualTo("/requestLoan"))
            .withHeader("speed", equalTo("slow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withFixedDelay(3000)
            ));
    }

    public void setupStubExercise203() {

        /************************************************
         * Create a stub that will respond to a POST request
         * to /requestLoan, but only if this request contains
         * a cookie 'session' with value 'invalid'
         *
         * Respond with a Fault of type RANDOM_DATA_THEN_CLOSE
         ************************************************/

        stubFor(post(urlEqualTo("/requestLoan"))
                .withCookie("session", equalTo("invalid"))
                .willReturn(aResponse()
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)
                ));
    }

    public void setupStubExercise204() {

        /************************************************
         * Create a stub that will respond to a POST request
         * to /requestLoan with status code 200,
         * but only if:
         * - the 'backgroundCheck' header has value 'OK'
         * - the 'backgroundCheck' header is not present
         ************************************************/

        stubFor(post(urlEqualTo("/requestLoan"))
                .withHeader("backgroundCheck",
                        equalTo("OK").or(absent())
                )
                .willReturn(aResponse()
                        .withStatus(200))
        );
    }

    @Test
    public void testExercise201() {

        /***
         * Use this test to test your implementation of exercise 201
         */

        setupStubExercise201();

        given().
            spec(requestSpec).
        when().
            post("/requestLoan").
        then().
            assertThat().
            statusCode(503).
        and().
            statusLine(org.hamcrest.Matchers.containsString("Loan processor service unavailable"));
    }

    @Test
    public void testExercise202() {

        /***
         * Use this test to test your implementation of exercise 202
         */

        setupStubExercise202();

        given().
            spec(requestSpec).
        and().
            header("speed","slow").
        when().
            post("/requestLoan").
        then().
            assertThat().
            statusCode(200).
        and().
            time(org.hamcrest.Matchers.greaterThan(3000L));
    }

    @Test
    public void testExercise203() {

        /***
         * Use this test to test your implementation of exercise 203
         */

        setupStubExercise203();

        Assertions.assertThrows(ClientProtocolException.class, () -> {

            given().
                spec(requestSpec).
            and().
                cookie("session", "invalid").
            when().
                post("/requestLoan");
        });
    }

    @Test
    public void testExercise204() {

        /***
         * Use this test to test your implementation of exercise 204
         */

        setupStubExercise204();

        given().
            spec(requestSpec).
        and().
            header("backgroundCheck", "OK").
        when().
            post("/requestLoan").
        then().
            assertThat().
            statusCode(200);

        given().
            spec(requestSpec).
        when().
            post("/requestLoan").
        then().
            assertThat().
            statusCode(200);

        given().
            spec(requestSpec).
        and().
            header("backgroundCheck", "FAILED").
        when().
            post("/requestLoan").
        then().
            assertThat().
            statusCode(404);
    }
}