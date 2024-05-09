-- テーブル削除
DROP TABLE IF EXISTS users;

-- 顧客テーブル
CREATE TABLE users
(
   id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT,
   password TEXT
);