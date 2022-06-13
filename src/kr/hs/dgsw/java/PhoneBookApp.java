package kr.hs.dgsw.java;

import java.io.*;
import java.util.*;

public class PhoneBookApp {

    private Scanner sc;

    private HashMap<String, String> phone;

    private String filePath,msg;

    public PhoneBookApp(HashMap<String, String> phone, String filePath) {
        this.sc = new Scanner(System.in);
        this.phone = phone;
        this.filePath = filePath;
    }

    public void sortName() {
        List<Map.Entry<String, String>> entryList = new LinkedList<>(phone.entrySet());
        entryList.sort(Map.Entry.comparingByValue());

        for(Map.Entry<String, String> entry : entryList){
            System.out.printf("전화번호: %-12s 이름: %-12s\n", entry.getKey(), entry.getValue());
        }
        System.out.println("▶ /도움말");

    }

    public void sortName(String msg) {
        List<Map.Entry<String, String>> entryList = new LinkedList<>(phone.entrySet());
        entryList.sort(Map.Entry.comparingByValue());

        for(Map.Entry<String, String> entry : entryList){
            if(entry.getKey().contains(msg) || entry.getValue().contains(msg)){
                System.out.printf("전화번호: %-12s 이름: %-12s\n", entry.getKey(), entry.getValue());
            }
        }

    }

    public void load() {
        String s;
        String[] str;
        try {
            phone = new HashMap<>();

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while((s = br.readLine()) != null){
                str = s.split(" ");
                phone.put(str[0], str[1]);
            }

            System.out.println("= 전화번호부 =");
            if(phone.size() == 0){
                System.out.println("등록된 정보가 없습니다.");
            }
            sortName();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        String phoneNumber, name;
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true));

            System.out.print("전화번호: ");
            phoneNumber = sc.next();
            if(phone.get(phoneNumber) != null){
                System.out.println("이미 등록된 전화번호입니다.");
                return;
            }

            System.out.print("이름: ");
            name = sc.next() + "\r\n";

            bw.write(phoneNumber + " ");
            bw.write(name);
            bw.close();
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search() {
        try {
            System.out.print("이름 or 전화번호: ");
            msg = sc.next();

            System.out.println("= 전화번호부 =");
            sortName(msg);
            System.out.println("▶ /도움말");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete() {
        try{
            System.out.print("삭제할 이름: ");
            msg = sc.next();

            if(getKey(phone,msg).contains("\r\n")){
                System.out.println("※ 중복된 이름이 있습니다.");
                sortName(msg);
                System.out.print("삭제할 번호: ");
                msg = sc.next();
            }

            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line,dummy = "";
            while((line = br.readLine()) != null){
                if(line.contains(msg)){
                    continue;
                }
                dummy += (line + "\r\n");
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(dummy);
            bw.close();
            br.close();

            load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        String keys = "";
        int cnt = 0;
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                if(cnt > 0){
                    keys += "\r\n";
                }
                keys += key;
                cnt++;
            }
        }
        return (K) keys;
    }

}
