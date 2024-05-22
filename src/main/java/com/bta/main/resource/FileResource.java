package com.bta.main.resource;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

import com.bta.main.database.FileDao;
import com.bta.main.model.DailyStats;
import com.bta.main.model.File;
import com.bta.main.model.Transaction;

/*
 * Servlet for managing single files (data retrieval and filtering)
 */
@Path("/data/{filename}")
public class FileResource {

    @Context
    UriInfo uriInfo;
    @Context
    HttpServletRequest request;
    @Context
    ServletContext servletContext;

    // Constructor
    public FileResource() {
        super();
    }

    /*
     * Responds to a GET HTML with the data page if the file given as argument exists in the database.
     * url: /BTA/app/data/{filename}
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable dataPage(@PathParam("filename") String file_name) {
        List<Transaction> list = FileDao.instance.getTransactions(file_name);
        if (list.isEmpty()) {
            throw new NotFoundException();
        }
        return new Viewable("/data");
    }

    /*
     * Responds to a GET JSON with a list of transactions in the stored MT940 file with the given filename.
     * url: /BTA/app/data/{filename}
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getTransactions(@PathParam("filename") String file_name) {
        List<Transaction> list = FileDao.instance.getTransactions(file_name);
        if (list == null || list.isEmpty()) {
            throw new NotFoundException();
        }
        return list;
    }

    /*
     * Responds to a GET JSON with the general data about the stored MT940 file with the given filename.
     * url: /BTA/app/data/{filename}/file
     */
    @GET
    @Path("file")
    @Produces(MediaType.APPLICATION_JSON)
    public File getFile(@PathParam("filename") String file_name) {
        File file = FileDao.instance.getFile(file_name);
        if (file == null) {
            throw new NotFoundException();
        }
        return file;
    }

    /*
     * Deletes a specific file with a given filename.
     * url: /BTA/app/data/{filename}
     */
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteFile(@PathParam("filename") String file_name) {
        Response res;
        if (FileDao.instance.delete(file_name)) {
            res = Response.ok().build();
        } else {
            res = Response.status(404)
                    .entity(null)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return res;
    }

    /*
     * Responds to a GET HTML with the statistics page for a stored mt940 file with the given filename.
     * url: /BTA/app/data/{filename}/stats
     */
    @GET
    @Path("stats")
    @Produces(MediaType.TEXT_HTML)
    public Viewable statsPage() {
        return new Viewable("/stats");
    }

    /*
     * Responds to a GET JSON with the daily statistics for a stored mt940 file with the given filename.
     * url: /BTA/app/data/{filename}/stats
     */
    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DailyStats> getStats(@PathParam("filename") String file_name) {
        List<DailyStats> list = FileDao.instance.getDailyStats(file_name);
        if (list == null || list.isEmpty()) {
            throw new NotFoundException();
        }
        return list;
    }


}
