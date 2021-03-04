package com.havaFun.handler;
import com.havaFun.conn.ConnectionFactory;
import com.havaFun.except.JdbcException;
import com.havaFun.meta.DBPage;
import com.havaFun.meta.DataRow;
import com.havaFun.transaction.SessionParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Component
public class DatasourceOperateHandler {
    private static Logger logger = Logger.getLogger(DatasourceOperateHandler.class);
    @Autowired
    ConnectionFactory connectionFactory;

    /**
     * 插入一条数据,但并不commit,属于一个事务里的单元会话
     *
     * @param tableName
     * @param data
     * @return
     */
    public SessionParams getInsertSql(String tableName, DataRow data) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into " + tableName + "(");
        String interrogationStr = "";
        int i = 0;
        ArrayList valueList = new ArrayList();
        Iterator iter = data.keySet().iterator();

        while (iter.hasNext()) {
            ++i;
            String key = (String) iter.next();
            valueList.add(data.get(key));
            if (i < data.size()) {
                sqlBuffer.append(key + ",");
                interrogationStr = interrogationStr + " ?,";
            } else {
                sqlBuffer.append(key);
                interrogationStr = interrogationStr + "?";
            }
        }

        sqlBuffer.append(") values (");
        sqlBuffer.append(interrogationStr);
        sqlBuffer.append(") ");
        return new SessionParams(sqlBuffer.toString(), valueList.toArray());
    }

    /**
     * 往标准插入一条数据,mysql可以用自增长主键,oracle可以创建一个sequence作为主键
     *
     * @param tableName
     * @param data
     */
    public void insert(@NotNull String tableName, DataRow data) {
        if (data == null || data.size() == 0) throw new JdbcException("empty dataset");
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into " + tableName + "(");
        StringBuffer interrogationStr = new StringBuffer();
        int i = 0;
        List<String> valueList = new ArrayList<String>();
        for (Iterator<String> it = data.keySet().iterator(); it.hasNext(); ++i) {
            String key = it.next();
            String value = data.getString(key);
            valueList.add(value);
            if (i < data.size()) {
                sqlBuffer.append(key + ",");
                interrogationStr.append(" ?,");
            } else {
                sqlBuffer.append(key);
                interrogationStr.append(" ?");
            }
        }
        sqlBuffer.append(") values (");
        sqlBuffer.append(interrogationStr);
        sqlBuffer.append(") ");
        sqlBuffer.append(") values (");
        sqlBuffer.append(interrogationStr);
        sqlBuffer.append(") ");
        this.update(sqlBuffer.toString(), valueList.toArray());

    }

    public void update(String tableName, DataRow data, String identify, Object identifyValue) {
        this.update(tableName, data, new String[]{identify}, new Object[]{identifyValue});

    }

    public void update(String tableName, DataRow data, String[] identifys, Object[] identifyValues) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("update " + tableName + " set ");
        int i = 0;
        ArrayList valueList = new ArrayList();

        int k;
        for (k = 0; k < identifys.length; ++k) {
            data.remove(identifys[k]);
        }

        Iterator iter = data.keySet().iterator();

        while (iter.hasNext()) {
            ++i;
            String key = (String) iter.next();
            valueList.add(data.get(key));
            if (i < data.size()) {
                sqlBuffer.append(key + "=?,");
            } else {
                sqlBuffer.append(key + "=?");
            }
        }

        for (k = 0; k < identifys.length; ++k) {
            sqlBuffer.append((k == 0 ? " where " : " and ") + identifys[k] + "=?");
            valueList.add(identifyValues[k]);
        }

        this.update(sqlBuffer.toString(), valueList.toArray());
    }

    private void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception var3) {
            logger.error("", var3);
        }

    }

    public void batchUpdateSql(@NotNull String[] sqlArray, @NotNull List<Object[]> argList) {
        if (sqlArray.length != argList.size() || sqlArray.length == 0 || argList.size() == 0)
            throw new JdbcException("error sql size");
        Connection conn = connectionFactory.getConnection();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        try {
            for (int i = 0; i < sqlArray.length; i++) {
                String sql = sqlArray[i];
                PreparedStatement pstmt = conn.prepareStatement(sql);
                Object[] args = argList.get(i);
                if (args != null) {
                    for (int j = 1; j <= args.length; ++j) {
                        pstmt.setObject(j, args[j - 1]);
                    }
                }
                pstmt.executeUpdate();
                closeStatement(pstmt);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new JdbcException("", e);
        } finally {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void delete(String tableName, String identify, Object identifyValue) {
        String sql = "delete from " + tableName + " where " + identify + "=?";
        this.update(sql, new Object[]{identifyValue});

    }

    public int update(String sql) {
        return this.update(sql, (Object[])null);
    }

    public int update(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        Connection conn = connectionFactory.getConnection();
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long beginTime = System.currentTimeMillis();
            pstmt = conn.prepareStatement(sql);
            int result;
            if (args != null) {
                for(result = 1; result <= args.length; ++result) {
                    pstmt.setObject(result, args[result - 1]);
                }
            }
            result = pstmt.executeUpdate();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            if (time > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + time + " millisecond]");
            }
            return result;
        } catch (SQLException e) {
            e.fillInStackTrace();
            throw new JdbcException("",e);
        } finally {
            this.closeStatement(pstmt);
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public int[] batchUpdate(String[] sqlArray) {
        Statement stmt = null;
        Connection conn = connectionFactory.getConnection();
        try {
            logger.info("开始执行");
            long beginTime = System.currentTimeMillis();
            stmt = conn.createStatement();

            for(int i = 0; i < sqlArray.length; ++i) {
                logger.info("sql= " + sqlArray[i]);
                stmt.addBatch(sqlArray[i]);
            }

            int[] result = stmt.executeBatch();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            return result;
        } catch (SQLException e) {
            throw new JdbcException("", e);
        } finally {
            this.closeStatement(stmt);
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public int queryInt(String sql) {
        return this.queryInt(sql, (Object[])null);
    }


    public int queryInt(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        Connection conn = connectionFactory.getConnection();
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long beginTime = System.currentTimeMillis();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                for(int i = 1; i <= args.length; ++i) {
                    pstmt.setObject(i, args[i - 1]);
                }
            }

            rs = pstmt.executeQuery();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            if (time > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + time + " millisecond]");
            }

            if (rs.next()) {
                result = rs.getInt(1);
            }

            return result;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage(), e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public int[] queryIntArray(String sql,Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = connectionFactory.getConnection();
        int[] is;
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long beginTime = System.currentTimeMillis();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                for(int i = 1; i <= args.length; ++i) {
                    pstmt.setObject(i, args[i - 1]);
                }
            }

            rs = pstmt.executeQuery();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            if (time > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + time + " millisecond]");
            }

            ArrayList resultList = new ArrayList();

            while(rs.next()) {
                int value = rs.getInt(1);
                resultList.add(new Integer(value));
            }
            int[] result = new int[resultList.size()];
            if (resultList.size() > 0) {
                result = new int[resultList.size()];

                for(int i = 0; i < resultList.size(); ++i) {
                    result[i] = (Integer)resultList.get(i);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage(), e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public int[] queryIntArray(String sql) {
        return queryIntArray(sql,null);
    }

    public long queryLong(String sql) {
        return this.queryLong(sql, (Object[])null);
    }


    public long queryLong(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long result = 0L;
        Connection conn = connectionFactory.getConnection();
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long beginTime = System.currentTimeMillis();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                for(int i = 1; i <= args.length; ++i) {
                    pstmt.setObject(i, args[i - 1]);
                }
            }
            rs = pstmt.executeQuery();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            if (time > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + time + " millisecond]");
            }

            if (rs.next()) {
                result = rs.getLong(1);
            }
            return result;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage(), e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public long[] queryLongArray(String sql) {
        return queryLongArray(sql,null);
    }

    public long[] queryLongArray(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = connectionFactory.getConnection();
        long[] var13;
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long beginTime = System.currentTimeMillis();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                for(int i = 1; i <= args.length; ++i) {
                    pstmt.setObject(i, args[i - 1]);
                }
            }

            rs = pstmt.executeQuery();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            if (time > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + time + " millisecond]");
            }
            ArrayList resultList = new ArrayList();
            while(rs.next()) {
                long value = rs.getLong(1);
                resultList.add(new Long(value));
            }

            long[] result = new long[resultList.size()];
            if (resultList.size() > 0) {
                for(int i = 0; i < resultList.size(); ++i) {
                    result[i] = (Long)resultList.get(i);
                }
            }
            return result;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage(), e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public String queryString(String sql) {
        return queryString(sql,null);
    }

    public String queryString(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = "";
        Connection conn = connectionFactory.getConnection();
        String var11;
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long beginTime = System.currentTimeMillis();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                for(int i = 1; i <= args.length; ++i) {
                    pstmt.setObject(i, args[i - 1]);
                }
            }

            rs = pstmt.executeQuery();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            if (time > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + time + " millisecond]");
            }

            if (rs.next()) {
                result = rs.getString(1);
            }
            return result;
        } catch (SQLException e) {
            throw new JdbcException("", e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public String[] queryStringArray(String sql) {
        return queryStringArray(sql,null);
    }

    public String[] queryStringArray(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = connectionFactory.getConnection();
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long beginTime = System.currentTimeMillis();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                for(int i = 1; i <= args.length; ++i) {
                    pstmt.setObject(i, args[i - 1]);
                }
            }

            rs = pstmt.executeQuery();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            if (time > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + time + " millisecond]");
            }

            ArrayList resultList = new ArrayList();

            while(rs.next()) {
                String value = rs.getString(1);
                resultList.add(value);
            }

            String[] result = new String[resultList.size()];
            if (resultList.size() > 0) {

                for(int i = 0; i < resultList.size(); ++i) {
                    result[i] = (String)resultList.get(i);
                }
            }

            return result;
        } catch (SQLException var16) {
            throw new JdbcException("", var16);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public DataRow queryMap(String sql) {
        return queryMap(sql,null);
    }

    public DataRow queryMap(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DataRow result = null;
        Connection conn = connectionFactory.getConnection();
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long beginTime = System.currentTimeMillis();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                for(int i = 1; i <= args.length; ++i) {
                    pstmt.setObject(i, args[i - 1]);
                }
            }
            rs = pstmt.executeQuery();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            if (time > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + time + " millisecond]");
            }

            ResultSetMetaData metaData = rs.getMetaData();
            if (rs.next()) {
                result = this.toDataRow(rs, metaData);
            }
            return result;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage(), e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    private DataRow toDataRow(ResultSet rs, ResultSetMetaData metaData) throws SQLException {
        DataRow dataRow = new DataRow();
        int count = metaData.getColumnCount();

        for(int i = 0; i < count; ++i) {
            String fieldName = metaData.getColumnName(i + 1);
            Object value = rs.getObject(fieldName);
            if (value instanceof Clob) {
                value = rs.getString(fieldName);
            } else if (value instanceof Blob) {
                value = rs.getBytes(fieldName);
            } else if (value instanceof Date) {
                value = rs.getTimestamp(fieldName);
            }

            dataRow.set(fieldName.toLowerCase(), value);
        }

        return dataRow;
    }

    public List<DataRow> query(String sql) {
        return query(sql,null);
    }

    public List<DataRow> query(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DataRow> list = new ArrayList<DataRow>();
        Connection conn = connectionFactory.getConnection();
        try {
            logger.info("开始执行 [sql= " + sql + "]");
            long beginTime = System.currentTimeMillis();
            pstmt = conn.prepareStatement(sql);
            if (args != null) {
                for(int i = 1; i <= args.length; ++i) {
                    pstmt.setObject(i, args[i - 1]);
                }
            }

            rs = pstmt.executeQuery();
            long time = System.currentTimeMillis() - beginTime;
            logger.info("执行完成 [time=" + time + " millisecond]");
            if (time > 1000L) {
                logger.warn("执行 [sql= " + sql + "]时间过长，当前执行时间[time=" + time + " millisecond]");
            }

            ResultSetMetaData metaData = rs.getMetaData();

            while(rs.next()) {
                list.add(this.toDataRow(rs, metaData));
            }
            return list;
        } catch (SQLException e) {
            throw new JdbcException(e.getMessage(), e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public List query(String sql, int rows) {
        return this.query(sql, (Object[])null, rows);
    }

    public List query(String sql, Object[] args, int rows) {
        return this.query(sql, args, 0, rows);
    }

    public List query(String sql, int startRow, int rows) {
        return this.query(sql, (Object[])null, startRow, rows);
    }

    /**
     * 这里只列举mysql,不同数据库分页不同,注解示例为oracle
     * @param sql
     * @param args
     * @param startRow
     * @param rows
     * sqlBuffer = new StringBuffer();
     *             sqlBuffer.append("select * from ( select row_.*, rownum rownum_ from ( ");
     *             sqlBuffer.append(sql);
     *             sqlBuffer.append(" ) row_ where rownum <= " + (startRow + rows) + ") where rownum_ > " + startRow);
     * @return
     */
    public List<DataRow> query(String sql, Object[] args, int startRow, int rows){
        StringBuffer sqlBuffer = new StringBuffer(sql);
        sqlBuffer.append(" limit " + startRow + "," + rows);
        return query(sql,args);
    }
//    public List<DataRow> query(String sql, Object[] args, int startRow, int rows) {
//        StringBuffer sqlBuffer = new StringBuffer();
//        if (this.databaseType == DatabaseType.ORACLE) {
//            sqlBuffer = new StringBuffer();
//            sqlBuffer.append("select * from ( select row_.*, rownum rownum_ from ( ");
//            sqlBuffer.append(sql);
//            sqlBuffer.append(" ) row_ where rownum <= " + (startRow + rows) + ") where rownum_ > " + startRow);
//            return this.queryFromSpecialDB(sqlBuffer.toString(), args);
//        } else if (this.databaseType == DatabaseType.MYSQL) {
//            sqlBuffer = new StringBuffer();
//            sqlBuffer.append(sql);
//            sqlBuffer.append(" limit " + startRow + "," + rows);
//            return this.queryFromSpecialDB(sqlBuffer.toString(), args);
//        } else if (this.databaseType == DatabaseType.MSSQL) {
//            return this.queryFromOtherDB(sql, args, startRow, rows);
//        } else if (this.databaseType == DatabaseType.DB2) {
//            String temp = sql.toUpperCase();
//            int fromIdx = temp.lastIndexOf(" FROM ");
//            int orderIdx = temp.lastIndexOf(" ORDER ");
//            String sFrom = "";
//            String sOrderBy = "";
//            if (orderIdx == -1) {
//                sFrom = sql.substring(fromIdx, sql.length());
//            } else {
//                sFrom = sql.substring(fromIdx, orderIdx);
//                sOrderBy = sql.substring(orderIdx);
//            }
//
//            String sSelect = sql.substring(0, fromIdx);
//            int iEnd = startRow + rows;
//            sqlBuffer.append("SELECT * FROM (" + sSelect + ", ROW_NUMBER() OVER(" + sOrderBy + ") AS rn " + sFrom + ") originTable WHERE rn BETWEEN " + startRow + " AND " + iEnd);
//            return this.queryFromSpecialDB(sqlBuffer.toString(), args);
//        } else if (this.databaseType == DatabaseType.POSTGRESQL) {
//            sqlBuffer = new StringBuffer();
//            sqlBuffer.append(sql);
//            sqlBuffer.append(" limit " + rows + " offset " + (startRow - 1));
//            return this.queryFromSpecialDB(sqlBuffer.toString(), args);
//        } else {
//            return this.queryFromOtherDB(sql, args, startRow, rows);
//        }
//    }

    public DBPage queryPage(String sql, int curPage, int numPerPage) {
        return this.queryPage(sql, (Object[])null, curPage, numPerPage);
    }

    public DBPage queryPage(String sql, Object[] args, int curPage, int numPerPage) {
        String temp = sql;
        int orderIdx = sql.toUpperCase().lastIndexOf(" ORDER ");
        if (orderIdx != -1) {
            temp = sql.substring(0, orderIdx);
        }
        StringBuffer totalSQL = new StringBuffer(" SELECT count(1) FROM ( ");
        totalSQL.append(temp);
        totalSQL.append(" ) totalTable ");
        int totalRows = this.queryInt(totalSQL.toString(), args);
        DBPage page = new DBPage(curPage, numPerPage);
        page.setTotalRows(totalRows);
        int startIndex = page.getStartIndex();
        int endIndex = page.getLastIndex();
        List list = this.query(sql, args, startIndex, endIndex - startIndex);
        page.setData(list);
        return page;
    }
}
