package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.MusicDAO;
import dao.PlaylistDAO;
import util.JDBCUtil;

public class PlaylistService {
	static private PlaylistService instance = null;
	private PlaylistService() {}
	PlaylistDAO playlist_dao = PlaylistDAO.getInstance();
	MusicDAO music_dao = MusicDAO.getInstance();
	JDBCUtil jdbc = JDBCUtil.getInstance();
	public static PlaylistService getInstance() {
		if(instance==null) {instance=new PlaylistService();}
		return instance;
	}
	
	public void show_my_musics() {
		List<String> my_music_numbers = new ArrayList<>();
		List<Map<String, Object>> rows = playlist_dao.show_my_musics();
		for(int i = 0; i<rows.size(); i++) {
			String music_num = (String)rows.get(i).get("MUSIC_NUM");
			my_music_numbers.add(music_num);
		}

		// 내가 플레이리스트속에 담은 음악들의 행들을 가져와서 my_musics에 집어넣으면 됨  
		List<Map<String, Object>> my_musics = music_dao.find_my_musics(my_music_numbers);
		for(int i = 0; i < my_musics.size(); i++) {
			System.out.println(my_musics.get(i).get("MUSIC_NAME"));
		}
	}
}
