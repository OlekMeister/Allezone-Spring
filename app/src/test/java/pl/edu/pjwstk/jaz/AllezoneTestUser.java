package pl.edu.pjwstk.jaz;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.IntegrationTest;
import pl.edu.pjwstk.LoginRequest;
import pl.edu.pjwstk.RegisterRequest;
import pl.edu.pjwstk.Request.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@IntegrationTest
public class AllezoneTestUser {

    private static Response adminResponse;
    private static Response userResponse;

    @BeforeClass
    public static void beforeClass() throws Exception {
        // @formatter:off
        given()
                .body(new RegisterRequest("admin","admin123")) //admin
                .contentType(ContentType.JSON)
                .when()
                .post("/api/register")
                .thenReturn();
        given()
                .body(new RegisterRequest("user","user123")) //user
                .contentType(ContentType.JSON)
                .when()
                .post("/api/register")
                .thenReturn();
        adminResponse = given()
                .body(new LoginRequest("admin","admin123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/api/login")
                .thenReturn();
        userResponse = given()
                .body(new LoginRequest("user","user123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/api/login")
                .thenReturn();
        // @formatter:on
    }

    @Test
    public void creatingNewAuctionShouldResponseStatus200(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("UsedFurniture","Home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Minimal usage on couch",
                        "Couch for sale like in the photos",
                        "UsedFurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .then()
                .statusCode(200);

        // @formatter:on

    }
    @Test
    public void creatingNewAuctionWhereTitleIsNullResponse500(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("UsedFurniture","Home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        null,
                        "Couch for sale like in the photos",
                        "UsedFurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .then()
                .statusCode(500);

        // @formatter:on

    }
    @Test
    public void editAuctionTitleShouldResponseStatus200(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("HomeAndGarden"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("category","HomeAndGarden"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Minimal usage on couch",
                        "Couch for sale like in the photos",
                        "category",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Minimal usage on couch.Bought one year ago.",
                        null,
                        null,
                        null,
                        2L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/3")
                .then()
                .statusCode(200);

        // @formatter:on
    }

    @Test
    public void getAuctionShouldResponse200(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("usedfurniture","home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "couch",
                        "Couch for sale like in the photo",
                        "usedfurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .get("/api/auctions/3")
                .then()
                .statusCode(200);

        // @formatter:on

    }
    @Test
    public void getAuctionListShouldResponse200(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("House"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("AlreadyUsedFurniture","House"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1233,
                        "Chair",
                        "Chair for sale",
                        "AlreadyUsedFurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Couch",
                        "Couch for sale like in the photo",
                        "AlreadyUsedFurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .get("/api/auctions")
                .then()
                .statusCode(200);

        // @formatter:on

    }
    @Test
    public void editAuctionByNotCreatorShouldResponseStatus401(){
        List<PhotoRequest> photoRequestList = getPhotoRequestList();

        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        // @formatter:off
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("house"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("GardenFurniture","house"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Minimal usage on couch",
                        "Couch for sale like in the photo",
                        "GardenFurniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Minimal usage on couch.Bought one year ago",
                        null,
                        null,
                        null,
                        2L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/3")
                .then()
                .statusCode(401);

        // @formatter:on
    }
    @Test
    public void checkingVersionShouldResponse400() {
        List<PhotoRequest> photoRequestList = getPhotoRequestList();


        List<ParameterRequest> parameterRequestList = getParameterRequestList();

        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("House"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("furniture","House"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(1843,
                        "Minimal usage on couch",
                        "Couch for sale like in the photo",
                        "furniture",
                        photoRequestList,
                        parameterRequestList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Minimal usage on couch.Bought one year ago",
                        null,
                        null,
                        null,
                        1L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/3")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Minimal usage on couch.Bought one year ago",
                        null,
                        null,
                        null,
                        1L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/3")
                .then()
                .statusCode(400);

    }


    @NotNull
    private List<PhotoRequest> getPhotoRequestList() {
        List<PhotoRequest> photoRequestList = new ArrayList<>();
        photoRequestList.add(new PhotoRequest("link1", 1));
        photoRequestList.add(new PhotoRequest("link2", 2));
        photoRequestList.add(new PhotoRequest("link3", 3));
        return photoRequestList;
    }

    @NotNull
    private List<ParameterRequest> getParameterRequestList() {
        List<ParameterRequest> parameterRequestList = new ArrayList<>();
        parameterRequestList.add(new ParameterRequest("Width", "187cm"));
        parameterRequestList.add(new ParameterRequest("Height", "98cm"));
        parameterRequestList.add(new ParameterRequest("Length", "121cm"));
        return parameterRequestList;
    }

}
