package com.cepheid.cloud.skel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NoContentException;

import com.cepheid.cloud.skel.exception.ResourceNotFoundException;
import com.cepheid.cloud.skel.service.ItemService;
import com.cepheid.cloud.skel.util.ItemPage;
import com.cepheid.cloud.skel.util.ItemSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.cepheid.cloud.skel.model.Item;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;


// curl http:/localhost:9443/app/api/1.0/items

@Component
@Path("/api/1.0/items")
@Api()
public class ItemController {

    private ItemService itemService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getItems(@RequestBody ItemSearchCriteria itemSearchCriteria,
                                      @QueryParam("page") @DefaultValue("0") Integer page,
                                      @QueryParam("size") @DefaultValue("10") Integer size,
                                      @QueryParam("sortBy") @DefaultValue("id") String sortBy,
                                      @QueryParam("direction") @DefaultValue("asc") String direction) {
        try {
            ItemPage itemPage = new ItemPage();
            itemPage.setPage(page);
            itemPage.setSize(size);
            itemPage.setSortBy(sortBy);
            itemPage.setDirection(Sort.Direction.fromString(direction.toUpperCase()));
            return new ResponseEntity<Page<Item>>(itemService.getItems(itemSearchCriteria, itemPage), HttpStatus.OK);
        } catch (NoContentException ex) {
            LOGGER.error("No Content: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NO_CONTENT);
        } catch (ConstraintViolationException ex) {
            LOGGER.error("Constraint Violation: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (BadRequestException ex) {
            LOGGER.error("Invalid Input(s): {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.error("Unexpected: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getItem(@PathParam("id") Long id) {
        try {
            return new ResponseEntity<Item>(itemService.getItem(id), HttpStatus.OK);

        } catch (ResourceNotFoundException ex) {
            LOGGER.error("Resource Unavailable: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (BadRequestException ex) {
            LOGGER.error("Invalid Input(s): {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.error("Unexpected: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> createItem(@Valid @RequestBody Item item) {
        try {
            return new ResponseEntity<Item>(itemService.saveItem(item), HttpStatus.CREATED);
        } catch (ConstraintViolationException ex) {
            LOGGER.error("Constraint Violation: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (BadRequestException ex) {
            LOGGER.error("Invalid Input(s): {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.error("Unexpected: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> updateItem(@Valid @RequestBody Item item) {
        try {
            return new ResponseEntity<Item>(itemService.updateItem(item), HttpStatus.OK);
        } catch (ConstraintViolationException ex) {
            LOGGER.error("Constraint Violation: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (BadRequestException ex) {
            LOGGER.error("Invalid Input(s): {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.error("Unexpected: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Path(("/{id}"))
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<String> deleteItem(@PathParam("id") Long id) {
        try {
            itemService.deleteItem(id);
        } catch (ResourceNotFoundException ex) {
            LOGGER.error("Resource Unavailable: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (BadRequestException ex) {
            LOGGER.error("Invalid Input(s): {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.error("Unexpected: {}", ex);
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Resource Deleted.", HttpStatus.OK);
    }


}
