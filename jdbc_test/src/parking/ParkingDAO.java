package parking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingDAO {
	
	final String driver = "org.mariadb.jdbc.Driver";
	final String db_ip = "localhost";
	final String db_port = "3306";
	final String db_name = "jdbc_test";
	final String db_url = 
			"jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public int insertParking(int location, String carNum) {
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
			String sql = "UPDATE tb_parking_info\n"
								 + "SET parking_car_number = ?,\n"
								 + "		parking_yn = \"Y\",\n"
								 + "    parking_date = current_timestamp()\n"
								 + "WHERE parking_number  = ?;";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, carNum);	
			pstmt.setInt(2, location);
			
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

	public boolean deleteParking(int location) {		
		boolean flag = false;
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
					String sql = "UPDATE tb_parking_info\n"
										 + "SET parking_car_number = NULL,\n"
										 + "		parking_yn = \"N\"\n"
										 + "WHERE parking_number  = ?;";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, location);
							
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
		
		if (resultChk > 0) {
      flag = true;
		} 
		
		return flag;
	}

	public List<HashMap<String, Object>> selectParkingSpace() {
		
		List<HashMap<String, Object>> parkingList = new ArrayList();
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
			String sql = "SELECT  parking_idx AS parkingIdx,\r\n"
					+ "		parking_number AS parkingNumber,\r\n"
					+ "		parking_location_x AS parkingX, \r\n"
					+ "		parking_location_y AS parkingY,\r\n"
					+ "        parking_yn AS parkingYn,\r\n"
					+ "        parking_car_number AS parkingCarNumber\r\n"
					+ "FROM tb_parking_info\n"
					+ "ORDER BY parking_location_x, parking_location_y;";

			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				HashMap<String, Object> parkingMap = new HashMap<String, Object>();
				parkingMap.put("parkingIdx", rs.getInt("parkingIdx"));
				parkingMap.put("parkingNumber", rs.getInt("parkingNumber"));
				parkingMap.put("parkingX", rs.getInt("parkingX"));
				parkingMap.put("parkingY", rs.getInt("parkingY"));
				parkingMap.put("parkingYn", rs.getString("parkingYn"));
				parkingMap.put("parkingCarNumber", rs.getString("parkingCarNumber"));
				
				parkingList.add(parkingMap);
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
		
		return parkingList;

	}
	
	public HashMap<String, Object> selectParkingInfo(int location) {
		// 1건에 대한 데이터를 받을 것이기 때문에 HashMap 사용하면 된다.
		HashMap<String, Object> parkingMap = new HashMap<String, Object>();
		
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
			String sql = "SELECT parking_idx as parkingIdx,\n"
								 + "			 parking_number as parkingNumber, \n"
								 + "			 parking_yn as parkingYn,\n"
								 + "       parking_car_number as parkingCarNumber\n"
								 + "FROM tb_parking_info\n"
								 + "WHERE parking_number = ?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, location);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {				
				parkingMap.put("parkingIdx", rs.getInt("parkingIdx"));
				parkingMap.put("parkingNumber", rs.getInt("parkingNumber"));
				parkingMap.put("parkingYn", rs.getString("parkingYn"));
				parkingMap.put("parkingCarNumber", rs.getString("parkingCarNumber"));				
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
		
		return parkingMap;
	}
	
	// 입출차 히스토리
	public int insertParkingHistory(int location, String carNum, String parkingType) {
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
			String sql = "INSERT INTO tb_parking_history (\n"
								 + "	parking_location,\n"
								 + "  parking_car_number,\n"
								 + "  parking_type,\n"
								 + "  parking_time\n"
								 + ") VALUES \n"
								 + "(?,?,?, current_timestamp());";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, location);	
			pstmt.setString(2, carNum);
			pstmt.setString(3, parkingType);
			
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

}
