package service;

import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.ClientDAO;
import dao.MusicDAO;
import dao.PlaylistDAO;
import util.ScanUtil;
import util.View;


	public class MusicService {

		private static MusicService instance = null;
		private MusicService() {}
		public static MusicService getInstance() {
			if(instance == null) instance = new MusicService();
			return instance;
		}
		
		ClientDAO clientdao = ClientDAO.getInstance();
		MusicDAO dao = MusicDAO.getInstance();
		PlaylistDAO playlist_dao = PlaylistDAO.getInstance();
		PlaylistService play_service = PlaylistService.getInstance();
		public int musicMain() {
			System.out.println("--------------------------------------------------------------");
			System.out.println("0. 노래검색");
			System.out.println("--------------------------------------------------------------");
			System.out.println("1. 추천곡");
			System.out.println("//추천곡 화면(간략히) 회원별로 추천하는거니까 회원테이블이랑");
			System.out.println("--------------------------------------------------------------");
			System.out.println("2. 신곡");
			List<Map<String, Object>> row1 = dao.SampleMusicNew();
			for(Map<String, Object> item : row1) {
				System.out.print(item.get("MUSIC_NAME")+ " - " +item.get("MUSIC_SINGER")+ ", ");
				//
			}
			System.out.println("\n--------------------------------------------------------------");
			System.out.println("3. 인기차트100");
			List<Map<String, Object>> row2 = dao.SampleMusicBest();
			for(Map<String, Object> item : row2) {
				System.out.print(item.get("MUSIC_NAME")+ " - " +item.get("MUSIC_SINGER")+ ", ");
			}
			System.out.println("\n--------------------------------------------------------------");
			System.out.println("4. 홈 화면으로 돌아가기");
			System.out.print("번호 입력 >> ");
			
			switch(ScanUtil.nextInt()){
			case 0:
				return View.MUSIC_SEARCH;
			case 1:
				return View.MUSIC_RECOMMEND;
			case 2:
				return View.MUSIC_NEW;		
			case 3:
				return View.MUSIC_BEST;
			case 4:
				return View.HOME;
			default:
				return View.MUSIC_MAIN;
			}
			
		}
		
		public int musicBest() {
			clearScreen();
			System.out.println("노래차트100 리스트");
			List<Map<String, Object>> row = dao.musicBest();
			for(Map<String, Object> item : row) {
				System.out.println("--------------------------------------------------------------");
				System.out.printf("%2s. %s\n - %4s\n%7s\n", item.get("ROWNUM"), 
						item.get("MUSIC_NAME"), item.get("MUSIC_SINGER"), item.get("MUSIC_PLAYCOUNT"));
			}
			
			System.out.println("--------------------------------------------------------------");
			System.out.println("< -1.이전페이지         다음페이지 +1 >");
			System.out.println();
			System.out.println("--------------------------------------------------------------");
			System.out.println("노래선택 >> ");
			return 0;
		}
		public int musicNew() {
			System.out.println("노래신곡 리스트");
			System.out.println("--------------------------------------------------------------");
			System.out.println("//해당 리스트 출력");
			System.out.println("--------------------------------------------------------------");
			System.out.println("< -1.이전페이지         다음페이지 +1 >");
			System.out.println();
			System.out.println("--------------------------------------------------------------");
			System.out.println("노래선택 >> ");
			return 0;
		}
		
		public int musicRecommend() {
			System.out.println("노래추천 리스트");
			System.out.println("--------------------------------------------------------------");
			System.out.println("//해당 리스트 출력");
			System.out.println("--------------------------------------------------------------");
			System.out.println("< -1.이전페이지         다음페이지 +1 >");
			System.out.println();
			System.out.println("--------------------------------------------------------------");
			System.out.println("노래선택 >> ");
			return 0;
		}
		
		public int musicSearch() {
			
			//List<Map<String, Object>> selectList(String sql, List<Object> param);
			
			System.out.println("노래 검색 창");
			System.out.println("--------------------------------------------------------------");
			System.out.println("1. 가수검색  2. 제목 검색  3. 홈 화면으로"  );
			System.out.println("--------------------------------------------------------------");
			System.out.print("번호입력 >> ");
			
			String sql, input;
			
			switch(ScanUtil.nextInt()) {
			case 1:  //가수검색 
				sql = "MUSIC_SINGER LIKE ";
				System.out.print("검색할 가수 이름을 입력하세요 >> ");
				input = ScanUtil.nextLine();
				List<Map<String, Object>> singer_list = dao.musicSearch(sql, input);
				
				if(singer_list == null) {
					System.out.println("일치하는 가수 이름이 없습니다. ");
					return View.MUSIC_MAIN;  //검색하는 화면으로 돌아갈지 ?
				}else {
					for(Map<String, Object> item : singer_list) {
						System.out.println("--------------------------------------------------------------");
						System.out.printf("%2s. %s\n - %4s\n", item.get("ROWNUM"), 
						item.get("MUSIC_NAME"), item.get("MUSIC_SINGER"));
					}
					System.out.println("노래를 선택해주세요 >> ");
					//System.out.println(singer_list.get(ScanUtil.nextInt()-1).get("MUSIC_NAME") + " ▶ 1. 재생하기  2. 플레이리스트에 추가" );
					return musicSelect(singer_list.get(ScanUtil.nextInt()-1).get("MUSIC_NUM"));
					
				}	
				
			case 2:  //제목검색
				sql= " MUSIC_NAME LIKE ";
				System.out.print("검색할 제목을 입력하세요 >> ");
				input = ScanUtil.nextLine();

				List<Map<String, Object>> name_list = dao.musicSearch(sql, input);
				if(name_list == null) {
					System.out.println("일치하는 음원이 없습니다. ");
					return View.MUSIC_MAIN;
				}else {
					for(Map<String, Object> item : name_list) {
						System.out.println("--------------------------------------------------------------");
						System.out.printf("%2s. %s\n - %4s\n", item.get("ROWNUM"), 
								item.get("MUSIC_NAME"), item.get("MUSIC_SINGER"));
					}
					System.out.println("노래를 선택해주세요 >> ");
					//System.out.println(name_list.get(ScanUtil.nextInt()-1).get("MUSIC_NAME") + " ▶ 1. 재생하기  2. 플레이리스트에 추가" );
					return musicSelect(name_list.get(ScanUtil.nextInt()-1).get("MUSIC_NUM"));
				}	
			case 3: 
				return View.HOME;
			default :
				return View.HOME;
			}
		}
		
		//회원 비회원 나눠서 해줘야함. (일단은 회원이라고 가정하고)
		private int musicSelect(Object musicNum) {
			Map<String, Object> row = dao.musicSelect(musicNum);
			//System.out.println(num);
			System.out.println(row.get("MUSIC_NAME") + " ▶ 1. 재생하기  2. 플레이리스트에 추가");
			switch(ScanUtil.nextInt()) {
			case 1:  
				System.out.println("    재생중 화면 ../  ");

				int row2 = dao.musicSelectUp(musicNum);  //플레이 카운트 ++
				
				return View.MUSIC_MAIN;  //노래 끝나면 어디로 보낼지 ??
			case 2:
				if(Controller.login_num!=1) {
					System.out.println("회원에게만 지원되는 서비스입니다!");
					return View.CLI_LOGIN;
				}else {
					Map<String, Object> my_row = clientdao.get_my_id_row();
					if(my_row.get("CLI_PAYCHECK").equals("n")) {
						System.out.println("멤버쉽 회원에게만 지원되는 서비스입니다!");
						// 원래는 HOME이 아니라 멤버쉽 이용권 구매 페이지로 안내해야함
						return View.CLI_MAIN;
					}else {
						// 플레이리스트가 기존에 없는 멤버쉽회원은 생성 이름도 입력받아서 만들어준다.
						if(playlist_dao.check_having_playlist()==null) {
							System.out.println("생성할 플레이리스트의 이름을 입력해주세요.");
							String playlist_name = ScanUtil.nextLine();
							playlist_dao.get_music(playlist_name, musicNum);
							play_service.show_my_musics();
							return View.CLI_MAIN;
						}else {
							// 이미 플레이리스트를 만들어 놓은 멤버쉽회원은 이름을 따로 받지 않는다.
							playlist_dao.get_music(musicNum);
							play_service.show_my_musics();
							return View.CLI_MAIN;
						}
						
					}
					
					
					
				}
//				System.out.println("회원 아이디와 음원번호를 이용해서 플레이리스트에 추가. 비회원일 경우 로그인/회원가입. 회원이지만 멤버십가입 안되있을경우 멤버십ㄱ");
				
			default: return View.MUSIC_MAIN;
			}
		}
		
		
		public static void clearScreen() {
			for(int i=0; i<100; i++) {
			System.out.println();
			}
		}
		

	}
