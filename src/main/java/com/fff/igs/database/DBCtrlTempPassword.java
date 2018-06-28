package com.fff.igs.database;

import com.fff.igs.tools.StringTool;
import com.google.common.base.Stopwatch;

import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

class DBCtrlTempPassword {
    private static final Logger LOGGER = Logger.getLogger(DBCtrlTempPassword.class.getName());

    DBCtrlTempPassword() {
        createTable();
    }

    private void createTable() {

        if(!checkTableExist()) {
            Connection conn = DBConnection.getConnection();

            StringBuffer strCreateTableSQL = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
            strCreateTableSQL.append(DBConstants.TABLE_NAME_TEMPPASSWORD).append(" ( ");
            strCreateTableSQL.append(DBConstants.TEMPPASSWORD_COL_EMAIL).append(" VARCHAR(128) NOT NULL, ");
            strCreateTableSQL.append(DBConstants.TEMPPASSWORD_COL_TEMPPASSWORD).append(" CHAR(8) NOT NULL, ");
            strCreateTableSQL.append("PRIMARY KEY (").append(DBConstants.TEMPPASSWORD_COL_EMAIL).append(") );");

            try {
                conn.createStatement().execute(strCreateTableSQL.toString());
            } catch (SQLException e) {
                LOGGER.warning("SQL erro, " + e.getMessage());
            }
        }
    }

    private boolean checkTableExist() {
        boolean bIsExist = false;

        Connection conn = DBConnection.getConnection();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, DBConstants.TABLE_NAME_TEMPPASSWORD,
                    new String[]{"TABLE"});
            bIsExist = rs.first();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }
        return bIsExist;
    }

    boolean insert(String strEmail, String strTempPassword) {

        boolean bRes = false;
        StringTool stringTool = new StringTool();

        if (!stringTool.checkStringNotNull(strEmail)
                || !stringTool.checkStringNotNull(strTempPassword))
            return false;

        Connection conn = DBConnection.getConnection();
        StringBuilder strCreateTempPasswordSQL = new StringBuilder( "INSERT INTO ");
        strCreateTempPasswordSQL.append(DBConstants.TABLE_NAME_TEMPPASSWORD).append(" (");
        strCreateTempPasswordSQL.append(DBConstants.TEMPPASSWORD_COL_EMAIL).append(",");
        strCreateTempPasswordSQL.append(DBConstants.TEMPPASSWORD_COL_TEMPPASSWORD).append(") VALUES (");
        strCreateTempPasswordSQL.append("\"").append(strEmail).append("\",");
        strCreateTempPasswordSQL.append("\"").append(strTempPassword).append("\");");

        Stopwatch stopwatch = Stopwatch.createStarted();
        try (PreparedStatement statementTempPassword = conn.prepareStatement(strCreateTempPasswordSQL.toString())) {
            bRes = statementTempPassword.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.warning("SQL erro, " + e.getMessage());
        }

        LOGGER.info("insert time (ms):" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return bRes;
    }

    boolean delete(String strEmail) {
        boolean bRes = false;
        StringTool stringTool = new StringTool();

        if (!stringTool.checkStringNotNull(strEmail))
            return false;

        Connection conn = DBConnection.getConnection();
        StringBuilder strDeleteTempPasswordSQL = new StringBuilder("DELETE FROM ");
        strDeleteTempPasswordSQL.append(DBConstants.TABLE_NAME_TEMPPASSWORD).append(" WHERE ");
        strDeleteTempPasswordSQL.append(DBConstants.TEMPPASSWORD_COL_EMAIL).append("=\"").append(strEmail);
        strDeleteTempPasswordSQL.append("\";");

        Stopwatch stopwatch = Stopwatch.createStarted();
        try (PreparedStatement statementDeleteTempPassword = conn.prepareStatement(strDeleteTempPasswordSQL.toString())) {
            bRes = statementDeleteTempPassword.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.warning("SQL erro, " + e.getMessage());
        }

        LOGGER.info("delete time (ms):" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return bRes;
    }

    boolean deleteAll() {
        boolean bRes = false;

        Connection conn = DBConnection.getConnection();
        StringBuilder strDeleteTempPasswordSQL = new StringBuilder("DELETE FROM ");
        strDeleteTempPasswordSQL.append(DBConstants.TABLE_NAME_TEMPPASSWORD);
        strDeleteTempPasswordSQL.append(";");

        Stopwatch stopwatch = Stopwatch.createStarted();
        try (PreparedStatement statementDeleteTempPassword = conn.prepareStatement(strDeleteTempPasswordSQL.toString())) {
            bRes = statementDeleteTempPassword.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.warning("SQL erro, " + e.getMessage());
        }

        LOGGER.info("delete time (ms):" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return bRes;
    }

    boolean isTempPasswordExist(String strEmail) {
        StringTool stringTool = new StringTool();

        if (!stringTool.checkStringNotNull(strEmail))
            return false;

        boolean bIsExist = false;

        Connection conn = DBConnection.getConnection();
        StringBuilder strSelectSQL = new StringBuilder();
        strSelectSQL.append("SELECT ").append(DBConstants.TEMPPASSWORD_COL_EMAIL).append(" FROM ");
        strSelectSQL.append(DBConstants.TABLE_NAME_TEMPPASSWORD).append(" WHERE ");
        strSelectSQL.append(DBConstants.TEMPPASSWORD_COL_EMAIL).append("=\"").append(strEmail).append("\"");
        strSelectSQL.append(";");

        Stopwatch stopwatch = Stopwatch.createStarted();
        try (ResultSet rs = conn.prepareStatement(strSelectSQL.toString()).executeQuery()) {
            stopwatch.stop();
            bIsExist = rs.first();
        } catch (SQLException e) {
            LOGGER.warning("SQL erro, " + e.getMessage());
        }

        LOGGER.info("query time (ms):" + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return bIsExist;
    }

    boolean update(String strEmail, String strTempPassword) {
        boolean bRes = false;
        StringTool stringTool = new StringTool();

        if (!stringTool.checkStringNotNull(strEmail)
                || !stringTool.checkStringNotNull(strTempPassword))
            return false;

        Connection conn = DBConnection.getConnection();
        StringBuilder strUpdateSQL = new StringBuilder("UPDATE ");
        strUpdateSQL.append(DBConstants.TABLE_NAME_TEMPPASSWORD).append(" SET ");
        strUpdateSQL.append(DBConstants.TEMPPASSWORD_COL_TEMPPASSWORD).append("=\"").append(strTempPassword).append("\"");
        strUpdateSQL.append(" WHERE ").append(DBConstants.TEMPPASSWORD_COL_EMAIL).append("=\"").append(strEmail);
        strUpdateSQL.append("\";");

        Stopwatch stopwatch = Stopwatch.createStarted();
        try (PreparedStatement statementUpdateTempPassword = conn.prepareStatement(strUpdateSQL.toString())) {
            bRes = statementUpdateTempPassword.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.warning("SQL erro, " + e.getMessage());
        }

        LOGGER.info("update time (ms):" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return bRes;
    }

    boolean login(String strEmail, String strTempPassword) {
        StringTool stringTool = new StringTool();

        if (!stringTool.checkStringNotNull(strEmail)
                || !stringTool.checkStringNotNull(strTempPassword))
            return false;

        boolean bIsValid = false;

        Connection conn = DBConnection.getConnection();
        StringBuilder strSelectSQL = new StringBuilder();
        strSelectSQL.append("SELECT ").append(DBConstants.TEMPPASSWORD_COL_EMAIL).append(" FROM ");
        strSelectSQL.append(DBConstants.TABLE_NAME_TEMPPASSWORD).append(" WHERE ");
        strSelectSQL.append(DBConstants.TEMPPASSWORD_COL_EMAIL).append("=\"").append(strEmail).append("\"");
        strSelectSQL.append(" AND ");
        strSelectSQL.append(DBConstants.TEMPPASSWORD_COL_TEMPPASSWORD).append("=\"").append(strTempPassword).append("\"");
        strSelectSQL.append(";");

        Stopwatch stopwatch = Stopwatch.createStarted();
        try (ResultSet rs = conn.prepareStatement(strSelectSQL.toString()).executeQuery()) {
            stopwatch.stop();
            bIsValid = rs.first();
        } catch (SQLException e) {
            LOGGER.warning("SQL erro, " + e.getMessage());
        }

        LOGGER.info("query time (ms):" + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return bIsValid;
    }
}
