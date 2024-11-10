package com.files.buyerservice.DTOs;

import java.util.List;

public class ReviewListdto {
    List<ReviewDto> Review;

    public List<ReviewDto> getReview() {
        return Review;
    }

    public int getMaxpages() {
        return Maxpages;
    }

    public void setMaxpages(int maxpages) {
        Maxpages = maxpages;
    }

    public void setReview(List<ReviewDto> review) {
        Review = review;
    }

    int Maxpages;
}
