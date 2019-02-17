package Client;

import DAO.BaseDAO;
import DAO.insertDAO;
import DAO.selectDAOImp;
import translatinPlug.TranslationPlug;

import java.sql.Connection;
import java.util.Scanner;

/**
 * @program: DatabaseEncryption
 * @description: define a client mian func
 * @author: WYY
 * @create: 2018-12-25 21:31
 **/
public class Main {
    public static void main(String []s) throws Exception {
      TranslationPlug tr=new TranslationPlug();
        /**
         * insert
         */
    insertDAO insert=new insertDAO();
    Scanner in=new Scanner(System.in);
//    while(true){
//        String sql=in.nextLine();
//        insert.insertOp(sql);
//    }


//      String sql=in.nextLine();
//      insert.insertOp(sql);


        /**
         * test design
         */
    //insert into students values("M201873231","王阳阳测试",NULL)
    //insert into students values("M201873232","wangyangtestl",NULL)
    //insert into students values("M201873233","1512920345",NULL)


    // select * from students where name like "%阳"
    // select * from students where name like "%阳%"
    // select * from students where name like "阳%"
    // select * from students where name like "%wang"
    // select * from students where name like "%wang%"
    // select * from students where name like "wang%"
    // select * from students where name like "%345"
    // select * from students where name like "%345%"
    // select * from students where name like "345%"

        /**
         * select
         */
    while(true){
        Scanner selectInput=new Scanner(System.in);
        String selectsql=selectInput.nextLine();
        System.out.println("按照索引对密文查询：");
        selectDAOImp select=new selectDAOImp();
        System.out.println(tr.translateLikeSearch_SQL(selectsql));
        select.findAllEncryptions(tr.translateLikeSearch_SQL(selectsql));
        select.findAllDncryptions(selectsql);
        System.out.println("全表密文查询：");
        select.findAllEncryptionsWithoutParse(selectsql);
        select.findAllDncryptionsAll(selectsql);
    }



    }
}
