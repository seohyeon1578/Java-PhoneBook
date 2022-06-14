package kr.hs.dgsw.java;


import java.util.HashMap;
import java.util.Scanner;

public class Controller {

    private Scanner sc;

    private HashMap<String, String> phone;
    private PhoneBookAppInterface pba;

    private String filePath = "D:/test.txt";

    public Controller() {
        sc = new Scanner(System.in);
        pba = new PhoneBookApp(phone, filePath);
        pba.load();
    }

    public void start() {
        while(true){
            String input = sc.nextLine();

            switch (input){
                case "/도움말" : manual(); break;
                case "/등록" : pba.insert(); break;
                case "/검색" : pba.search(); break;
                case "/삭제" : pba.delete(); break;
                case "/종료" : return;
                default: System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");

            }
        }
    }

    static void manual() {
        System.out.println("= 전화번호 등록 =");
        System.out.println("▶ /등록");
        System.out.println("= 전화번호 검색 =");
        System.out.println("▶ /검색");
        System.out.println("= 전화번호 삭제 =");
        System.out.println("▶ /삭제");
        System.out.println("= 프로그램 종료 =");
        System.out.println("▶ /종료");
    }

}
