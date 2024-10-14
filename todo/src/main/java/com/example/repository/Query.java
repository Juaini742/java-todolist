package com.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Query {

    private static final Logger LOGGER = Logger.getLogger(Query.class.getName());
    private Connection conn;

    public Query(Connection conn) {
        this.conn = conn;
    }

    private String constructorQuery(String table, String where, String[] attributes) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + table);
        if (where != null && !where.isEmpty()) {
            sql.append(" WHERE " + where);
        }
        return sql.toString();
    }

    private PreparedStatement prepareStatement(String sql, Object[] params) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt;
    }

    final public ResultSet findAll(String table, String where, Object... params) {
        String sql = constructorQuery(table, where, null);
        try {
            PreparedStatement stmt = prepareStatement(sql, params);
            return stmt.executeQuery();
        } catch (SQLException e) {
            LOGGER.severe("Error while executing query: " + sql + " with params: " + params);
            throw new QueryException("Error while executing query", e);
        }
    }

    final public ResultSet findOne(String table, String where, Object... params) {
        String sql = constructorQuery(table, where, null);
        try {
            PreparedStatement stmt = prepareStatement(sql, params);
            ResultSet rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            LOGGER.severe("Error while executing query: " + sql + " with params: " + params);
            throw new QueryException("Error while executing query", e);
        }
    }

    final public int insert(String table, String[] attributes, Object[] values) {
        if (attributes.length != values.length) {
            throw new IllegalArgumentException("attributes and values must have same length");
        }

        StringBuilder columnBuilder = new StringBuilder();
        StringBuilder placeHolderBuilder = new StringBuilder();
        for (int i = 0; i < attributes.length; i++) {
            columnBuilder.append(attributes[i]);
            placeHolderBuilder.append("?");
            if (i < attributes.length - 1) {
                columnBuilder.append(",");
                placeHolderBuilder.append(",");
            }
        }

        String sql = "INSERT INTO " + table + "(" + columnBuilder.toString() + ")" + "VALUES " + "("
                + placeHolderBuilder.toString() + ")";

        try {
            PreparedStatement stmt = prepareStatement(sql, values);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Error while executing query: " + sql + " with params: " + values);
            throw new QueryException("Error while executing query", e);
        }
    }

    final public int update(String table, String where, String[] attributes, Object[] values, Object[] params) {
        if (attributes.length != values.length) {
            throw new IllegalArgumentException("attributes and values must have the same length");
        }

        StringBuilder columnBuilder = new StringBuilder();
        for (int i = 0; i < attributes.length; i++) {
            columnBuilder.append(attributes[i]).append(" = ?");
            if (i < attributes.length - 1) {
                columnBuilder.append(", ");
            }
        }

        String sql = "UPDATE " + table + " SET " + columnBuilder + " WHERE " + where;

        try {
            PreparedStatement stmt = prepareStatement(sql, values);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(values.length + i + 1, params[i]);
            }

            return stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Error while executing query: " + sql + " with params: " + values + " and " + params);
            throw new QueryException("Error while executing query", e);
        }
    }

    final public int delete(String table, String where, Object... params) {
        String sql = "DELETE FROM " + table + " WHERE " + where;
        try {
            PreparedStatement stmt = prepareStatement(sql, params);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Error while executing query: " + sql + " with params: " + params);
            throw new QueryException("Error while executing query", e);
        }
    }

    public static class QueryException extends RuntimeException {
        public QueryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
