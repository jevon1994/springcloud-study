package cn.leon.mq.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementProcessor {

    void process(PreparedStatement ps) throws SQLException;
}
