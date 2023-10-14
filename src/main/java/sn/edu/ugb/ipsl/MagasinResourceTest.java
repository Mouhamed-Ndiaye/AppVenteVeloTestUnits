package sn.edu.ugb.ipsl;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class MagasinResourceTest {

    private static final String BASE_URI = "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/services/magasins";

    @Test
    public void testGetMagasinsEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetMagasinByIdEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/5")
                .then()
                .statusCode(302)
                .log().all();
    }

    @Test
    public void testCreateMagasinEndpoint() {
        String requestBody = "<magasin>\n" +
                "\t<nom>Magasin</nom>\n" +
                "\t<telephone>(123) 456-8890</telephone>\n" +
                "\t<email>emil@gmail.com</email>\n" +
                "\t<adresse>\n" +
                "\t\t<adresse>adresse</adresse>\n" +
                "\t\t<ville></ville>\n" +
                "\t</adresse>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(201)
                .log().all();
    }

    @Test
    public void testUpdateMagasinEndpoint() {
        String requestBody = "{\n" +
                "  \"nom\": \"Magasin Modifié\",\n" +
                "  \"email\": \"modifie@example.com\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/11")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testDeleteMagasinEndpoint() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/12")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetMagasinNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/8")
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testSearchMagasinNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=nonexistent")
                .then()
                .statusCode(604)
                .log().all();
    }

    @Test
    public void testNotAllowedMethodMagasin() {
        RestAssured.given()
                .when()
                .put(BASE_URI)
                .then()
                .statusCode(505)
                .log().all();
    }

    @Test
    public void testNoDataInRequiredFields() {
        String requestBody = "<magasin>\n" +
                "\t<telephone>(123) 456-8890</telephone>\n" +
                "\t<email>emil@gmail.com</email>\n" +
                "\t<adresse>\n" +
                "\t\t<adresse>adresse</adresse>\n" +
                "\t\t<ville></ville>\n" +
                "\t</adresse>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(600)
                .log().all();
    }

    @Test
    public void testInvalidEmailFormat() {
        String requestBody = "<magasin>\n" +
                "\t<nom>Magasin</nom>\n" +
                "\t<telephone>(123) 456-8890</telephone>\n" +
                "\t<email>emil@gmaicom</email>\n" +
                "\t<adresse>\n" +
                "\t\t<adresse>adresse</adresse>\n" +
                "\t\t<ville></ville>\n" +
                "\t</adresse>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(615)
                .log().all();
    }

    @Test
    public void testInvalidPhoneFormat() {
        String requestBody = "<magasin>\n" +
                "\t<nom>Magasin</nom>\n" +
                "\t<telephone>(123)456-8890</telephone>\n" +
                "\t<email>emil@gmail.com</email>\n" +
                "\t<adresse>\n" +
                "\t\t<adresse>adresse</adresse>\n" +
                "\t\t<ville></ville>\n" +
                "\t</adresse>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(630)
                .log().all();
    }

    @Test
    public void testAlreadyEmailExist() {
        String requestBody = "<magasin>\n" +
                "\t<nom>Magasin</nom>\n" +
                "\t<email>baldwin@bikes.shop</email>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/1")
                .then()
                .statusCode(700)
                .log().all();
    }

    @Test
    public void testTooLongState() {
        String requestBody = "<magasin>\n" +
                "\t<adresse>\n" +
                "\t\t<adresse></adresse>\n" +
                "\t\t<ville>string</ville>\n" +
                "\t\t<etat>Eh le tocard tu fois quoi la, t'es teube tu sais mais bonn</etat>\n" +
                "\t</adresse>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/1")
                .then()
                .statusCode(750)
                .log().all();
    }

    @Test
    public void testTooLongCodeZip() {
        String requestBody = "<magasin>\n" +
                "\t<adresse>\n" +
                "\t\t<adresse>adresse</adresse>\n" +
                "\t\t<ville></ville>\n" +
                "\t\t<codeZip>123456</codeZip>\n" +
                "\t</adresse>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/2")
                .then()
                .statusCode(760)
                .log().all();
    }

    @Test
    public void testAlreadylTelephoneExist() {
        String requestBody = "<magasin>\n" +
                "\t<nom>Magasin</nom>\n" +
                "\t<telephone>(516) 379-8888</telephone>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/1")
                .then()
                .statusCode(800)
                .log().all();
    }

    @Test
    public void testInvalidFormatCodeZip() {
        String requestBody = "<magasin>\n" +
                "\t<adresse>\n" +
                "\t\t<adresse>adresse</adresse>\n" +
                "\t\t<ville></ville>\n" +
                "\t\t<codeZip>12 5</codeZip>\n" +
                "\t</adresse>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/2")
                .then()
                .statusCode(850)
                .log().all();
    }

    @Test
    public void testNotFoundMagasin() {
        String requestBody = "<magasin>\n" +
                "\t<adresse>\n" +
                "\t\t<adresse>adresse</adresse>\n" +
                "\t\t<ville></ville>\n" +
                "\t\t<codeZip>125</codeZip>\n" +
                "\t</adresse>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/20")
                .then()
                .statusCode(404)
                .log().all();
    }


    @Test
    public void testBadRequest() {
        String requestBody = "<magasin>\n" +
                "\t<adresse>\n" +
                "\t\t<adresse>string</adresse>\n" +
                "\t\t<ville>string</ville>\n" +
                "\t\t<etat>Etat Trop Long machin truc je ne sais quoi hoo le bigleux ça suffit, allez vas y ça va erroné t'en auras marre.</etat>\n" +
                "\t</adresse>\n" +
                "</magasin>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/1")
                .then()
                .statusCode(500)
                .log().all();
    }
}
