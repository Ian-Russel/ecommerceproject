package com.bautista.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import com.bautista.entity.ReviewData;
import com.bautista.model.Review;
import org.springframework.stereotype.Component;

public class Transform< V, K>{

    private Class<K> clazz;

    public Transform(Class<K> clazz){
        this.clazz = clazz;
    }

    public K transform (V v){
        Field[] fields;
        Map<String, Object> methodMap = new HashMap<>();
        Method[] methods;
        Object[] getArgs = {};
        Object[] setArgs = {1};
        try {
            K k = clazz.getDeclaredConstructor().newInstance();
            methods =  v.getClass().getMethods();

            for(Method method: methods) {
                String methodName = method.getName();
                if(methodName.startsWith("get")) {
                    Object object = method.invoke(v, getArgs);
                    methodMap.put(methodName, object);
                }
            }

            methods = clazz.getMethods();
            for(Method method: methods) {
                String methodName = method.getName();
                if(methodName.startsWith("set")){
                    methodName = methodName.replaceFirst("s", "g");
                    setArgs[0] =  methodMap.get(methodName);
                    method.invoke(k, setArgs);
                }
            }
            return k;
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException nex){
            return null;
        }
    }
    public Review toReview(ReviewData reviewData) {
        if (reviewData == null) {
            return null;
        }

        Review review = new Review();
        review.setId(reviewData.getId());
        review.setProductId(reviewData.getProductId());
        review.setUserId(reviewData.getUserId());
        review.setOrderId(reviewData.getOrderId());
        review.setRating(reviewData.getRating());
        review.setTitle(reviewData.getTitle());
        review.setComment(reviewData.getComment());
        review.setVerifiedPurchase(reviewData.getVerifiedPurchase());
        review.setIsApproved(reviewData.getIsApproved());
        review.setCustomerName(reviewData.getCustomerName());
        review.setCustomerEmail(reviewData.getCustomerEmail());
        review.setHelpfulCount(reviewData.getHelpfulCount());
        review.setNotHelpfulCount(reviewData.getNotHelpfulCount());
        review.setCreatedAt(reviewData.getCreatedAt());
        review.setUpdatedAt(reviewData.getUpdatedAt());

        return review;
    }
}
