package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class MusicDAO {
	private static MusicDAO instance = null;
	private MusicDAO() {}
	
	public static MusicDAO getInstance() {
		
		if(instance == null) instance = new MusicDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	
	public List<Map<String, Object>> SampleMusicBest() { //main에서 보여주는 베스트
		return jdbc.selectList("SELECT ROWNUM, MUSIC_NAME, MUSIC_SINGER        \r\n" + 
				"                 FROM (SELECT MUSIC_NAME, MUSIC_SINGER, MUSIC_PLAYCOUNT\r\n" + 
				"                         FROM MUSIC\r\n" + 
				"                       ORDER BY 3 DESC)\r\n" + 
				"                WHERE ROWNUM <= 3");
	}
	public List<Map<String, Object>> SampleMusicNew() {	//main에서 보여주는 신곡
		return jdbc.selectList("SELECT ROWNUM, MUSIC_NAME, MUSIC_SINGER        \r\n" + 
				"                 FROM (SELECT MUSIC_NAME, MUSIC_SINGER, MUSIC_DATE\r\n" + 
				"                         FROM MUSIC\r\n" + 
				"                       ORDER BY 3 DESC)\r\n" + 
				"                WHERE ROWNUM <= 3");
	}
	
	
	public List<Map<String, Object>> musicBest() {  //베스트
		return jdbc.selectList("SELECT ROWNUM, MUSIC_NAME, MUSIC_SINGER, MUSIC_PLAYCOUNT\r\n" + 
				"  FROM (SELECT MUSIC_NAME, MUSIC_SINGER, MUSIC_PLAYCOUNT \r\n" + 
				"          FROM MUSIC\r\n" + 
				"        ORDER BY 3 DESC)\r\n" + 
				"  WHERE ROWNUM BETWEEN 1 AND 25");
	}


	public List<Map<String, Object>> musicSearch(String sql, String input) { //검색
		return jdbc.selectList("SELECT ROWNUM, MUSIC_NAME, MUSIC_SINGER, MUSIC_NUM FROM MUSIC WHERE " + sql + "'%" + input + "%'");
	}

	public Map<String, Object> musicSelect(Object num) {
		return jdbc.selectOne("SELECT MUSIC_NUM, MUSIC_NAME, MUSIC_SINGER, MUSIC_PLAYCOUNT FROM MUSIC WHERE MUSIC_NUM = " + num);
	}

	public int musicSelectUp(Object musicNum) {  //플레이 카운트 업데이트
		return jdbc.update("UPDATE MUSIC SET MUSIC_PLAYCOUNT = MUSIC_PLAYCOUNT + 1" + "WHERE MUSIC_NUM = " + musicNum);
	}

	public List<Map<String, Object>> find_my_musics(List<String> my_music_nums) {
		List<Map<String, Object>> rows = new ArrayList<>();
		for(int i = 0; i < my_music_nums.size(); i++) {
			String sql = "SELECT * FROM MUSIC WHERE "
					+ " MUSIC_NUM = '"+my_music_nums.get(i)+"' ";
			Map<String, Object> row = jdbc.selectOne(sql);
			rows.add(row);
		}
		
		return rows;
		
	}


	
}
