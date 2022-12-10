package com.example.csc221_assignment4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TableInterface
{
    Connection getConnection(String url, String username, String password);

    static void createSchema(Connection connection, String nameSchema) throws SQLException
    {
        PreparedStatement createTable = connection.prepareStatement("CREATE SCHEMA " + nameSchema);
        try
        {
            createTable.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static void createTable(Connection connection, String ddlCreateTable) throws SQLException
    {
        PreparedStatement createTable = connection.prepareStatement(ddlCreateTable);
        try
        {
            createTable.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static void updateField(Connection connection, String ddlUpdateField) throws SQLException
    {
        PreparedStatement updateField = connection.prepareStatement(ddlUpdateField);
        try
        {
            updateField.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static void populateTable(Connection connection, String ddlPopulateTable) throws SQLException
    {
        PreparedStatement populateTable = connection.prepareStatement(ddlPopulateTable);
        try
        {
            populateTable.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        } ;
    }

    static void deleteRecord(Connection connection, String ddlDeleteRecord) throws SQLException
    {
        PreparedStatement deleteRecord = connection.prepareStatement(ddlDeleteRecord);
        try
        {
            deleteRecord.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static ResultSet getTable(Connection connection, String nameTable) throws SQLException
    {
        ResultSet RS = null;
        PreparedStatement getTable = connection.prepareStatement("SELECT * FROM " + nameTable);
        try
        {
            RS = getTable.executeQuery();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return RS;
    }
}
