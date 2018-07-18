package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import test.model.PhoneTask;
import test.model.UserOrder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author liuyuyu
 */
public class JiaLiAoTest {
    @BeforeClass
    public static void setUp(){
        ObjectMapper mapper = new ObjectMapper();
        //设置时间格式
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(myDateFormat);
        JiaLiAo.renascence(mapper);
    }

    @Test
    public void testWrite() throws IOException {
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

    @Test
    public void testRead() throws IOException {
        List<UserOrder> list = JiaLiAo.r(UserOrder.class, Boolean.FALSE)
                .w(Boolean.TRUE, new FileInputStream("out/userOrder.xlsx"))
                .q();
        for (UserOrder o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void testExport() throws IOException, InterruptedException {
        List<PhoneTask> phoneTaskList = IntStream.range(0, 100_000)
                .boxed()
                .map(i -> {
                    PhoneTask phoneTask = new PhoneTask();
                    phoneTask.setMobile(i + "");
                    phoneTask.setName("name" + i);
                    phoneTask.setOrder(i + "");
                    phoneTask.setRemark("remark" + i);
                    return phoneTask;
                })
                .collect(Collectors.toList());
        TimeUnit.SECONDS.sleep(5);
        JiaLiAo.r(PhoneTask.class,Boolean.FALSE)
                .e(phoneTaskList)
                .q("电话",new FileOutputStream("out/phone_task.xlsx"));
        System.out.println("end.");
        TimeUnit.SECONDS.sleep(5);
    }

}