package com.skt.mbp.pandocs.fileHandler;

import com.skt.mbp.pandocs.table.Table;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Resources {

    public static Map<String,String> cols;
    public static Map<String,String> tables;
    public static Map<String,String> table_pips;

    public Resources(){
        cols = getColsDes();
        tables = getTableDes();
        table_pips = getTablePipeInfo();
    }
    public Map<String,String> getTableDes(){

        Map<String,String> res = new LinkedHashMap<String, String>();
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            //파일 객체 생성
            File file = new File(classLoader.getResource("all_tables.csv").getFile());
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                if(line.split(",").length==2){
                    res.put(line.split(",")[0],line.split(",")[1]);
                }
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.
            bufReader.close();
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }

        return res;

    }

    public Map<String,String> getColsDes(){

        Map<String,String> res = new LinkedHashMap<String, String>();
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            //파일 객체 생성
            File file = new File(classLoader.getResource("all_cols.csv").getFile());
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                if(line.split(",").length==3){
                    res.put(line.split(",")[0],line.split(",")[2]);
                }
            }
            bufReader.close();
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }

        return res;

    }

    public Map<String,String> getTablePipeInfo(){

        Map<String,String> res = new LinkedHashMap<String, String>();
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            //파일 객체 생성
            File file = new File(classLoader.getResource("all_table_pips.csv").getFile());
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                if(line.split(",").length==6){
                    res.put(line.split(",")[0],line.split(",")[1]+"$$"+line.split(",")[2]+"$$"+line.split(",")[3]+"$$"+line.split(",")[4]+"$$"+line.split(",")[5]);
                }
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.
            bufReader.close();
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }

        return res;

    }
}
