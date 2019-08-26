package com.wsq.repository;

import com.wsq.dataObject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wsq
 *         Created by Tp on 2019/5/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("skdfjjsk");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(65456);
        productInfo.setProductDec("安监局卡卡电脑卡了");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(1);
        ProductInfo info = repository.save(productInfo);
        Assert.assertNotNull(info);
    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> status = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,status.size());
    }


}