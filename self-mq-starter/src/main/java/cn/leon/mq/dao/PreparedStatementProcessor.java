package cn.leon.order.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementProcessor {

    void process(PreparedStatement ps) throws SQLException;
}
