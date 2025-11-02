package com.bautista.util;

import com.bautista.entity.ReviewData;
import com.bautista.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewTransform extends Transform<ReviewData, Review> {
    public ReviewTransform() {
        super(Review.class);
    }
}
