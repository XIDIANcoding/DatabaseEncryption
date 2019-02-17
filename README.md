# DatabaseEncryption
Ciphertext database inserts data and makes the same fuzzy query

  By designing a middleware, the client passes the sql statement from the user to the middleware, parses it by the middleware, and then sends
the parsed sql statement to the server, and the server executes the parsed sql. When the insert data is executed, the middleware completes 
the parsing of the sql statement and the encryption of the data, and the server stores the ciphertext in the cloud. When the fuzzy query 
is executed, the superset returned by the server is sent to the middleware, the middleware completes the decryption, and the plaintext is 
saved in the client database. At this point, a second query is performed to get the final result. In this way, the security of cloud databa
-se storage and query is guaranteed.

  The middleware is designed to perform encryption and decryption and sql parsing. It is encrypted and decrypted by AES. Considering the net
-work transmission, the ciphertext is encoded by using Base64. In addition, the ciphertext database stores the ciphertext corresponding to 
the plaintext and the ciphertext index. The design of the index is done by assigning a partition to each character.

insert datas：

Original sql statement：Insert  into students values(“M201873235”,”wang”,NULL)

Parsing sql statement：Insert  into students values(“M201873235”,enc(wang),Index(wang))

select datas：

Original sql statement：Select * from students where name like “wang%”

Parsing sql statement：Select * from students where contentindex like “getIndex(wang)%”

-------------------------------------------------------------------------------------------------------------------------------------------


本项目通过设计一个中间件，客户端将来自用户的sql语句传给中间件，由中间件进行解析，然后将解析后的sql语句发给服务器，由服务器执行解析后的sql。当执行插入数
据时，由中间件完成sql语句的解析和数据的加密，由服务器将密文存储在云端。当执行模糊查询时，将服务器返回的超集发给中间件，由中间件完成解密，并将明文保存在客
户端数据库。此时进行二次查询，得到最终的结果。通过此种方式，保证了云数据库存储和查询的安全性。

中间件的设计完成加解密和sql的解析，通过AES进行加解密，考虑到网络传输，通过使用Base64对密文进行数据编码。此外，密文数据库存储的是明文对应的密文以及密文索
引。索引的设计通过为每一个字符分配分区来完成。
