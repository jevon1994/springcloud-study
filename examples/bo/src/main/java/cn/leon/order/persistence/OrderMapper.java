package cn.leon.order.persistence;

import org.apache.ibatis.annotations.Mapper;

//@Repository
//@RequiredArgsConstructor

@Mapper
public interface OrderMapper {

    int insert(Order order);


//
//    private final JdbcTemplate jdbcTemplate;
//
//    private static final ResultSetConverter<Order> CONVERTER = r -> {
//        Order order = new Order();
//        order.setId(r.getInt("id"));
//        order.setUserId(r.getString("user_id"));
//        order.setCommodityCode(r.getString("commodity_code"));
//        order.setCount(r.getInt("count"));
//        order.setMoney(r.getBigDecimal("money"));
//        return order;
//    };
//
//
//    public int insert(Order record) {
//        return jdbcTemplate.update("insert into order_tbl (user_id,commodity_code,count,money) values (?,?,?,?)",
//                r -> {
//                    r.setString(1, record.getUserId());
//                    r.setString(2, record.getCommodityCode());
//                    r.setInt(3, record.getCount());
//                    r.setBigDecimal(4, record.getMoney());
//                });
//    }
}
