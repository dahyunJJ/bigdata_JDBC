package jdbc_00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class jdbc_test2 {

	public static void main(String[] args) {
		// 우송고등학교에 재학중인 학생정보를 출력하시오.
		// TODO Auto-generated method stub
				final String driver = "org.mariadb.jdbc.Driver";
				final String db_ip = "localhost";
				final String db_port = "3306";
				final String db_name = "student_test";
				final String db_url = 
						"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;
				
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
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
					String sql = "SELECT sh.school_id,  sh.school_name, sh.school_area, st.student_name, if(st.student_graduate_yn = \'N\', \'재학중\', \'졸업생\') as '졸업여부'"
										 + "FROM tb_student_info st "
										 + "INNER JOIN tb_school_info sh "
										 + "ON st.school_id = sh.school_id "
										 + "AND st.student_graduate_yn = 'N' "
								//	 + "WHERE sh.school_name = '우송고등학교'";
										 + "WHERE sh.school_name = ?";
					
					pstmt = conn.prepareStatement(sql);
				  pstmt.setString(1, "우송고등학교");
					
					rs = pstmt.executeQuery();
					List<HashMap<String, Object>> list = new ArrayList<>();
					
					while(rs.next()) {
						HashMap<String, Object> rsMap = new HashMap<String, Object>();
						
						rsMap.put("schoolId", rs.getInt(1));
						rsMap.put("schoolName", rs.getString(2));
						rsMap.put("schoolArea", rs.getString(3));
						rsMap.put("studentName", rs.getString(4));
						rsMap.put("graduateYN", rs.getString(5));
						
						list.add(rsMap);

					}
					System.out.println("학교ID\t학교이름\t\t학교지역\t학생이름\t졸업여부");
					for(int i = 0; i<list.size(); i++) {
						System.out.print(list.get(i).get("schoolId").toString() + "\t");
						System.out.print(list.get(i).get("schoolName").toString() + "\t");
						System.out.print(list.get(i).get("schoolArea").toString() + "\t");
						System.out.print(list.get(i).get("studentName").toString() + "\t");
						System.out.println(list.get(i).get("graduateYN").toString());
						
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
	}
	
}
