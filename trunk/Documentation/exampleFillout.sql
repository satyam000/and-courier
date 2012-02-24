insert into parceltype values(1, 'letter');
insert into parceltype values(2, 'delicate');
insert into parceltype values(3, 'normalpackage');

insert into customers values(1, 'kamil', 'janowski', 'Mysłowice', 'Grunwaldzka', '41-400', 20, 11);
insert into customers values(2, 'krzysztof', 'łucki', 'krakow', 'Wroclawska', '12-345', 34, 7);
insert into customers values(3, 'asdf', 'asdf', 'katowice', 'ulica', '12-345', 1,0);

insert into parcels values(1, 0.1, '2006-02-02', 0, 1,2,null,0,1,null);
insert into parcels values(2, 1, '2006-02-02', 0, 3,1,null,0,2,null);
insert into parcels values(3, 2.5, '2006-02-02', 0, 2,3,null,0,3,null);

insert into couriers values (1, 'misio', 'password');