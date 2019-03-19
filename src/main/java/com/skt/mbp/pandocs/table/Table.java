package com.skt.mbp.pandocs.table;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;



public class Table {
    public Schema schema;
    public String dbName;
    public String tableName;
    public String example;
    public Map<String,Vector<String>> exampleData;

    public Table(){
        schema = new Schema();
        example="";
        exampleData = new LinkedHashMap<String,Vector<String>>();
    }
}

