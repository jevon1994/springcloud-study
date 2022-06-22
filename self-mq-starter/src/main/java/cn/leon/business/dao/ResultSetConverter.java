package cn.leon.mq.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetConverter<T> {

    T convert(ResultSet resultSet) throws SQLException;
}
