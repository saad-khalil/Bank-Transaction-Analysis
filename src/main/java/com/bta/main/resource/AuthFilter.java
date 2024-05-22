package com.bta.main.resource;

import com.bta.web.JWTManager;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


/**
 * Servlet Filter implementation class ValidationFilter
 */
@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {


    /**
     * A filter method that runs BEFORE the request  reaches the requested resource.
     * If the JWT is deemed invalid the server responds with 401 unauthorized and the request is rejected.
     *
     * @param containerRequestContext
     * @throws IOException if it fails to get the path string of the request.
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        if (!(JWTManager.checkTokenCookie(containerRequestContext)) && !containerRequestContext.getUriInfo().getPath().contains("login")) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            System.out.println("Filter is running!");
            return;
        }


    }
}
