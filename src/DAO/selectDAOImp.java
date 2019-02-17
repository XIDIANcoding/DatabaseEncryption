package DAO;

//import translatinPlug.EncryptionAndDecrytion;
import translatinPlug.EncryptionAndDecrytion;
import translatinPlug.EncryptionAndDecrytion2;
import translatinPlug.TranslationPlug;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static translatinPlug.EncryptionAndDecrytion2.parseHexStr2Byte;

/**
 * @program: DatabaseEncryption
 * @description: define class to implement selectDAO
 * @author: WYY
 * @create: 2018-12-25 21:38
 **/
public class selectDAOImp extends BaseDAO implements selectDAO{
    public void findAllEncryptions(String sql) throws Exception {
        TranslationPlug tr=new TranslationPlug();
        Connection conn=BaseDAO.getConnection();
//        Connection connclient=BaseDAO.getConnection2();
        PreparedStatement stmt= conn.prepareStatement(sql);
        ResultSet rs=stmt.executeQuery();
        EncryptionAndDecrytion de=new EncryptionAndDecrytion();
        //EncryptionAndDecrytion2 de=new EncryptionAndDecrytion2();
        while(rs.next()) {
            String  id=rs.getString("id");
            String encryption=rs.getString("encryption");
            String index=rs.getString("contentindex");
            System.out.println(rs.getString("id")+" " +rs.getString("encryption")+" "+ rs.getString("contentindex"));

            //byte[] newcontent=parseHexStr2Byte(encryption.trim());

            //byte[] decryption=de.decrypt(newcontent,"12345678");
            String decryption=de.decrypt(encryption.trim());
            String newSql="insert into studentsde values(\""+id+"\""+",\""+decryption+"\""+",\""+index+"\""+")";
            PreparedStatement stmt2= conn.prepareStatement(sql);
            stmt2.executeUpdate(newSql);
        }
        BaseDAO.closeAll(conn, stmt, rs);
    }
    public void findAllEncryptionsWithoutParse(String sql)throws Exception{
        Connection conn=BaseDAO.getConnection();
        String newsql=sql.substring(0,sql.indexOf("where")-1);
        PreparedStatement stmt= conn.prepareStatement(newsql);
        ResultSet rs=stmt.executeQuery();
        EncryptionAndDecrytion de=new EncryptionAndDecrytion();
        //EncryptionAndDecrytion2 de=new EncryptionAndDecrytion2();
        while(rs.next()) {
            String  id=rs.getString("id");
            String encryption=rs.getString("encryption");
            String index=rs.getString("contentindex");
            System.out.println(rs.getString("id")+" " +rs.getString("encryption")+" "+ rs.getString("contentindex"));

            //byte[] newcontent=parseHexStr2Byte(encryption.trim());

            //byte[] decryption=de.decrypt(newcontent,"12345678");
            String decryption=de.decrypt(encryption.trim());
            String newSql="insert into studentsdeall values(\""+id+"\""+",\""+decryption+"\""+",\""+index+"\""+")";
            PreparedStatement stmt2= conn.prepareStatement(sql);
            stmt2.executeUpdate(newSql);
        }
        BaseDAO.closeAll(conn, stmt, rs);
    }
    public void findAllDncryptions(String sql) throws Exception {
        int index0=sql.indexOf("from");
        int index1=sql.indexOf("like");
        String newsql0=sql.substring(0,index0+5);
        String parse=sql.substring(index1);
        newsql0=newsql0+"studentsde where dencryption "+parse;
        Connection connclient=BaseDAO.getConnection();
        PreparedStatement stmt= connclient.prepareStatement(newsql0);
        ResultSet rs=stmt.executeQuery();
        while(rs.next()) {
           System.out.println(rs.getString("id")+ " "+rs.getString("dencryption")+" "+ rs.getString("contentindex"));
        }
        BaseDAO.closeAll(connclient, stmt, rs);
    }
    public void findAllDncryptionsAll(String sql) throws Exception {
        int index0=sql.indexOf("from");
        int index1=sql.indexOf("like");
        String newsql0=sql.substring(0,index0+5);
        String parse=sql.substring(index1);
        newsql0=newsql0+"studentsdeall where dencryption "+parse;
        Connection connclient=BaseDAO.getConnection();
        PreparedStatement stmt= connclient.prepareStatement(newsql0);
        ResultSet rs=stmt.executeQuery();
        while(rs.next()) {
            System.out.println(rs.getString("id")+ " "+rs.getString("dencryption")+" "+ rs.getString("contentindex"));
        }
        BaseDAO.closeAll(connclient, stmt, rs);
    }

}
