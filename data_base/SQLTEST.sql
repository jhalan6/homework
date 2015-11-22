/*********************************
ѧ�ţ�1510122557
�������N��
*********************************/

/********************************
һ������
*********************************/
--�л���ϵͳ�����ݿ�
USE master;
GO --�������ָ���������ִ��

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
--�л����Լ������ݿ�
USE stu
GO
/***********************************
��������
***********************************/
--student��
CREATE TABLE Student(
	Sno CHAR(5) PRIMARY KEY,
	Sname CHAR(20) NOT NULL,
	Ssex CHAR(2) CHECK(Ssex IN ('M','F')),
	Sdept CHAR(2)
);
GO
--course��
CREATE TABLE Course(
	Cname CHAR(10) UNIQUE NOT NULL,
	Cno CHAR(4) PRIMARY KEY,
	Cpno CHAR(4),
	Ccredit SMALLINT,
	FOREIGN KEY (Cpno) REFERENCES Course(Cno)
);
GO 
--course/student��
CREATE TABLE SC(
	Sno CHAR(5),
	Cno CHAR(4),
	Grade SMALLINT CHECK(Grade>=0 AND Grade<=100),
	FOREIGN KEY (Sno)REFERENCES Student(Sno),
	FOREIGN KEY(Cno)REFERENCES Course(Cno)
);
GO
--����ѧ������
INSERT INTO Student(Sname,Sno,Ssex,Sdept) VALUES('����','95001','M','CS');
INSERT INTO Student(Sname,Sno,Ssex,Sdept) VALUES('����','95002','F','IS');
INSERT INTO Student(Sname,Sno,Ssex,Sdept) VALUES('����','95003','F','MA');
INSERT INTO Student(Sname,Sno,Ssex,Sdept) VALUES('����','95004','M','IS');

--����γ�����
INSERT INTO Course(Cno,Cname,Ccredit) VALUES('C6','���ݴ���',2)
INSERT INTO Course(Cno,Cname,Ccredit) VALUES('C2','�ߵ���ѧ',2)
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C7','PASCAL',4,'C6')
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C5','���ݽṹ',4,'C7')
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C1','���ݿ�',4,'C5')
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C3','��Ϣϵͳ',4,'C1')
INSERT INTO Course(Cno,Cname,Ccredit,Cpno) VALUES('C4','����ϵͳ',3,'C6')

--����ѧ���ɼ�
INSERT INTO SC(Sno,Cno,Grade) VALUES('95001','C1',92)
INSERT INTO SC(Sno,Cno,Grade) VALUES('95001','C2',65)
INSERT INTO SC(Sno,Cno,Grade) VALUES('95001','C4',88)
INSERT INTO SC(Sno,Cno,Grade) VALUES('95002','C2',90)
INSERT INTO SC(Sno,Cno,Grade) VALUES('95002','C5',73)

--��������
--ɾ����
DROP TABLE SC
DROP TABLE Student
DROP TABLE Course
GO
--ɾ�����ݿ�
USE master
GO
DROP DATABASE stu
