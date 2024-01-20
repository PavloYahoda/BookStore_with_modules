package pyah.bookstore;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {

    @BeforeAll
    public static void setBaseUrl() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    protected RequestSpecification requestBase() {
        return RestAssured
                .given().log().all();
    }

    protected RequestSpecification requestWithAuth(String token) {
        return requestBase()
                .queryParams(Map.of(
                        "Content-Type", "application/json",
                        "token", token
                ));
    }

    protected RequestSpecification requestWithoutAuth() {
        return requestBase()
                .queryParams(Map.of(
                        "Content-Type", "application/json"
                ));
    }


    public ExtractableResponse<Response> postMethod(String baseUrl, UserData payload, String bearerToken, String endpoint) {
        return given()
                .spec(getSpecForPost(bearerToken))
                .when()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload).log().all()
                .post(endpoint)
                .then().log().all().extract();
    }

    public ExtractableResponse<Response> postMethodWithStringPayload(String baseUrl, String payload, String bearerToken, String endpoint) {
        return given()
                .spec(getSpecForPost(bearerToken))
                .when()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload).log().all()
                .post(endpoint)
                .then().log().all().extract();
    }

    public ExtractableResponse<Response> getMethod(String baseUrl, String bearerToken, String endpoint) {
        return given()
                .spec(getSpecForPost(bearerToken))
                .when()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get(endpoint)
                .then().log().all().extract();
    }

    public ExtractableResponse<Response> deleteMethodWithStringPayload(String baseUrl, String payload, String bearerToken, String endpoint) {
        return given()
                .spec(getSpecForPost(bearerToken))
                .when()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload).log().all()
                .delete(endpoint)
                .then().log().all().extract();
    }

    public ExtractableResponse<Response> deleteMethodWithQueryParam(String baseUrl, String queryParam, String bearerToken, String endpoint) {
        return given()
                .spec(getSpecForPost(bearerToken))
                .when()
                .baseUri(baseUrl)
                .queryParam("UserId", queryParam)
                .contentType(ContentType.JSON)
                .delete(endpoint)
                .then().log().all().extract();
    }

    public ExtractableResponse<Response> deleteMethod(String baseUrl, String bearerToken, String endpoint) {
        return given()
                .spec(getSpecForPost(bearerToken))
                .when()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .delete(endpoint)
                .then().log().all().extract();
    }

    public RequestSpecification getSpecForPost(String token) {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder();
        specBuilder.addHeader("Content Type", "application/json");
        specBuilder.addHeader("Authorization", "Bearer " + token);
        return specBuilder.build();
    }

    // JSON comparator
    public JSONComparator compareSomethingExceptSomething(String path) {
        return new CustomComparator(JSONCompareMode.LENIENT,
                new Customization(path, (o1, o2) -> true)
        );
    }

//    JSON Schema Validator
    public boolean validationJSONSchema(String baseUrl, String bearerToken, String endpoint, String path){
         RestAssured
                .given()
                .spec(getSpecForPost(bearerToken))
                .when()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get(endpoint)
                .then().log().all()
                .body(JsonSchemaValidator.matchesJsonSchema(new File(path)));
         return true;
    }


}