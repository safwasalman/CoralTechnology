package API;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserAPIs {


        String ID, firstName;


        @Test
        public void createNewUser() {

        //a.Send Post request add new user

            JSONObject request = new JSONObject();
            JSONObject profile = new JSONObject();
            request.put("name", "");
            profile.put("firstname", "");
            profile.put("lastname", "");
            request.put("profile", profile);
            request.put("orders", "[]");
            System.out.println("Post request body:"+request);


            String response = given()
                    .header("content-Type", "application/json")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(request.toJSONString()).
            when()
                    .post("https://620e3da1585fbc3359db4edf.mockapi.io/api/v1/users")

            //b.Assert on status code = 201
            .then()

                    .assertThat().statusCode(201).extract().asString();

            System.out.println("Post response"+response);

            //c.Print out the id
            JsonPath res = new JsonPath(response);
            ID=res.get("id").toString();
            System.out.println("ID="+ID);
            firstName = res.get("profile.firstName").toString();

        }

        @Test
        public void getCreatedUser() {

            //d.Send Get request to get the created user by id

            String actualFirstName= given()
                    .param("id", ID).
            when()
                    .get("https://620e3da1585fbc3359db4edf.mockapi.io/api/v1/users").
            then()
                    .assertThat().statusCode(200).extract().asString();


            //e.Assert on First Name the same as sent in the post request

            JsonPath res = new JsonPath(actualFirstName);
            //System.out.println(res.get("profile.firstName").toString().replaceAll("^\\[|\\]$", ""));
            System.out.println(firstName.replaceAll("^\\[|\\]$", ""));

            Assert.assertEquals(firstName.replaceAll("^\\[|\\]$", ""),firstName);


        }








}
