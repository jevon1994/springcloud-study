package cn.leon.order.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetConverter<T> {

    T convert(ResultSet resultSet) throws SQLException;
}
