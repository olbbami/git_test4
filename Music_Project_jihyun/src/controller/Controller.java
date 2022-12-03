package controller;



import dao.ClientDAO;
import service.ClientService;
import service.MusicService;
import util.ScanUtil;
import util.View;

public class Controller {

	static public int login_num;
	static public String login_id;
	static public String login_pwd;
	
	MusicService musicService = MusicService.getInstance();
	ClientDAO clientDAO = ClientDAO.getInstance();
	ClientService client_service = ClientService.getInstance();
	public static void main(String[] args) {
		new Controller().start();
	}

	private void start() {
		int view = View.HOME;
		while(true) {
			switch(view) {
			case View.HOME: view = home(); break; //home에서 받고 계속 돌아야하므로 while문을 돌린다.
			case View.CLI_MAIN: view = client_service.showmain(); break;
			case View.CLI_LOGIN: view = client_service.login();break;
			
			
			
			case View.MUSIC_MAIN: view = musicService.musicMain(); break; //노래메인창으로 이동
			case View.MUSIC_SEARCH: view = musicService.musicSearch(); break;
			case View.MUSIC_RECOMMEND: view = musicService.musicRecommend(); break;
			case View.MUSIC_NEW: view = musicService.musicNew(); break;
			case View.MUSIC_BEST: view = musicService.musicBest(); break;
			
			
			
			}
		}
	}
	
	private int home() { 
		// view는 숫자를 의미하므로 반환 타입이 숫자임
		// 메인화면
		System.out.println("========================= 메인 =========================");
		System.out.println("1.로그인 2.노래차트 3.이용권구매 4.고객센터 5.회원가입");
		System.out.println("========================================================");
		System.out.print("번호 입력 >>");
		switch (ScanUtil.nextInt()) {
		case 1: return View.CLI_LOGIN;
		case 2: return View.MUSIC_MAIN;  //노래차트
		default : return View.HOME;
		}
	}
	
	
	
	
	
}
