package sn.edu.ugb.ipsl;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class ProduitResourceTest {

    private static final String BASE_URI = "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/services/produits";

    @Test
    public void testGetProduitEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetProduitByIdEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/1") // ID existant pour tester un produit existant
                .then()
                .statusCode(302)
                .log().all();
    }

    @Test
    public void testCreateProduitEndpoint() {
        String requestBody = "<produit>\n" +
                "\t<id>329</id>\n" +
                "\t<nom>NouveauProduit</nom>\n" +
                "\t<marque>1</marque>\n" +
                "\t<categorie>1</categorie>\n" +
                "\t<annee_model>2023</annee_model>\n" +
                "\t<prix_depart>199.99</prix_depart>\n" +
                "</produit>";

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
    public void testUpdateProduitEndpoint() {
        String requestBody = "<produit>\n" +
                "\t<nom>ModifProduit</nom>\n" +
                "\t<marque>2</marque>\n" +
                "</produit>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/329")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testDeleteProduitEndpoint() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/329") // ID existant pour supprimer un produit existant
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetProduitNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/404") // ID inexistant pour provoquer une erreur 404
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testSearchProduitNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=nonexistent") // Nom inexistant pour provoquer une erreur 604
                .then()
                .statusCode(604)
                .log().all();
    }

    @Test
    public void testNotAllowedMethodProduit() {
        RestAssured.given()
                .when()
                .put(BASE_URI)
                .then()
                .statusCode(505)
                .log().all();
    }

    @Test
    public void testNoDataInRequiredField() {
        String requestBody = "<produit>\n" +
                "\t<id>329</id>\n" +
                "\t<marque>0</marque>\n" +
                "\t<annee_model>0</annee_model>\n" +
                "\t<prix_depart>0</prix_depart>\n" +
                "</produit>";

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
    public void testAlreadyExistProduct() {
        String requestBody = "<produit>\n" +
                "\t<id>1</id>\n" +
                "\t<marque>0</marque>\n" +
                "\t<annee_model>0</annee_model>\n" +
                "\t<prix_depart>0</prix_depart>\n" +
                "</produit>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(509)
                .log().all();
    }

    @Test
    public void testInexistantMarkOrCategory() {
        String requestBody = "<produit>\n" +
                "\t<marque>58</marque>\n" +
                "</produit>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/2")
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testNoDataInCategoryOrMark() {
        String requestBody = "<produit>\n" +
                "\t<id>329</id>\n" +
                "\t<nom>NouveauProduit</nom>\n" +
                "\t<marque>1</marque>\n" +
                "\t<annee_model>2023</annee_model>\n" +
                "\t<prix_depart>199.99</prix_depart>\n" +
                "</produit>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(508)
                .log().all();
    }



    @Test
    public void testDeserializingErrorException() {
        String requestBody = "<produit>\n" +
                "\t<id>329</id>\n" +
                "\t<nom>string</nom>\n" +
                "\t<marque>0</marque>\n" +
                "\t<annee_model>0</annee_model>\n" +
                "\t<prix_depart>0</prix_depart>\n" +
                "</produit>";

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(500)
                .log().all();
    }

    @Test
    public void testInexistantProduct() {
        RestAssured.given()
                .contentType("application/xml")
                .when()
                .delete(BASE_URI + "/330")
                .then()
                .statusCode(404)
                .log().all();
    }

}
