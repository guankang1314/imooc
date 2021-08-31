package com.imooc.pojo;

import java.util.Date;
import javax.persistence.*;

public class Orders {
    @Id
    private String id;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "total_count")
    private Integer totalCount;

    @Column(name = "total_amount")
    private Double totalAmount;

    private Integer state;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return order_time
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return total_count
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return total_amount
     */
    public Double getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount
     */
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}