

delimiter $$
create trigger 入库触发器
after insert on 入库订单
for each row
begin
insert into 仓库信息表 values(new.入库单号, new.图书号, new.图书数量, new.入库时间);
end;
$$

delimiter $$
create trigger 出库触发器
after insert on 销售订单
for each row
begin
insert into 仓库信息表 values(new.销售单号, new.图书号, new.销售数量, new.销售时间);
end;
$$


delimiter $$ 
create trigger 退货触发器
after insert on 退货订单
for each row
begin
insert into 仓库信息表 values(new.退货单号, new.图书号, new.退货数量, new.退货时间,'退货');
end;
$$

create view 查询仓库库存
as 
select 仓库.图书号 as 图书号 , 图书.图书名称 as 图书名称, 图书.图书类别, 仓库.库存总量 
from 仓库, 图书 
where 仓库.图书号 = 图书.图书号;


create view 查询退货情况视图
as 
select 退货订单.退货单号 as 退货单号 , 退货订单.图书号 as 图书号, 图书.图书名称 as 图书名称, 图书.图书类别 as 图书类别 , 退货订单.退货数量 as 退货数量, 退货订单.退货时间 as 
退货时间 , 退货订单.购买者号码 as 退货者联系方式, 退货订单.备注 as 备注
from 退货订单, 图书
where 退货订单.图书号 = 图书.图书号;

create view 查询进货情况视图
as 
select 入库订单.入库单号 as 入库单号 ,  图书.图书名称 as 图书名称, 图书.图书类别 as 图书类别 , 入库订单.图书数量 as 图书数量, 入库订单.入库时间 as 入库时间,
 入库订单.备注 as 备注
from 入库订单, 图书
where 入库订单.图书号 = 图书.图书号;

create view 查询购买者及购买情况
as 
select 购买者.购买者号码 as 编号, 购买者.购买者姓名 as 姓名, 购买者.优惠状态 as 优惠状态,  销售订单.图书号  as 图书号, 图书.图书名称 as 书名, 销售订单.销售数量 as 购买数量,
销售订单.销售时间 as 购买时间
from 购买者, 销售订单, 图书
where 购买者.购买者号码 = 销售订单.购买者号码 and 销售订单.图书号 = 图书.图书号;
select * from 查询购买者及购买情况;


delimiter $$
create trigger 入库修改库存触发器
after insert on 入库订单 
for each row
begin 
set @a = new.图书号;
set @b = new.图书数量;
if(select 仓库.图书号 from 仓库 where 仓库.图书号 = @a)is not null then
	
    update 仓库 
    set 仓库.库存总量 = 仓库.库存总量 + @b
    where 仓库.图书号 = @a;
    else 
    insert into 仓库 values(@a, @b);
    end if;
    
end;
$$

delimiter $$
create trigger 出库修改库存触发器
after insert on 销售订单 
for each row
begin 
set @a = new.图书号;
set @b = new.销售数量;
if(select 仓库.图书号 from 仓库 where 仓库.图书号 = @a)is not null then
	
    update 仓库 
    set 仓库.库存总量 = 仓库.库存总量 - @b
    where 仓库.图书号 = @a;
    end if;
    
end;
$$



delimiter $$
create trigger 退货修改库存触发器
after insert on 退货订单 
for each row
begin 
set @a = new.图书号;
set @b = new.退货数量;
if(select 仓库.图书号 from 仓库 where 仓库.图书号 = @a)is not null then
	
    update 仓库 
    set 仓库.图书数量 = 仓库.图书数量 + @b
    where 仓库.图书号 = @a;
    end if;
    
end;
$$


delimiter $$
create trigger 修改优惠状态触发器
after insert on 销售订单
for each row
begin
if new.销售数量 >= 50 then
update 购买者
set 购买者.优惠状态 = 'vip'
where 购买者.购买者号码 = new.购买者号码;
end if;
end;
$$

insert into 购买者
values('191870301','王强','13712341234','none');*/
/*insert into 入库订单
values('I100','101',20,'2020-10-31','无');



alter table 销售订单 
add constraint
foreign key (购买者号码) references 购买者(购买者号码);



insert into 销售订单 
values('S102','101',50,'191870180',50,'2020-12-03');


create view 查看vip客户
as
select 购买者.购买者姓名 as 姓名, 购买者.购买者号码 as 编号, 购买者.联系方式 as 联系方式
from 购买者
where 购买者.优惠状态 = 'vip';


delimiter $$
create trigger 统计销售触发器
after insert on 销售订单
for each row 
begin
set @a = new.图书号;
set @b = new.销售数量;
set @c = new.图书价格;
set @d = (select 图书.图书价格 from 图书 where 图书.图书号 = @a) ;
if(select 销售情况表.图书号 from 销售情况表 where 销售情况表.图书号 = new.图书号 ) is not null then
update 销售情况表
set 销售情况表.销售数量 = 销售情况表.销售数量 + @b, 销售情况表.销售总额 = 销售情况表.销售总额 + (@c * @b), 
销售情况表.销售利润 = 销售情况表.销售利润 + (@b)*(@c - @d)
where 销售情况表.图书号 = @a;
else 
insert into 销售情况表 values(@a, @b, @c * @b,(@b)*(@c - @d));
end if;
end;
$$



update 购买者
set 购买者.优惠状态 = 'none' where 购买者.购买者号码 = '191870180';
select * from 查看vip客户;

alter table 管理员
add column 邮箱 char(50) not null;


alter table 管理员
add column 性别 char(4) not null
constraint  sexCheck check(性别 in('男','女'));


alter table 退货订单
drop foreign key 退货订单_ibfk_2;


alter table 退货订单
drop column 购买者号码;
alter table 退货订单 
add foreign key (原购单号) references 销售订单(销售单号);


delimiter $$
create trigger 退货统计触发器
after insert on 退货订单
for each row 
begin
set @a = new.图书号;
set @b = new.退货数量;
set @c = new.原购单号;
set @d = (select 图书.图书价格 from 图书 where 图书.图书号 = @a);
set @e = (select 销售订单.图书价格 from 销售订单 where 销售订单.销售单号 = @c);
set @f = new.退货单价;
if(select 销售情况表.图书号 from 销售情况表 where 销售情况表.图书号 = new.图书号 ) is not null then
update 销售情况表
set 销售情况表.销售数量 = 销售情况表.销售数量 - @b, 销售情况表.销售总额 = 销售情况表.销售总额 - @f * @b, 
销售情况表.销售利润 = 销售情况表.销售利润 - (@b)*(@e - @d) + (@b)*(@f - @d)
where 销售情况表.图书号 = @a;
end if;
end;
$$


alter table 退货订单
add column 退货单价 int not null;



create view 查询退货情况视图
as 
select 退货订单.退货单号 as 退货单号 , 退货订单.图书号 as 图书号, 图书.图书名称 as 图书名称, 图书.图书类别 as 图书类别 ,退货订单.原购单号 as 原单号, 退货订单.退货数量 as 退货数量, 退货订单.退货时间 as 
退货时间 , 销售订单.购买者号码 as 购买者编号, 购买者.购买者姓名 as 退货人,  退货订单.备注 as 备注
from 退货订单, 图书, 销售订单, 购买者
where 退货订单.图书号 = 图书.图书号 and 销售订单.销售单号 = 退货订单.原购单号 and 销售订单.购买者号码 = 购买者.购买者号码;



insert into 入库订单
values('I101','101',100,'2020-01-01 11:20:21','no');
select * from 入库订单;

insert into 销售订单
values('S101','101',140,'191870180',50,'2020-02-01 12:12:12');



insert into 退货订单
values('T101','101',30,'2020-09-20 11:10:10','no','S101',110);



alter table 退货订单
drop constraint 退货订单_ibfk_2;

insert into 销售订单
values('S101','101',140,'191870180',50,'2020-02-01 12:12:12');
select * from 销售情况表;

delimiter $$
create trigger 设置初始利润
after insert on 入库订单
for each row 
begin
set @a = new.图书号;
set @b = new.图书数量;
set @d = (select 图书.图书价格 from 图书 where 图书.图书号 = @a) ;
if(select 销售情况表.图书号 from 销售情况表 where 销售情况表.图书号 = @a) is null then
insert into 销售情况表 values(@a, 0,0,0-(@d * @b));
else 
update 销售情况表 
set 销售情况表.销售利润 = 销售情况表.销售利润 - (@d*@b)
where 销售情况表.图书号 = @a;
end if;
end;
$$


alter table 仓库信息表 
add column 具体操作 char(10) not null;
select * from 仓库信息表; 


create role 进货操作员;
grant all on 入库订单 to 进货操作员;

-- grant all on 图书 to 进货操作员;
-- create role 销售操作员;
-- grant all on 销售订单  to 销售操作员;
-- grant all on 销售详情表 to 销售操作员;
grant all on 退货订单 to 销售操作员;
create role 技术人员;


-- grant all on 图书销售系统 to 技术人员 with grant option;

grant select on 查询仓库库存 to 进货操作员;
grant select on 查询入库订单 to 进货操作员;
grant select on 查询购买者及购买情况 to 进货操作员;
grant select on 查询进货情况视图 to 进货操作员;

create user '张三'@'localhost' identified by '123456';
insert into 管理员
values('张三','123456','进货操作员','zhangsan@163.com','男');
select * from 管理员;
                    

-- select user,host from mysql.user;
/*
create user '李四'@'localhost' identified by 'qwert';

insert into 管理员
values('李四','qwert','进货操作员','lisy@126.com','男');
grant 进货操作员 to '李四'@'localhost';
select * from 管理员;

create user '李华'@'localhost' identified by 'asdf';
insert into 管理员
values('李华','asdf','销售操作员','huali@qq.com','女');
grant 销售操作员 to '李华'@'localhost';
select * from 管理员;
*/

/*
select count(distinct(单号)) as 查询入库数量
from 仓库信息表
where 单号 like 'I%' ;
*/
/*
insert into 购买者
values('191870302','许丽','19011111111','none');

select 购买者号码 as 联系方式, 购买者姓名 as 姓名 
from 购买者 
where 购买者姓名 like '王%';
*/
/*
delimiter $$
create procedure 获取书籍进货情况
(in 目标图书号 char(20))
begin
select 入库单号 as 单号, 图书号 ,图书数量 as 入库数量, 入库时间 as 入库时间
from 入库订单 
where 入库订单.图书号 = 目标图书号;
end;
$$

delimiter $$
create procedure 获取书籍销售情况
(in 目标图书号 char(20))
begin 
select 图书号, 销售数量, 销售总额, 销售利润 
from 销售情况表
where 销售情况表.图书号 = 目标图书号;
end;
$$
*/
-- SET SQL_SAFE_UPDATES = 0;
/*insert into 销售订单
values('S102','123',80,'191870301',55,'2020-12-01 19:21:02');
select * from 销售订单;
*/
/*
delimiter $$
create procedure 更改入库订单(in 目标入库单号 char(20), in 修改后数量 int)
begin
set @a = (select 图书号 from 入库订单 where 入库单号 = 目标入库单号);
set @b = (select 图书数量 from 入库订单 where 入库单号 = 目标入库单号);
set @c = (select 图书价格 from 图书 where 图书号 = @a);

update 仓库信息表
set 图书数量 = 图书数量 - @b + 修改后数量 
where 仓库信息表.单号 = 目标入库单号;

update 销售情况表
set 销售利润 = 销售利润 + @c*(修改后数量) - @c*@b
where 图书号 = @a;

update 仓库
set 库存总量 = 库存总量 - @b + 修改后数量
where 图书号 = @a;

update 入库订单
set 图书数量 = 图书数量 - @b + 修改后数量
where 图书号 = @a;
end;

$$
*/
-- call 更改入库订单('I106',80);
/*
delimiter $$
create procedure updateorder销售时间(in 目标销售单号 char(20), in 目标销售时间 datetime)
begin
update 仓库信息表
set 操作时间 = 目标销售时间
where 仓库信息表.单号 = 目标销售单号;

update 销售订单
set 销售时间 = 目标销售时间
where 销售订单.销售单号 = 目标销售单号;

end;
$$
*/
-- call updateorder销售时间('S101','2020-02-02 12:13:12');
-- select * from 仓库信息表;
/*
delimiter $$
create procedure updateorder图书号(in 目标销售单号 char(20), in 目标图书号 char(20))
begin

set @a = (select 图书号 from 销售订单 where 销售单号 = 目标销售单号);

set @b = (select 销售数量 from 销售订单 where 销售单号 = 目标销售单号);

set @c = (select 图书价格 from 图书 where 图书号 = @a);

set @d = (select 图书价格 from 销售订单 where 销售单号 = 目标销售单号);

set @e = (select 图书价格 from 图书 where 图书号 = 目标图书号);


update 仓库
set 库存总量 = 库存总量 + @b
where 图书号 = @a;

update 仓库 
set 库存总量 = 库存总量 - @b
where 图书号 = 目标图书号;



update 仓库信息表
set 图书号 = 目标图书号
where 单号 = 目标销售单号;

update 销售情况表
set 销售数量 = 销售数量 - @b, 销售总额 = 销售总额 - (@d *@b), 销售利润 = 销售利润 - (@d - @c)*@b
where 图书号 = @a;


update 销售情况表
set 销售数量 = 销售数量 + @b, 销售总额 = 销售总额 + @b*@d, 销售利润 = 销售利润 + (@d - @e)*@b
where 图书号 = 目标图书号;

update 销售订单
set 图书号 = 目标图书号
where 销售单号 = 目标销售单号;

end;
$$
*/
/*
update 仓库
set 库存总量 = 200
where 图书号 = '102';
update 仓库 
set 库存总量 = 35
where 图书号 = '123';
select * from 仓库;
*/

-- drop procedure updateorder图书号;
/*insert into 入库订单
values('I107','102',200,'2020-11-02 13:00:06','无');
select * from 入库订单;
*/

-- SET SQL_SAFE_UPDATES = 0;
/*insert into 销售订单 
values('S103','102',70,'191870301',5,'2020-12-03 19:08:02');
select * from 销售订单;

*/


-- call updateorder图书号('S103','102');
-- select * from 入库订单;

/*
delimiter $$
create procedure updateorder图书价格(in 目标销售单号 char(20), in 目标图书价格 int)
begin

set @a = (select 图书号 from 销售订单 where )
*/

/*
select * from 仓库信息表;
*/
 
-- call updateorder图书号('S103','102');

/*
delimiter $$
create procedure updateorder图书价格(in 目标销售单号 char(20), in 目标图书价格 int)
begin 
set @a = (select 图书号 from 销售订单 where 销售单号 = 目标销售单号);

set @b = (select 图书价格 from 销售订单 where 图书号 = @a);

set @c = (select 销售数量 from 销售订单 where 图书号 = @a);

set @d = (select 图书价格 from 图书 where 图书号 = @a);


update 销售订单
set 图书价格 = 目标图书价格
where 销售单号 = 目标销售单号;

update 销售情况表
set 销售总额 = 销售总额 - (@c *@b) + (@c *目标图书价格), 销售利润 = 销售利润 - (@b - @d)*@c + (目标图书价格 - @d)*@c
where 图书号 = @a;

end;
$$

*/


-- call updateorder图书价格('S103',80);
-- call updateorder图书价格('S103',70);


/*
delimiter $$
create procedure updateorder销售数量(in 目标销售单号 char(20), in 目标销售数量 int)

begin
set @a = (select 图书号 from 销售订单 where 销售单号 = 目标销售单号);

set @b = (select 销售数量 from 销售订单 where 销售单号 = 目标销售单号);

set @c = (select 图书价格 from 销售订单 where 销售单号 = 目标销售单号);

set @d = (select 图书价格 from 图书 where 图书号 = @a);

update 销售订单
set 销售数量 = 目标销售数量
where 销售单号 = 目标销售单号;

update 仓库 
set 库存总量 = 库存总量 + @b - 目标销售数量
where 图书号 = @a;

update 仓库信息表
set 图书数量 = 图书数量 + @b - 目标销售数量 
where 图书号 = @a;

update 销售情况表
set 销售总额 = 销售总额 - @b*@c + 目标销售数量 * @c, 销售利润 = 销售利润 - (@c - @d)*@b + (@c - @d)*目标销售数量, 销售数量 = 销售数量 - @b + 目标销售数量
where 图书号 = @a;

end;
$$


delimiter $$

create procedure 修改入库图书号(in 目标入库单号 char(20), in 目标图书号 char(20) )
begin

set @a = (select 图书号 from 入库订单 where 入库单号 = 目标入库单号);

set @b = (select 图书数量 from 入库订单 where 入库单号 = 目标入库单号);

set @c = (select 图书价格 from 图书 where 图书号 = @a);

set @d = (select 图书价格 from 图书 where 图书号 = 目标图书号);

update 入库订单
set 图书号 = 目标图书号
where 入库单号 = 目标入库单号;

update 仓库
set 库存总量 = 库存总量 + @b
where 图书号 = 目标图书号;

update 仓库 
set 库存总量 = 库存总量 - @b
where 图书号 = @a;

update 仓库信息表
set 图书号 = 目标图书号 
where 单号 = 目标入库单号;

update 销售情况表
set 销售利润 = 销售利润 + @c*@b
where 图书号 = @a;

update 销售情况表
set 销售利润 = 销售利润 - @d*@b
where 图书号 = 目标图书号;

end;
$$

*/
/*
insert into 入库订单
values('I108','102',20,'2020-07-04 15:44:02','无');
*/

/*
call 修改入库图书号('I108','102'); 
select * from 销售情况表;
*/

/*
delimiter $$
create procedure 修改入库图书数量(in 目标入库单号 char(20), in 目标图书数量 int)

begin

set @a = (select 图书号 from 入库订单 where 入库单号 = 目标入库单号);

set @b = (select 图书价格 from 图书 where 图书号 = @a);

set @c = (select 图书数量 from 入库订单 where 入库单号 = 目标入库单号);



update 入库订单 
set 图书数量 = 目标图书数量
where 入库单号 = 目标入库单号;

update 仓库
set 库存总量 = 库存总量 - @c + 目标图书数量
where 图书号 = @a;

update 仓库信息表
set 图书数量 = 目标图书数量 
where 单号 = 目标入库单号;

update 销售情况表
set 销售利润 = 销售利润 + (@b *@c) - 目标图书数量*@b
where 图书号 = @a;

end;
$$

*/
/*
delimiter $$

create procedure 修改入库时间(in 目标入库单号 char(20), in 目标时间 datetime)

begin

set @a = (select 图书号 from 入库订单 where 入库单号 = 目标入库单号);

update 入库订单
set 入库时间 = 目标时间
where 入库单号 = 目标入库单号;

update 仓库信息表
set 操作时间 = 目标时间
where 单号 = 目标入库单号;

end;
$$



delimiter $$

create procedure 删除入库订单(in 目标入库单号 char(20))
begin

set @a = (select 图书号 from 入库订单 where 入库单号 = 目标入库单号);

set @b = (select 图书价格 from 图书 where 图书号 = @a);

set @c = (select 图书数量 from 入库订单 where 入库单号 = 目标入库单号);

update 仓库
set 库存总量 = 库存总量 - @c
where 图书号 = @a;

update 销售情况表 
set 销售利润 = 销售利润 + @c*@b
where 图书号 = @a;

delete from 仓库信息表
where 单号 = 目标入库单号;

delete from 入库订单
where 入库单号 = 目标入库单号;

end;
$$
*/
/*
delimiter $$
create procedure 删除退货订单(in 目标退货单号 char(20))
begin
set @a = (select 图书号 from 退货订单 where 退货单号 = 目标退货单号);

set @b = (select 退货数量 from 退货订单 where 退货单号 = 目标退货单号);

set @c = (select 原购单号 from 退货订单 where 退货单号 = 目标退货单号);

set @d = (select 退货单价 from 退货订单 where 退货单号 = 目标退货单号);

set @e = (select 图书价格 from 图书 where 图书号 = @a);

set @f = (select 图书价格 from 销售订单 where 销售单号 = @c);

set @g = (select 退货单价 from 退货订单 where 退货单号 = 目标退货单号);


update 销售情况表
set 销售总额 = 销售总额 + @b*@g, 销售利润 = 销售利润 - (@g - @e)*@b + (@f - @e)*@b 
where 图书号 = @a;

update 仓库
set 库存总量 = 库存总量 - @b
where 图书号 = @a;

delete from 仓库信息表 
where 单号 = 目标退货单号;


delete from 退货订单
where 退货单号 = 目标退货单号;
end;
$$


*/

-- insert into 入库订单
-- values('I109','102',1,'2020-01-11 00:01:01','no');
/*
delimiter $$
create procedure 删除销售订单(in 目标销售单号 char(20))
begin

set @a = (select 图书号 from 销售订单 where 销售单号 = 目标销售单号);

set @b = (select 图书价格 from 销售订单 where 销售单号 = 目标销售单号);

set @c = (select 图书价格 from 图书 where 图书号 = @a);

set @d = (select 销售数量 from 销售订单 where 销售单号 = 目标销售单号);

update 仓库
set 库存总量 = 库存总量 + @d
where 图书号 = @a;

update 销售情况表
set 销售数量 = 销售数量 - @d , 销售总额 = 销售总额 - @d*@b , 销售利润 = 销售利润 - (@b - @c) *@d
where 图书号 = @a;

delete from 仓库信息表
where 单号 = 目标销售单号;

delete from 销售订单
where 销售单号 = 目标销售单号;

end;
$$
*/
/*
insert into 销售订单
values('S104','101',120,'191870180',10,'2020-02-05 19:03:01');
*/
/*
call 删除销售订单('S104');
select * from 销售情况表;
*/
/*
insert into 入库订单
values('I101','101',200,'2020-12-01 10:00:01','no');


insert into 入库订单
values('I102','102',200,'2020-12-02 08:45:31','no'); 
select * from 销售情况表;
*/
/*
insert into 销售订单 
values('S101','101',50,'191870180',50,'2020-12-09 21:10:34');

insert into 销售订单
values('S102','102',10,'191870301',10,'2020-12-13 10:45:44');
*/


/*
insert into 销售订单
values('S102','102',50,'191870180',60,'2020-12-23 10:23:44');


drop trigger 修改销售额触发器;
*/
 
/*
delimiter $$
create trigger 修改销售额触发器
after insert on 销售订单
for each row
begin
if(select 销售情况表.图书号 from 销售情况表 where 销售情况表.图书号 = new.图书号)is not null then
update 销售情况表
set 销售情况表.销售总额 = 销售情况表.销售总额 + (new.图书价格)*(new.销售数量)
where 销售情况表.图书号 = new.图书号;
end if;
end;
$$

delete from 仓库信息表;
delete from 仓库;
delete from 入库订单;
delete from 退货订单;
delete from 销售订单;
delete from 销售情况表;


insert into 入库订单
values('I101','101',200,'2020-12-01 10:00:01','no');


insert into 入库订单
values('I102','102',200,'2020-12-02 08:45:31','no'); 

insert into 销售订单 
values('S101','101',50,'191870180',50,'2020-12-09 21:10:34');

insert into 销售订单
values('S102','102',10,'191870301',70,'2020-12-13 10:45:44');

select * from 销售情况表;
*/
/*
insert into 图书
values('103','复变函数','吴晗','数学基础',35,'特殊进货');


insert into 入库订单
values('I103','103',200,'2020-12-08 20:10:23','no');
select * from 销售情况表;
*/
/*
insert into 退货订单 
values('T101','102',10,'2020-12-24 10:01:04','无','S102',55);

select * from 销售情况表;
*/
/*


$$
*/
/*insert into 退货订单 
values('T101','102',10,'2020-12-24 10:01:04','无','S102',55);

select * from 销售情况表;
*/
-- call 删除退货订单('T101');

/*
delimiter $$
create procedure 查找图书销售情况(in 目标图书号 char(20))
begin

select 仓库信息表.单号 as 单号, 图书.图书名称 as 书名 , 图书.图书作者 as 作者, 图书.图书类别 as 类别, 
仓库信息表.图书数量 as 数量, 仓库信息表.操作时间 as 操作时间, 仓库信息表.具体操作 as 操作
from 仓库信息表, 图书
where 仓库信息表.图书号 = 目标图书号 and 图书.图书号 = 目标图书号;

end;
$$


delimiter $$
create procedure 增加入库订单(in 新增入库单号 char(20), in 新增图书号 char(20), in 新增图书数量 int , in 新增入库时间 datetime, in 新增备注 char(50))
begin

insert into 入库订单
values(新增入库单号, 新增图书号, 新增图书数量, 新增入库时间, 新增备注);

end;
$$

delimiter $$
create procedure 增加销售订单(in 新销售单号 char(20), in 新图书号 char(20), in 新图书价格 int, in 新购买者号码 char(20), in 新销售数量 int, in 销售时间 datetime) 
begin 
insert into 销售订单
values(新销售单号, 新图书号, 新图书价格,新购买者号码 , 新销售数量 ,销售时间);
end;
$$
*/
/*
call 增加销售订单('S103','101',50,'191870180',20,'2020-12-19 12:14:59');
select * from 销售情况表;

delimiter $$
create procedure 增加退货订单(in 新退货单号 char(20), in 新图书号 char(20), in 新退货数量 int, in 新退货时间 datetime, in 新备注 char(50),
 in 新原购单号 char(20), in 新退货单价 int)
begin 

insert into 退货订单
values(新退货单号, 新图书号 , 新退货数量, 新退货时间, 新备注 ,新原购单号 , 新退货单价);

end;
$$
*/
/*
delimiter $$
create procedure 查找盈利书籍()
begin
select  销售情况表.图书号 ,图书.图书名称, 图书.图书作者, 图书.图书类别, 销售情况表.销售数量, 销售情况表.销售总额, 销售情况表.销售利润
from 销售情况表, 图书
where 销售情况表.图书号 = 图书.图书号 and 销售情况表.销售利润 > 0;

end;
$$
*/
/*
drop procedure checkbook总额;

delimiter $$
create procedure checkbook总额(in 销售总额目标 int)

begin
select 图书.图书名称, 图书.图书作者, 图书.图书类别, 销售情况表.销售数量, 销售情况表.销售总额, 销售情况表.销售利润
from 图书,销售情况表
where 图书.图书号 = 销售情况表.图书号 and 销售情况表.销售总额 >= 销售总额目标;

end;
$$
call checkbook总额(3500);
*/

/*
delimiter $$
create procedure 模糊查找书名(in 待查书名 char(20))
begin 
select 图书.图书名称, 图书.图书作者, 图书.图书类别, 图书.图书价格, 销售情况表.销售数量, 销售情况表.销售总额, 销售情况表.销售利润
from 图书,销售情况表
where locate(待查书名 , 图书.图书名称) > 0 and 图书.图书号 = 销售情况表.图书号;
end;
$$ 
call 模糊查找书名('数');
*/
/*
delimiter $$
create procedure 精确查询作者(in 目标作者 char(20))
begin 
select 图书.图书名称, 图书.图书作者, 图书.图书类别, 销售情况表.销售数量, 销售情况表.销售总额, 销售情况表.销售利润
from 图书,销售情况表
where 图书.图书号 = 销售情况表.图书号 and 图书.图书作者 = 目标作者;
end;
$$
call 精确查询作者('李丽');
*/
/*
delimiter $$

create procedure 多重查找销售订单(in 目标销量 int, in 目标图书类别 char(20))

begin
select 销售订单.销售单号 , 销售订单.图书号, 图书.图书名称, 图书.图书作者, 图书.图书价格 as 成本,销售订单.图书价格 as 售价, 销售订单.销售数量, 销售订单.销售时间 as 销售时间
from 销售订单 , 图书
where 销售订单.图书号 = 图书.图书号 and 销售订单.销售数量 >= 目标销量 and 图书.图书类别 = 目标图书类别;

end;
$$
*/
/*
drop procedure 查找用户消费记录;
delimiter $$
create procedure 查找用户消费记录(in 用户编号 char(20), in 指示符号 int)
begin
if 指示符号 > 0 is true  then
select  distinct 销售订单.销售单号 as 购买单号, 销售订单.购买者号码 as 用户编号,购买者.购买者姓名 as 姓名, 购买者.优惠状态 , 销售订单.图书号, 图书.图书名称, 图书.图书作者, 销售订单.图书价格 as 支出, 销售订单.销售数量 as 购买数量, 销售订单.销售时间 as 购买时间
from 销售订单 ,购买者,  图书
where 用户编号 = 销售订单.购买者号码 and 销售订单.购买者号码 = 购买者.购买者号码 and 销售订单.图书号 = 图书.图书号
order by 销售订单.购买者号码;

else 
select distinct 销售订单.销售单号 as 购买单号, 购买者.购买者姓名 as 姓名, 销售订单.购买者号码 as 用户编号, 购买者.优惠状态 , 销售订单.图书号, 图书.图书名称, 图书.图书作者, 销售订单.图书价格 as 支出, 销售订单.销售数量 as 购买数量, 销售订单.销售时间 as 购买时间
from 销售订单 ,购买者,  图书
where locate(用户编号,购买者.购买者姓名) > 0 and 销售订单.购买者号码 = 购买者.购买者号码 and 销售订单.图书号 = 图书.图书号
order by 销售订单.购买者号码;
end if;
end;
$$

call 查找用户消费记录('王',-1);
*/


/*
delimiter $$
create procedure 测试(in 指示符号 int)
begin
if 指示符号 > 0 is true then
select * from 查看vip客户;
else 
select * from 查询仓库库存;
end if;
end;
$$
*/

/*
delimiter $$
create procedure 批量删除图书号(in 目标图书号 char(20))
begin
delete from 仓库信息表
where 图书号 = 目标图书号;

delete from 入库订单
where 图书号 = 目标图书号;

delete from 销售订单 
where 图书号 = 目标图书号;

delete from 退货订单
where 图书号 = 目标图书号;

delete from 仓库 
where 图书号 = 目标图书号;

delete from 销售情况表
where 图书号 = 目标图书号;

delete from 图书
where 图书号 = 目标图书号;
end;
$$


drop procedure 模糊查找书名;

delimiter $$
create procedure 模糊查找书名(in 待查书名 char(20))
begin 

select 图书.图书号 ,图书.图书名称, 图书.图书作者, 图书.图书类别, 图书.图书价格, 销售情况表.销售数量, 销售情况表.销售总额, 销售情况表.销售利润
from 图书,销售情况表
where locate(待查书名,图书.图书名称) > 0 and 图书.图书号 = 销售情况表.图书号 
order by 图书.图书号;
end;
$$ 
call 模糊查找书名('十')




insert into 销售情况表
values('567',0,0,0);

delimiter $$
create trigger 增加图书修改销售情况触发器
after insert on 图书
for each row
begin
insert into 销售情况表 values(new.图书号,0,0,0);
end;
*/
/*
drop procedure 修改入库时间;

delimiter $$

create procedure 修改入库时间(in 目标入库单号 char(20), in 目标时间 datetime)

begin

set @a = (select 图书号 from 入库订单 where 入库单号 = 目标入库单号);

update 入库订单
set 入库时间 = 目标时间
where 入库单号 = 目标入库单号;

update 仓库信息表
set 操作时间 = 目标时间
where 单号 = 目标入库单号;

select * from 入库订单;
end;
$$


drop procedure 修改入库时间;

delimiter $$

create procedure 修改入库时间(in 目标入库单号 char(20), in 目标时间 datetime)

begin

set @a = (select 图书号 from 入库订单 where 入库单号 = 目标入库单号);

update 入库订单
set 入库时间 = 目标时间
where 入库单号 = 目标入库单号;

update 仓库信息表
set 操作时间 = 目标时间
where 单号 = 目标入库单号;

end;
$$
*/





