package rest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


public class RestAssuredDemoTest {
    @BeforeAll
    public static void setUpClass() {
       
        RestAssured.defaultParser = Parser.JSON;
    }

    public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
    }

    //This test is disabeld because it breaks if i run all the test with [ALT] + [F6]
    //But it dosn't break if i just run this class.
    @Disabled
    @Test
    public void testExternal() {
        given().
                log().all().get("http://api.icndb.com/jokes/random").then().log().body();

    }
}
