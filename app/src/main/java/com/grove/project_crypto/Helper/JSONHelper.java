package com.grove.project_crypto.Helper;

import android.content.Context;


import com.google.gson.Gson;
import com.grove.project_crypto.Encrypted;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class JSONHelper {

   private static final String FILE_DATA_NAME = "data.json";
   private Gson gson;

 public  static boolean exportToJSON(Context context, LinkedList<Encrypted> dataList) {

       Gson gson = new Gson();
       DataItems dataItems = new DataItems();
       dataItems.setData(dataList);
       String jsonString = gson.toJson(dataItems);

       FileOutputStream fileOutputStream = null;

       try {
           fileOutputStream = context.openFileOutput(FILE_DATA_NAME, Context.MODE_PRIVATE);
           fileOutputStream.write(jsonString.getBytes());
           return true;
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (fileOutputStream != null) {
               try {
                   fileOutputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }

       return false;
   }

   public static LinkedList<Encrypted> importFromJSON(Context context) {

       InputStreamReader streamReader = null;
       FileInputStream fileInputStream = null;
       try{
           fileInputStream = context.openFileInput(FILE_DATA_NAME);
           streamReader = new InputStreamReader(fileInputStream);
           Gson gson = new Gson();
           DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
           return  dataItems.getData();
       }
       catch (IOException ex){
           ex.printStackTrace();
       }
       finally {
           if (streamReader != null) {
               try {
                   streamReader.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if (fileInputStream != null) {
               try {
                   fileInputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }

       return null;
   }

   private static class DataItems {
       private LinkedList<Encrypted> passwords;

       LinkedList<Encrypted> getData() {
           return passwords;
       }
       void setData(LinkedList<Encrypted> data) {
           this.passwords = data;
       }
   }
}
