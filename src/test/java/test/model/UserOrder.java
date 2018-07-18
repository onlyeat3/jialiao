package test.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import test.JiaLiAo;
import org.apache.poi.ss.usermodel.CellType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuyuyu
 */
public class UserOrder {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户名
     */
    @JiaLiAo.ExcelCell(value = "用户名",order = 2)
    private String userName;
    /**
     * 订单总数
     */
    @JiaLiAo.ExcelCell(value = "订单总数",order = 1,cellType = CellType.NUMERIC)
    private Long totalOrder;
    /**
     * 订单总金额
     */
    @JsonIgnore
    private BigDecimal totalMoney;
    /**
     * 创建时间
     */
    private Date createdTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Long totalOrder) {
        this.totalOrder = totalOrder;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
