### jialiao(加里奥)

全都是彩蛋的Excel读写库
```
Jiliao.r(Data.class) //选择友方英雄（选择目标）
      .w() //w吸收伤害并嘲讽（读） / .e() //e击飞（写）
      .q() //持续伤害（返回结果）
```


导出Excel用法
```
git clone https://github.com/liuyuyu/jialiao
然后拷贝jialiao代码到你的工程
```
开始使用
```
        //设置时间格式
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(myDateFormat);
        JiaLiAo.renascence(mapper);

        //准备数据
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
        //导出
        OutputStream os = new FileOutputStream("out/userOrder.xlsx");
        JiaLiAo.r(UserOrder.class, false)
                .e(dataList)
                .q("导出的订单", os);
```

看这战绩就应该知道加里奥有多厉害了（傲娇脸）
![战绩](doc/img/jialiao_zj.png)

### 捐赠

电信一区求带上白银，ID:不如剑舞