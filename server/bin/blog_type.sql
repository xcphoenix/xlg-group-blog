-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2020-02-20 16:07:56
-- 服务器版本： 8.0.16
-- PHP Version: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `group_blog`
--

-- --------------------------------------------------------

--
-- 表的结构 `blog_type`
--

CREATE TABLE IF NOT EXISTS `blog_type` (
  `type_id` int(10) unsigned NOT NULL COMMENT '类型id',
  `type_name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名',
  `overview_rule` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户中心表达式',
  `blog_page_rule` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '博客页url表达式',
  `feed_rule` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'rss订阅',
  `need_arg` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '博客类型需要的参数',
  `bean_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行抓取任务bean的名字'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 转存表中的数据 `blog_type`
--

INSERT INTO `blog_type` (`type_id`, `type_name`, `overview_rule`, `blog_page_rule`, `feed_rule`, `need_arg`, `bean_name`) VALUES
(1, 'csdn', 'https://blog.csdn.net/{{username}}/article/list/', 'https://blog.csdn.net/{{username}}/article/details/', 'https://blog.csdn.net/{{username}}/rss/list', '[\n  {\n    "param": "username", \n    "description": "CSDN博客用户名"\n  }\n]', 'crawl-csdn'),
(2, 'atomv1.0', NULL, NULL, '{{atomUrl}}', '[\r\n  {\r\n    "param": "atomUrl", \r\n    "description": "基于atomv1.0的订阅链接（包含Http等协议）"\r\n  }\r\n]', 'crawl-atomv1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blog_type`
--
ALTER TABLE `blog_type`
  ADD PRIMARY KEY (`type_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blog_type`
--
ALTER TABLE `blog_type`
  MODIFY `type_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '类型id',AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
