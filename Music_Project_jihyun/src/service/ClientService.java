package service;

import java.util.Map;

import controller.Controller;
import dao.ClientDAO;
import util.ScanUtil;
import util.View;

public class ClientService {
	static private ClientService instance = null;
	
	ClientDAO clientdao = ClientDAO.getInstance();
	private ClientService() {}
	
	public static ClientService getInstance() {
		if(instance==null) {instance = new ClientService();}
		return instance;
	}

	public int showmain() {
		System.out.println("1. 마이페이지   2.노래차트   3.이용권 구매   4.고객센터  5.로그아웃   0.종료");
		System.out.println("번호를 입력하세요>> ");
		
		switch(ScanUtil.nextInt()) {
		case 2:
			return View.MUSIC_MAIN;
	
		default: return 1;
		}
	}
	
	public int login() {
		System.out.println("아이디를 입력해주세요 >> ");
		String id = ScanUtil.nextLine();
		System.out.println("비밓번호를 입력해주세요 >> ");
		String pwd = ScanUtil.nextLine();
		Map<String, Object> row = clientdao.login(id, pwd);
		if(row == null) {
			System.out.println("존재하지 않는 회원입니다 !");
			return View.HOME;
		}
		if(row.get("CLI_ID") == "a001") { //관리자 로그인
			Controller.login_num = 2;
			System.out.println("관리자 로그인 성공 !");
			Controller.login_id = (String) row.get("CLI_ID");
			Controller.login_pwd = pwd;
			return View.HOME;
		}else{
			Controller.login_num = 1;
			System.out.println("회원로그인 성공 !");
			Controller.login_id = (String)row.get("CLI_ID");
			Controller.login_pwd = pwd;
			return View.CLI_MAIN;
		}
	}

}
