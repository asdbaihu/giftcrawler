package com.hnayyc.giftcrawler.utils;

import java.lang.reflect.Field;
import java.sql.*;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtils {

	 //数据库用户名  
    private static final String USERNAME = "root";  
    //数据库密码  
    private static final String PASSWORD = "12345678";  
    //驱动信息   
    private static final String DRIVER = "com.mysql.jdbc.Driver";  
    //数据库地址  
    private static final String URL = "jdbc:mysql://localhost:3306/coffee";
    
    private Connection connection;  
    private PreparedStatement pstmt;  
    private ResultSet resultSet;  
    
    
    public JdbcUtils() {  
        try{  
            Class.forName(DRIVER);  
            getConnection();
            System.out.println("数据库连接成功！");  
        }catch(Exception e){  
        }  
    }  
      
    /** 
     * get connection 
     * @return 
     */  
    public Connection getConnection(){  
        try {  
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return connection;  
    }  

    public int excuteSql(String sql) {

        int num = 0;
        try {
            Statement statement =  connection.createStatement();
            num = statement.executeUpdate(sql);
            System.out.println("SQL : " + sql);
            System.out.println("成功操作数据库，影响条数：" + num);
        } catch(Exception e) {
            // 处理异常：回滚事务后抛出异常
            e.printStackTrace();
            System.out.println("事务回滚");
        } finally {
            releaseConn();
        }
        return num;
    }
    /** 
     * insert & delete & update 
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public boolean updateByPreparedStatement(String sql, List<Object>params)throws SQLException{  
        boolean flag = false;  
        int result = -1;  
        pstmt = connection.prepareStatement(sql);  
        int index = 1;  
        if(params != null && !params.isEmpty()){  
            for(int i=0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        result = pstmt.executeUpdate();  
        flag = result > 0 ? true : false;  
        return flag;  
    }  
  
    /** 
     * select one record
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{  
        Map<String, Object> map = new HashMap<String, Object>();  
        int index  = 1;  
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i=0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();//返回查询结果  
        ResultSetMetaData metaData = resultSet.getMetaData();  
        int col_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            for(int i=0; i<col_len; i++ ){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                map.put(cols_name, cols_value);  
            }  
        }  
        return map;  
    }  
  
    /**
     * select several records
     * @param sql 
     * @param params 
     * @return 
     * @throws SQLException 
     */  
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException{  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        int index = 1;  
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            Map<String, Object> map = new HashMap<String, Object>();  
            for(int i=0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                map.put(cols_name, cols_value);  
            }  
            list.add(map);  
        }  
  
        return list;  
    }  
  
    /**
     * select one record by reflect 
     * @param sql 
     * @param params 
     * @param cls 
     * @return 
     * @throws Exception 
     */  
    public <T> T findSimpleRefResult(String sql, List<Object> params,  
            Class<T> cls )throws Exception{  
        T resultObject = null;  
        int index = 1;  
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData  = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            //通过反射机制创建一个实例  
            resultObject = cls.newInstance();  
            for(int i = 0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                Field field = cls.getDeclaredField(cols_name);  
                field.setAccessible(true); //打开javabean的访问权限  
                field.set(resultObject, cols_value);  
            }  
        }  
        return resultObject;  
  
    }  
  
    /**
     * select several records by records
     * @param sql  
     * @param params 
     * @param cls 
     * @return 
     * @throws Exception 
     */  
    public <T> List<T> findMoreRefResult(String sql, List<Object> params,  
            Class<T> cls )throws Exception {  
        List<T> list = new ArrayList<T>();  
        int index = 1;  
        pstmt = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData  = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            //通过反射机制创建一个实例  
            T resultObject = cls.newInstance();  
            for(int i = 0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                Field field = cls.getDeclaredField(cols_name);  
                field.setAccessible(true); //打开javabean的访问权限  
                field.set(resultObject, cols_value);  
            }  
            list.add(resultObject);  
        }  
        return list;  
    }  
  
    /** 
     * close connection 
     */  
    public void releaseConn(){  
        if(resultSet != null){  
            try{  
                resultSet.close();  
            }catch(SQLException e){  
                e.printStackTrace();  
            }  
        }  
    }  
}
