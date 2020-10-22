package com.guuidea.inreading.model.bean.packages;

import com.guuidea.inreading.model.bean.BaseBean;
import com.guuidea.inreading.model.bean.BookReviewBean;

import java.util.List;

/**
 * Created by guuidea on 17-4-21.
 */

public class BookReviewPackage extends BaseBean {

    private List<BookReviewBean> reviews;

    public List<BookReviewBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReviewBean> reviews) {
        this.reviews = reviews;
    }
}
