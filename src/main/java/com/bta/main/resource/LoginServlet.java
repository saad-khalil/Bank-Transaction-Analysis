package com.bta.main.resource;


import com.bta.main.database.DatabaseConn;
import com.bta.web.JWTManager;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Login servlet
 */
@Path("/login")
public class LoginServlet {

    @Context
    UriInfo uriInfo;
    @Context
    HttpServletRequest request;
    @Context
    ServletContext servletContext;


    /**
     * A method that handles the login request.
     * @param json data username and password.
     * @return A response with a status message and with a cookie containing a JWT token if the credentials are correct.
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handleRequest(JSONObject json) {
        String user_name = json.getString("username");
        String passwordToHash = json.getString("password");
        String generatedPassword = null;
        List<String> userData = new ArrayList<>();
        JSONObject jsonData = new JSONObject();

        try {
            DatabaseConn c = new DatabaseConn();
            userData.addAll(c.getUserData(user_name));
            userData.get(1);
            c.closeConnection();
        } catch (SQLException | IndexOutOfBoundsException e) {
            jsonData.put("message", "No such username exists in the Database!");
            NewCookie mycookie = new NewCookie("token", null, "/", null, null, 0, false, true);
            return Response.ok("\"Invalid Username/Password combination, try again!\"").cookie(mycookie).build();
        }
        try {
            //Add the salt from the database
            passwordToHash += userData.get(0);
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();

            //Compare with database
            if (generatedPassword.matches(userData.get(1))) {
                int tokenAge = 1200000;
                String jwt = JWTManager.createJWT("id", userData.get(0), "login", tokenAge);
                jsonData.put("message", "Welcome " + user_name + "!");
                jsonData.put("jwt", jwt);
                System.out.println("JSON data: " + jsonData.toString());
//				System.out.println("JSON data: "+jsonData.toString());
//				String response = "{ \"message\" : \"Welcome " + user_name + "! \", \"jwt\" : \""+ jwt +  "\"}";
//				System.out.println("----------------------" + response);
                NewCookie cookie = new NewCookie("token", jwt);
                return Response.ok("Welcome " + user_name + "!").cookie(cookie).build();
            }

        } catch (NoSuchAlgorithmException e) {
            jsonData.put("message", "Internal Server Error: NoSuchAlgorithmException! Contact the administrators of this domain and show them this error.");
            NewCookie mycookie = new NewCookie("token", null, "/", "null", null, 0, false, true);
            return Response.ok("Internal Server Error: NoSuchAlgorithmException! Contact the administrators of this domain and show them this error.").cookie(mycookie).build();
        }
        System.out.println("Hashed password: " + generatedPassword);
        System.out.println(user_name);
        jsonData.put("message", "Invalid Username/Password combination, try again!");
        NewCookie mycookie = new NewCookie("token", null, "/", null, null, 0, false, true);
        return Response.ok("\"Invalid Username/Password combination, try again!\"").cookie(mycookie).build();

    }


    /**
     * A method for logging out and destroying the jwt token.
     * @return a response with a status message.
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteToken() {
        Response res;

        NewCookie mycookie = new NewCookie("token", null, "/BTA/app", "", NewCookie.DEFAULT_VERSION, null, 0, new Date(), false, false);
        res = Response.ok().cookie(mycookie).build();

        return res;

    }

}