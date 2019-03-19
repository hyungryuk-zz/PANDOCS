package com.skt.mbp.pandocs.sampleQuery;

import com.skt.mbp.pandocs.hive.HiveConnectionBuilder;
import com.skt.mbp.pandocs.properties.PropertiesBuilder;
import com.skt.mbp.pandocs.table.Table;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;
import java.util.Vector;

import java.util.stream.Collectors;

public class SampleQuery {

    public void makeExample(Table table,String query) throws Exception{

        HiveConnectionBuilder hcb = new HiveConnectionBuilder(table.dbName);


        ResultSet example_res = null;


        try{
            example_res=hcb.getStmt().executeQuery(query.replaceAll(";",""));
        }catch (Exception e){
            System.out.println("SQL ERROR OCCURED!!");
        }

        if(example_res!=null) {
            boolean check_colName = false;
            Vector<Vector<String>> col_datas = new Vector<Vector<String>>();
            Vector<String> col_name = new Vector<>();
            while (example_res.next()) {
//                    for(int i=0;i<Integer.parseInt(properties.getProperty("table_example_left_padding"));i++){
//                        tmpTable.example+=" ";
//                    }

                if (check_colName == false) {
                    for (int i = 1; i <= example_res.getMetaData().getColumnCount(); i++) {
                        col_datas.add(new Vector<String>());
                        col_name.add(example_res.getMetaData().getColumnName(i));
                    }
                    check_colName = true;
                }
                for (int i = 1; i <= example_res.getMetaData().getColumnCount(); i++) {
//                            System.out.println(example_res.getString(i));
                    if (example_res.getString(i) == null | example_res.getString(i) == "") {
                        col_datas.get(i - 1).add("null");
                    } else {
                        col_datas.get(i - 1).add(example_res.getString(i));
                    }

//                            tmpTable.exampleData.get(example_res.getMetaData().getColumnName(i)).add(example_res.getString(i));
                }


            }
            for (int i = 0; i < col_name.size(); i++) {
                table.exampleData.put(col_name.get(i), col_datas.get(i));
            }
        }



        ////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////


        int col_max_length_limit = Integer.parseInt(PropertiesBuilder.properties.getProperty("col_max_length_limit_in_example"));
        int example_row_length  = Integer.parseInt(PropertiesBuilder.properties.getProperty("example_row_length"));

        String example="";
        Vector<Integer> col_size=new Vector<Integer>();

        for (Map.Entry<String,Vector<String>> dataMap:table.exampleData.entrySet()) {
            if(dataMap.getKey().split(".").length==2){
                try {
                    int col_max_length = Collections.max(dataMap.getValue().stream().map(obj -> obj.length()).collect(Collectors.toList()));
                    if (Math.max(col_max_length, dataMap.getKey().split(".")[1].length()) > col_max_length_limit) {
                        col_size.add(col_max_length_limit);
                    } else {
                        col_size.add(Math.max(col_max_length, dataMap.getKey().split(".")[1].length()));
                    }
                }catch (NullPointerException e){
                    if (Math.max(0, dataMap.getKey().split(".")[1].length()) > col_max_length_limit) {
                        col_size.add(col_max_length_limit);
                    } else {
                        col_size.add(Math.max(0, dataMap.getKey().split(".")[1].length()));
                    }
                }
            }else{
                try {
                    int col_max_length = Collections.max(dataMap.getValue().stream().map(obj -> obj.length()).collect(Collectors.toList()));
                    if (Math.max(col_max_length, dataMap.getKey().length()) > col_max_length_limit) {
                        col_size.add(col_max_length_limit);
                    } else {
                        col_size.add(Math.max(col_max_length, dataMap.getKey().length()));
                    }
                }catch (NullPointerException e){
                    if (Math.max(0, dataMap.getKey().length()) > col_max_length_limit) {
                        col_size.add(col_max_length_limit);
                    } else {
                        col_size.add(Math.max(0, dataMap.getKey().length()));
                    }
                }
            }


        }


        for(int i=0;i<Integer.parseInt(PropertiesBuilder.properties.getProperty("table_example_left_padding"));i++){
            example+=" ";
        }
        example+="+";
        for(int i=0;i<col_size.size();i++){
            for(int j=0;j<col_size.get(i);j++){
                example+="-";
            }
            example+="+";
        }

        example+="\n";

        for(int i=0;i<Integer.parseInt(PropertiesBuilder.properties.getProperty("table_example_left_padding"));i++){
            example+=" ";
        }

        example+="|";


        int tmp_idx = 0;
        for (Map.Entry<String,Vector<String>> dataMap:table.exampleData.entrySet()){
            if(dataMap.getKey().length()<col_size.get(tmp_idx)){
                if(dataMap.getKey().split(".").length==2){
                    for(int j=0;j<col_size.get(tmp_idx)-dataMap.getKey().split(".")[1].length();j++){
                        example+=" ";
                    }
                }else{
                    for(int j=0;j<col_size.get(tmp_idx)-dataMap.getKey().length();j++){
                        example+=" ";
                    }
                }

            }
            if(dataMap.getKey().split(".").length==2){
                example+=dataMap.getKey().split(".")[1];
            }else{
                example+=dataMap.getKey();
            }

            example+="|";
            tmp_idx++;
        }

        example+="\n";

        for(int i=0;i<Integer.parseInt(PropertiesBuilder.properties.getProperty("table_example_left_padding"));i++){
            example+=" ";
        }
        example+="+";
        for(int i=0;i<col_size.size();i++){
            for(int j=0;j<col_size.get(i);j++){
                example+="-";
            }
            example+="+";
        }

        example+="\n";



        int tmp_idx_1 = 0;

        Vector<Vector<String>>rows= new Vector<Vector<String>>();

        for( String key : table.exampleData.keySet() ){
            rows.add(table.exampleData.get(key));
        }

        for(int l=0;l<example_row_length;l++){
            for(int i=0;i<Integer.parseInt(PropertiesBuilder.properties.getProperty("table_example_left_padding"));i++){
                example+=" ";
            }
            example+="|";
            for(int m=0;m<rows.size();m++){
                if(rows.get(m).get(l).length()<=col_size.get(m)){
                    for(int n=0;n<col_size.get(m)-rows.get(m).get(l).length();n++){
                        example+=" ";
                    }
                    example+=rows.get(m).get(l);
                    example+="|";
                }else{
                    for(int n=0;n<col_size.get(m);n++){
                        if(n>col_size.get(m)-4){
                            example+=".";
                        }else{
                            example+=rows.get(m).get(l).charAt(n);
                        }

                    }
                    example+="|";
                }
            }
            example+="\n";

        }





        for(int i=0;i<Integer.parseInt(PropertiesBuilder.properties.getProperty("table_example_left_padding"));i++){
            example+=" ";
        }
        example+="+";
        for(int i=0;i<col_size.size();i++){
            for(int j=0;j<col_size.get(i);j++){
                example+="-";
            }
            example+="+";
        }
        table.example=example;


        hcb.close();
    }

}
