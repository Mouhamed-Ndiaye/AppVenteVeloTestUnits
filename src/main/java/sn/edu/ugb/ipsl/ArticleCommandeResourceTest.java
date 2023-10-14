package sn.edu.ugb.ipsl;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class ArticleCommandeResourceTest {
    private static final String BASE_URI = "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/services/articleCommandes"; // URI de base

    @Test
    public void testGetArticleCommandesEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetArticleCommandeByIdEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/1/1")
                .then()
                .statusCode(302)
                .log().all();
    }

    @Test
    public void testCreateArticleCommandeEndpoint() {
        String requestBody = "{\n" +
                "  \"numeroCommande\": 1,\n" +
                "  \"ligne\": 1,\n" +
                "  \"produit\": 1,\n" +
                "  \"quantite\": 1,\n" +
                "  \"prixDepart\": 379.99,\n" +
                "  \"remise\": 0\n" +
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
    public void testUpdateArticleCommandeEndpoint() {
        String requestBody = "{\n" +
                "  \"produit\": 2,\n" +
                "  \"quantite\": 2,\n" +
                "  \"prixDepart\": 749.99,\n" +
                "  \"remise\": 0.05\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/1/1444")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testDeleteArticleCommandeEndpoint() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/1/1444")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testSearchArticleCommandeNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=852.3")
                .then()
                .statusCode(604)
                .log().all();
    }

    @Test
    public void testArticleCommandeNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/1/1444")
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testArticleCommandeExistant() {
        String requestBody = "{\n" +
                "  \"numeroCommande\": 1,\n" +
                "  \"ligne\": 1,\n" +
                "  \"produit\": 1,\n" +
                "  \"quantite\": 1,\n" +
                "  \"prixDepart\": 379.99,\n" +
                "  \"remise\": 0\n" +
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
    public void testArticleCommandeWithInvalidCommand() {
        String requestBody = "{\n" +
                "  \"numeroCommande\": 196454,\n" +
                "  \"ligne\": 1,\n" +
                "  \"produit\": 1,\n" +
                "  \"quantite\": 1,\n" +
                "  \"prixDepart\": 379.99,\n" +
                "  \"remise\": 0\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testArticleCommandeWithInvalidProduct() {
        String requestBody = "{\n" +
                "  \"numeroCommande\": 1,\n" +
                "  \"ligne\": 1444,\n" +
                "  \"produit\": 500,\n" +
                "  \"quantite\": 1,\n" +
                "  \"prixDepart\": 379.99,\n" +
                "  \"remise\": 0\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testArticleCommandeWithNoProduct() {
        String requestBody = "{\n" +
                "  \"numeroCommande\": 1,\n" +
                "  \"ligne\": 1444,\n" +
                "  \"quantite\": 1,\n" +
                "  \"prixDepart\": 379.99,\n" +
                "  \"remise\": 0\n" +
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
