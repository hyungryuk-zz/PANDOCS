package com.skt.mbp.pandocs.fileHandler;

import com.skt.mbp.pandocs.properties.PropertiesBuilder;
import com.skt.mbp.pandocs.table.ConvertToRst;
import com.skt.mbp.pandocs.table.Table;

import java.io.FileOutputStream;
import java.util.Map;

public class FileOut {

    public static ConvertToRst ctr;
    public FileOut(){
        ctr = new ConvertToRst();
    }
    public void mapToCsv(Map<String,String> inputMap,String fileName) throws Exception{
        FileOutputStream output = new FileOutputStream(PropertiesBuilder.properties.get("file_out_path") +fileName+".csv");
        for(String colName : inputMap.keySet()){
            output.write((colName+","+inputMap.get(colName)+"\n").getBytes());
        }
        output.close();
    }

    public void tableToRst(Table table) throws Exception{

        ConvertToRst ctr = new ConvertToRst();

        FileOutputStream output = new FileOutputStream(PropertiesBuilder.properties.get("rst_file_out_path") +table.tableName+".rst");

        output.write(ctr.tableToRst(table).getBytes());

        output.close();

        System.out.println(table.dbName+"."+table.tableName+"   COMPLETED!");
    }
}
