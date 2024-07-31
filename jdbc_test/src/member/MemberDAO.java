package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberDAO {
	
	final String driver = "org.mariadb.jdbc.Driver";
	final String db_ip = "localhost";
	final String db_port = "3306";
	final String db_name = "jdbc_test";
	final String db_url = 
			"jdbc:mariadb://" + db_ip + ":" + db_port + "/" + db_name;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 1. 회원 정보 입력
	public int insertMember(Member member) {
		int chk = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "INSERT INTO tb_member_info (\r\n"
					 + " member_id,\r\n"
					 + " member_pw,\r\n"
					 + " member_name,\r\n"
					 + " member_birth,\r\n"
					 + " member_phone,\r\n"
					 + " member_email\r\n"
					 + ") VALUES (\r\n"
					 + "	?,?,?,?,?,?\r\n"
					 + ");";
			
			pstmt = conn.prepareStatement(sql);
		  pstmt.setString(1, member.getMemberId());
		  pstmt.setString(2, member.getMemberPw());
		  pstmt.setString(3, member.getMemberName());
		  pstmt.setString(4, member.getMemberBirth());
		  pstmt.setString(5, member.getMemberPhone());
		  pstmt.setString(6, member.getMemberEmail());
			
			// pstmt.executeQuery(); // SELECT문
		  chk = pstmt.executeUpdate(); // INSERT, UPDATE, DELETE
			
		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
			try {
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return chk;
	}
	
	// 2. 회원 정보 수정
	public int updateSearchMember(int memberId, String updatePhone) {
		int resultChk = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "UPDATE tb_member_info\r\n"
								 + "SET member_phone = ?\r\n"
								 + "WHERE member_idx = ?;";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updatePhone);			
			pstmt.setInt(2, memberId);			
			
			// pstmt.executeQuery(); // SELECT문
			resultChk = pstmt.executeUpdate(); // INSERT, UPDATE, DELETE
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;
	}
	
	// 3. 회원 정보 삭제 (회원명)
	public int deleteSearchMemeber(int memberId, String deleteName) {
		int resultChk = 0;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "DELETE FROM tb_member_info\r\n"
								 + "WHERE member_idx = ?;";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			
			
			// pstmt.executeQuery(); // SELECT문
			resultChk = pstmt.executeUpdate(); // INSERT, UPDATE, DELETE
			
		}catch (SQLException e) {
			System.out.println("error :" + e);
		}finally {
			try {
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultChk;
	}
	
	// 4. 회원 정보 출력 (회원명)
	public List<HashMap<String, Object>> printSearchMembers(String name) {
		List<HashMap<String, Object>> memberList = new ArrayList();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT member_idx,\r\n"
								 + "			 member_id,\r\n"
								 + "			 member_pw,\r\n"
								 + "       member_name,\r\n"
								 + "       member_birth,\r\n"
								 + "       member_phone,\r\n"
								 + "       member_email\r\n"
								 + "FROM tb_member_info\r\n"
								 + "WHERE member_name = ?;";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				
				rsMap.put("member_idx", rs.getInt("member_idx"));
				rsMap.put("member_id", rs.getString("member_id"));
				rsMap.put("member_pw", rs.getString("member_pw"));
				rsMap.put("member_name", rs.getString("member_name"));
				rsMap.put("member_birth", rs.getString("member_birth"));
				rsMap.put("member_phone", rs.getString("member_phone"));
				rsMap.put("member_email", rs.getString("member_email"));				
				
				memberList.add(rsMap);

			}
		
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return memberList;
	}
	
	// 5. 회원 전체 출력
	public List<HashMap<String, Object>> printAllMembers() {
		List<HashMap<String, Object>> memberList = new ArrayList();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT member_id,\r\n"
								 + "    member_pw,\r\n"
								 + "    member_name,\r\n"
								 + "    member_birth,\r\n"
								 + "    member_phone,\r\n"
								 + "    member_email\r\n"
								 + "FROM tb_member_info;";
			
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
						
			while(rs.next()) { // rs.next()가 참일 때 -> rs 안에 다음 값이 있는지
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				
				rsMap.put("member_id", rs.getString("member_id"));
				rsMap.put("member_pw", rs.getString("member_pw"));
				rsMap.put("member_name", rs.getString("member_name"));
				rsMap.put("member_birth", rs.getString("member_birth"));
				rsMap.put("member_phone", rs.getString("member_phone"));
				rsMap.put("member_email", rs.getString("member_email"));
				
				memberList.add(rsMap);

			}
		
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return memberList;
	}
	
}
