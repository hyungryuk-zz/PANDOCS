package com.skt.mbp.pandocs.table;

import com.skt.mbp.pandocs.hive.HiveConnectionBuilder;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class TableCollector {

    HiveConnectionBuilder conn;
    String dbName;

    public TableCollector(String _dbName){
        conn = new HiveConnectionBuilder(_dbName);
        dbName = _dbName;

    }

    public void close(){
        conn.close();
    }

    public Vector<Table> getAllTables() throws Exception{

        ResultSet table_list_rs = conn.getStmt().executeQuery("show tables");

        Vector<Table> res = new Vector<Table>();

        try {
            while (table_list_rs.next()) {
                Map<String, String> resMap = new LinkedHashMap<String, String>();

                String sql = "describe " +dbName+"." + table_list_rs.getString(1);

                ResultSet cols_rs = conn.getStmt().executeQuery(sql);

                Table tmpTable = new Table();

                while (cols_rs.next()) {

                    if(cols_rs.getString(2) ==null){
                        resMap.put(cols_rs.getString(1), "");
                    }else{
                        resMap.put(cols_rs.getString(1), cols_rs.getString(2));
                    }


                }

                tmpTable.schema.col_type = resMap;
                tmpTable.dbName = dbName;
                tmpTable.tableName = table_list_rs.getString(1);

                System.out.println("=========" + tmpTable.dbName + "." + tmpTable.tableName + " 로드 완료!!");
                res.add(tmpTable);

            }
        }catch(org.apache.hive.service.cli.HiveSQLException e){
            return res;
        }

        return res;

    }

    public Table getTable(String _tableName) throws Exception{

        String sql = "describe  mbp" + "." + _tableName;

        ResultSet cols_rs = conn.getStmt().executeQuery(sql);

        Table res = new Table();

        Map<String,String> resMap = new LinkedHashMap<String,String>();

        while (cols_rs.next()) {

            resMap.put(cols_rs.getString(1), cols_rs.getString(2));

        }

        res.schema.col_type = resMap;
        res.dbName = dbName;
        res.tableName = _tableName;

        return res;

    }

}
