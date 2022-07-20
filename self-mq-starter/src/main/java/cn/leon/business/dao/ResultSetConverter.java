package cn.leon.business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetConverter<T> {

    T convert(ResultSet resultSet) throws SQLException;
}
