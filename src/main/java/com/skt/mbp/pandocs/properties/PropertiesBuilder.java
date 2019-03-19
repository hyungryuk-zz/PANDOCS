package com.skt.mbp.pandocs.properties;

import java.io.InputStream;
import java.util.Properties;

public class  PropertiesBuilder {

    public static Properties properties;

    public void setProperties(){

        try {
            properties = new Properties();
            InputStream inputStream = getClass().getResourceAsStream("/properties.properties");
            properties.load(inputStream);
            inputStream.close();

        }catch(java.io.IOException e){
            System.out.println("프로퍼티 파일을 찾을 수 없습니다");
            e.printStackTrace();
        }

    }

}
