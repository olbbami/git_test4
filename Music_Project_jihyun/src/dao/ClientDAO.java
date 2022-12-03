package dao;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class ClientDAO {
	
	private static ClientDAO instance = null;
	private ClientDAO() {}
	
	public static ClientDAO getInstance() {
		if(instance == null) instance = new ClientDAO();
		return instance;		
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();	
	
	public Map<String, Object> login(String id, String pwd) {
		return jdbc.selectOne(" SELECT * FROM CLIENT "
				+ " WHERE CLI_ID = '" + id 
				+ "' AND CLI_PW ='" + pwd + "'");
	}
	
	public Map<String, Object> get_perfer_musics(){
		Map<String, Object> map  = new HashMap<>();
		String sql = "SELECT * FROM CLIENT "
				+ " WHERE CLI_ID = '"+Controller.login_id+"' ";
		Map<String, Object> row = jdbc.selectOne(sql);
		map.put("선호장르1", row.get("CLI_GENRE1"));
		map.put("선호장르2", row.get("CLI_GENRE2"));
		return map;
	}
	
	public Map<String, Object> get_my_id_row(){
		String sql = "SELECT * FROM CLIENT "
				+ " WHERE CLI_ID = '"+Controller.login_id+"' ";
		Map<String, Object> row = jdbc.selectOne(sql);
		return row;
	}
	

}
