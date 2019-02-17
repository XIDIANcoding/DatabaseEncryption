package DAO;

import translatinPlug.TranslationPlug;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: DatabaseEncryption
 * @description:
 * @author: WYY
 * @create: 2018/12/26 2:02
 **/
public class insertDAO {
    public void insertOp(String sql) throws Exception {
        TranslationPlug tr=new TranslationPlug();
        Connection connclient=BaseDAO.getConnection();
        String newSql=tr.translateLikeInsert_SQL(sql,1,2);

        PreparedStatement stmt= connclient.prepareStatement(newSql);
        stmt.executeUpdate(newSql);
        BaseDAO.closeAll(connclient, stmt, null);
    }
}
