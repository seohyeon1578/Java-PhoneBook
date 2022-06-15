package kr.hs.dgsw.java;

import java.io.*;
import java.util.*;

public class PhoneBookApp implements PhoneBookAppInterface{

    private Scanner sc;

    private HashMap<String, String> phone;

    private String filePath,msg;

    public PhoneBookApp(HashMap<String, String> phone, String filePath) {
        this.sc = new Scanner(System.in);
        this.phone = phone;
        this.filePath = filePath;
    }

    //전체 출력 이름순 정렬
    public void sortName() {
        List<Map.Entry<String, String>> entryList = new LinkedList<>(phone.entrySet());
        entryList.sort(Map.Entry.comparingByValue());

        for(Map.Entry<String, String> entry : entryList){
            System.out.printf("전화번호: %-12s 이름: %-12s\n", entry.getKey(), entry.getValue());
        }

    }

    // 검색(또는 삭제할 이름이 중복인 경우)에서 사용하는 이름순 정렬
    public void sortName(String msg) {
        //리스트안에 검색한 값이 있는지 확인.
        boolean listCheck = false;

        List<Map.Entry<String, String>> entryList = new LinkedList<>(phone.entrySet());
        entryList.sort(Map.Entry.comparingByValue());

        System.out.println("= 전화번호부 =");
        for(Map.Entry<String, String> entry : entryList){
            if(entry.getKey().contains(msg) || entry.getValue().contains(msg)){
                System.out.printf("전화번호: %-12s 이름: %-12s\n", entry.getKey(), entry.getValue());
                //검색한 값이 있으면 true
                listCheck = true;
            }
        }

        //검색한 값이 없을시 출력
        if(!listCheck){
            System.out.println("[ " + msg + " ]에 대한 검색 결과가 없습니다.");
        }
    }

    // value값 받아와서 key값으로 바꿔주기 ( 키값이 여러개면 \r\n으로 구분)
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

    //파일에 저장된 정보 불러오기
    @Override
    public void load() {
        String s;
        String[] str;
        try {
            phone = new HashMap<>();

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            //bufferedreader에 담은 값을 라인으로 구분지어 s에 담는다. (라인이 null 값 일때까지)
            while((s = br.readLine()) != null){
                //전화번호와 이름을 스페이스바로 구분지어 받음.
                str = s.split(" ");
                phone.put(str[0], str[1]);
            }

            System.out.println("= 전화번호부 =");
            //파일에 저장된 전화번호가 없을시.
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

    @Override
    public void insert() {
        String phoneNumber, name;
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath,true));

            //전화번호 입력 받기 (이미 등록된 전화번호는 다시 입력)
            while (true) {
                System.out.print("전화번호: ");
                phoneNumber = sc.next();

                if (phone.get(phoneNumber) != null) {

                    while (true) {
                        System.out.print("이미 등록된 전화번호입니다. 다시입력하시겠습니까? (Y/N): ");
                        String YN = sc.next();

                        if (YN.equals("Y")) {
                            break;
                        } else if (YN.equals("N")) {
                            load();
                            return;
                        } else {
                            System.out.println("Y 또는 N 으로 입력해주세요.");
                            continue;
                        }
                    }
                    continue;
                }
                break;
            }

            System.out.print("이름: ");
            name = sc.next() + "\r\n";

            //파일에 올리기
            bw.write(phoneNumber + " ");
            bw.write(name);
            bw.close();
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search() {
        try {
            System.out.print("이름 or 전화번호: ");
            msg = sc.next();
            sortName(msg);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try{
            System.out.print("삭제할 이름: ");
            msg = sc.next();

            //키값(전화번호)를 받아와서 value에 맞는 값이 없으면
            if(getKey(phone, msg).equals("")){
                System.out.println("※ 등록되지 않은 이름입니다.");
                load();
                return;
            }

            if(getKey(phone, msg).contains("\r\n")){
                System.out.println("※ 중복된 이름이 있습니다.");
                sortName(msg);
                System.out.print("삭제할 번호: ");
                msg = sc.next();
            }

            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line,dummy = "";
            while((line = br.readLine()) != null){
                //라인안에 삭제를 원한 값이 있다면 dummy에 저장하지않고 다음 라인으로
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

}