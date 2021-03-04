package com.havaFun.conn;
import com.havaFun.except.ConnectionInitFailException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
@Component
public class ConnectionFactory {
    @Autowired
    DataSource dataSource;
    private static Logger logger = Logger.getLogger(ConnectionFactory.class);
    public  Connection getConnection() {
        try {
            Connection conn = dataSource.getConnection();
            return conn;
        } catch (SQLException e) {
            e.fillInStackTrace();
            logger.error(e.getMessage(),e);
            throw new ConnectionInitFailException("getConnection",e);
        }
    }

    public static void begin(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.setAutoCommit(false);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error(e.getMessage(),e);
            throw new ConnectionInitFailException("begin",e);
        }
    }

    public static void commit(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.commit();
            }

        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error(e.getMessage(),e);
            throw new ConnectionInitFailException("commit",e);
        }
    }

    public static void rollback(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
            }

        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error(e.getMessage(),e);
            throw new ConnectionInitFailException("rollback",e);
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }

        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error(e.getMessage(),e);
            throw new ConnectionInitFailException("close",e);
        }
    }
}
