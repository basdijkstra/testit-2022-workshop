package checks;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@WireMockTest(httpPort = 9876)
public class LetsSeeIfEverythingWorksTest {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpec() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }

    @Test
    public void IfThisTestPassesYoureGoodToGo() {

        setupHelloWorldMock();

        given().spec(requestSpec).when().get("/hello-world").then().assertThat().statusCode(200);
    }

    private void setupHelloWorldMock() {

        stubFor(
                get(
                        urlEqualTo("/hello-world"))
                        .willReturn(
                                aResponse().
                                        withStatus(200)
                        )
        );
    }
}
