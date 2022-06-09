package kr.hs.dgsw.java;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Controller {

    private HashMap<String, PhoneBook> phone;

    private String filePath = "D:/test.txt";

    public Controller() {
        phone = load();
        System.out.println(phone);
        if(phone == null) phone = new HashMap<>();
    }

    private HashMap<String, PhoneBook> load() {
        HashMap<String, PhoneBook> map = null;

        File file = new File(filePath);
        if(!file.exists()) file.mkdirs();

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
            System.out.println("끝");
            map = (HashMap<String, PhoneBook>) ois.readObject();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        } finally {
            if(ois != null){
                try{
                    ois.close();
                }catch (IOException e){

                }
            }
        }

        return map;
    }

    public void insert() {

    }

    public void search() {

    }

    public void print() {

        if(phone.size() == 0) {
            System.out.println("등록된 정보가 없습니다.");
        }else {
            for (String key : phone.keySet()) {
                PhoneBook value = phone.get(key);
                System.out.println(key + value);
            }
        }

    }

}
