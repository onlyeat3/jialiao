package io.github.liuyuyu;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.liuyuyu.model.UserOrder;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * @author liuyuyu
 */
public class JiaLiAoTest {

    @Test
    public void testWrite() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //设置时间格式
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(myDateFormat);
        JiaLiAo.renascence(mapper);

        //导出
        List<UserOrder> dataList = IntStream.range(0, 100)
                .boxed()
                .map(i -> {
                    UserOrder userOrder = new UserOrder();
                    userOrder.setMobile("1330000" + i);
                    userOrder.setTotalMoney(BigDecimal.valueOf(i));
                    userOrder.setTotalOrder(Long.valueOf(i));
                    userOrder.setUserName("user-" + i);
                    userOrder.setCreatedTime(new Date());
                    return userOrder;
                })
                .collect(Collectors.toList());
        OutputStream os = new FileOutputStream("out/userOrder.xlsx");
        JiaLiAo.r(UserOrder.class, false)
                .e(dataList)
                .q("导出的订单", os);
    }

}