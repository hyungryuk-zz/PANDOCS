package com.skt.mbp.pandocs;


import com.skt.mbp.pandocs.fileHandler.FileOut;
import com.skt.mbp.pandocs.fileHandler.Resources;
import com.skt.mbp.pandocs.properties.PropertiesBuilder;
import com.skt.mbp.pandocs.table.ConvertToRst;
import com.skt.mbp.pandocs.table.Table;
import com.skt.mbp.pandocs.table.TableCollector;

import java.util.Map;
import java.util.Vector;

public class Main {

    public static void main(String... args){

        new PropertiesBuilder().setProperties();
        new Resources();
//        //컬럼이름 및 테이블 목록 csv파일 작성
//        try {
//            Vector<Table> all_table = new TableCollector("indb").getAllTables();
//            all_table.addAll(new TableCollector("mbp").getAllTables());
                //컬럼이름
//            new FileOut().mapToCsv(new TableCollection().getAllCols(all_table), "all_cols");
                //테이블 목록
//            new FileOut().mapToCsv(new TableCollection().getAllTableNames(all_table), "all_tables");
//        }catch(Exception e){
//            e.printStackTrace();
//        }

//        System.out.println(new ConvertToRst().getTemplate());

        try {
            Vector<Table> all_table = new TableCollector("indb").getAllTables();
            all_table.addAll(new TableCollector("mbp").getAllTables());
            for(Table table : all_table){
                new FileOut().tableToRst(table);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
