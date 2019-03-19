package com.skt.mbp.pandocs.table;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class TableCollection {

    public Map<String,String> getAllCols(Vector<Table> tables){

        Map<String,String> res = new LinkedHashMap<String, String>();

        for(Table table : tables){
            for(String key : table.schema.col_type.keySet()){
                res.put(key,table.dbName+"."+table.tableName+"."+table.schema.col_type.get(key));
            }
        }

        return res;
    }

    public Map<String,String> getAllTableNames(Vector<Table>tables){

        Map<String,String> res = new LinkedHashMap<String, String>();

        for(Table table : tables){
            res.put(table.dbName+"."+table.tableName,"");
        }
        return res;
    }
}
