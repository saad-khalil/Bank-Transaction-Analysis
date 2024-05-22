package com.bta.main.resource;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Context
    private HttpHeaders headers;

    public Response toResponse(NotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private String getAcceptType() {
        List<MediaType> accepts = headers.getAcceptableMediaTypes();
        if (accepts != null && accepts.size() > 0) {
            accepts.get(0);
        } else {
            //return a default one like Application/json
            return MediaType.APPLICATION_JSON;
        }
        return null;
    }
}