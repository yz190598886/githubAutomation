package Method;


import java.sql.*;

public class jdbc {
    private static Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    String sql = null;

    public void initDb() throws Exception {
        final String URI = "jdbc:mysql://192.168.2.3:3306/fkh_test?user=test&password=111111&useUnicode=true&characterEncoding=UTF-8";
        final String DRIVER = "com.mysql.jdbc.Driver";
        //1、加载数据库驱动
        Class.forName(DRIVER);
        //2、获取数据库连接
        conn = DriverManager.getConnection(URI);
    }

//    public void getBranchIdByLogAccount(String idcard) throws SQLException{

//        //更新
//        sql="update assist_benefit_apply set publicity_end_time=now() where uid='"+getuid(idcard)+"' ";
//        ps=(PreparedStatement) conn.prepareStatement(sql);
//        ps.executeUpdate();

    //    }
    public String getSearchResult(String sql) throws Exception {
        //创建连接
        initDb();
        PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String SearchResult = null;
        while (rs.next()) {
            SearchResult = rs.getString(1);
        }
        return SearchResult;
    }
//        public void startTime() throws SQLException{
//            String time="";
//            sql="SELECT start_time from assist_pai_orders where  item_id=8761 and uid=(SELECT id from `user` where idCard='971513191701170177')  order by start_time DESC LIMIT 1";
//            ps=(PreparedStatement) conn.prepareStatement(sql);
//            rs=ps.executeQuery();
//            while(rs.next()){
//                time=rs.getString(1);
//        }
//                System.out.println(time);
//    }
    public static void main(String[] args) throws Exception {
        String sql="SELECT userId from newdriver where id=60";
        jdbc db=new jdbc();
        System.out.println(db.getSearchResult(sql));
    }
}
