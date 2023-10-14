package sn.edu.ugb.ipsl;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;


public class CategorieResourceTest {

    private static final String BASE_URI = "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/services/categories";

    @Test
    public void testGetCategorieEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(200)  // On vérifie que le statut de la réponse est 200 (Renvoyé avec succés.)
                .log().all();
    }

    @Test
    public void testGetCategorieByIdEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/1")
                .then()
                .statusCode(302)
                .log().all(); // On vérifie que le statut de la réponse est 302 (Trouvé)
    }

    @Test
    public void testCreateCategorieEndpoint() {
        // Simulation de données pour créer une nouvelle catégorie
        String requestBody = "{\n" +
                "  \"id\": 86,\n" +
                "  \"nom\": \"Electric Bikes\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(201)
                .log().all(); // Vérifiez que le statut de la réponse est 201 (Créée avec succés)
    }

    @Test
    public void testUpdateCategorieEndpoint() {
        // Simulation de données pour mettre à jour une catégorie existante
        String requestBody = "{\n" +
                "  \"nom\": \"Children Bicycles\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/87")
                .then()
                .statusCode(200)
                .log().all(); // Vérifiez que le statut de la réponse est 200 (Modifiée avec succés)
    }

    @Test
    public void testDeleteCategorieEndpoint() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/21")
                .then()
                .statusCode(200)
                .log().all(); // Vérifiez que le statut de la réponse est 200 (Supprimée avec succés)
    }

    @Test
    public void testGetCategorieNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/999") // ID inexistant pour provoquer une erreur 404
                .then()
                .statusCode(404)
                .log().all(); // Vérifiez que le statut de la réponse est 404 (Not Found)
    }

    @Test
    public void testSearchCategorieNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=7777") // ID inexistant pour provoquer une erreur 404
                .then()
                .statusCode(604)
                .log().all();// Vérifiez que le statut de la réponse est 404 (Not Found)
    }

    @Test
    public void testNotAllowedMethodCategorie() {
        RestAssured.given()
                .when()
                .delete(BASE_URI)
                .then()
                .statusCode(505)
                .log().all();
    }

    @Test
    public void testNotAppropriateMedia() {
        String requestBody = "{\n" +
                "  \"nom\": \"Children Bicycles\"\n" +
                "}";
        RestAssured.given()
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(515)
                .log().all();// Vérifiez que le statut de la réponse est 404 (Not Found)
    }

    @Test
    public void testCategorieAlreadyExist() {
        // Simulation de données pour créer une nouvelle catégorie
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"nom\": \"Electric Bikes\"\n" +
                "}";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(509)
                .log().all(); // Vérifiez que le statut de la réponse est 201 (Créée avec succés)
    }

    @Test
    public void testCategorieNotFound(){
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/485")
                .then()
                .statusCode(404)
                .log().all();
    }
}

