package com.skt.mbp.pandocs.table;

import com.skt.mbp.pandocs.fileHandler.Resources;
import com.skt.mbp.pandocs.properties.PropertiesBuilder;
import com.skt.mbp.pandocs.sampleQuery.SampleQuery;

import java.io.*;
import java.util.Map;

public class ConvertToRst {

    public String getTemplate(){
        String template = "";
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            //파일 객체 생성
            File file = new File(classLoader.getResource("templateFile.txt").getFile());
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                template+=line+"\n";
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.
            bufReader.close();
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }

        return template;
    }


    public String schemaToString(Table table){
        int col_size = Integer.parseInt(PropertiesBuilder.properties.getProperty("col_name_space_size"));
        int type_size = Integer.parseInt(PropertiesBuilder.properties.getProperty("type_space_size"));
        String res = "";
        for(String key : table.schema.col_type.keySet()){
            if(!(key.equals("")||key==null)){

                if(key.length()<col_size){
                    res+=key;
                    for(int i=0;i<col_size-key.length();i++){
                        res+=" ";
                    }
                }
                if(res.split("\n")[res.split("\n").length-1].length()+table.schema.col_type.get(key).length()<type_size){
                    res+=table.schema.col_type.get(key);
                    for(int i=0;i<type_size-res.split("\n")[res.split("\n").length-1].length();i++){
                        res+=" ";
                    }
                }
                if(!(Resources.cols.get(key)==null || Resources.cols.get(key).equals(""))){
                    res+=Resources.cols.get(key);
                }
                res+="\n";

            }
        }
        return res;
    }


    public String tableToRst(Table table){

        String template = getTemplate();

        template = template.replaceAll("_.tableName",table.tableName);
        template = template.replaceAll("_.dbName",table.dbName);

        template = template.replaceAll("_.tableSchema",schemaToString(table));

        if(Resources.tables.get(table.dbName+"."+table.tableName)!=null){
            template = template.replaceAll("_.tableDescription",Resources.tables.get(table.dbName+"."+table.tableName));
        }else{
            template = template.replaceAll("_.tableDescription","");
        }

        if(Resources.table_pips.get(table.dbName+"."+table.tableName)!=null){
            template = template.replaceAll("_.tableInput",Resources.table_pips.get(table.dbName+"."+table.tableName).split("\\$\\$")[0]);
            template = template.replaceAll("_.tableOutput",Resources.table_pips.get(table.dbName+"."+table.tableName).split("\\$\\$")[1]);
            template = template.replaceAll("_.makeWayName",Resources.table_pips.get(table.dbName+"."+table.tableName).split("\\$\\$")[2]);
            template = template.replaceAll("_.makeWayDetail",Resources.table_pips.get(table.dbName+"."+table.tableName).split("\\$\\$")[3]);
            template = template.replaceAll("_.URL",Resources.table_pips.get(table.dbName+"."+table.tableName).split("\\$\\$")[4]);
        }else{
            template = template.replaceAll("_.tableInput","");
            template = template.replaceAll("_.tableOutput","");
            template = template.replaceAll("_.makeWayName","");
            template = template.replaceAll("_.makeWayDetail","");
            template = template.replaceAll("_.URL","");
        }
        template = template.replaceAll("_.exampleSqlQuery",PropertiesBuilder.properties.getProperty("sql_example_query").replaceAll("_.db",table.dbName).replaceAll("_.table",table.tableName));

        try {
            new SampleQuery().makeExample(table,PropertiesBuilder.properties.getProperty("sql_example_query").replaceAll("_.db",table.dbName).replaceAll("_.table",table.tableName));
        }catch (Exception e){
            System.out.println(table.dbName+"."+table.tableName+"  SQL ERROR!");
        }

        template = template.replaceAll("_.exampleSqlResult",table.example);

        return template;
    }

}
