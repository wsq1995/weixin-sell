package com.wsq.dataObject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wsq
 * @date 2019/6/30 22:45
 */
@Data
@Entity
@Table(name = "seller_info")
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    public SellerInfo() {
    }

    public SellerInfo(String sellerId, String username, String password) {
        this.sellerId = sellerId;
        this.username = username;
        this.password = password;
    }
}
