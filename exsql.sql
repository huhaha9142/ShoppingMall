
select * from shoppingmall.orders;
select * from shoppingmall.users;
select * from shoppingmall.products;
insert into shoppingmall.orders value ('10000', 1, '2020-03-01', '1' , '1', '1');
    
insert into shoppingmall.users values (1,'kim123@naver.com',123,'kim','신림동','010-2918-2113','2021-10-12');
insert into shoppingmall.users values (2, 'park9142@daum.net',9142, 'park', '대치동', '010-311-9212','2019-02-21');
insert into shoppingmall.users values (3, 'sam142@daum.net',1422, 'sam', '방학동', '010-2311-1112','2020-09-21');
insert into shoppingmall.users values (4, 'hong112@google.com',2111, 'hong', '대치동', '010-1231-1222','2020-04-10');
insert into shoppingmall.users values (5, 'hang1233@naver.com',4221, 'hong', '봉평동', '010-223-1012','2020-05-14');
insert into shoppingmall.users values (6, 'koo1@naver.com',2345, 'koo', '방배동', '010-2133-1212','2020-05-14');
insert into shoppingmall.users values (7, 'ryuhyeonjin123@naver.com',9999, 'ryu', '이촌동', '010-3332-1222','2019-01-23');
insert into shoppingmall.users values (8, 'bit12333312@google.com',221445, 'kim', '역삼동', '010-2223-1312','2020-05-14');
insert into shoppingmall.users values (9, 'hongJ@daum.net',2311, 'hong', '대림동', '010-2231-3123','2020-12-25');
insert into shoppingmall.users values (10, 'test11@naver.com',1234, 'test', '서림동', '010-2223-3311','2020-09-14');
    
insert into shoppingmall.products
	value (100,'red','outer',3,999991,'제품설명입니다.!','/com/image/testimg.png','test');

