-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2020-02-20 15:19:03
-- 服务器版本： 8.0.16
-- PHP Version: 7.3.5

--
-- GROUP_BLOG
--
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `group_blog`
--
CREATE DATABASE IF NOT EXISTS `group_blog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `group_blog`;

-- --------------------------------------------------------

--
-- 表的结构 `blog`
--
-- 创建时间： 2020-01-30 01:47:05
-- 最后更新： 2020-02-19 14:50:59
--

CREATE TABLE IF NOT EXISTS `blog` (
  `blog_id` bigint(20) unsigned NOT NULL COMMENT '博客id',
  `source_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '博客来源Id',
  `uid` bigint(20) unsigned NOT NULL,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `author` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '作者',
  `is_original` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否原创',
  `pub_time` datetime NOT NULL COMMENT '发布时间',
  `summary` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '概要',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '文章内容',
  `original_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '原文链接',
  `tags` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- 表的结构 `blog_type`
--
-- 创建时间： 2020-01-30 03:17:39
--

CREATE TABLE IF NOT EXISTS `blog_type` (
  `type_id` int(10) unsigned NOT NULL COMMENT '类型id',
  `type_name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名',
  `overview_rule` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户中心表达式',
  `blog_page_rule` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '博客页url表达式',
  `feed_rule` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'rss订阅',
  `need_arg` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '博客类型需要的参数',
  `bean_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行抓取任务bean的名字'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- 表的结构 `category`
--
-- 创建时间： 2020-01-19 03:05:02
--

CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int(10) unsigned NOT NULL COMMENT '分类id',
  `name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- 表的结构 `tag`
--
-- 创建时间： 2020-01-11 10:17:19
--

CREATE TABLE IF NOT EXISTS `tag` (
  `tag_id` int(10) unsigned NOT NULL COMMENT '标签id',
  `tag_name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- 表的结构 `user`
--
-- 创建时间： 2020-01-20 11:33:19
-- 最后更新： 2020-02-19 14:50:59
--

CREATE TABLE IF NOT EXISTS `user` (
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `username` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(35) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `qq` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'QQ 号',
  `signature` varchar(25) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '个性签名',
  `blog_type` int(10) unsigned NOT NULL COMMENT '博客类型',
  `blog_arg` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_pub_time` datetime NOT NULL COMMENT '最新发布博客的时间',
  `category_id` int(10) unsigned NOT NULL COMMENT '分组id',
  `authority` int(11) NOT NULL COMMENT '权限'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blog`
--
ALTER TABLE `blog`
  ADD PRIMARY KEY (`blog_id`),
  ADD UNIQUE KEY `UNI_SOURCE_ID` (`source_id`),
  ADD KEY `uid` (`uid`),
  ADD FULLTEXT KEY `title` (`title`,`content`);

--
-- Indexes for table `blog_type`
--
ALTER TABLE `blog_type`
  ADD PRIMARY KEY (`type_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`),
  ADD KEY `blog_type` (`blog_type`),
  ADD KEY `category_id` (`category_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blog`
--
ALTER TABLE `blog`
  MODIFY `blog_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '博客id';
--
-- AUTO_INCREMENT for table `blog_type`
--
ALTER TABLE `blog_type`
  MODIFY `type_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '类型id';
--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类id';
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `uid` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id';
--
-- 限制导出的表
--

--
-- 限制表 `blog`
--
ALTER TABLE `blog`
  ADD CONSTRAINT `FK_BLOG_USER` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 限制表 `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK_USER_CATEGORY` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `FK_USER_TYPE` FOREIGN KEY (`blog_type`) REFERENCES `blog_type` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
