package hello.jdbc.repository;


import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryVO {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con=null;
        //SQL injection 문제는 PreparedStatement를 사용해서 방어
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            int count = pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw new RuntimeException(e);
        }finally {
            close(con,pstmt,null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else{
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally {
            close(con, pstmt, rs);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs){
        if (rs != null) {
            try {
                rs.close(); //Exception
            } catch (SQLException e) {
                log.info("error",e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close(); //Exception
            } catch (SQLException e) {
                log.info("error",e);
            }
        }
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error",e);
            }
        }
    }
    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
