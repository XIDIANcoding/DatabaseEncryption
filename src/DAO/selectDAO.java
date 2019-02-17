package DAO;

import java.util.List;

/**
 * @program: DatabaseEncryption
 * @description: define selectDAO to get all result
 * @author: WYY
 * @create: 2018-12-25 21:36
 **/
public interface selectDAO {
    void findAllEncryptions(String sql) throws Exception;
    void findAllDncryptions(String sql) throws Exception;
    void findAllEncryptionsWithoutParse(String sql)throws Exception;
    void findAllDncryptionsAll(String sql) throws Exception;
}
