package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDAO {
	final String driver = "org.mariadb.jdbc.Driver";
	final String db_ip = "localhost";
	final String db_port = "3306";
	final String db_name = "jdbc_test";
	final String db_url = 
			"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 학생 정보 등록
	public int insertStudent(HashMap<String, Object> paramMap) {
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
			String sql = "INSERT INTO tb_student_info (\r\n"
								 + "  student_name,\r\n"
								 + "  student_grade,\r\n"
								 + "  student_school,\r\n"
								 + "  student_addr,\r\n"
								 + "  student_phone\r\n"
								 + "  ) VALUES (?,?,?,?,?);";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramMap.get("studentName").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("studentGrade").toString()));
			pstmt.setString(3, paramMap.get("studentSchool").toString());
			pstmt.setString(4, paramMap.get("studentAddr").toString());
			pstmt.setString(5, paramMap.get("studentPhone").toString());
		  
		  resultChk = pstmt.executeUpdate(); 
			
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
	
	// 학생 성적 등록
	public int insertScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		return resultChk;
	}
		
	// 전체 학생 조회
	public List<HashMap<String, Object>> printAllStudent(){
		List<HashMap<String, Object>> studentList = new ArrayList();
		
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
			String sql = "SELECT student_name,\r\n"
								 + "			 student_grade,\r\n"
								 + "			 student_school,\r\n"
								 + "			 student_addr,\r\n"
								 + "			 student_phone\r\n"
								 + "FROM tb_student_info;";
			
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();
						
			while(rs.next()) { // rs.next()가 참일 때 -> rs 안에 다음 값이 있는지
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				
				rsMap.put("studentName", rs.getString("student_name"));
				rsMap.put("studentGrade", rs.getString("student_grade"));
				rsMap.put("studentSchool", rs.getString("student_school"));
				rsMap.put("studentAddr", rs.getString("student_addr"));
				rsMap.put("studentPhone", rs.getString("student_phone"));				
				
				studentList.add(rsMap);

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
		
		return studentList;
	}
	
	// 학생이름으로 학생 정보 조회
	public List<HashMap<String, Object>> printSearchStudent(String studentName){
		List<HashMap<String, Object>> studentList = new ArrayList();
		
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
			String sql = "SELECT student_idx,\r\n"
								 + "			 student_name,\r\n"
								 + "			 student_grade,\r\n"
								 + "			 student_school,\r\n"
								 + "			 student_addr,\r\n"
								 + "			 student_phone\r\n"
								 + "FROM tb_student_info\r\n"
								 + "WHERE student_name = ?;";
			
			pstmt = conn.prepareStatement(sql);		
			pstmt.setString(1, studentName);
			
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				
				rsMap.put("studentIdx", rs.getString("student_idx"));
				rsMap.put("studentName", rs.getString("student_name"));
				rsMap.put("studentGrade", rs.getString("student_grade"));
				rsMap.put("studentSchool", rs.getString("student_school"));
				rsMap.put("studentAddr", rs.getString("student_addr"));
				rsMap.put("studentPhone", rs.getString("student_phone"));				
				
				studentList.add(rsMap);

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
		
		return studentList;
	}
	
	
	
	// 학생 정보 수정
	public int updateStudent(HashMap<String, Object> paramMap) {
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
			String sql = "";
      int updateChoice = Integer.parseInt(paramMap.get("updateChoice").toString());
			
      switch(updateChoice) {
			case 1 :
				sql = "UPDATE tb_student_info\r\n"
						+ "SET student_name = ?\r\n"
						+ "WHERE student_idx = ?;";					
				break;
				
			case 2 : 
				sql = "UPDATE tb_student_info\r\n"
						+ "SET student_school = ?\r\n"
						+ "WHERE student_idx = ?;";					          
				break;
				
			case 3 :
				sql = "UPDATE tb_student_info\r\n"
						+ "SET student_grade = ?\r\n"
						+ "WHERE student_idx = ?;";					
				break;
				
			case 4 :
				sql = "UPDATE tb_student_info\r\n"
						+ "SET student_phone = ?\r\n"
						+ "WHERE student_idx = ?;";					
				break;
				
			case 5 :
				sql = "UPDATE tb_student_info\r\n"
						+ "SET student_addr = ?\r\n"
						+ "WHERE student_idx = ?;";					
				break;
				
			default :
				System.out.println("학생 정보 수정에 실패하였습니다.");
		}	
			
			pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, paramMap.get("updateContents").toString());
			pstmt.setInt(2, Integer.parseInt(paramMap.get("studentIdx").toString()));
			
			resultChk = pstmt.executeUpdate();
			
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
	
	// 학생 성적 수정
	public int updateScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		return resultChk;
	}
	
	// 학생 정보 삭제
	public int deleteStudent(int studentIdx) {
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
			String sql = "DELETE FROM tb_student_info\r\n"
								 + "WHERE student_idx = ?;";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentIdx);
			
			
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
	
	// 학생 성적 삭제
	public int deleteScore(HashMap<String, Object> paramMap) {
		int resultChk = 0;
		
		return resultChk;
	}
}
