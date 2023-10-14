package sn.edu.ugb.ipsl;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

public class CommandeResourceTest {

    private static final String BASE_URI = "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/services/commandes";

    @Test
    public void testGetCommandesEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI)
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testGetCommandeByIdEndpoint() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/1")
                .then()
                .statusCode(302)
                .log().all();
    }

    @Test
    public void testCreateCommandeEndpoint() {
         String requestBody = "<commande>\n" +
                 "\t<client>1451</client>\n" +
                 "\t<statut>4</statut>\n" +
                 "\t<dateCommande>2023-10-08</dateCommande>\n" +
                 "\t<dateLivraisonVoulue>2023-10-08</dateLivraisonVoulue>\n" +
                 "\t<dateLivraison>2023-10-08</dateLivraison>\n" +
                 "\t<magasin>4</magasin>\n" +
                 "\t<vendeur>5</vendeur>\n" +
                 "</commande>";

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
    public void testUpdateCommandeEndpoint() {
        String requestBody = "<commande>\n" +
                "\t<magasin>3</magasin>\n" +
                "\t<vendeur>5</vendeur>\n" +
                "</commande>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .patch(BASE_URI + "/1618")
                .then()
                .statusCode(200)
                .log().all();
    }


    @Test
    public void testDeleteCommandeEndpoint() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/1617")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testSearchCommandeNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=2023/10/08")
                .then()
                .statusCode(604)
                .log().all();
    }

    @Test
    public void testSearchCommandeFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=2018/08/23")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testSearchCommandWithInvalidDateFormat() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "?search=2018-08-23")
                .then()
                .statusCode(618)
                .log().all();
    }

    @Test
    public void testGetCommandeByIdNotFound() {
        RestAssured.given()
                .when()
                .get(BASE_URI + "/8520")
                .then()
                .statusCode(404)
                .log().all();
    }




    @Test
    public void testInvalidDateFormatInsertion() {
        String requestBody = "<commande>\n" +
                "\t<client>1451</client>\n" +
                "\t<statut>4</statut>\n" +
                "\t<dateCommande>2023-10-08</dateCommande>\n" +
                "\t<dateLivraisonVoulue>2023/10-08</dateLivraisonVoulue>\n" +//
                "\t<dateLivraison>2023-10-08</dateLivraison>\n" +
                "\t<magasin>4</magasin>\n" +
                "\t<vendeur>5</vendeur>\n" +
                "</commande>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(618)
                .log().all();
    }

    @Test
    public void testNoForeignKeyInsertion() {
        String requestBody = "<commande>\n" +
                "\t<client>1451</client>\n" +
                "\t<statut>4</statut>\n" +
                "\t<dateCommande>2023-10-08</dateCommande>\n" +
                "\t<dateLivraisonVoulue>2023-10-08</dateLivraisonVoulue>\n" +//
                "\t<dateLivraison>2023-10-08</dateLivraison>\n" +
                "\t<vendeur>5</vendeur>\n" +
                "</commande>";

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
    public void testNotFoundForeignKey() {
        String requestBody = "<commande>\n" +
                "\t<client>1451</client>\n" +
                "\t<statut>4</statut>\n" +
                "\t<dateCommande>2023-10-08</dateCommande>\n" +
                "\t<dateLivraisonVoulue>2023-10-08</dateLivraisonVoulue>\n" +//
                "\t<dateLivraison>2023-10-08</dateLivraison>\n" +
                "\t<magasin>4</magasin>\n" +
                "\t<vendeur>85</vendeur>\n" +
                "</commande>";

        RestAssured.given()
                .contentType("application/xml")
                .body(requestBody)
                .when()
                .post(BASE_URI)
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    public void testDeleteCommandeNotFound() {
        RestAssured.given()
                .when()
                .delete(BASE_URI + "/2000")
                .then()
                .statusCode(404)
                .log().all();
    }

}
