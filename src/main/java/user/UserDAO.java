package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	private Connection conn; // 자바와 데이터베이스 연결
	private PreparedStatement pstmt; // 쿼리문 대기 및 설정
	private ResultSet rs; // 결과값 받아오기
	
	// 기본 생성자
	// UserDAO가 실행되면 자동으로 생성되는 부분
	// 메소드마다 반복되는 코드를 이곳에 넣으면 코드가 간소화된다
	public UserDAO() {
		try {
			String dbURL = "jdbc:mariadb://localhost:3307/board";
			String dbID = "root";
			String dbPassword = "root1234";
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 로그인 영역
	public int login(String userID, String userPassword) {
		String sql = "SELECT userPassword FROM user WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(sql); // sql 쿼리문을 대기
			pstmt.setString(1, userID); // 첫번째 '?'에 매개변수로 받아온 'userId'를 대입
			rs = pstmt.executeQuery(); // 쿼리를 실행한 결과를 rs에 저장
			if (rs.next()) {
				if (rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				} else 
					return 0; // 로그인 실패 (비밀번호 불일치)
			}
			return -1; // 로그인 실패 (아이디 미존재)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 오류
	}

}
