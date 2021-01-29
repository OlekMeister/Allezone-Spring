package pl.edu.pjwstk.average;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.IntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringRunner.class)
@IntegrationTest
public class AverageControllerIT {
    @Test
    public void should_calculate_simple_average_1() {
        var response = given()
                .param("numbers",4,2,1,2,1)
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals to: 2"));
    }
    @Test
    public void should_calculate_simple_average_2() {
        var response = given()
                .param("numbers",2,1)
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals to: 1.5"));
    }
    @Test
    public void should_calculate_simple_average_3() {
        var response = given()
                .param("numbers",120,60,20,25)
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals to: 56.25"));
    }
    @Test
    public void should_calculate_simple_average_4() {
        var response = given()
                .param("numbers",1,5,4)
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals to: 3.33"));
    }
    @Test
    public void test_without_parameters() {
        var response = given()
                .param("numbers")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Put the parameters"));
    }

}