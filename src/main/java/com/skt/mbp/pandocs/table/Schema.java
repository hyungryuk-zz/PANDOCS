package com.skt.mbp.pandocs.table;

import java.util.LinkedHashMap;
import java.util.Map;

public class Schema{

    public Map<String,String> col_type;

    public Schema(){

        col_type = new LinkedHashMap<String, String>();

    }
}