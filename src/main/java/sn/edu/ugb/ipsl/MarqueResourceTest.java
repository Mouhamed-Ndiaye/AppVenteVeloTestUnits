package sn.edu.ugb.ipsl;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class MarqueResourceTest {

    private static final String BASE_URI = "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/services/marques";

    @Test
    public void testGetMarquesEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetMarqueByIdEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/1")
                .then()
                .statusCode(302)
                .log().all();
    }

    @Test
    public void testCreateMarqueEndpoint() {
        String requestBody = "{\n" +
                "  \"id\": 20,\n" +
                "  \"nom\": \"Ritchey\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(201)
                .log().all();
    }

    @Test
    public void testUpdateMarqueEndpoint() {
        String requestBody = "{\n" +
                "  \"nom\": \"Updated Marque\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/20")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testDeleteMarqueEndpoint() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/20")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetMarqueNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/20")
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testSearchMarqueNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=nonexistent")
                .then()
                .statusCode(604)
                .log().all();
    }

    @Test
    public void testSearchMarqueFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=le")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testNotAllowedMethodMarque() {
        RestAssured.given()
                .when()
                .put(BASE_URI)
                .then()
                .statusCode(505)
                .log().all();
    }

    @Test
    public void testCreateAlreadyExistedMarque() {
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"nom\": \"Ritchey\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(509)
                .log().all();
    }

    @Test
    public void testMarqueNotFound() {
        RestAssured.given()
                .when()
                .patch(BASE_URI + "/20")
                .then()
                .statusCode(404)
                .log().all();
    }
}
