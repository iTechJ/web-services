package com.itechart.javalab.webservice.rest.util;

import com.google.common.base.Strings;
import com.itechart.javalab.webservice.rest.model.Product;
import com.itechart.javalab.webservice.rest.model.Review;

public class ProductValidator {

    private static final int LONG_TEXT_FIELD_LENGTH = 255;
    private static final int DEFAULT_TEXT_FIELD_LENGTH = 50;

    public boolean validateProduct(Product product) {
        return !(product == null ||
                Strings.isNullOrEmpty(product.getTitle()) || product.getTitle().length() > DEFAULT_TEXT_FIELD_LENGTH ||
                (!Strings.isNullOrEmpty(product.getDescription()) && product.getDescription().length() > LONG_TEXT_FIELD_LENGTH) ||
                (!Strings.isNullOrEmpty(product.getCategory()) && product.getCategory().length() > DEFAULT_TEXT_FIELD_LENGTH)
        );
    }

    public boolean validateReview(Review comment) {
        return !(comment == null ||
            Strings.isNullOrEmpty(comment.getText()) ||
                comment.getText().length() > LONG_TEXT_FIELD_LENGTH ||
            Strings.isNullOrEmpty(comment.getUsername()) ||
                comment.getUsername().length() > DEFAULT_TEXT_FIELD_LENGTH);
    }
}
