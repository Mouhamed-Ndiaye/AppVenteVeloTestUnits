package sn.edu.ugb.ipsl;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class ClientResourceTest {

    private static final String BASE_URI = "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/services/clients";

    @Test
    public void testGetClientsEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetClientByIdEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/1")
                .then()
                .statusCode(302)
                .log().all();
    }

    @Test
    public void testCreateClientEndpoint() {
        String requestBody = "{\n" +
                "  \"nom\": \"NomClient\",\n" +
                "  \"prenom\": \"PrenomClient\",\n" +
                "  \"email\": \"client@example.com\",\n" +
                "  \"telephone\": \"(999) 999-9999\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"123 Rue de la Ville\",\n" +
                "    \"codeZip\": \"12345\",\n" +
                "    \"etat\": \"Etat\",\n" +
                "    \"ville\": \"Ville\"\n" +
                "  }\n" +
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
    public void testUpdateClientEndpoint() {
        String requestBody = "{\n" +
                "  \"telephone\": \"(888) 888-8888\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"456 Rue de la Ville\",\n" +
                "    \"ville\": \"NouvelleVille\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/1454")
                .then()
                .statusCode(200)
                .log().all();
    }


    @Test
    public void testDeleteClientEndpoint() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/1453")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testSearchClientNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=ClientInexistant")
                .then()
                .statusCode(604)
                .log().all();
    }

    @Test
    public void testClientNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/1563")
                .then()
                .statusCode(404)
                .log().all();
    }


    @Test
    public void testCreateClientWithNoDataInRequiredField() {
        String requestBody = "{\n" +
                "  \"prenom\": \"PrenomClient\",\n" +
                "  \"email\": \"client@example.com\",\n" +
                "  \"telephone\": \"(999) 999-9999\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"123 Rue de la Ville\",\n" +
                "    \"codeZip\": \"12345\",\n" +
                "    \"etat\": \"Etat\",\n" +
                "    \"ville\": \"Ville\"\n" +
                "  }\n" +
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
    public void testIncorrectEmailFormat() {
        String requestBody = "{\n" +
                "  \"nom\": \"NomClient\",\n" +
                "  \"prenom\": \"PrenomClient\",\n" +
                "  \"email\": \"client@examplom\",\n" +
                "  \"telephone\": \"(999) 999-9999\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"123 Rue de la Ville\",\n" +
                "    \"codeZip\": \"12345\",\n" +
                "    \"etat\": \"Etat\",\n" +
                "    \"ville\": \"Ville\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(615)
                .log().all();
    }

    @Test
    public void testIncorrectPhoneFormat() {
        String requestBody = "{\n" +
                "  \"nom\": \"NomClient\",\n" +
                "  \"prenom\": \"PrenomClient\",\n" +
                "  \"email\": \"client@example.com\",\n" +
                "  \"telephone\": \"(999) 9999999\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"123 Rue de la Ville\",\n" +
                "    \"codeZip\": \"12345\",\n" +
                "    \"etat\": \"Etat\",\n" +
                "    \"ville\": \"Ville\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(630)
                .log().all();
    }

    @Test
    public void testEmailAlreadyExist() {
        String requestBody = "{\n" +
                "  \"nom\": \"NomClient\",\n" +
                "  \"prenom\": \"PrenomClient\",\n" +
                "  \"email\": \"pamel.newman@gmail.com\",\n" +
                "  \"telephone\": \"(999) 9999999\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"123 Rue de la Ville\",\n" +
                "    \"codeZip\": \"12345\",\n" +
                "    \"etat\": \"Etat\",\n" +
                "    \"ville\": \"Ville\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(700)
                .log().all();
    }



    @Test
    public void testPhoneAlreadyExist() {
        String requestBody = "{\n" +
                "  \"nom\": \"NomClient\",\n" +
                "  \"prenom\": \"PrenomClient\",\n" +
                "  \"email\": \"example@gmail.com\",\n" +
                "  \"telephone\": \"(221) 478-8847\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"123 Rue de la Ville\",\n" +
                "    \"codeZip\": \"12345\",\n" +
                "    \"etat\": \"Etat\",\n" +
                "    \"ville\": \"Ville\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(800)
                .log().all();
    }

    @Test
    public void testTooLongState() {
        String requestBody = "{\n" +
                "  \"nom\": \"NomClient\",\n" +
                "  \"prenom\": \"PrenomClient\",\n" +
                "  \"email\": \"example@gmail.com\",\n" +
                "  \"telephone\": \"(999) 999-9999\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"123 Rue de la Ville\",\n" +
                "    \"codeZip\": \"12345\",\n" +
                "    \"etat\": \"Etatskdfnvkdsnvknfdsknfndkfnkdnfkndknfkndknfkdnkfndkfndnkfkdnkfeknfedf\",\n" +
                "    \"ville\": \"Ville\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(750)
                .log().all();
    }

    @Test
    public void testTooLongCodeZip() {
        String requestBody = "{\n" +
                "  \"nom\": \"NomClient\",\n" +
                "  \"prenom\": \"PrenomClient\",\n" +
                "  \"email\": \"example@gmail.com\",\n" +
                "  \"telephone\": \"(999) 999-9999\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"123 Rue de la Ville\",\n" +
                "    \"codeZip\": \"123456\",\n" +
                "    \"etat\": \"Etat\",\n" +
                "    \"ville\": \"Ville\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(760)
                .log().all();
    }


    @Test
    public void testInvalidCodeZipFormat() {
        String requestBody = "{\n" +
                "  \"nom\": \"NomClient\",\n" +
                "  \"prenom\": \"PrenomClient\",\n" +
                "  \"email\": \"example@gmail.com\",\n" +
                "  \"telephone\": \"(999) 999-9999\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"123 Rue de la Ville\",\n" +
                "    \"codeZip\": \"1 3 4\",\n" +
                "    \"etat\": \"Etat\",\n" +
                "    \"ville\": \"Ville\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(850)
                .log().all();
    }


    @Test
    public void testDeleteClientNotFound() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/1563")
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testNotApproppriateMedia() {
        String requestBody = "{\n" +
                "  \"nom\": \"NouveauNom\",\n" +
                "  \"prenom\": \"NouveauPrenom\",\n" +
                "  \"email\": \"nouveauclient@example.com\",\n" +
                "  \"telephone\": \"(888) 888-8888\",\n" +
                "  \"adresse\": {\n" +
                "    \"adresse\": \"456 Rue de la Ville\",\n" +
                "    \"codeZip\": \"54321\",\n" +
                "    \"etat\": \"NouvelEtat\",\n" +
                "    \"ville\": \"NouvelleVille\"\n" +
                "  }\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/")
                .then()
                .statusCode(400)
                .log().all();
    }
}
