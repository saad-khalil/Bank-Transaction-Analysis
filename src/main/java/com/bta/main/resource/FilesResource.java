package com.bta.main.resource;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.bta.main.database.FileDao;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.field.Field65;
import com.prowidesoftware.swift.model.field.Field86;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.glassfish.jersey.server.mvc.Viewable;

/*
 * Servlet for uploading files (the global files handler)
 */
@Path("/upload")
public class FilesResource {

    @Context
    UriInfo uriInfo;
    @Context
    HttpServletRequest request;
    @Context
    ServletContext servletContext;


    //Constructor
    public FilesResource() {
        super();
    }

    /*
     * Responds to a GET HTML with the upload page.
     * url: /BTA/app/upload
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable dataPage() {
        return new Viewable("/desktop-7");
    }

    /*
     * Returns the navbar HTML used on several pages
     * url: /BTA/app/upload/navbar
     */
    @GET
    @Path("navbar")
    @Produces(MediaType.TEXT_HTML)
    public Viewable navitem() {
        return new Viewable("/navbar");
    }


    /*
     * Responds to a GET JSON with a list of filenames that are stored in the database.
     * url: /BTA/app/upload
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getRecents() {
        return FileDao.instance.getFileNames();
    }


    /*
     * Stores an uploaded MT940 file in the database.
     * url: /BTA/app/upload
     */
    @POST
    public Response uploadFile() {
        System.out.println("upload ...");
        ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> files = null;
        String[] array;

        try {
            files = sf.parseRequest((HttpServletRequest) request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        Response res = null;
        if (files == null) {
            res = Response.status(415).build();
            System.err.println("No file uploaded");
        } else {
            StringBuilder out = new StringBuilder();
            Reader in = null;
            int c;
            try {
                in = new InputStreamReader(files.get(0).getInputStream(), StandardCharsets.UTF_8);
                while ((c = in.read()) != -1) {
                    out.append((char) c);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            array = out.toString().split("(?<=[-][}])");

            if (array.length < 3) {
                MT940 mt940 = null;
                String filename = "";
                try {
                    mt940 = new MT940(files.get(0).getInputStream());
                    filename = files.get(0).getName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (FileDao.instance.save(mt940, filename)) {
                    res = Response.ok().build();
                    System.out.println("Upload Successful");
                } else {
                    res = Response.status(404)
                            .entity(null)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                }
            } else {
                MT940 mt940 = new MT940();
                for (int i = 0; i < array.length - 1; i++) {
                    MT940 tempMT940 = new MT940(array[i]);

                    if (i == 0) {
                        List<Field> fieldsList = tempMT940.getFields()
                                .stream()
                                .filter(this::filterTags)
                                .collect(Collectors.toList());
                        for (Field field : fieldsList) {
                            mt940.append(field);
                        }
                    } else if (i < array.length - 2) {
                        mergeTransactionFields(mt940, tempMT940);
                    } else {
                        mergeTransactionFields(mt940, tempMT940);
                        if (tempMT940.getField62F() != null) {
                            mt940.append(tempMT940.getField62F());
                        } else {
                            mt940.append(tempMT940.getField62M());
                        }
                        mt940.append(tempMT940.getField64());
                        List<Field65> field65 = tempMT940.getField65();
                        for (Field65 value : field65) {
                            mt940.append(value);
                        }
                    }
                }
                String filename = files.get(0).getName();
                if (FileDao.instance.save(mt940, filename)) {
                    res = Response.ok().build();
                    System.out.println("Upload Successful");
                } else {
                    res = Response.status(404)
                            .entity(null)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                }
            }
        }
        return res;
    }

    /*
     * Delete all files stored in the database.
     * url: /BTA/app/upload
     */
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAllFile() {
        Response res;
        if (FileDao.instance.deleteAll()) {
            res = Response.ok().build();
        } else {
            res = Response.status(404)
                    .entity(null)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return res;

    }


    public boolean filterTags(Field field) {
        String[] arr = new String[]{"62F", "62M", "64", "65"};
        boolean condition = true;
        for (String tag : arr) {
            if (tag.equals(field.asTag().getName())) {
                condition = false;
            }
        }
        return condition;
    }

    private void mergeTransactionFields(MT940 mt940, MT940 tempMT940) {
        List<Field61> field61 = tempMT940.getField61();
        List<Field86> field86 = tempMT940.getField86();
        for (int j = 0; j < field61.size(); j++) {
            mt940.append(field61.get(j));
            mt940.append(field86.get(j));
        }
    }
}