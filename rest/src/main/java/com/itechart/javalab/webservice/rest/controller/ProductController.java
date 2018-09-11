package com.itechart.javalab.webservice.rest.controller;

import com.itechart.javalab.webservice.rest.dao.ProductsStorage;
import com.itechart.javalab.webservice.rest.dto.ProductWrapper;
import com.itechart.javalab.webservice.rest.model.Product;
import com.itechart.javalab.webservice.rest.model.Review;
import com.itechart.javalab.webservice.rest.util.ProductValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Path("/product")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProductController {

    private static final int UNPROCESSABLE_ENTITY_STATUS = 422;

    private ProductValidator validator = new ProductValidator();

    @GET
    public Response searchProducts(@QueryParam("searchText") String searchText,
                                @QueryParam("maxPrice") BigDecimal maxPrice) {
        Set<Product> products = ProductsStorage.search(searchText, maxPrice);
        ProductWrapper responseWrapper = new ProductWrapper(products);
        return Response.ok(responseWrapper)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getProductDetails(@PathParam("id") Integer productId) {
        Product product = ProductsStorage.findById(productId);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(product)
                .build();
    }

    @GET
    @Path("/report")
    @Produces("text/csv")
    public Response buildReport(@QueryParam("searchText") String searchText,
                                @QueryParam("maxPrice") BigDecimal maxPrice) {
        Set<Product> searchResults = ProductsStorage.search(searchText, maxPrice);
        final String reportSeparator = ",";
        String result = searchResults.stream()
                .map(product -> product.toDelimitedString(reportSeparator))
                .reduce(Product.getReportHeader(reportSeparator), (s1, s2) -> s1 += System.getProperty("line.separator") + s2);
        return Response.ok(result)
                .header("Content-Disposition", String.format("attachment; filename=report_%s.csv", LocalDate.now().toString()))
                .build();
    }

    @POST
    public Response createProduct(Product product) {
        if (!validator.validateProduct(product)) {
            return Response.status(UNPROCESSABLE_ENTITY_STATUS).build();
        }
        Product newProduct = ProductsStorage.addProduct(product);
        return Response.status(Response.Status.CREATED)
                    .entity(newProduct)
                    .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") Integer productId, Product product) {
        if (!validator.validateProduct(product)) {
            return Response.status(UNPROCESSABLE_ENTITY_STATUS).build();
        }

        Product updated = ProductsStorage.updateProduct(productId, product);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Integer productId) {
        Product deleted = ProductsStorage.deleteProduct(productId);
        if (deleted == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();

    }

    @POST
    @Path("/{id}/review")
    public Response postReview(@PathParam("id") Integer id, Review review) {
        if (!validator.validateReview(review)) {
            return Response.status(UNPROCESSABLE_ENTITY_STATUS).build();
        }
        Product product = ProductsStorage.addReview(id, review);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(product)
                .build();
    }

}
