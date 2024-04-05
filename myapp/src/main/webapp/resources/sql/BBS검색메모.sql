-- 작성자의 email 에 'user'가 포함되는 게시글이 몇개?
SELECT COUNT(*)
  FROM USER_T U INNER JOIN BBS_T B
    ON U.USER_NO = B.USER_NO
 WHERE U.EMAIL LIKE '%admin%';

-- 내용에 마라탕이 포함되는 게시글이 몇개
SELECT COUNT(*)
  FROM USER_T U INNER JOIN BBS_T B
    ON U.USER_NO = B.USER_NO
 WHERE B.CONTENTS LIKE '%마라탕%';

Map.of("column", "U.EMAIL", "query", "user")
 
SELECT COUNT(*)
 FROM USER_T U INNER JOIN BBS_T B
   ON U.USER_NO = B.USER_NO
 WHERE ${column} LIKE '%' ||  #{query}  || '%'       -- 칼럼이나 키워드는 ${}, 변수값 전달은 #{}
 WHERE  U.EMAIL  LIKE '%' ||   'user'   || '%'
 WHERE ${column} LIKE CONCAT(CONCAT('%', #{query}), '%')        -- 오라클의 concat 은 2개밖에 못 써서 하나 더 붙임..!