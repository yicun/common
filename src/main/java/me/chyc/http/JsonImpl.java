package me.chyc.http;

import org.json.JSONObject;

/**
 * Created by chyc on 8/9/14.
 */
public class JsonImpl {
    public static void main(String args[]){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", "10001");
        jsonObject.put("Name", "James");

        System.out.println(jsonObject.toString());
        jsonObject.put("ID", "10010");
        System.out.println(jsonObject.toString());
    }

}
