package com.evilcorp.computervisionweb.controller;

import com.evilcorp.computervision.api.ImageAPI;
import com.evilcorp.computervision.model.FilterType;
import com.evilcorp.computervision.model.MorphologyType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;
import org.opencv.core.Scalar;

@Path("/")
public class ImageController {
    
    private ImageAPI api = new ImageAPI();
    private Logger log = Logger.getLogger(ImageController.class);
    private ObjectMapper objectMapper = new ObjectMapper();


    @POST
    @Path("/changeChan")
    @Consumes("application/json")
    @Produces({"image/png", "image/jpg"})
    public Response changeChan(String json){
        try{
            Map map = objectMapper.readValue(json, Map.class);
            String imgName = (String) map.get("imgName");
            Integer numberOfChan = (Integer) map.get("numberOfChan");

            log.info("Zero channel " + numberOfChan);
            Mat imageSrc = api.loadImage(imgName);
            imageSrc = api.changeChan(imageSrc, numberOfChan);
            return Response.status(200).entity(api.getImageByteArray(imageSrc)).build();
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.status(500).build();
        }
    }

    @POST
    @Path("/morphology")
    @Consumes("application/json")
    @Produces({"image/png", "image/jpg"})
    public Response morphology(String json){
        try{
            Map map = objectMapper.readValue(json, Map.class);
            String imgName = (String) map.get("imgName");
            MorphologyType morphType = MorphologyType.valueOf(((String) map.get("morphType")).toUpperCase());
            Integer coreSize = (Integer) map.get("coreSize");

            log.info(morphType + " " + coreSize);
            Mat srcImage = api.loadImage(imgName);
            srcImage = api.morphology(srcImage, coreSize, morphType);
            return Response.status(200).entity(api.getImageByteArray(srcImage)).build();
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.status(500).build();
        }
    }

    @POST
    @Path("/filters")
    @Consumes("application/json")
    @Produces({"image/png", "image/jpg"})
    public Response filters(String json){
        try{
            Map map = objectMapper.readValue(json, Map.class);
            String imgName = (String) map.get("imgName");
            FilterType filtType = FilterType.valueOf(((String) map.get("filterType")).toUpperCase());
            Integer coreSize = (Integer) map.get("coreSize");

            log.info(filtType + " " + coreSize);

            Mat srcImage = api.loadImage(imgName);
            srcImage = api.filters(srcImage, coreSize, filtType);
            return Response.status(200).entity(api.getImageByteArray(srcImage)).build();
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.status(500).build();
        }
    }


    @POST
    @Path("/fillFlood")
    @Consumes("application/json")
    @Produces({"image/png", "image/jpg"})
    public Response fillFlood(String json){
        try{
            Map map = objectMapper.readValue(json, Map.class);
            String imgName = (String) map.get("imgName");
            Integer seedPointX = (Integer) map.get("seedPointX");
            Integer seedPointY = (Integer) map.get("seedPointY");
            Integer intensVal1 = (Integer) map.get("intensVal1");
            Integer intensVal2 = (Integer) map.get("intensVal2");
            Scalar fillRGBcol = (Scalar) map.get("fillRGBcol");
            
            log.info("Fill flood");
            Mat imageSrc = api.loadImage(imgName);
            imageSrc = api.fillFlood(imageSrc, seedPointX, seedPointY, intensVal1, intensVal2, fillRGBcol);
            return Response.status(200).entity(api.getImageByteArray(imageSrc)).build();
        } catch (Exception e){
            log.error(e.getMessage());
            return Response.status(500).build();
        }
    }
}