USE BananaTest;
INSERT INTO employees (emp_id,active,address,city,email,firstname,hire_date,lastname,password,pay_period,permission_level,state,username,zipcode) 
VALUES (1,b'1',"7962 Est Road","Hattiesburg","lcline@email.com","Lysandra","17-04-23","Cline","FOH17JVL7DN","0","0","MS","Oojjculdueegqlx1721",'38016'),
	   (2,b'1',"9771 Vel Av.","Metairie","ccolon@email.com","Carolyn","16-11-22","Colon","DMS44XFM4HF","0","0","LA","Lrshymgfwmcixwd5732",'38016'),
       (3,b'1',"1079 Cubilia Av.","Montgomery","jhawkins@email.com","Justina","18-02-01","Hawkins","GSR78FBK9GF","0","0","AL","Xertdlcllaapgth9955",'38016'),
       (4,b'1',"1084 Cum Avenue","Salem","nanthony@email.com","Nell","17-03-28","Anthony","IEF38ISR3MU","0","0","OR","Pqjlczblyrxnqbp7851",'38016'),
       (5,b'1',"4596 Vel, Street","Harrisburg","csharpe@email.com","Chantale","18-02-05","Sharpe","ING96RAK6LJ","0","0","PA","Pfhpwyhiajaelbx9878",'38016'),
       (6,b'1',"8967 Maecenas Rd.","Wichita","zriley@email.com","Zane","17-04-01","Riley","MHY13MZN1OY","1","0","KS","Biwqsziiruoybxb8144",'38016'),
       (7,b'1',"2895 Ut Avenue","Glendale","cpatel@email.com","Cally","16-11-06","Patel","WWF21OWL8WC","1","1","AZ","Fmjhczejxnvsrut9900",'38016'),
       (8,b'1',"1240 Sit Av.","Saint Louis","lfloyd@email.com","Louis","17-04-11","Floyd","QIX44ACK1GJ","1","1","MO","Tdfemwsvbzgmhvp1511",'38016'),
       (9,b'1',"7639 Mauris Avenue","Springdale","nharrison@email.com","Nissim","16-11-09","Harrison","TWF42XZY1JM","1","2","AR","Lyeborrpixnlhwm5526",'38016'),
       (10,b'1',"1682 Fleet St.","Memphis","kbridson@memphis.edu","Katie","16-1-1","Bridson","$2a$10$N04753bFYBHb1v1rjqdd7.5xjCFeyrGtbc8B7cjDwZnZ7MYIeUU0C","1","2","TN","kbrid63",'38016'),
       (11,b'1',"1368 Fox Trace Dr.","Cordova","Ziegenkonigreality@gmail.com","Alex","16-1-1","Ziegenhorn","$2a$10$N04753bFYBHb1v1rjqdd7.5xjCFeyrGtbc8B7cjDwZnZ7MYIeUU0C","1","2","TN","Ziegenkonig",'38016'),
       (12,b'1',"1242 Address Ave","Memphis","aedorris@memphis.edu","Allen","16-1-1","Dorris","$2a$10$N04753bFYBHb1v1rjqdd7.5xjCFeyrGtbc8B7cjDwZnZ7MYIeUU0C","1","2","TN","aedorris",'38122');

INSERT INTO vendors (vendor_id,address,city,contact_name,name,phone,primary_email,state,zipcode) 
VALUES (1,"2398 Lacus, Ave","Spokane","Rhea E. Sanford","Enim Mi Tempor Consulting","1-677-390-5331","aliquet.liberoprojects@nuncsedpede.edu","WA",'38016'),
	   (2,"P.O. Box 335, 7539 Sed, Street","San Jose","Lillian Q. Knowles","Ac Eleifend Associates","1-979-689-4099","interdum@In.com","CA",'38016'),
       (3,"8614 Morbi Road","Kapolei","Lois U. Buchanan","Ut Pellentesque Eget Industries","1-820-307-6493","Praesent@orci.com","HI",'38016');

INSERT INTO projects (project_id,billing_rate,client_location,client_name,name,vendor_id) 
VALUES (1,"300.00","6389 Cras Av.","Fermentum Limited","Magna Et Consulting",1),
	   (2,"450.00","4862 Leo. Rd.","Et Risus Quisque Ltd","Cras Institute",2),
       (3,"200.00","3404 Fusce Av.","Diam At Pretium Corporation","Orci Quis Industries",3),
       (4,"400.00","8512 Malesuada Av.","Nullam Ut Nisi Foundation","Primis Industries",1);
       
INSERT INTO projects_employees (project_employee_id,date_ended,date_started,pay_rate,emp_id,project_id) 
VALUES (1,NULL,"16-03-10","50.00",1,1),
	   (2,NULL,"16-03-10","125.00",2,2),
       (3,NULL,"16-03-10","50.00",3,3),
       (4,NULL,"16-03-10","125.00",4,4),
       (5,NULL,"16-03-10","125.00",5,1),
       (6,NULL,"16-03-10","125.00",6,2),
       (7,NULL,"16-03-10","50.00",7,3),
       (8,NULL,"16-03-10","125.00",8,4),
       (9,NULL,"16-03-10","100.00",9,1),
       (10,NULL,"16-03-10","125.00",1,2),
       (11,NULL,"16-03-10","75.00",2,3),
       (12,NULL,"16-03-10","125.00",3,4);