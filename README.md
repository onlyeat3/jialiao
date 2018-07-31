### jialiao(加里奥)

全都是彩蛋的Excel读写库
```
Jiliao.r(Data.class) //选择友方英雄（选择目标）
      .w() //w吸收伤害并嘲讽（读） / .e() //e击飞（写）
      .q() //持续伤害（返回结果）
```

### 开始使用

pom.xml增加依赖
```
<dependency>
  <groupId>com.github.liuyuyu</groupId>
  <artifactId>jialiao</artifactId>
  <version>1.0.4</version>
</dependency>
```
梨子代码
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
//导入
List<UserOrder> list = JiaLiAo.r(UserOrder.class, Boolean.FALSE)
                .w(Boolean.TRUE, new FileInputStream("out/userOrder.xlsx"))
                .q();
```

#### 改变导出Excel的顺序和名称
```JiaLiAo.ExcelCell```的```order```表示字段的顺序（不是index），数字越小越靠前。```value```是字段对应表头的名称。cellType是导出单元格的数据类型，不能保证一定符合
```
@JiaLiAo.ExcelCell(value = "订单总数",order = 1,cellType = CellType.NUMERIC)
private Long totalOrder;
```

#### 格式转换
为了少写轮子代码，类型转换借用了Jackson。如果需要转换格式，比如日期、金额。可以使用```renascence```方法改变Jackson的```ObjectMapper```的配置
```
//设置时间格式
ObjectMapper mapper = new ObjectMapper();
SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
mapper.setDateFormat(myDateFormat);
JiaLiAo.renascence(mapper);
```

#### 忽略属性
因为依赖Jackson，所有Jackson的注解都可以用
```
/**
 * 订单总金额
 */
@JsonIgnore
private BigDecimal totalMoney;
```

#### 其他
Jackson的其他骚操作也可以用起来了。


### 依赖
- [x] POI
- [x] Jackson
- [x] Java 8

### 有多强？
看这战绩就应该知道加里奥有多厉害了（傲娇脸）

![战绩](doc/img/jialiao_zj.png)

### 捐赠

电信一区求带上白银，ID:不如剑舞
