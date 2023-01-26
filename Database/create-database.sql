/*
Cafe Sanchez v1 - Case Study


*/



USE [master]
GO


/****** Database [CafeSanchez] ******/
CREATE DATABASE [CafeSanchez]
GO

USE [CafeSanchez]
GO

/****** Table [Orders] ******/
CREATE TABLE [Orders](
	[CustomerName] [nvarchar](50) PRIMARY KEY NOT NULL,
	[Date] [datetime] NOT NULL,
	[Status] [nvarchar](50) DEFAULT (N'New') NOT NULL
) 
GO

/****** Table [Products] n******/
CREATE TABLE [Products](
	[Name] [nvarchar](50) PRIMARY KEY NOT NULL,
	[Description] [nvarchar](max) NULL,
	[Price] [decimal](18, 2) NULL
) 
GO

/****** Table [OrderLines] ******/
CREATE TABLE [OrderLines](
	[OrderCustomerName] [nvarchar](50) FOREIGN KEY REFERENCES [Orders] ([CustomerName]) NOT NULL,
	[ProductName] [nvarchar](50) FOREIGN KEY REFERENCES [Products] ([Name]) NOT NULL,
	[Quantity] [int] NOT NULL,
	CONSTRAINT [PK_OrderLines] PRIMARY KEY CLUSTERED 
	(
		[OrderCustomerName] ASC,
		[ProductName] ASC
	)
) 
GO