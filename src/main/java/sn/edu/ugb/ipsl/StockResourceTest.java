package sn.edu.ugb.ipsl;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class StockResourceTest {

    private static final String BASE_URI = "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/services/stocks";

    @Test
    public void testGetStocksEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetStockByIdEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/1/1") // ID de magasin et produit existants pour tester un stock existant
                .then()
                .statusCode(302)
                .log().all();
    }

    @Test
    public void testAddStockEndpoint() {
        String requestBody = "{\n" +
                "  \"magasin\": 5,\n" +
                "  \"produit\": 25,\n" +
                "  \"quantite\": 10\n" +
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
    public void testUpdateStockEndpoint() {
        String requestBody = "{\n" +
                "  \"quantite\": 20\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/5/25") // ID de magasin et produit existants pour modifier un stock existant
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testRemoveStockEndpoint() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/4/300") // ID de magasin et produit existants pour supprimer un stock existant
                .then()
                .statusCode(200)
                .log().all();
    }


    @Test
    public void testSearchStockFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=476-4321")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testSearchStockNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=(999)")
                .then()
                .statusCode(604)
                .log().all();
    }

    @Test
    public void testProductOrStoreNotFound() {
        String requestBody = "{\n" +
                "  \"magasin\": 15,\n" +
                "  \"produit\": 25,\n" +
                "  \"quantite\": 10\n" +
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
    public void testGetStockNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/999/999") // ID de magasin et produit inexistants pour provoquer une erreur 404
                .then()
                .statusCode(404)
                .log().all();
    }


    @Test
    public void testStockAlreadyExist() {
        String requestBody = "{\n" +
                "  \"magasin\": 1,\n" +
                "  \"produit\": 1,\n" +
                "  \"quantite\": 10\n" +
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
    public void testUpdateStockNotFound() {
        String requestBody = "{\n" +
                "  \"quantite\": 0\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/1/404")
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testRemoveStockNotFound() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/5/404")
                .then()
                .statusCode(404)
                .log().all();
    }


    @Test
    public void testNotAllowedMethodStock() {
        RestAssured.given()
                .when()
                .put(BASE_URI)
                .then()
                .statusCode(505) // Test pour provoquer une méthode inappoprié
                .log().all();
    }

    @Test
    public void testBadRequestStock() {
        String requestBody = "{\n" +
                "  \"magasin\": 15,\n" +
                "  \"produit\": 25,\n" +
                "  \"quantite\": 10\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(400)
                .log().all();
    }

}
