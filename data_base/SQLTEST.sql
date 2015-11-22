/*********************************
学号：1510122557
姓名：陈昇辉
*********************************/

/********************************
一、建库
*********************************/
--切换到系统主数据库
USE master;
GO --完成上面指令后再向下执行

CREATE DATABASE stu
ON
PRIMARY
	(NAME=stu1,
	FILENAME='C:\SQLserver\data\Stu\studata.mdf',
	SIZE=100MB,
	MAXSIZE=200,
	FILEGROWTH=20)
LOG ON
	(NAME=stulog,
	FILENAME='C:\SQLserver\data\Stu\stulog.ldf',
	SIZE=100MB,
	MAXSIZE=200,
	FILEGROWTH=20);
	GO
--切换到自己的数据库
USE stu
GO
/***********************************
二、建表
***********************************/
--student表
CREATE TABLE Student(
	Sno CHAR(5) PRIMARY KEY,
	Sname CHAR(20) NOT NULL,
	Ssex CHAR(2) CHECK(Ssex IN ('M','F')),
	Sdept CHAR(2)
);
GO
--course表
CREATE TABLE Course(
	Cname CHAR(10) UNIQUE NOT NULL,
	Cno CHAR(4) PRIMARY KEY,
	Cpno CHAR(4),
	Ccredit SMALLINT,
	FOREIGN KEY (Cpno) REFERENCES Course(Cno)
);
GO 
--course/student表
CREATE TABLE SC(
	Sno CHAR(5),
	Cno CHAR(4),
	Grade SMALLINT CHECK(Grade>=0 AND Grade<=100),
	FOREIGN KEY (Sno)REFERENCES Student(Sno),
	FOREIGN KEY(Cno)REFERENCES Course(Cno)
);
GO
--插入学生数据
INSERT INTO Student(Sname,Sno,Ssex,Sdept) VALUES('李勇','95001','M','CS');
INSERT INTO Student(Sname,Sno,Ssex,Sdept) VALUES('刘晨','95002','F','IS');
INSERT INTO Student(Sname,Sno,Ssex,Sdept) VALUES('王敏','95003','F','MA');
INSERT INTO Student(Sname,Sno,Ssex,Sdept) VALUES('张立','95004','M','IS');

--插入课程数据
INSERT INTO Course(Cno,Cname,Ccredit) VALUES('C6','数据处理',2)
INSERT INTO Course(Cno,Cname,Ccredit) VALUES('C2','高等数学',2)
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C7','PASCAL',4,'C6')
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C5','数据结构',4,'C7')
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C1','数据库',4,'C5')
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C3','信息系统',4,'C1')
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C4','操作系统',3,'C6')

--插入学生成绩
INSERT INTO SC(Sno,Cno,Grade) VALUES('95001','C1',92)
INSERT INTO SC(Sno,Cno,Grade) VALUES('95001','C2',65)
INSERT INTO SC(Sno,Cno,Grade) VALUES('95001','C4',88)
INSERT INTO SC(Sno,Cno,Grade) VALUES('95002','C2',90)
INSERT INTO SC(Sno,Cno,Grade) VALUES('95002','C5',73)

--插入数据
--删除表
DROP TABLE SC
DROP TABLE Student
DROP TABLE Course
GO
--删除数据库
USE master
GO
DROP DATABASE stu
