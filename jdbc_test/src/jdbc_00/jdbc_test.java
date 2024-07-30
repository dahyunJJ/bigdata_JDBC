package jdbc_00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class jdbc_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String driver = "org.mariadb.jdbc.Driver";
		final String db_ip = "localhost";
		final String db_port = "3306"; // mariadb는 mysql 사용 가능하므로, 이미 만들어진 데이터베이스 사용을 위해 mysql 포트 번호로 설정
		final String db_name = "student_test";
		final String db_url = "jdbc:mariadb://" + db_ip + ":" + db_port + "/" + db_name;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// DB 접속
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");

			if (conn != null) {
				System.out.println("접속성공");
			}

		} catch (ClassNotFoundException e) {

			System.out.println("드라이버 로드 실패");
			e.printStackTrace(); // 발생한 e 변수의 로그를 출력해주는 메소드

		} catch (SQLException e) {

			System.out.println("DB접속 실패");
			e.printStackTrace();

		}
		
		// 쿼리 실행
		try {
			String sql = "SELECT school_id, school_name, school_area " // 쿼리 문장 마다 띄워쓰기 유의하기!!
								 + "FROM tb_school_info ";
			System.out.println(sql);
			
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
//			int schoolId = 0;
//			String schoolName = null;
//			String schoolArea = null;
			
			// 여러 건의 데이터 조회
			List<HashMap<String, Object>> list = new ArrayList<>();
			
			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("schoolId", rs.getInt(1));
				rsMap.put("schoolName", rs.getString(2));
				rsMap.put("schoolArea", rs.getString(3));
				list.add(rsMap);
				
//				schoolId = rs.getInt(1);
//				schoolName = rs.getString("school_name");
//				schoolArea = rs.getString(3);
			}
			
			System.out.println("학교ID\t학교이름\t학교지역");
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).get("schoolId").toString() + "\t"
													+ list.get(i).get("schoolName").toString() + "\t"
													+ list.get(i).get("schoolArea").toString());
			}
			
//			System.out.println("schoolId : " + schoolId);
//			System.out.println("schoolName : " + schoolName);
//			System.out.println("schoolArea : " + schoolArea);

		} catch (SQLException e) {
			
			System.out.println("error :" + e);
			
		} finally {
			// 데이터베이스 닫아주기
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
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}

	}

}
