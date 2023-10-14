package sn.edu.ugb.ipsl;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class EmployeResourceTest {

    private static final String BASE_URI = "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/services/employes";

    @Test
    public void testGetEmployeEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetEmployeByIdEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/12")
                .then()
                .statusCode(302)
                .log().all();
    }

    @Test
    public void testCreateEmployeEndpoint() {
        String requestBody = "{\n" +
                "  \"prenom\": \"Samba\",\n" +
                "  \"nom\": \"Sidibe\",\n" +
                "  \"email\": \"sidibe.samba@ept.sn\",\n" +
                "  \"telephone\": \"(123) 456-7890\",\n" +
                "  \"actif\": 1,\n" +
                "  \"magasin\": 1,\n" +
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
    public void testUpdateEmployeEndpoint() {
        String requestBody = "{\n" +
                "  \"manager\": 12\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/15")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testDeleteEmployeEndpoint() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/86")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetEmployeNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/999") // ID inexistant pour provoquer une erreur 404
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testSearchEmployeNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=nonexistent") // Nom inexistant pour provoquer une erreur 604
                .then()
                .statusCode(604)
                .log().all(); // Vérifiez que le statut de la réponse est 604 (Non trouvé)
    }

    @Test
    public void testSearchEmployeFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=mikasa") // Nom inexistant pour provoquer une erreur 604
                .then()
                .statusCode(200)
                .log().all(); // Vérifiez que le statut de la réponse est 604 (Non trouvé)
    }

    @Test
    public void testNotAllowedMethodEmploye() {
        RestAssured.given()
                .when()
                .put(BASE_URI)
                .then()
                .statusCode(505)
                .log().all();
    }

    @Test
    public void testNoDataInRequiredField() {
        String requestBody = "{\n" +
                "  \"email\": \"sidibe.samba@ept.sn\",\n" +
                "  \"telephone\": \"(123) 456-7890\",\n" +
                "  \"actif\": 1,\n" +
                "  \"magasin\": 1\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(600)
                .log().all();
    }

    @Test
    public void testInvalidEmailFormat() {
        String requestBody = "{\n" +
                "  \"email\": \"sidibe.sambaept.sn\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/15")
                .then()
                .statusCode(615)
                .log().all();
    }

    @Test
    public void testInvalidPhoneFormat() {
        String requestBody = "{\n" +
                "  \"telephone\": \"777777777\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/15")
                .then()
                .statusCode(630)
                .log().all();
    }

    @Test
    public void testEmailAlreadyExist() {
        String requestBody = "{\n" +
                "  \"email\": \"ackerman.mikasa@yahoo.com\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/15")
                .then()
                .statusCode(700)
                .log().all();
    }

    @Test
    public void testPhonelAlreadyExist() {
        String requestBody = "{\n" +
                "  \"telephone\": \"(025) 187-8787\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/15")
                .then()
                .statusCode(800)
                .log().all();
    }

    @Test
    public void testNoDataInForeignKey() {
        String requestBody = "{\n" +
                "  \"prenom\": \"Samba\",\n" +
                "  \"nom\": \"Sidibe\",\n" +
                "  \"email\": \"sidibe.samba@ept.sn\",\n" +
                "  \"telephone\": \"(123) 456-7890\",\n" +
                "  \"actif\": 1\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(508)
                .log().all();
    }

}
