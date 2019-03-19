package com.skt.mbp.pandocs.hive;

import com.skt.mbp.pandocs.properties.PropertiesBuilder;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

public class HiveConnectionBuilder {

    Connection conn;
    Statement stmt;


    public HiveConnectionBuilder(String dbName){
        String driverName = "org.apache.hive.jdbc.HiveDriver";

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(PropertiesBuilder.properties.getProperty("hive_url")+dbName);
            stmt = conn.createStatement();

            stmt.setFetchSize(Integer.parseInt(PropertiesBuilder.properties.getProperty("stmt_fetch_size")));
//            stmt.setQueryTimeout(Integer.parseInt(PropertiesBuilder.properties.getProperty("query_timeout")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Statement getStmt(){
        return stmt;
    }

    public void close(){
        try{
            conn.close();
        }catch(java.sql.SQLException e){
            e.printStackTrace();
        }

    }

}
