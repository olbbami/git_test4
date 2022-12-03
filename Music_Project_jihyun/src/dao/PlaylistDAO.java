package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class PlaylistDAO {
	static private PlaylistDAO instance = null;
	private PlaylistDAO() {}

	JDBCUtil jdbc = JDBCUtil.getInstance();
	public static PlaylistDAO getInstance() {
		if(instance==null) {instance=new PlaylistDAO();}
		return instance;
	}
	
	
	public List<Map<String, Object>> check_having_playlist() {
		String sql = "SELECT * FROM PLAYLIST "
				+ " WHERE CLIENT_ID = '"+Controller.login_id+"'";
		List<Map<String, Object>> rows = jdbc.selectList(sql);
		return rows;
	}
	
	
	// <메소드 오버로딩> 플리명을 받냐 안받냐 구분을 해줘야 하므로 
	public void get_music(String name, Object music_num) {
		String sql = "INSERT INTO PLAYLIST VALUES('"+Controller.login_id+"', ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(music_num);
		param.add(name);
		jdbc.insert_data(sql, param);
		System.out.println("플레이리스트에 음악을 추가했습니다!");
	}
	
	
	// 플레이리스트가 이미 있을 경우 플레이리스트명을 알고있어야함. 
	public int get_music(Object music_num) {
		List<String> number_list = new ArrayList<>();
		
		
		List<Map<String, Object>> rows = show_my_musics();
		for(int i = 0; i < rows.size(); i++) {
			number_list.add((String)rows.get(i).get("MUSIC_NUM"));
		}
		
		// 이미 담겨있는 노래를 담을려하면 아래와 같은 메시지를 띄우고 함수 강제종료 
		if(number_list.contains((String)music_num)) {
			System.out.println("이미 담겨있는 노래입니다!");
			return 888;
		}
		
		String playlist_name = find_playlist_name();
		
		
		String sql = "INSERT INTO PLAYLIST VALUES('"+Controller.login_id+"', ?, '"+playlist_name+"')";
		List<Object> param = new ArrayList<>();
		param.add(music_num);
		jdbc.insert_data(sql, param);
		System.out.println("플레이리스트에 음악을 추가했습니다!");
		return 888888;
	}


	public String find_playlist_name() {
		String sql = "SELECT PLAYLIST_NAME FROM PLAYLIST "
				+ " WHERE CLIENT_ID = '"+Controller.login_id+"'";
		List<Map<String, Object>> rows = jdbc.selectList(sql);
		String name = (String)rows.get(0).get("PLAYLIST_NAME");
		
		return name;
	}


	public List<Map<String, Object>> show_my_musics() {
		
		String sql = "SELECT * FROM PLAYLIST WHERE CLIENT_ID = '"+Controller.login_id+"'";
		List<Map<String, Object>> rows = jdbc.selectList(sql);
		return rows;
		
	}
	
}
