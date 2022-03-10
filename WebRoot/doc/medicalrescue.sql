/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : medicalrescue

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2019-01-16 18:34:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accident
-- ----------------------------
DROP TABLE IF EXISTS `accident`;
CREATE TABLE `accident` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(225) DEFAULT NULL,
  `content` varchar(4000) DEFAULT NULL,
  `lat` varchar(50) DEFAULT NULL,
  `lng` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `site` varchar(255) DEFAULT NULL,
  `photopath` varchar(500) DEFAULT NULL,
  `radius1` varchar(20) DEFAULT NULL,
  `radius2` varchar(20) DEFAULT NULL,
  `radius3` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accident
-- ----------------------------
INSERT INTO `accident` VALUES ('5', '天津港特别重大火灾爆炸事故特别重大火灾爆炸事故特别', '<p><br/></p><p style=\"font-family: 微软雅黑; white-space: normal;  margin-top: 0px; margin-bottom: 0px; line-height: 1.5; font-size: 13px; text-indent: 2em; text-align: left;\">2015年8月12日22时51分46秒，位于天津市滨海新区吉运二道95号的瑞海公司危险品仓库(北纬39°02′22.98″，东经117 °44′11.64″)运抵区最先起火，23时34分06秒发生第一次爆炸，23时34分37秒发生第二次更剧烈的爆炸。</p><p style=\"font-family: 微软雅黑; white-space: normal;  margin-top: 0px; margin-bottom: 0px; line-height: 1.5; font-size: 13px; text-indent: 2em; text-align: left;\">事故现场情况：事故现场按受损程度，分为事故中心区、爆炸冲击波波及区。严重受损区在不同方向距爆炸中心最远距离为：东3公里，西3.6公里，南2.5公里，北2.8公里。中度受损区在不同方向距爆炸中心最远距离为：东3.42公里，西5.4 公里，南5公里，北5.4公里。</p><p style=\"font-family: 微软雅黑; white-space: normal;  margin-top: 0px; margin-bottom: 0px; line-height: 1.5; font-size: 13px; text-indent: 2em; text-align: left;\"><br/></p><p><br/></p>', '39.016677', '117.716096', null, '天津市天津市滨海新区港滨路768号瑞海船舶技术有限公司', '07d9f4fa7ca04f32bdb1e50ed065da28.jpg', '2600', '4600', '6200');
INSERT INTO `accident` VALUES ('6', '台湾海峡发生6.2级地震', '<p>11月26日07时57分在台湾海峡（北纬23.28度，东经118.60度）发生6.2级地震，震源深度20千米。</p>', '39.157024', '117.208082', '2018-11-26 08:19:00', '天津市天津市河北区金纬路293号', null, '2000', '3000', '4000');

-- ----------------------------
-- Table structure for core_menu
-- ----------------------------
DROP TABLE IF EXISTS `core_menu`;
CREATE TABLE `core_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parentid` int(11) DEFAULT NULL,
  `grade` int(2) DEFAULT NULL,
  `sequence` int(11) NOT NULL DEFAULT '0',
  `leaf` int(2) DEFAULT NULL,
  `menuname` varchar(200) DEFAULT NULL,
  `entrypoint` varchar(200) DEFAULT '',
  `ispopwindow` int(2) NOT NULL DEFAULT '0',
  `icon` varchar(100) DEFAULT '',
  `isview` int(2) NOT NULL DEFAULT '1',
  `xtype` varchar(255) DEFAULT NULL,
  `menudesc` varchar(500) DEFAULT '',
  `isiframe` int(2) DEFAULT NULL,
  `deltime` datetime DEFAULT NULL,
  `deluserid` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `createuserid` int(11) DEFAULT NULL,
  `lastupdatetime` datetime DEFAULT NULL,
  `lastupdateuserid` int(11) DEFAULT NULL,
  `accidenttype` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27955 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of core_menu
-- ----------------------------
INSERT INTO `core_menu` VALUES ('1', '0', '0', '1', '0', '功能菜单', null, '0', 'x-fa fa-list', '1', null, '功能菜单说明文字', '0', null, null, null, null, null, null, null);
INSERT INTO `core_menu` VALUES ('2', '1', '1', '100', '0', '系统管理', '', '0', 'x-fa fa-gears', '1', '', null, null, null, null, null, null, '2018-09-17 16:20:50', '-100', null);
INSERT INTO `core_menu` VALUES ('21897', '2', '2', '10', '1', '菜单管理', 'admin/system/MenuPanel.jsp', '0', 'x-fa fa-list-ol', '1', 'menumanagerpanel', '', null, null, null, null, null, '2018-12-28 13:08:43', null, null);
INSERT INTO `core_menu` VALUES ('22523', '2', '2', '5', '1', '字典数据', 'admin/system/DictPanel.jsp', '0', 'x-fa fa-book', '1', 'dictmanagerpanel', '', null, null, null, null, null, '2018-12-27 14:57:08', null, null);
INSERT INTO `core_menu` VALUES ('27914', '1', '1', '1', '0', '事故概况', '', '0', 'x-fa fa-info-circle', '1', 'accidentmappanel', '', null, null, null, '2018-12-27 16:04:33', null, '2018-12-29 16:08:16', null, null);
INSERT INTO `core_menu` VALUES ('27915', '1', '1', '2', '0', '风险评估', null, '0', 'x-fa fa-paste', '1', null, '', null, null, null, '2018-12-27 16:05:59', null, '2018-12-27 16:21:03', null, null);
INSERT INTO `core_menu` VALUES ('27916', '27915', '2', '4', '1', '事故性质分析（ 爆炸物性质、种类、数量等）', '', '0', 'x-fa fa-file-text-o', '1', '', '', null, null, null, '2018-12-27 16:07:03', null, '2018-12-27 17:51:47', null, null);
INSERT INTO `core_menu` VALUES ('27917', '1', '1', '3', '0', '抽组专家组', null, '0', 'x-fa fa-users', '1', null, '', null, null, null, '2018-12-27 16:14:13', null, '2018-12-27 16:14:58', null, null);
INSERT INTO `core_menu` VALUES ('27918', '1', '1', '4', '0', '现场救治与医疗后送', null, '0', 'x-fa fa-medkit', '1', null, '', null, null, null, '2018-12-27 16:17:22', null, '2019-01-02 15:26:10', null, null);
INSERT INTO `core_menu` VALUES ('27919', '1', '1', '5', '1', '救援协同', 'accident/AccidentMapPenel.jsp?pageflag=rescue', '0', 'x-fa fa-link', '1', 'accidentmappanel', '', null, null, null, '2018-12-27 16:18:53', null, '2018-12-28 22:36:40', null, 'rescue');
INSERT INTO `core_menu` VALUES ('27920', '1', '1', '6', '0', '信息汇总上报', null, '0', 'x-fa fa-map-o', '1', null, '', null, null, null, '2018-12-27 16:19:55', null, '2018-12-27 16:19:55', null, null);
INSERT INTO `core_menu` VALUES ('27921', '27915', '2', '1', '1', '居住人数', '', '0', 'x-fa fa-child', '1', '', '', null, null, null, '2018-12-27 16:22:17', null, '2018-12-27 17:48:26', null, null);
INSERT INTO `core_menu` VALUES ('27922', '27915', '2', '2', '1', '建筑物损毁情况', '', '0', 'x-fa fa-building-o', '1', '', '', null, null, null, '2018-12-27 16:23:55', null, '2018-12-27 17:51:34', null, null);
INSERT INTO `core_menu` VALUES ('27923', '27915', '2', '3', '1', '道路交通情况', '', '0', 'x-fa fa-level-up', '1', '', '', null, null, null, '2018-12-27 16:24:48', null, '2018-12-27 17:51:41', null, null);
INSERT INTO `core_menu` VALUES ('27924', '27915', '2', '5', '1', '伤员预计', '', '0', 'x-fa fa-wheelchair', '1', '', '', null, null, null, '2018-12-27 16:26:27', null, '2018-12-27 17:51:53', null, null);
INSERT INTO `core_menu` VALUES ('27925', '27915', '2', '6', '1', '伤情分析', '', '0', 'x-fa fa-pencil-square-o', '1', '', '', null, null, null, '2018-12-27 16:27:00', null, '2018-12-27 17:51:58', null, null);
INSERT INTO `core_menu` VALUES ('27926', '27915', '2', '7', '1', '天津市医疗资源分布情况', 'accident/AccidentMapPenel.jsp?pageflag=medical', '0', 'x-fa fa-bullseye', '1', 'accidentmappanel', '', null, null, null, '2018-12-27 16:27:49', null, '2019-01-07 16:49:58', null, 'medical');
INSERT INTO `core_menu` VALUES ('27927', '27917', '2', '1', '1', '抽组机构', '', '0', 'x-fa fa-indent', '1', '', '', null, null, null, '2018-12-27 16:29:54', null, '2018-12-27 17:52:11', null, null);
INSERT INTO `core_menu` VALUES ('27928', '27917', '2', '2', '1', '抽组专业', '', '0', 'x-fa fa-external-link', '1', '', '', null, null, null, '2018-12-27 16:30:21', null, '2018-12-27 17:52:17', null, null);
INSERT INTO `core_menu` VALUES ('27929', '27917', '2', '3', '1', '抽组人数', '', '0', 'x-fa fa-retweet', '1', '', '', null, null, null, '2018-12-27 16:30:40', null, '2018-12-27 17:52:22', null, null);
INSERT INTO `core_menu` VALUES ('27930', '27918', '2', '1', '0', '医疗救援力量抽组', null, '0', 'x-fa fa-user-plus', '1', null, '', null, null, null, '2018-12-27 16:38:47', null, '2018-12-27 16:38:47', null, null);
INSERT INTO `core_menu` VALUES ('27931', '27930', '3', '1', '0', '出队规模', null, '0', 'x-fa fa-skyatlas', '1', null, '', null, null, null, '2018-12-27 16:40:05', null, '2018-12-27 16:43:47', null, null);
INSERT INTO `core_menu` VALUES ('27932', '27931', '4', '1', '1', '（1）确定出队模式（20人，30人，50人，100人）', '', '0', 'x-fa fa-street-view', '1', '', '', null, null, null, '2018-12-27 16:41:06', null, '2018-12-27 19:24:17', null, null);
INSERT INTO `core_menu` VALUES ('27933', '27931', '4', '2', '1', '（2）确定抽组专业（取决于事故性质）', '', '0', 'x-fa fa-check-square-o', '1', '', '', null, null, null, '2018-12-27 16:42:18', null, '2018-12-27 19:24:22', null, null);
INSERT INTO `core_menu` VALUES ('27934', '27930', '3', '2', '1', '任务时限要求', '', '0', 'x-fa fa-clock-o', '1', '', '', null, null, null, '2018-12-27 16:43:40', null, '2018-12-27 19:17:58', null, null);
INSERT INTO `core_menu` VALUES ('27935', '27930', '3', '3', '1', '开进路径选择', 'accident/AccidentMapPenel.jsp?pageflag=startpath', '0', 'x-fa fa-yahoo', '1', 'accidentmappanel', '', null, null, null, '2018-12-27 16:44:43', null, '2019-01-02 11:46:17', null, 'startpath');
INSERT INTO `core_menu` VALUES ('27936', '27930', '3', '4', '1', '营地选择', 'accident/AccidentMapPenel.jsp?pageflag=camp', '0', 'x-fa fa-university', '1', 'accidentmappanel', '', null, null, null, '2018-12-27 16:45:19', null, '2019-01-09 23:59:51', null, 'camp');
INSERT INTO `core_menu` VALUES ('27937', '27918', '2', '2', '0', '药品、耗材、装备筹措与供应', null, '0', 'x-fa fa-user-md', '1', null, '', null, null, null, '2018-12-27 16:46:37', null, '2018-12-27 16:46:37', null, null);
INSERT INTO `core_menu` VALUES ('27938', '27937', '3', '1', '1', '天津市各应急物资装备库分布', '', '0', 'x-fa fa-spinner', '1', '', '', null, null, null, '2018-12-27 16:47:27', null, '2018-12-27 19:18:16', null, null);
INSERT INTO `core_menu` VALUES ('27939', '27937', '3', '2', '1', '天津市卫生应急队伍装备参考目录（试行）', 'documents/showRanksFilePanel.jsp', '0', 'x-fa fa-align-right', '1', 'showranksfilepanel', '', null, null, null, '2018-12-27 16:48:04', null, '2018-12-28 18:55:44', null, null);
INSERT INTO `core_menu` VALUES ('27940', '27937', '3', '3', '1', '天津市卫生应急物资储备参考目录（试行）', 'documents/showMatterFilePanel.jsp', '0', 'x-fa fa-list', '1', 'showmatterfilepanel', '', null, null, null, '2018-12-27 16:48:34', null, '2018-12-28 21:16:03', null, null);
INSERT INTO `core_menu` VALUES ('27941', '27918', '2', '3', '0', '医疗后送机构选择', null, '0', 'x-fa fa-delicious', '1', null, '', null, null, null, '2018-12-27 16:49:26', null, '2018-12-27 16:49:26', null, null);
INSERT INTO `core_menu` VALUES ('27942', '27941', '3', '1', '1', '天津市医疗资源分布情况', 'accident/AccidentMapPenel.jsp?pageflag=medical_mp', '0', 'x-fa fa-sitemap', '1', 'accidentmappanel', '', null, null, null, '2018-12-27 16:50:19', null, '2018-12-28 21:21:14', null, 'medical_mp');
INSERT INTO `core_menu` VALUES ('27943', '27941', '3', '2', '0', '后送工具选择', null, '0', 'x-fa fa-wrench', '1', null, '', null, null, null, '2018-12-27 16:52:12', null, '2018-12-27 16:52:12', null, null);
INSERT INTO `core_menu` VALUES ('27944', '27943', '4', '1', '1', '空中', '', '0', 'x-fa fa-fighter-jet', '1', '', '', null, null, null, '2018-12-27 16:53:29', null, '2018-12-27 19:18:41', null, null);
INSERT INTO `core_menu` VALUES ('27945', '27943', '4', '2', '1', '陆地', '', '0', 'x-fa fa-automobile', '1', '', '', null, null, null, '2018-12-27 16:54:12', null, '2018-12-27 19:18:47', null, null);
INSERT INTO `core_menu` VALUES ('27946', '27943', '4', '3', '1', '海（水）上', '', '0', 'x-fa fa-ship', '1', '', '', null, null, null, '2018-12-27 16:55:53', null, '2018-12-27 19:18:52', null, null);
INSERT INTO `core_menu` VALUES ('27947', '27941', '3', '3', '1', '后送路径选择', 'accident/AccidentMapPenel.jsp?pageflag=endpath', '0', 'x-fa fa-connectdevelop', '1', 'accidentmappanel', '', null, null, null, '2018-12-27 16:56:35', null, '2019-01-10 11:18:45', null, 'endpath');
INSERT INTO `core_menu` VALUES ('27948', '27920', '2', '1', '1', '负责部门', '', '0', 'x-fa fa-bookmark-o', '1', '', '', null, null, null, '2018-12-27 16:59:25', null, '2018-12-27 19:19:10', null, null);
INSERT INTO `core_menu` VALUES ('27949', '27920', '2', '2', '1', '上报时限、部门及内容 <br/>（轻伤及重伤人数、性别，其中，儿童数量为…，<br/>门诊及留观人数、手术人数、死亡人数等）', '', '0', 'x-fa fa-commenting-o', '1', '', '', null, null, null, '2018-12-27 16:59:53', null, '2018-12-27 19:19:44', null, null);
INSERT INTO `core_menu` VALUES ('27950', '27920', '2', '3', '1', '信息归口发布', '', '0', 'x-fa fa-pie-chart', '1', '', '', null, null, null, '2018-12-27 17:00:35', null, '2018-12-27 19:19:21', null, null);
INSERT INTO `core_menu` VALUES ('27951', '2', '2', '1', '1', '医疗资源管理', 'map/MedicalPanel.jsp', '0', 'x-fa fa-ambulance', '1', 'medicallistpanel', '', null, null, null, '2018-12-28 13:09:34', null, '2018-12-28 13:09:34', null, null);
INSERT INTO `core_menu` VALUES ('27952', '2', '2', '2', '1', '救援协助管理', 'map/RescuePanel.jsp', '0', 'x-fa fa-hand-grab-o', '1', 'rescuelistpanel', '', null, null, null, '2018-12-28 22:37:19', null, '2018-12-28 22:38:03', null, null);
INSERT INTO `core_menu` VALUES ('27953', '2', '2', '3', '1', '事故管理', 'accident/AccidentPanel.jsp', '0', 'x-fa fa-leanpub', '1', 'accidentlist', '', null, null, null, '2018-12-29 15:54:40', null, '2018-12-29 15:54:40', null, null);
INSERT INTO `core_menu` VALUES ('27954', '2', '2', '100', '1', '爬虫配置', 'admin/system/PythonPanel.jsp', '0', 'x-fa fa-lastfm', '1', 'pythonpanel', '', null, null, null, '2019-01-03 13:42:17', null, '2019-01-03 13:42:17', null, null);

-- ----------------------------
-- Table structure for core_path
-- ----------------------------
DROP TABLE IF EXISTS `core_path`;
CREATE TABLE `core_path` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `urlpath` varchar(500) DEFAULT NULL,
  `keyword` varchar(500) DEFAULT NULL,
  `must_keyword` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of core_path
-- ----------------------------
INSERT INTO `core_path` VALUES ('1', 'http://news.sina.com.cn/zt/col_china/', '爆炸、地震、坍塌、起火、事故、大火', '天津');

-- ----------------------------
-- Table structure for medical
-- ----------------------------
DROP TABLE IF EXISTS `medical`;
CREATE TABLE `medical` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `hospitalname` varchar(225) DEFAULT NULL,
  `haddress` varchar(225) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `bed` int(4) DEFAULT NULL,
  `advantage` varchar(225) DEFAULT NULL,
  `position` varchar(225) DEFAULT NULL,
  `lat` varchar(20) DEFAULT NULL,
  `lng` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of medical
-- ----------------------------
INSERT INTO `medical` VALUES ('4', '天津市第四中心医院', '天津市天津市河北区中山路3号天津市第四中心医院', '022-26183057', '1000', '心血管内科、耳鼻咽喉头颈外科、肿瘤血液科、骨科、神经内科', '北部', '39.169942', '117.215303');
INSERT INTO `medical` VALUES ('5', '解放军第254医院', '天津市天津市河北区中国人民解放军二五四医院', '022-26220266', '750', '心血管科、眼科', '北部', '39.167481', '117.198093');
INSERT INTO `medical` VALUES ('6', '天津中医药大学第二附属医院', '天津市天津市河北区天津中医药大学第二附属医院', '022-60335320', '504', '肺病科、脑病科、妇科', '东北', '39.163723', '117.244255');
INSERT INTO `medical` VALUES ('7', '天津市中医药研究院附属医院', '天津市天津市红桥区北马路354号天津市中医药研究院附属医院', '022-27339608', '710', '儿童皮肤科和儿童内科', '东北', '39.15268', '117.184025');
INSERT INTO `medical` VALUES ('8', '天津市人民医院', '天津市天津市红桥区天津市人民医院', '022-87729595,800-622-9595', '1500', '消化外科、脊柱外科', '西部', '39.15365', '117.153779');
INSERT INTO `medical` VALUES ('9', '天津市南开医院', '天津市天津市南开区三纬路122号天津南开医院-主楼', '022-27022268', '1500', '中西医结合', '西南', '39.134113', '117.177246');
INSERT INTO `medical` VALUES ('10', '天津市中心妇产科医院', '天津市天津市南开区天津市中心妇产科医院', '022-58287742,022-58287388', '608', '妇科、产科', '西南', '39.132491', '117.179295');
INSERT INTO `medical` VALUES ('11', '天津市口腔医院', '天津市天津市和平区大沽北路75号天津市口腔医院', '022-27119191', '80', '口腔正畸科', '南部', '39.133689', '117.212504');
INSERT INTO `medical` VALUES ('12', '天津市眼科医院', '天津市天津市和平区甘肃路4号天津市眼科医院', '022-27313336', '200', '斜视弱视与小儿眼科', '西南', '39.130143', '117.191736');
INSERT INTO `medical` VALUES ('13', '中国医学科学院血液病医院', '天津市天津市和平区中国医学科学院血液病医院', '022-23909999', '600', '血液科', '西南', '39.127754', '117.189724');
INSERT INTO `medical` VALUES ('14', '天津市第二人民医院', '天津市天津市南开区苏堤路75号天津市第二人民医院', '022-23372212', '400', '传染病', '西南', '39.119587', '117.158156');
INSERT INTO `medical` VALUES ('15', '中国医学科学院放射医学研究所', '天津市天津市南开区白堤路238中国医学科学院放射医学研究所', '022-85682291', null, '卫生部核事故医学应急中心', '东南', '39.113326', '117.161307');
INSERT INTO `medical` VALUES ('16', '天津市儿童医院', '天津市天津市河西区天津儿童医院', '022-58116666', '521', '新生儿疾病、儿童心血管（内、外科）疾病、儿科急救', '南部', '39.106883', '117.206076');
INSERT INTO `medical` VALUES ('17', '武警后勤学院附属医院', '天津市天津市东丽区武警后勤学院附属医院', '022-60578778', '1200', '心脏医院、脑科医院', '东南', '39.134907', '117.287073');
INSERT INTO `medical` VALUES ('18', '天津市第三中心医院', '天津市天津市河东区大桥道78号天津市第三中心医院', '022-84112114', '1600', '肝胆学科、心脏学科', '东南', '39.116485', '117.255764');
INSERT INTO `medical` VALUES ('19', '天津市天津医院', '天津市天津市河西区天津市天津医院（河西院区）', '022-28332917', '2533', '天津市创伤急救中心、骨科', '东南', '39.099424', '117.237201');
INSERT INTO `medical` VALUES ('20', '天津医科大学第二医院', '天津市天津市河西区平江道23天津医科大学第二医院', '022-28331788', '1000', '泌尿外科', '南部', '39.08899', '117.226651');
INSERT INTO `medical` VALUES ('21', '天津医科大学肿瘤医院', '天津市天津市河西区天津市肿瘤医院', '022-23340123', '2000', '胸外科', '南部', '39.08591', '117.190323');
INSERT INTO `medical` VALUES ('22', '解放军第464医院', '天津市天津市南开区中国人民解放军第四六四医院', '022-84632888', '1200', '泌尿外科', '南部', '39.073059', '117.190398');
INSERT INTO `medical` VALUES ('23', '天津市胸科医院', '天津市天津市津南区天津市胸科医院', '022-88185111', '911', '心脏内科', '东南', '39.080181', '117.300537');
INSERT INTO `medical` VALUES ('24', '天津市环湖医院', '天津市天津市津南区天津市环湖医院', '022-59065906', '1000', '神经外科、神经内科', '东南', '39.078253', '117.299249');
INSERT INTO `medical` VALUES ('25', '天津中医药大学第一附属医院', '天津市天津市南开区鞍山西道314号天津中医药大学第一附属医院-门诊部', '022-27432299', '2600', '儿科', '西南', '39.121186', '117.171043');
INSERT INTO `medical` VALUES ('26', '天津市海河医院', '天津市天津市津南区天津市海河医院', '022-58830037', '810', '呼吸系统疾病', '东南', '39.048975', '117.328421');
INSERT INTO `medical` VALUES ('27', '天津市第五中心医院', '天津市天津市滨海新区西半圆路207号天津市第五中心医院', '022-65665000', '800', '重症医学科、骨科', '东南', '39.021438', '117.653759');
INSERT INTO `medical` VALUES ('28', '泰达国际心血管病医院', '天津市天津市滨海新区泰达国际心血管病医院', null, '500', '心力衰竭和急性心肌梗死', '东部', '39.034687', '117.721424');

-- ----------------------------
-- Table structure for rescue
-- ----------------------------
DROP TABLE IF EXISTS `rescue`;
CREATE TABLE `rescue` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(225) DEFAULT NULL,
  `address` varchar(225) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `lat` varchar(20) DEFAULT NULL,
  `lng` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rescue
-- ----------------------------
INSERT INTO `rescue` VALUES ('1', '解放军科学院三防医学大队某中队', '北京市北京市昌平区兴隆街3号南口镇', 'xxxx-58830196', '40.253067', '116.142117');
INSERT INTO `rescue` VALUES ('2', '北京通州蓝天救援队队部', '北京市北京市通州区北京通州蓝天救援队队部', '0xxxx2-65202000', '39.996465', '116.740237');
INSERT INTO `rescue` VALUES ('3', '广东警官学院', '广东省广州市白云区广东警官学院', 'xxxxx', '23.26039', '113.312989');
INSERT INTO `rescue` VALUES ('4', '中国医学科学院生物医学工程研究所', '天津市天津市南开区白堤路236中国医学科学院生物医学工程研究所', null, '39.114238', '117.161775');
INSERT INTO `rescue` VALUES ('5', '中国人民解放军总医院', '北京市北京市海淀区中国人民解放军总医院', '0101-2222', '39.9112', '116.283506');

-- ----------------------------
-- Table structure for selectitem
-- ----------------------------
DROP TABLE IF EXISTS `selectitem`;
CREATE TABLE `selectitem` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(200) DEFAULT NULL,
  `TYPE` varchar(100) DEFAULT NULL,
  `SEQUENCE` int(11) DEFAULT NULL,
  `ISVIEW` int(11) DEFAULT NULL,
  `PARENTCODE` varchar(20) DEFAULT NULL,
  `SHORTNAME` varchar(20) DEFAULT NULL,
  `ITEMCODE` varchar(20) DEFAULT NULL,
  `FULLCODE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=737 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of selectitem
-- ----------------------------
INSERT INTO `selectitem` VALUES ('8', 'fa-glass', '图标', '1', '1', null, null, '1', null);
INSERT INTO `selectitem` VALUES ('9', 'fa-music', '图标', '2', '1', null, null, '2', null);
INSERT INTO `selectitem` VALUES ('10', 'fa-search', '图标', '3', '1', null, null, '3', null);
INSERT INTO `selectitem` VALUES ('11', 'fa-envelope-o', '图标', '4', '1', null, null, '4', null);
INSERT INTO `selectitem` VALUES ('12', 'fa-heart', '图标', '5', '1', null, null, '5', null);
INSERT INTO `selectitem` VALUES ('13', 'fa-star', '图标', '6', '1', null, null, '6', null);
INSERT INTO `selectitem` VALUES ('14', 'fa-star-o', '图标', '7', '1', null, null, '7', null);
INSERT INTO `selectitem` VALUES ('15', 'fa-user', '图标', '8', '1', null, null, '8', null);
INSERT INTO `selectitem` VALUES ('16', 'fa-film', '图标', '9', '1', null, null, '9', null);
INSERT INTO `selectitem` VALUES ('17', 'fa-th-large', '图标', '10', '1', null, null, '10', null);
INSERT INTO `selectitem` VALUES ('18', 'fa-th', '图标', '11', '1', null, null, '11', null);
INSERT INTO `selectitem` VALUES ('19', 'fa-th-list', '图标', '12', '1', null, null, '12', null);
INSERT INTO `selectitem` VALUES ('20', 'fa-check', '图标', '13', '1', null, null, '13', null);
INSERT INTO `selectitem` VALUES ('21', 'fa-remove', '图标', '14', '1', null, null, '14', null);
INSERT INTO `selectitem` VALUES ('22', 'fa-close', '图标', '15', '1', null, null, '15', null);
INSERT INTO `selectitem` VALUES ('23', 'fa-times', '图标', '16', '1', null, null, '16', null);
INSERT INTO `selectitem` VALUES ('24', 'fa-search-plus', '图标', '17', '1', null, null, '17', null);
INSERT INTO `selectitem` VALUES ('25', 'fa-search-minus', '图标', '18', '1', null, null, '18', null);
INSERT INTO `selectitem` VALUES ('26', 'fa-power-off', '图标', '19', '1', null, null, '19', null);
INSERT INTO `selectitem` VALUES ('27', 'fa-signal', '图标', '20', '1', null, null, '20', null);
INSERT INTO `selectitem` VALUES ('28', 'fa-gear', '图标', '21', '1', null, null, '21', null);
INSERT INTO `selectitem` VALUES ('29', 'fa-cog', '图标', '22', '1', null, null, '22', null);
INSERT INTO `selectitem` VALUES ('30', 'fa-trash-o', '图标', '23', '1', null, null, '23', null);
INSERT INTO `selectitem` VALUES ('31', 'fa-home', '图标', '24', '1', null, null, '24', null);
INSERT INTO `selectitem` VALUES ('32', 'fa-file-o', '图标', '25', '1', null, null, '25', null);
INSERT INTO `selectitem` VALUES ('33', 'fa-clock-o', '图标', '26', '1', null, null, '26', null);
INSERT INTO `selectitem` VALUES ('34', 'fa-road', '图标', '27', '1', null, null, '27', null);
INSERT INTO `selectitem` VALUES ('35', 'fa-download', '图标', '28', '1', null, null, '28', null);
INSERT INTO `selectitem` VALUES ('36', 'fa-arrow-circle-o-down', '图标', '29', '1', null, null, '29', null);
INSERT INTO `selectitem` VALUES ('37', 'fa-arrow-circle-o-up', '图标', '30', '1', null, null, '30', null);
INSERT INTO `selectitem` VALUES ('38', 'fa-inbox', '图标', '31', '1', null, null, '31', null);
INSERT INTO `selectitem` VALUES ('39', 'fa-play-circle-o', '图标', '32', '1', null, null, '32', null);
INSERT INTO `selectitem` VALUES ('40', 'fa-rotate-right', '图标', '33', '1', null, null, '33', null);
INSERT INTO `selectitem` VALUES ('41', 'fa-repeat', '图标', '34', '1', null, null, '34', null);
INSERT INTO `selectitem` VALUES ('42', 'fa-refresh', '图标', '35', '1', null, null, '35', null);
INSERT INTO `selectitem` VALUES ('43', 'fa-list-alt', '图标', '36', '1', null, null, '36', null);
INSERT INTO `selectitem` VALUES ('44', 'fa-lock', '图标', '37', '1', null, null, '37', null);
INSERT INTO `selectitem` VALUES ('45', 'fa-flag', '图标', '38', '1', null, null, '38', null);
INSERT INTO `selectitem` VALUES ('46', 'fa-headphones', '图标', '39', '1', null, null, '39', null);
INSERT INTO `selectitem` VALUES ('47', 'fa-volume-off', '图标', '40', '1', null, null, '40', null);
INSERT INTO `selectitem` VALUES ('48', 'fa-volume-down', '图标', '41', '1', null, null, '41', null);
INSERT INTO `selectitem` VALUES ('49', 'fa-volume-up', '图标', '42', '1', null, null, '42', null);
INSERT INTO `selectitem` VALUES ('50', 'fa-qrcode', '图标', '43', '1', null, null, '43', null);
INSERT INTO `selectitem` VALUES ('51', 'fa-barcode', '图标', '44', '1', null, null, '44', null);
INSERT INTO `selectitem` VALUES ('52', 'fa-tag', '图标', '45', '1', null, null, '45', null);
INSERT INTO `selectitem` VALUES ('53', 'fa-tags', '图标', '46', '1', null, null, '46', null);
INSERT INTO `selectitem` VALUES ('54', 'fa-book', '图标', '47', '1', null, null, '47', null);
INSERT INTO `selectitem` VALUES ('55', 'fa-bookmark', '图标', '48', '1', null, null, '48', null);
INSERT INTO `selectitem` VALUES ('56', 'fa-print', '图标', '49', '1', null, null, '49', null);
INSERT INTO `selectitem` VALUES ('57', 'fa-camera', '图标', '50', '1', null, null, '50', null);
INSERT INTO `selectitem` VALUES ('58', 'fa-font', '图标', '51', '1', null, null, '51', null);
INSERT INTO `selectitem` VALUES ('59', 'fa-bold', '图标', '52', '1', null, null, '52', null);
INSERT INTO `selectitem` VALUES ('60', 'fa-italic', '图标', '53', '1', null, null, '53', null);
INSERT INTO `selectitem` VALUES ('61', 'fa-text-height', '图标', '54', '1', null, null, '54', null);
INSERT INTO `selectitem` VALUES ('62', 'fa-text-width', '图标', '55', '1', null, null, '55', null);
INSERT INTO `selectitem` VALUES ('63', 'fa-align-left', '图标', '56', '1', null, null, '56', null);
INSERT INTO `selectitem` VALUES ('64', 'fa-align-center', '图标', '57', '1', null, null, '57', null);
INSERT INTO `selectitem` VALUES ('65', 'fa-align-right', '图标', '58', '1', null, null, '58', null);
INSERT INTO `selectitem` VALUES ('66', 'fa-align-justify', '图标', '59', '1', null, null, '59', null);
INSERT INTO `selectitem` VALUES ('67', 'fa-list', '图标', '60', '1', null, null, '60', null);
INSERT INTO `selectitem` VALUES ('68', 'fa-dedent', '图标', '61', '1', null, null, '61', null);
INSERT INTO `selectitem` VALUES ('69', 'fa-outdent', '图标', '62', '1', null, null, '62', null);
INSERT INTO `selectitem` VALUES ('70', 'fa-indent', '图标', '63', '1', null, null, '63', null);
INSERT INTO `selectitem` VALUES ('71', 'fa-video-camera', '图标', '64', '1', null, null, '64', null);
INSERT INTO `selectitem` VALUES ('72', 'fa-photo', '图标', '65', '1', null, null, '65', null);
INSERT INTO `selectitem` VALUES ('73', 'fa-image', '图标', '66', '1', null, null, '66', null);
INSERT INTO `selectitem` VALUES ('74', 'fa-picture-o', '图标', '67', '1', null, null, '67', null);
INSERT INTO `selectitem` VALUES ('75', 'fa-pencil', '图标', '68', '1', null, null, '68', null);
INSERT INTO `selectitem` VALUES ('76', 'fa-map-marker', '图标', '69', '1', null, null, '69', null);
INSERT INTO `selectitem` VALUES ('77', 'fa-adjust', '图标', '70', '1', null, null, '70', null);
INSERT INTO `selectitem` VALUES ('78', 'fa-tint', '图标', '71', '1', null, null, '71', null);
INSERT INTO `selectitem` VALUES ('79', 'fa-edit', '图标', '72', '1', null, null, '72', null);
INSERT INTO `selectitem` VALUES ('80', 'fa-pencil-square-o', '图标', '73', '1', null, null, '73', null);
INSERT INTO `selectitem` VALUES ('81', 'fa-share-square-o', '图标', '74', '1', null, null, '74', null);
INSERT INTO `selectitem` VALUES ('82', 'fa-check-square-o', '图标', '75', '1', null, null, '75', null);
INSERT INTO `selectitem` VALUES ('83', 'fa-arrows', '图标', '76', '1', null, null, '76', null);
INSERT INTO `selectitem` VALUES ('84', 'fa-step-backward', '图标', '77', '1', null, null, '77', null);
INSERT INTO `selectitem` VALUES ('85', 'fa-fast-backward', '图标', '78', '1', null, null, '78', null);
INSERT INTO `selectitem` VALUES ('86', 'fa-backward', '图标', '79', '1', null, null, '79', null);
INSERT INTO `selectitem` VALUES ('87', 'fa-play', '图标', '80', '1', null, null, '80', null);
INSERT INTO `selectitem` VALUES ('88', 'fa-pause', '图标', '81', '1', null, null, '81', null);
INSERT INTO `selectitem` VALUES ('89', 'fa-stop', '图标', '82', '1', null, null, '82', null);
INSERT INTO `selectitem` VALUES ('90', 'fa-forward', '图标', '83', '1', null, null, '83', null);
INSERT INTO `selectitem` VALUES ('91', 'fa-fast-forward', '图标', '84', '1', null, null, '84', null);
INSERT INTO `selectitem` VALUES ('92', 'fa-step-forward', '图标', '85', '1', null, null, '85', null);
INSERT INTO `selectitem` VALUES ('93', 'fa-eject', '图标', '86', '1', null, null, '86', null);
INSERT INTO `selectitem` VALUES ('94', 'fa-chevron-left', '图标', '87', '1', null, null, '87', null);
INSERT INTO `selectitem` VALUES ('95', 'fa-chevron-right', '图标', '88', '1', null, null, '88', null);
INSERT INTO `selectitem` VALUES ('96', 'fa-plus-circle', '图标', '89', '1', null, null, '89', null);
INSERT INTO `selectitem` VALUES ('97', 'fa-minus-circle', '图标', '90', '1', null, null, '90', null);
INSERT INTO `selectitem` VALUES ('98', 'fa-times-circle', '图标', '91', '1', null, null, '91', null);
INSERT INTO `selectitem` VALUES ('99', 'fa-check-circle', '图标', '92', '1', null, null, '92', null);
INSERT INTO `selectitem` VALUES ('100', 'fa-question-circle', '图标', '93', '1', null, null, '93', null);
INSERT INTO `selectitem` VALUES ('101', 'fa-info-circle', '图标', '94', '1', null, null, '94', null);
INSERT INTO `selectitem` VALUES ('102', 'fa-crosshairs', '图标', '95', '1', null, null, '95', null);
INSERT INTO `selectitem` VALUES ('103', 'fa-times-circle-o', '图标', '96', '1', null, null, '96', null);
INSERT INTO `selectitem` VALUES ('104', 'fa-check-circle-o', '图标', '97', '1', null, null, '97', null);
INSERT INTO `selectitem` VALUES ('105', 'fa-ban', '图标', '98', '1', null, null, '98', null);
INSERT INTO `selectitem` VALUES ('106', 'fa-arrow-left', '图标', '99', '1', null, null, '99', null);
INSERT INTO `selectitem` VALUES ('107', 'fa-arrow-right', '图标', '100', '1', null, null, '100', null);
INSERT INTO `selectitem` VALUES ('108', 'fa-arrow-up', '图标', '101', '1', null, null, '101', null);
INSERT INTO `selectitem` VALUES ('109', 'fa-arrow-down', '图标', '102', '1', null, null, '102', null);
INSERT INTO `selectitem` VALUES ('110', 'fa-mail-forward', '图标', '103', '1', null, null, '103', null);
INSERT INTO `selectitem` VALUES ('111', 'fa-share', '图标', '104', '1', null, null, '104', null);
INSERT INTO `selectitem` VALUES ('112', 'fa-expand', '图标', '105', '1', null, null, '105', null);
INSERT INTO `selectitem` VALUES ('113', 'fa-compress', '图标', '106', '1', null, null, '106', null);
INSERT INTO `selectitem` VALUES ('114', 'fa-plus', '图标', '107', '1', null, null, '107', null);
INSERT INTO `selectitem` VALUES ('115', 'fa-minus', '图标', '108', '1', null, null, '108', null);
INSERT INTO `selectitem` VALUES ('116', 'fa-asterisk', '图标', '109', '1', null, null, '109', null);
INSERT INTO `selectitem` VALUES ('117', 'fa-exclamation-circle', '图标', '110', '1', null, null, '110', null);
INSERT INTO `selectitem` VALUES ('118', 'fa-gift', '图标', '111', '1', null, null, '111', null);
INSERT INTO `selectitem` VALUES ('119', 'fa-leaf', '图标', '112', '1', null, null, '112', null);
INSERT INTO `selectitem` VALUES ('120', 'fa-fire', '图标', '113', '1', null, null, '113', null);
INSERT INTO `selectitem` VALUES ('121', 'fa-eye', '图标', '114', '1', null, null, '114', null);
INSERT INTO `selectitem` VALUES ('122', 'fa-eye-slash', '图标', '115', '1', null, null, '115', null);
INSERT INTO `selectitem` VALUES ('123', 'fa-warning', '图标', '116', '1', null, null, '116', null);
INSERT INTO `selectitem` VALUES ('124', 'fa-exclamation-triangle', '图标', '117', '1', null, null, '117', null);
INSERT INTO `selectitem` VALUES ('125', 'fa-plane', '图标', '118', '1', null, null, '118', null);
INSERT INTO `selectitem` VALUES ('126', 'fa-calendar', '图标', '119', '1', null, null, '119', null);
INSERT INTO `selectitem` VALUES ('127', 'fa-random', '图标', '120', '1', null, null, '120', null);
INSERT INTO `selectitem` VALUES ('128', 'fa-comment', '图标', '121', '1', null, null, '121', null);
INSERT INTO `selectitem` VALUES ('129', 'fa-magnet', '图标', '122', '1', null, null, '122', null);
INSERT INTO `selectitem` VALUES ('130', 'fa-chevron-up', '图标', '123', '1', null, null, '123', null);
INSERT INTO `selectitem` VALUES ('131', 'fa-chevron-down', '图标', '124', '1', null, null, '124', null);
INSERT INTO `selectitem` VALUES ('132', 'fa-retweet', '图标', '125', '1', null, null, '125', null);
INSERT INTO `selectitem` VALUES ('133', 'fa-shopping-cart', '图标', '126', '1', null, null, '126', null);
INSERT INTO `selectitem` VALUES ('134', 'fa-folder', '图标', '127', '1', null, null, '127', null);
INSERT INTO `selectitem` VALUES ('135', 'fa-folder-open', '图标', '128', '1', null, null, '128', null);
INSERT INTO `selectitem` VALUES ('136', 'fa-arrows-v', '图标', '129', '1', null, null, '129', null);
INSERT INTO `selectitem` VALUES ('137', 'fa-arrows-h', '图标', '130', '1', null, null, '130', null);
INSERT INTO `selectitem` VALUES ('138', 'fa-bar-chart-o', '图标', '131', '1', null, null, '131', null);
INSERT INTO `selectitem` VALUES ('139', 'fa-bar-chart', '图标', '132', '1', null, null, '132', null);
INSERT INTO `selectitem` VALUES ('140', 'fa-twitter-square', '图标', '133', '1', null, null, '133', null);
INSERT INTO `selectitem` VALUES ('141', 'fa-facebook-square', '图标', '134', '1', null, null, '134', null);
INSERT INTO `selectitem` VALUES ('142', 'fa-camera-retro', '图标', '135', '1', null, null, '135', null);
INSERT INTO `selectitem` VALUES ('143', 'fa-key', '图标', '136', '1', null, null, '136', null);
INSERT INTO `selectitem` VALUES ('144', 'fa-gears', '图标', '137', '1', null, null, '137', null);
INSERT INTO `selectitem` VALUES ('145', 'fa-cogs', '图标', '138', '1', null, null, '138', null);
INSERT INTO `selectitem` VALUES ('146', 'fa-comments', '图标', '139', '1', null, null, '139', null);
INSERT INTO `selectitem` VALUES ('147', 'fa-thumbs-o-up', '图标', '140', '1', null, null, '140', null);
INSERT INTO `selectitem` VALUES ('148', 'fa-thumbs-o-down', '图标', '141', '1', null, null, '141', null);
INSERT INTO `selectitem` VALUES ('149', 'fa-star-half', '图标', '142', '1', null, null, '142', null);
INSERT INTO `selectitem` VALUES ('150', 'fa-heart-o', '图标', '143', '1', null, null, '143', null);
INSERT INTO `selectitem` VALUES ('151', 'fa-sign-out', '图标', '144', '1', null, null, '144', null);
INSERT INTO `selectitem` VALUES ('152', 'fa-linkedin-square', '图标', '145', '1', null, null, '145', null);
INSERT INTO `selectitem` VALUES ('153', 'fa-thumb-tack', '图标', '146', '1', null, null, '146', null);
INSERT INTO `selectitem` VALUES ('154', 'fa-external-link', '图标', '147', '1', null, null, '147', null);
INSERT INTO `selectitem` VALUES ('155', 'fa-sign-in', '图标', '148', '1', null, null, '148', null);
INSERT INTO `selectitem` VALUES ('156', 'fa-trophy', '图标', '149', '1', null, null, '149', null);
INSERT INTO `selectitem` VALUES ('157', 'fa-github-square', '图标', '150', '1', null, null, '150', null);
INSERT INTO `selectitem` VALUES ('158', 'fa-upload', '图标', '151', '1', null, null, '151', null);
INSERT INTO `selectitem` VALUES ('159', 'fa-lemon-o', '图标', '152', '1', null, null, '152', null);
INSERT INTO `selectitem` VALUES ('160', 'fa-phone', '图标', '153', '1', null, null, '153', null);
INSERT INTO `selectitem` VALUES ('161', 'fa-square-o', '图标', '154', '1', null, null, '154', null);
INSERT INTO `selectitem` VALUES ('162', 'fa-bookmark-o', '图标', '155', '1', null, null, '155', null);
INSERT INTO `selectitem` VALUES ('163', 'fa-phone-square', '图标', '156', '1', null, null, '156', null);
INSERT INTO `selectitem` VALUES ('164', 'fa-twitter', '图标', '157', '1', null, null, '157', null);
INSERT INTO `selectitem` VALUES ('165', 'fa-facebook-f', '图标', '158', '1', null, null, '158', null);
INSERT INTO `selectitem` VALUES ('166', 'fa-facebook', '图标', '159', '1', null, null, '159', null);
INSERT INTO `selectitem` VALUES ('167', 'fa-github', '图标', '160', '1', null, null, '160', null);
INSERT INTO `selectitem` VALUES ('168', 'fa-unlock', '图标', '161', '1', null, null, '161', null);
INSERT INTO `selectitem` VALUES ('169', 'fa-credit-card', '图标', '162', '1', null, null, '162', null);
INSERT INTO `selectitem` VALUES ('170', 'fa-feed', '图标', '163', '1', null, null, '163', null);
INSERT INTO `selectitem` VALUES ('171', 'fa-rss', '图标', '164', '1', null, null, '164', null);
INSERT INTO `selectitem` VALUES ('172', 'fa-hdd-o', '图标', '165', '1', null, null, '165', null);
INSERT INTO `selectitem` VALUES ('173', 'fa-bullhorn', '图标', '166', '1', null, null, '166', null);
INSERT INTO `selectitem` VALUES ('174', 'fa-bell', '图标', '167', '1', null, null, '167', null);
INSERT INTO `selectitem` VALUES ('175', 'fa-certificate', '图标', '168', '1', null, null, '168', null);
INSERT INTO `selectitem` VALUES ('176', 'fa-hand-o-right', '图标', '169', '1', null, null, '169', null);
INSERT INTO `selectitem` VALUES ('177', 'fa-hand-o-left', '图标', '170', '1', null, null, '170', null);
INSERT INTO `selectitem` VALUES ('178', 'fa-hand-o-up', '图标', '171', '1', null, null, '171', null);
INSERT INTO `selectitem` VALUES ('179', 'fa-hand-o-down', '图标', '172', '1', null, null, '172', null);
INSERT INTO `selectitem` VALUES ('180', 'fa-arrow-circle-left', '图标', '173', '1', null, null, '173', null);
INSERT INTO `selectitem` VALUES ('181', 'fa-arrow-circle-right', '图标', '174', '1', null, null, '174', null);
INSERT INTO `selectitem` VALUES ('182', 'fa-arrow-circle-up', '图标', '175', '1', null, null, '175', null);
INSERT INTO `selectitem` VALUES ('183', 'fa-arrow-circle-down', '图标', '176', '1', null, null, '176', null);
INSERT INTO `selectitem` VALUES ('184', 'fa-globe', '图标', '177', '1', null, null, '177', null);
INSERT INTO `selectitem` VALUES ('185', 'fa-wrench', '图标', '178', '1', null, null, '178', null);
INSERT INTO `selectitem` VALUES ('186', 'fa-tasks', '图标', '179', '1', null, null, '179', null);
INSERT INTO `selectitem` VALUES ('187', 'fa-filter', '图标', '180', '1', null, null, '180', null);
INSERT INTO `selectitem` VALUES ('188', 'fa-briefcase', '图标', '181', '1', null, null, '181', null);
INSERT INTO `selectitem` VALUES ('189', 'fa-arrows-alt', '图标', '182', '1', null, null, '182', null);
INSERT INTO `selectitem` VALUES ('190', 'fa-group', '图标', '183', '1', null, null, '183', null);
INSERT INTO `selectitem` VALUES ('191', 'fa-users', '图标', '184', '1', null, null, '184', null);
INSERT INTO `selectitem` VALUES ('192', 'fa-chain', '图标', '185', '1', null, null, '185', null);
INSERT INTO `selectitem` VALUES ('193', 'fa-link', '图标', '186', '1', null, null, '186', null);
INSERT INTO `selectitem` VALUES ('194', 'fa-cloud', '图标', '187', '1', null, null, '187', null);
INSERT INTO `selectitem` VALUES ('195', 'fa-flask', '图标', '188', '1', null, null, '188', null);
INSERT INTO `selectitem` VALUES ('196', 'fa-cut', '图标', '189', '1', null, null, '189', null);
INSERT INTO `selectitem` VALUES ('197', 'fa-scissors', '图标', '190', '1', null, null, '190', null);
INSERT INTO `selectitem` VALUES ('198', 'fa-copy', '图标', '191', '1', null, null, '191', null);
INSERT INTO `selectitem` VALUES ('199', 'fa-files-o', '图标', '192', '1', null, null, '192', null);
INSERT INTO `selectitem` VALUES ('200', 'fa-paperclip', '图标', '193', '1', null, null, '193', null);
INSERT INTO `selectitem` VALUES ('201', 'fa-save', '图标', '194', '1', null, null, '194', null);
INSERT INTO `selectitem` VALUES ('202', 'fa-floppy-o', '图标', '195', '1', null, null, '195', null);
INSERT INTO `selectitem` VALUES ('203', 'fa-square', '图标', '196', '1', null, null, '196', null);
INSERT INTO `selectitem` VALUES ('204', 'fa-navicon', '图标', '197', '1', null, null, '197', null);
INSERT INTO `selectitem` VALUES ('205', 'fa-reorder', '图标', '198', '1', null, null, '198', null);
INSERT INTO `selectitem` VALUES ('206', 'fa-bars', '图标', '199', '1', null, null, '199', null);
INSERT INTO `selectitem` VALUES ('207', 'fa-list-ul', '图标', '200', '1', null, null, '200', null);
INSERT INTO `selectitem` VALUES ('208', 'fa-list-ol', '图标', '201', '1', null, null, '201', null);
INSERT INTO `selectitem` VALUES ('209', 'fa-strikethrough', '图标', '202', '1', null, null, '202', null);
INSERT INTO `selectitem` VALUES ('210', 'fa-underline', '图标', '203', '1', null, null, '203', null);
INSERT INTO `selectitem` VALUES ('211', 'fa-table', '图标', '204', '1', null, null, '204', null);
INSERT INTO `selectitem` VALUES ('212', 'fa-magic', '图标', '205', '1', null, null, '205', null);
INSERT INTO `selectitem` VALUES ('213', 'fa-truck', '图标', '206', '1', null, null, '206', null);
INSERT INTO `selectitem` VALUES ('214', 'fa-pinterest', '图标', '207', '1', null, null, '207', null);
INSERT INTO `selectitem` VALUES ('215', 'fa-pinterest-square', '图标', '208', '1', null, null, '208', null);
INSERT INTO `selectitem` VALUES ('216', 'fa-google-plus-square', '图标', '209', '1', null, null, '209', null);
INSERT INTO `selectitem` VALUES ('217', 'fa-google-plus', '图标', '210', '1', null, null, '210', null);
INSERT INTO `selectitem` VALUES ('218', 'fa-money', '图标', '211', '1', null, null, '211', null);
INSERT INTO `selectitem` VALUES ('219', 'fa-caret-down', '图标', '212', '1', null, null, '212', null);
INSERT INTO `selectitem` VALUES ('220', 'fa-caret-up', '图标', '213', '1', null, null, '213', null);
INSERT INTO `selectitem` VALUES ('221', 'fa-caret-left', '图标', '214', '1', null, null, '214', null);
INSERT INTO `selectitem` VALUES ('222', 'fa-caret-right', '图标', '215', '1', null, null, '215', null);
INSERT INTO `selectitem` VALUES ('223', 'fa-columns', '图标', '216', '1', null, null, '216', null);
INSERT INTO `selectitem` VALUES ('224', 'fa-unsorted', '图标', '217', '1', null, null, '217', null);
INSERT INTO `selectitem` VALUES ('225', 'fa-sort', '图标', '218', '1', null, null, '218', null);
INSERT INTO `selectitem` VALUES ('226', 'fa-sort-down', '图标', '219', '1', null, null, '219', null);
INSERT INTO `selectitem` VALUES ('227', 'fa-sort-desc', '图标', '220', '1', null, null, '220', null);
INSERT INTO `selectitem` VALUES ('228', 'fa-sort-up', '图标', '221', '1', null, null, '221', null);
INSERT INTO `selectitem` VALUES ('229', 'fa-sort-asc', '图标', '222', '1', null, null, '222', null);
INSERT INTO `selectitem` VALUES ('230', 'fa-envelope', '图标', '223', '1', null, null, '223', null);
INSERT INTO `selectitem` VALUES ('231', 'fa-linkedin', '图标', '224', '1', null, null, '224', null);
INSERT INTO `selectitem` VALUES ('232', 'fa-rotate-left', '图标', '225', '1', null, null, '225', null);
INSERT INTO `selectitem` VALUES ('233', 'fa-undo', '图标', '226', '1', null, null, '226', null);
INSERT INTO `selectitem` VALUES ('234', 'fa-legal', '图标', '227', '1', null, null, '227', null);
INSERT INTO `selectitem` VALUES ('235', 'fa-gavel', '图标', '228', '1', null, null, '228', null);
INSERT INTO `selectitem` VALUES ('236', 'fa-dashboard', '图标', '229', '1', null, null, '229', null);
INSERT INTO `selectitem` VALUES ('237', 'fa-tachometer', '图标', '230', '1', null, null, '230', null);
INSERT INTO `selectitem` VALUES ('238', 'fa-comment-o', '图标', '231', '1', null, null, '231', null);
INSERT INTO `selectitem` VALUES ('239', 'fa-comments-o', '图标', '232', '1', null, null, '232', null);
INSERT INTO `selectitem` VALUES ('240', 'fa-flash', '图标', '233', '1', null, null, '233', null);
INSERT INTO `selectitem` VALUES ('241', 'fa-bolt', '图标', '234', '1', null, null, '234', null);
INSERT INTO `selectitem` VALUES ('242', 'fa-sitemap', '图标', '235', '1', null, null, '235', null);
INSERT INTO `selectitem` VALUES ('243', 'fa-umbrella', '图标', '236', '1', null, null, '236', null);
INSERT INTO `selectitem` VALUES ('244', 'fa-paste', '图标', '237', '1', null, null, '237', null);
INSERT INTO `selectitem` VALUES ('245', 'fa-clipboard', '图标', '238', '1', null, null, '238', null);
INSERT INTO `selectitem` VALUES ('246', 'fa-lightbulb-o', '图标', '239', '1', null, null, '239', null);
INSERT INTO `selectitem` VALUES ('247', 'fa-exchange', '图标', '240', '1', null, null, '240', null);
INSERT INTO `selectitem` VALUES ('248', 'fa-cloud-download', '图标', '241', '1', null, null, '241', null);
INSERT INTO `selectitem` VALUES ('249', 'fa-cloud-upload', '图标', '242', '1', null, null, '242', null);
INSERT INTO `selectitem` VALUES ('250', 'fa-user-md', '图标', '243', '1', null, null, '243', null);
INSERT INTO `selectitem` VALUES ('251', 'fa-stethoscope', '图标', '244', '1', null, null, '244', null);
INSERT INTO `selectitem` VALUES ('252', 'fa-suitcase', '图标', '245', '1', null, null, '245', null);
INSERT INTO `selectitem` VALUES ('253', 'fa-bell-o', '图标', '246', '1', null, null, '246', null);
INSERT INTO `selectitem` VALUES ('254', 'fa-coffee', '图标', '247', '1', null, null, '247', null);
INSERT INTO `selectitem` VALUES ('255', 'fa-cutlery', '图标', '248', '1', null, null, '248', null);
INSERT INTO `selectitem` VALUES ('256', 'fa-file-text-o', '图标', '249', '1', null, null, '249', null);
INSERT INTO `selectitem` VALUES ('257', 'fa-building-o', '图标', '250', '1', null, null, '250', null);
INSERT INTO `selectitem` VALUES ('258', 'fa-hospital-o', '图标', '251', '1', null, null, '251', null);
INSERT INTO `selectitem` VALUES ('259', 'fa-ambulance', '图标', '252', '1', null, null, '252', null);
INSERT INTO `selectitem` VALUES ('260', 'fa-medkit', '图标', '253', '1', null, null, '253', null);
INSERT INTO `selectitem` VALUES ('261', 'fa-fighter-jet', '图标', '254', '1', null, null, '254', null);
INSERT INTO `selectitem` VALUES ('262', 'fa-beer', '图标', '255', '1', null, null, '255', null);
INSERT INTO `selectitem` VALUES ('263', 'fa-h-square', '图标', '256', '1', null, null, '256', null);
INSERT INTO `selectitem` VALUES ('264', 'fa-plus-square', '图标', '257', '1', null, null, '257', null);
INSERT INTO `selectitem` VALUES ('265', 'fa-angle-double-left', '图标', '258', '1', null, null, '258', null);
INSERT INTO `selectitem` VALUES ('266', 'fa-angle-double-right', '图标', '259', '1', null, null, '259', null);
INSERT INTO `selectitem` VALUES ('267', 'fa-angle-double-up', '图标', '260', '1', null, null, '260', null);
INSERT INTO `selectitem` VALUES ('268', 'fa-angle-double-down', '图标', '261', '1', null, null, '261', null);
INSERT INTO `selectitem` VALUES ('269', 'fa-angle-left', '图标', '262', '1', null, null, '262', null);
INSERT INTO `selectitem` VALUES ('270', 'fa-angle-right', '图标', '263', '1', null, null, '263', null);
INSERT INTO `selectitem` VALUES ('271', 'fa-angle-up', '图标', '264', '1', null, null, '264', null);
INSERT INTO `selectitem` VALUES ('272', 'fa-angle-down', '图标', '265', '1', null, null, '265', null);
INSERT INTO `selectitem` VALUES ('273', 'fa-desktop', '图标', '266', '1', null, null, '266', null);
INSERT INTO `selectitem` VALUES ('274', 'fa-laptop', '图标', '267', '1', null, null, '267', null);
INSERT INTO `selectitem` VALUES ('275', 'fa-tablet', '图标', '268', '1', null, null, '268', null);
INSERT INTO `selectitem` VALUES ('276', 'fa-mobile-phone', '图标', '269', '1', null, null, '269', null);
INSERT INTO `selectitem` VALUES ('277', 'fa-mobile', '图标', '270', '1', null, null, '270', null);
INSERT INTO `selectitem` VALUES ('278', 'fa-circle-o', '图标', '271', '1', null, null, '271', null);
INSERT INTO `selectitem` VALUES ('279', 'fa-quote-left', '图标', '272', '1', null, null, '272', null);
INSERT INTO `selectitem` VALUES ('280', 'fa-quote-right', '图标', '273', '1', null, null, '273', null);
INSERT INTO `selectitem` VALUES ('281', 'fa-spinner', '图标', '274', '1', null, null, '274', null);
INSERT INTO `selectitem` VALUES ('282', 'fa-circle', '图标', '275', '1', null, null, '275', null);
INSERT INTO `selectitem` VALUES ('283', 'fa-mail-reply', '图标', '276', '1', null, null, '276', null);
INSERT INTO `selectitem` VALUES ('284', 'fa-reply', '图标', '277', '1', null, null, '277', null);
INSERT INTO `selectitem` VALUES ('285', 'fa-github-alt', '图标', '278', '1', null, null, '278', null);
INSERT INTO `selectitem` VALUES ('286', 'fa-folder-o', '图标', '279', '1', null, null, '279', null);
INSERT INTO `selectitem` VALUES ('287', 'fa-folder-open-o', '图标', '280', '1', null, null, '280', null);
INSERT INTO `selectitem` VALUES ('288', 'fa-smile-o', '图标', '281', '1', null, null, '281', null);
INSERT INTO `selectitem` VALUES ('289', 'fa-frown-o', '图标', '282', '1', null, null, '282', null);
INSERT INTO `selectitem` VALUES ('290', 'fa-meh-o', '图标', '283', '1', null, null, '283', null);
INSERT INTO `selectitem` VALUES ('291', 'fa-gamepad', '图标', '284', '1', null, null, '284', null);
INSERT INTO `selectitem` VALUES ('292', 'fa-keyboard-o', '图标', '285', '1', null, null, '285', null);
INSERT INTO `selectitem` VALUES ('293', 'fa-flag-o', '图标', '286', '1', null, null, '286', null);
INSERT INTO `selectitem` VALUES ('294', 'fa-flag-checkered', '图标', '287', '1', null, null, '287', null);
INSERT INTO `selectitem` VALUES ('295', 'fa-terminal', '图标', '288', '1', null, null, '288', null);
INSERT INTO `selectitem` VALUES ('296', 'fa-code', '图标', '289', '1', null, null, '289', null);
INSERT INTO `selectitem` VALUES ('297', 'fa-mail-reply-all', '图标', '290', '1', null, null, '290', null);
INSERT INTO `selectitem` VALUES ('298', 'fa-reply-all', '图标', '291', '1', null, null, '291', null);
INSERT INTO `selectitem` VALUES ('299', 'fa-star-half-empty', '图标', '292', '1', null, null, '292', null);
INSERT INTO `selectitem` VALUES ('300', 'fa-star-half-full', '图标', '293', '1', null, null, '293', null);
INSERT INTO `selectitem` VALUES ('301', 'fa-star-half-o', '图标', '294', '1', null, null, '294', null);
INSERT INTO `selectitem` VALUES ('302', 'fa-location-arrow', '图标', '295', '1', null, null, '295', null);
INSERT INTO `selectitem` VALUES ('303', 'fa-crop', '图标', '296', '1', null, null, '296', null);
INSERT INTO `selectitem` VALUES ('304', 'fa-code-fork', '图标', '297', '1', null, null, '297', null);
INSERT INTO `selectitem` VALUES ('305', 'fa-unlink', '图标', '298', '1', null, null, '298', null);
INSERT INTO `selectitem` VALUES ('306', 'fa-chain-broken', '图标', '299', '1', null, null, '299', null);
INSERT INTO `selectitem` VALUES ('307', 'fa-question', '图标', '300', '1', null, null, '300', null);
INSERT INTO `selectitem` VALUES ('308', 'fa-info', '图标', '301', '1', null, null, '301', null);
INSERT INTO `selectitem` VALUES ('309', 'fa-exclamation', '图标', '302', '1', null, null, '302', null);
INSERT INTO `selectitem` VALUES ('310', 'fa-superscript', '图标', '303', '1', null, null, '303', null);
INSERT INTO `selectitem` VALUES ('311', 'fa-subscript', '图标', '304', '1', null, null, '304', null);
INSERT INTO `selectitem` VALUES ('312', 'fa-eraser', '图标', '305', '1', null, null, '305', null);
INSERT INTO `selectitem` VALUES ('313', 'fa-puzzle-piece', '图标', '306', '1', null, null, '306', null);
INSERT INTO `selectitem` VALUES ('314', 'fa-microphone', '图标', '307', '1', null, null, '307', null);
INSERT INTO `selectitem` VALUES ('315', 'fa-microphone-slash', '图标', '308', '1', null, null, '308', null);
INSERT INTO `selectitem` VALUES ('316', 'fa-shield', '图标', '309', '1', null, null, '309', null);
INSERT INTO `selectitem` VALUES ('317', 'fa-calendar-o', '图标', '310', '1', null, null, '310', null);
INSERT INTO `selectitem` VALUES ('318', 'fa-fire-extinguisher', '图标', '311', '1', null, null, '311', null);
INSERT INTO `selectitem` VALUES ('319', 'fa-rocket', '图标', '312', '1', null, null, '312', null);
INSERT INTO `selectitem` VALUES ('320', 'fa-maxcdn', '图标', '313', '1', null, null, '313', null);
INSERT INTO `selectitem` VALUES ('321', 'fa-chevron-circle-left', '图标', '314', '1', null, null, '314', null);
INSERT INTO `selectitem` VALUES ('322', 'fa-chevron-circle-right', '图标', '315', '1', null, null, '315', null);
INSERT INTO `selectitem` VALUES ('323', 'fa-chevron-circle-up', '图标', '316', '1', null, null, '316', null);
INSERT INTO `selectitem` VALUES ('324', 'fa-chevron-circle-down', '图标', '317', '1', null, null, '317', null);
INSERT INTO `selectitem` VALUES ('325', 'fa-html5', '图标', '318', '1', null, null, '318', null);
INSERT INTO `selectitem` VALUES ('326', 'fa-css3', '图标', '319', '1', null, null, '319', null);
INSERT INTO `selectitem` VALUES ('327', 'fa-anchor', '图标', '320', '1', null, null, '320', null);
INSERT INTO `selectitem` VALUES ('328', 'fa-unlock-alt', '图标', '321', '1', null, null, '321', null);
INSERT INTO `selectitem` VALUES ('329', 'fa-bullseye', '图标', '322', '1', null, null, '322', null);
INSERT INTO `selectitem` VALUES ('330', 'fa-ellipsis-h', '图标', '323', '1', null, null, '323', null);
INSERT INTO `selectitem` VALUES ('331', 'fa-ellipsis-v', '图标', '324', '1', null, null, '324', null);
INSERT INTO `selectitem` VALUES ('332', 'fa-rss-square', '图标', '325', '1', null, null, '325', null);
INSERT INTO `selectitem` VALUES ('333', 'fa-play-circle', '图标', '326', '1', null, null, '326', null);
INSERT INTO `selectitem` VALUES ('334', 'fa-ticket', '图标', '327', '1', null, null, '327', null);
INSERT INTO `selectitem` VALUES ('335', 'fa-minus-square', '图标', '328', '1', null, null, '328', null);
INSERT INTO `selectitem` VALUES ('336', 'fa-minus-square-o', '图标', '329', '1', null, null, '329', null);
INSERT INTO `selectitem` VALUES ('337', 'fa-level-up', '图标', '330', '1', null, null, '330', null);
INSERT INTO `selectitem` VALUES ('338', 'fa-level-down', '图标', '331', '1', null, null, '331', null);
INSERT INTO `selectitem` VALUES ('339', 'fa-check-square', '图标', '332', '1', null, null, '332', null);
INSERT INTO `selectitem` VALUES ('340', 'fa-pencil-square', '图标', '333', '1', null, null, '333', null);
INSERT INTO `selectitem` VALUES ('341', 'fa-external-link-square', '图标', '334', '1', null, null, '334', null);
INSERT INTO `selectitem` VALUES ('342', 'fa-share-square', '图标', '335', '1', null, null, '335', null);
INSERT INTO `selectitem` VALUES ('343', 'fa-compass', '图标', '336', '1', null, null, '336', null);
INSERT INTO `selectitem` VALUES ('344', 'fa-toggle-down', '图标', '337', '1', null, null, '337', null);
INSERT INTO `selectitem` VALUES ('345', 'fa-caret-square-o-down', '图标', '338', '1', null, null, '338', null);
INSERT INTO `selectitem` VALUES ('346', 'fa-toggle-up', '图标', '339', '1', null, null, '339', null);
INSERT INTO `selectitem` VALUES ('347', 'fa-caret-square-o-up', '图标', '340', '1', null, null, '340', null);
INSERT INTO `selectitem` VALUES ('348', 'fa-toggle-right', '图标', '341', '1', null, null, '341', null);
INSERT INTO `selectitem` VALUES ('349', 'fa-caret-square-o-right', '图标', '342', '1', null, null, '342', null);
INSERT INTO `selectitem` VALUES ('350', 'fa-euro', '图标', '343', '1', null, null, '343', null);
INSERT INTO `selectitem` VALUES ('351', 'fa-eur', '图标', '344', '1', null, null, '344', null);
INSERT INTO `selectitem` VALUES ('352', 'fa-gbp', '图标', '345', '1', null, null, '345', null);
INSERT INTO `selectitem` VALUES ('353', 'fa-dollar', '图标', '346', '1', null, null, '346', null);
INSERT INTO `selectitem` VALUES ('354', 'fa-usd', '图标', '347', '1', null, null, '347', null);
INSERT INTO `selectitem` VALUES ('355', 'fa-rupee', '图标', '348', '1', null, null, '348', null);
INSERT INTO `selectitem` VALUES ('356', 'fa-inr', '图标', '349', '1', null, null, '349', null);
INSERT INTO `selectitem` VALUES ('357', 'fa-cny', '图标', '350', '1', null, null, '350', null);
INSERT INTO `selectitem` VALUES ('358', 'fa-rmb', '图标', '351', '1', null, null, '351', null);
INSERT INTO `selectitem` VALUES ('359', 'fa-yen', '图标', '352', '1', null, null, '352', null);
INSERT INTO `selectitem` VALUES ('360', 'fa-jpy', '图标', '353', '1', null, null, '353', null);
INSERT INTO `selectitem` VALUES ('361', 'fa-ruble', '图标', '354', '1', null, null, '354', null);
INSERT INTO `selectitem` VALUES ('362', 'fa-rouble', '图标', '355', '1', null, null, '355', null);
INSERT INTO `selectitem` VALUES ('363', 'fa-rub', '图标', '356', '1', null, null, '356', null);
INSERT INTO `selectitem` VALUES ('364', 'fa-won', '图标', '357', '1', null, null, '357', null);
INSERT INTO `selectitem` VALUES ('365', 'fa-krw', '图标', '358', '1', null, null, '358', null);
INSERT INTO `selectitem` VALUES ('366', 'fa-bitcoin', '图标', '359', '1', null, null, '359', null);
INSERT INTO `selectitem` VALUES ('367', 'fa-btc', '图标', '360', '1', null, null, '360', null);
INSERT INTO `selectitem` VALUES ('368', 'fa-file', '图标', '361', '1', null, null, '361', null);
INSERT INTO `selectitem` VALUES ('369', 'fa-file-text', '图标', '362', '1', null, null, '362', null);
INSERT INTO `selectitem` VALUES ('370', 'fa-sort-alpha-asc', '图标', '363', '1', null, null, '363', null);
INSERT INTO `selectitem` VALUES ('371', 'fa-sort-alpha-desc', '图标', '364', '1', null, null, '364', null);
INSERT INTO `selectitem` VALUES ('372', 'fa-sort-amount-asc', '图标', '365', '1', null, null, '365', null);
INSERT INTO `selectitem` VALUES ('373', 'fa-sort-amount-desc', '图标', '366', '1', null, null, '366', null);
INSERT INTO `selectitem` VALUES ('374', 'fa-sort-numeric-asc', '图标', '367', '1', null, null, '367', null);
INSERT INTO `selectitem` VALUES ('375', 'fa-sort-numeric-desc', '图标', '368', '1', null, null, '368', null);
INSERT INTO `selectitem` VALUES ('376', 'fa-thumbs-up', '图标', '369', '1', null, null, '369', null);
INSERT INTO `selectitem` VALUES ('377', 'fa-thumbs-down', '图标', '370', '1', null, null, '370', null);
INSERT INTO `selectitem` VALUES ('378', 'fa-youtube-square', '图标', '371', '1', null, null, '371', null);
INSERT INTO `selectitem` VALUES ('379', 'fa-youtube', '图标', '372', '1', null, null, '372', null);
INSERT INTO `selectitem` VALUES ('380', 'fa-xing', '图标', '373', '1', null, null, '373', null);
INSERT INTO `selectitem` VALUES ('381', 'fa-xing-square', '图标', '374', '1', null, null, '374', null);
INSERT INTO `selectitem` VALUES ('382', 'fa-youtube-play', '图标', '375', '1', null, null, '375', null);
INSERT INTO `selectitem` VALUES ('383', 'fa-dropbox', '图标', '376', '1', null, null, '376', null);
INSERT INTO `selectitem` VALUES ('384', 'fa-stack-overflow', '图标', '377', '1', null, null, '377', null);
INSERT INTO `selectitem` VALUES ('385', 'fa-instagram', '图标', '378', '1', null, null, '378', null);
INSERT INTO `selectitem` VALUES ('386', 'fa-flickr', '图标', '379', '1', null, null, '379', null);
INSERT INTO `selectitem` VALUES ('387', 'fa-adn', '图标', '380', '1', null, null, '380', null);
INSERT INTO `selectitem` VALUES ('388', 'fa-bitbucket', '图标', '381', '1', null, null, '381', null);
INSERT INTO `selectitem` VALUES ('389', 'fa-bitbucket-square', '图标', '382', '1', null, null, '382', null);
INSERT INTO `selectitem` VALUES ('390', 'fa-tumblr', '图标', '383', '1', null, null, '383', null);
INSERT INTO `selectitem` VALUES ('391', 'fa-tumblr-square', '图标', '384', '1', null, null, '384', null);
INSERT INTO `selectitem` VALUES ('392', 'fa-long-arrow-down', '图标', '385', '1', null, null, '385', null);
INSERT INTO `selectitem` VALUES ('393', 'fa-long-arrow-up', '图标', '386', '1', null, null, '386', null);
INSERT INTO `selectitem` VALUES ('394', 'fa-long-arrow-left', '图标', '387', '1', null, null, '387', null);
INSERT INTO `selectitem` VALUES ('395', 'fa-long-arrow-right', '图标', '388', '1', null, null, '388', null);
INSERT INTO `selectitem` VALUES ('396', 'fa-apple', '图标', '389', '1', null, null, '389', null);
INSERT INTO `selectitem` VALUES ('397', 'fa-windows', '图标', '390', '1', null, null, '390', null);
INSERT INTO `selectitem` VALUES ('398', 'fa-android', '图标', '391', '1', null, null, '391', null);
INSERT INTO `selectitem` VALUES ('399', 'fa-linux', '图标', '392', '1', null, null, '392', null);
INSERT INTO `selectitem` VALUES ('400', 'fa-dribbble', '图标', '393', '1', null, null, '393', null);
INSERT INTO `selectitem` VALUES ('401', 'fa-skype', '图标', '394', '1', null, null, '394', null);
INSERT INTO `selectitem` VALUES ('402', 'fa-foursquare', '图标', '395', '1', null, null, '395', null);
INSERT INTO `selectitem` VALUES ('403', 'fa-trello', '图标', '396', '1', null, null, '396', null);
INSERT INTO `selectitem` VALUES ('404', 'fa-female', '图标', '397', '1', null, null, '397', null);
INSERT INTO `selectitem` VALUES ('405', 'fa-male', '图标', '398', '1', null, null, '398', null);
INSERT INTO `selectitem` VALUES ('406', 'fa-gittip', '图标', '399', '1', null, null, '399', null);
INSERT INTO `selectitem` VALUES ('407', 'fa-gratipay', '图标', '400', '1', null, null, '400', null);
INSERT INTO `selectitem` VALUES ('408', 'fa-sun-o', '图标', '401', '1', null, null, '401', null);
INSERT INTO `selectitem` VALUES ('409', 'fa-moon-o', '图标', '402', '1', null, null, '402', null);
INSERT INTO `selectitem` VALUES ('410', 'fa-archive', '图标', '403', '1', null, null, '403', null);
INSERT INTO `selectitem` VALUES ('411', 'fa-bug', '图标', '404', '1', null, null, '404', null);
INSERT INTO `selectitem` VALUES ('412', 'fa-vk', '图标', '405', '1', null, null, '405', null);
INSERT INTO `selectitem` VALUES ('413', 'fa-weibo', '图标', '406', '1', null, null, '406', null);
INSERT INTO `selectitem` VALUES ('414', 'fa-renren', '图标', '407', '1', null, null, '407', null);
INSERT INTO `selectitem` VALUES ('415', 'fa-pagelines', '图标', '408', '1', null, null, '408', null);
INSERT INTO `selectitem` VALUES ('416', 'fa-stack-exchange', '图标', '409', '1', null, null, '409', null);
INSERT INTO `selectitem` VALUES ('417', 'fa-arrow-circle-o-right', '图标', '410', '1', null, null, '410', null);
INSERT INTO `selectitem` VALUES ('418', 'fa-arrow-circle-o-left', '图标', '411', '1', null, null, '411', null);
INSERT INTO `selectitem` VALUES ('419', 'fa-toggle-left', '图标', '412', '1', null, null, '412', null);
INSERT INTO `selectitem` VALUES ('420', 'fa-caret-square-o-left', '图标', '413', '1', null, null, '413', null);
INSERT INTO `selectitem` VALUES ('421', 'fa-dot-circle-o', '图标', '414', '1', null, null, '414', null);
INSERT INTO `selectitem` VALUES ('422', 'fa-wheelchair', '图标', '415', '1', null, null, '415', null);
INSERT INTO `selectitem` VALUES ('423', 'fa-vimeo-square', '图标', '416', '1', null, null, '416', null);
INSERT INTO `selectitem` VALUES ('424', 'fa-turkish-lira', '图标', '417', '1', null, null, '417', null);
INSERT INTO `selectitem` VALUES ('425', 'fa-try', '图标', '418', '1', null, null, '418', null);
INSERT INTO `selectitem` VALUES ('426', 'fa-plus-square-o', '图标', '419', '1', null, null, '419', null);
INSERT INTO `selectitem` VALUES ('427', 'fa-space-shuttle', '图标', '420', '1', null, null, '420', null);
INSERT INTO `selectitem` VALUES ('428', 'fa-slack', '图标', '421', '1', null, null, '421', null);
INSERT INTO `selectitem` VALUES ('429', 'fa-envelope-square', '图标', '422', '1', null, null, '422', null);
INSERT INTO `selectitem` VALUES ('430', 'fa-wordpress', '图标', '423', '1', null, null, '423', null);
INSERT INTO `selectitem` VALUES ('431', 'fa-openid', '图标', '424', '1', null, null, '424', null);
INSERT INTO `selectitem` VALUES ('432', 'fa-institution', '图标', '425', '1', null, null, '425', null);
INSERT INTO `selectitem` VALUES ('433', 'fa-bank', '图标', '426', '1', null, null, '426', null);
INSERT INTO `selectitem` VALUES ('434', 'fa-university', '图标', '427', '1', null, null, '427', null);
INSERT INTO `selectitem` VALUES ('435', 'fa-mortar-board', '图标', '428', '1', null, null, '428', null);
INSERT INTO `selectitem` VALUES ('436', 'fa-graduation-cap', '图标', '429', '1', null, null, '429', null);
INSERT INTO `selectitem` VALUES ('437', 'fa-yahoo', '图标', '430', '1', null, null, '430', null);
INSERT INTO `selectitem` VALUES ('438', 'fa-google', '图标', '431', '1', null, null, '431', null);
INSERT INTO `selectitem` VALUES ('439', 'fa-reddit', '图标', '432', '1', null, null, '432', null);
INSERT INTO `selectitem` VALUES ('440', 'fa-reddit-square', '图标', '433', '1', null, null, '433', null);
INSERT INTO `selectitem` VALUES ('441', 'fa-stumbleupon-circle', '图标', '434', '1', null, null, '434', null);
INSERT INTO `selectitem` VALUES ('442', 'fa-stumbleupon', '图标', '435', '1', null, null, '435', null);
INSERT INTO `selectitem` VALUES ('443', 'fa-delicious', '图标', '436', '1', null, null, '436', null);
INSERT INTO `selectitem` VALUES ('444', 'fa-digg', '图标', '437', '1', null, null, '437', null);
INSERT INTO `selectitem` VALUES ('445', 'fa-pied-piper-pp', '图标', '438', '1', null, null, '438', null);
INSERT INTO `selectitem` VALUES ('446', 'fa-pied-piper-alt', '图标', '439', '1', null, null, '439', null);
INSERT INTO `selectitem` VALUES ('447', 'fa-drupal', '图标', '440', '1', null, null, '440', null);
INSERT INTO `selectitem` VALUES ('448', 'fa-joomla', '图标', '441', '1', null, null, '441', null);
INSERT INTO `selectitem` VALUES ('449', 'fa-language', '图标', '442', '1', null, null, '442', null);
INSERT INTO `selectitem` VALUES ('450', 'fa-fax', '图标', '443', '1', null, null, '443', null);
INSERT INTO `selectitem` VALUES ('451', 'fa-building', '图标', '444', '1', null, null, '444', null);
INSERT INTO `selectitem` VALUES ('452', 'fa-child', '图标', '445', '1', null, null, '445', null);
INSERT INTO `selectitem` VALUES ('453', 'fa-paw', '图标', '446', '1', null, null, '446', null);
INSERT INTO `selectitem` VALUES ('454', 'fa-spoon', '图标', '447', '1', null, null, '447', null);
INSERT INTO `selectitem` VALUES ('455', 'fa-cube', '图标', '448', '1', null, null, '448', null);
INSERT INTO `selectitem` VALUES ('456', 'fa-cubes', '图标', '449', '1', null, null, '449', null);
INSERT INTO `selectitem` VALUES ('457', 'fa-behance', '图标', '450', '1', null, null, '450', null);
INSERT INTO `selectitem` VALUES ('458', 'fa-behance-square', '图标', '451', '1', null, null, '451', null);
INSERT INTO `selectitem` VALUES ('459', 'fa-steam', '图标', '452', '1', null, null, '452', null);
INSERT INTO `selectitem` VALUES ('460', 'fa-steam-square', '图标', '453', '1', null, null, '453', null);
INSERT INTO `selectitem` VALUES ('461', 'fa-recycle', '图标', '454', '1', null, null, '454', null);
INSERT INTO `selectitem` VALUES ('462', 'fa-automobile', '图标', '455', '1', null, null, '455', null);
INSERT INTO `selectitem` VALUES ('463', 'fa-car', '图标', '456', '1', null, null, '456', null);
INSERT INTO `selectitem` VALUES ('464', 'fa-cab', '图标', '457', '1', null, null, '457', null);
INSERT INTO `selectitem` VALUES ('465', 'fa-taxi', '图标', '458', '1', null, null, '458', null);
INSERT INTO `selectitem` VALUES ('466', 'fa-tree', '图标', '459', '1', null, null, '459', null);
INSERT INTO `selectitem` VALUES ('467', 'fa-spotify', '图标', '460', '1', null, null, '460', null);
INSERT INTO `selectitem` VALUES ('468', 'fa-deviantart', '图标', '461', '1', null, null, '461', null);
INSERT INTO `selectitem` VALUES ('469', 'fa-soundcloud', '图标', '462', '1', null, null, '462', null);
INSERT INTO `selectitem` VALUES ('470', 'fa-database', '图标', '463', '1', null, null, '463', null);
INSERT INTO `selectitem` VALUES ('471', 'fa-file-pdf-o', '图标', '464', '1', null, null, '464', null);
INSERT INTO `selectitem` VALUES ('472', 'fa-file-word-o', '图标', '465', '1', null, null, '465', null);
INSERT INTO `selectitem` VALUES ('473', 'fa-file-excel-o', '图标', '466', '1', null, null, '466', null);
INSERT INTO `selectitem` VALUES ('474', 'fa-file-powerpoint-o', '图标', '467', '1', null, null, '467', null);
INSERT INTO `selectitem` VALUES ('475', 'fa-file-photo-o', '图标', '468', '1', null, null, '468', null);
INSERT INTO `selectitem` VALUES ('476', 'fa-file-picture-o', '图标', '469', '1', null, null, '469', null);
INSERT INTO `selectitem` VALUES ('477', 'fa-file-image-o', '图标', '470', '1', null, null, '470', null);
INSERT INTO `selectitem` VALUES ('478', 'fa-file-zip-o', '图标', '471', '1', null, null, '471', null);
INSERT INTO `selectitem` VALUES ('479', 'fa-file-archive-o', '图标', '472', '1', null, null, '472', null);
INSERT INTO `selectitem` VALUES ('480', 'fa-file-sound-o', '图标', '473', '1', null, null, '473', null);
INSERT INTO `selectitem` VALUES ('481', 'fa-file-audio-o', '图标', '474', '1', null, null, '474', null);
INSERT INTO `selectitem` VALUES ('482', 'fa-file-movie-o', '图标', '475', '1', null, null, '475', null);
INSERT INTO `selectitem` VALUES ('483', 'fa-file-video-o', '图标', '476', '1', null, null, '476', null);
INSERT INTO `selectitem` VALUES ('484', 'fa-file-code-o', '图标', '477', '1', null, null, '477', null);
INSERT INTO `selectitem` VALUES ('485', 'fa-vine', '图标', '478', '1', null, null, '478', null);
INSERT INTO `selectitem` VALUES ('486', 'fa-codepen', '图标', '479', '1', null, null, '479', null);
INSERT INTO `selectitem` VALUES ('487', 'fa-jsfiddle', '图标', '480', '1', null, null, '480', null);
INSERT INTO `selectitem` VALUES ('488', 'fa-life-bouy', '图标', '481', '1', null, null, '481', null);
INSERT INTO `selectitem` VALUES ('489', 'fa-life-buoy', '图标', '482', '1', null, null, '482', null);
INSERT INTO `selectitem` VALUES ('490', 'fa-life-saver', '图标', '483', '1', null, null, '483', null);
INSERT INTO `selectitem` VALUES ('491', 'fa-support', '图标', '484', '1', null, null, '484', null);
INSERT INTO `selectitem` VALUES ('492', 'fa-life-ring', '图标', '485', '1', null, null, '485', null);
INSERT INTO `selectitem` VALUES ('493', 'fa-circle-o-notch', '图标', '486', '1', null, null, '486', null);
INSERT INTO `selectitem` VALUES ('494', 'fa-ra', '图标', '487', '1', null, null, '487', null);
INSERT INTO `selectitem` VALUES ('495', 'fa-resistance', '图标', '488', '1', null, null, '488', null);
INSERT INTO `selectitem` VALUES ('496', 'fa-rebel', '图标', '489', '1', null, null, '489', null);
INSERT INTO `selectitem` VALUES ('497', 'fa-ge', '图标', '490', '1', null, null, '490', null);
INSERT INTO `selectitem` VALUES ('498', 'fa-empire', '图标', '491', '1', null, null, '491', null);
INSERT INTO `selectitem` VALUES ('499', 'fa-git-square', '图标', '492', '1', null, null, '492', null);
INSERT INTO `selectitem` VALUES ('500', 'fa-git', '图标', '493', '1', null, null, '493', null);
INSERT INTO `selectitem` VALUES ('501', 'fa-y-combinator-square', '图标', '494', '1', null, null, '494', null);
INSERT INTO `selectitem` VALUES ('502', 'fa-yc-square', '图标', '495', '1', null, null, '495', null);
INSERT INTO `selectitem` VALUES ('503', 'fa-hacker-news', '图标', '496', '1', null, null, '496', null);
INSERT INTO `selectitem` VALUES ('504', 'fa-tencent-weibo', '图标', '497', '1', null, null, '497', null);
INSERT INTO `selectitem` VALUES ('505', 'fa-qq', '图标', '498', '1', null, null, '498', null);
INSERT INTO `selectitem` VALUES ('506', 'fa-wechat', '图标', '499', '1', null, null, '499', null);
INSERT INTO `selectitem` VALUES ('507', 'fa-weixin', '图标', '500', '1', null, null, '500', null);
INSERT INTO `selectitem` VALUES ('508', 'fa-send', '图标', '501', '1', null, null, '501', null);
INSERT INTO `selectitem` VALUES ('509', 'fa-paper-plane', '图标', '502', '1', null, null, '502', null);
INSERT INTO `selectitem` VALUES ('510', 'fa-send-o', '图标', '503', '1', null, null, '503', null);
INSERT INTO `selectitem` VALUES ('511', 'fa-paper-plane-o', '图标', '504', '1', null, null, '504', null);
INSERT INTO `selectitem` VALUES ('512', 'fa-history', '图标', '505', '1', null, null, '505', null);
INSERT INTO `selectitem` VALUES ('513', 'fa-circle-thin', '图标', '506', '1', null, null, '506', null);
INSERT INTO `selectitem` VALUES ('514', 'fa-header', '图标', '507', '1', null, null, '507', null);
INSERT INTO `selectitem` VALUES ('515', 'fa-paragraph', '图标', '508', '1', null, null, '508', null);
INSERT INTO `selectitem` VALUES ('516', 'fa-sliders', '图标', '509', '1', null, null, '509', null);
INSERT INTO `selectitem` VALUES ('517', 'fa-share-alt', '图标', '510', '1', null, null, '510', null);
INSERT INTO `selectitem` VALUES ('518', 'fa-share-alt-square', '图标', '511', '1', null, null, '511', null);
INSERT INTO `selectitem` VALUES ('519', 'fa-bomb', '图标', '512', '1', null, null, '512', null);
INSERT INTO `selectitem` VALUES ('520', 'fa-soccer-ball-o', '图标', '513', '1', null, null, '513', null);
INSERT INTO `selectitem` VALUES ('521', 'fa-futbol-o', '图标', '514', '1', null, null, '514', null);
INSERT INTO `selectitem` VALUES ('522', 'fa-tty', '图标', '515', '1', null, null, '515', null);
INSERT INTO `selectitem` VALUES ('523', 'fa-binoculars', '图标', '516', '1', null, null, '516', null);
INSERT INTO `selectitem` VALUES ('524', 'fa-plug', '图标', '517', '1', null, null, '517', null);
INSERT INTO `selectitem` VALUES ('525', 'fa-slideshare', '图标', '518', '1', null, null, '518', null);
INSERT INTO `selectitem` VALUES ('526', 'fa-twitch', '图标', '519', '1', null, null, '519', null);
INSERT INTO `selectitem` VALUES ('527', 'fa-yelp', '图标', '520', '1', null, null, '520', null);
INSERT INTO `selectitem` VALUES ('528', 'fa-newspaper-o', '图标', '521', '1', null, null, '521', null);
INSERT INTO `selectitem` VALUES ('529', 'fa-wifi', '图标', '522', '1', null, null, '522', null);
INSERT INTO `selectitem` VALUES ('530', 'fa-calculator', '图标', '523', '1', null, null, '523', null);
INSERT INTO `selectitem` VALUES ('531', 'fa-paypal', '图标', '524', '1', null, null, '524', null);
INSERT INTO `selectitem` VALUES ('532', 'fa-google-wallet', '图标', '525', '1', null, null, '525', null);
INSERT INTO `selectitem` VALUES ('533', 'fa-cc-visa', '图标', '526', '1', null, null, '526', null);
INSERT INTO `selectitem` VALUES ('534', 'fa-cc-mastercard', '图标', '527', '1', null, null, '527', null);
INSERT INTO `selectitem` VALUES ('535', 'fa-cc-discover', '图标', '528', '1', null, null, '528', null);
INSERT INTO `selectitem` VALUES ('536', 'fa-cc-amex', '图标', '529', '1', null, null, '529', null);
INSERT INTO `selectitem` VALUES ('537', 'fa-cc-paypal', '图标', '530', '1', null, null, '530', null);
INSERT INTO `selectitem` VALUES ('538', 'fa-cc-stripe', '图标', '531', '1', null, null, '531', null);
INSERT INTO `selectitem` VALUES ('539', 'fa-bell-slash', '图标', '532', '1', null, null, '532', null);
INSERT INTO `selectitem` VALUES ('540', 'fa-bell-slash-o', '图标', '533', '1', null, null, '533', null);
INSERT INTO `selectitem` VALUES ('541', 'fa-trash', '图标', '534', '1', null, null, '534', null);
INSERT INTO `selectitem` VALUES ('542', 'fa-copyright', '图标', '535', '1', null, null, '535', null);
INSERT INTO `selectitem` VALUES ('543', 'fa-at', '图标', '536', '1', null, null, '536', null);
INSERT INTO `selectitem` VALUES ('544', 'fa-eyedropper', '图标', '537', '1', null, null, '537', null);
INSERT INTO `selectitem` VALUES ('545', 'fa-paint-brush', '图标', '538', '1', null, null, '538', null);
INSERT INTO `selectitem` VALUES ('546', 'fa-birthday-cake', '图标', '539', '1', null, null, '539', null);
INSERT INTO `selectitem` VALUES ('547', 'fa-area-chart', '图标', '540', '1', null, null, '540', null);
INSERT INTO `selectitem` VALUES ('548', 'fa-pie-chart', '图标', '541', '1', null, null, '541', null);
INSERT INTO `selectitem` VALUES ('549', 'fa-line-chart', '图标', '542', '1', null, null, '542', null);
INSERT INTO `selectitem` VALUES ('550', 'fa-lastfm', '图标', '543', '1', null, null, '543', null);
INSERT INTO `selectitem` VALUES ('551', 'fa-lastfm-square', '图标', '544', '1', null, null, '544', null);
INSERT INTO `selectitem` VALUES ('552', 'fa-toggle-off', '图标', '545', '1', null, null, '545', null);
INSERT INTO `selectitem` VALUES ('553', 'fa-toggle-on', '图标', '546', '1', null, null, '546', null);
INSERT INTO `selectitem` VALUES ('554', 'fa-bicycle', '图标', '547', '1', null, null, '547', null);
INSERT INTO `selectitem` VALUES ('555', 'fa-bus', '图标', '548', '1', null, null, '548', null);
INSERT INTO `selectitem` VALUES ('556', 'fa-ioxhost', '图标', '549', '1', null, null, '549', null);
INSERT INTO `selectitem` VALUES ('557', 'fa-angellist', '图标', '550', '1', null, null, '550', null);
INSERT INTO `selectitem` VALUES ('558', 'fa-cc', '图标', '551', '1', null, null, '551', null);
INSERT INTO `selectitem` VALUES ('559', 'fa-shekel', '图标', '552', '1', null, null, '552', null);
INSERT INTO `selectitem` VALUES ('560', 'fa-sheqel', '图标', '553', '1', null, null, '553', null);
INSERT INTO `selectitem` VALUES ('561', 'fa-ils', '图标', '554', '1', null, null, '554', null);
INSERT INTO `selectitem` VALUES ('562', 'fa-meanpath', '图标', '555', '1', null, null, '555', null);
INSERT INTO `selectitem` VALUES ('563', 'fa-buysellads', '图标', '556', '1', null, null, '556', null);
INSERT INTO `selectitem` VALUES ('564', 'fa-connectdevelop', '图标', '557', '1', null, null, '557', null);
INSERT INTO `selectitem` VALUES ('565', 'fa-dashcube', '图标', '558', '1', null, null, '558', null);
INSERT INTO `selectitem` VALUES ('566', 'fa-forumbee', '图标', '559', '1', null, null, '559', null);
INSERT INTO `selectitem` VALUES ('567', 'fa-leanpub', '图标', '560', '1', null, null, '560', null);
INSERT INTO `selectitem` VALUES ('568', 'fa-sellsy', '图标', '561', '1', null, null, '561', null);
INSERT INTO `selectitem` VALUES ('569', 'fa-shirtsinbulk', '图标', '562', '1', null, null, '562', null);
INSERT INTO `selectitem` VALUES ('570', 'fa-simplybuilt', '图标', '563', '1', null, null, '563', null);
INSERT INTO `selectitem` VALUES ('571', 'fa-skyatlas', '图标', '564', '1', null, null, '564', null);
INSERT INTO `selectitem` VALUES ('572', 'fa-cart-plus', '图标', '565', '1', null, null, '565', null);
INSERT INTO `selectitem` VALUES ('573', 'fa-cart-arrow-down', '图标', '566', '1', null, null, '566', null);
INSERT INTO `selectitem` VALUES ('574', 'fa-diamond', '图标', '567', '1', null, null, '567', null);
INSERT INTO `selectitem` VALUES ('575', 'fa-ship', '图标', '568', '1', null, null, '568', null);
INSERT INTO `selectitem` VALUES ('576', 'fa-user-secret', '图标', '569', '1', null, null, '569', null);
INSERT INTO `selectitem` VALUES ('577', 'fa-motorcycle', '图标', '570', '1', null, null, '570', null);
INSERT INTO `selectitem` VALUES ('578', 'fa-street-view', '图标', '571', '1', null, null, '571', null);
INSERT INTO `selectitem` VALUES ('579', 'fa-heartbeat', '图标', '572', '1', null, null, '572', null);
INSERT INTO `selectitem` VALUES ('580', 'fa-venus', '图标', '573', '1', null, null, '573', null);
INSERT INTO `selectitem` VALUES ('581', 'fa-mars', '图标', '574', '1', null, null, '574', null);
INSERT INTO `selectitem` VALUES ('582', 'fa-mercury', '图标', '575', '1', null, null, '575', null);
INSERT INTO `selectitem` VALUES ('583', 'fa-intersex', '图标', '576', '1', null, null, '576', null);
INSERT INTO `selectitem` VALUES ('584', 'fa-transgender', '图标', '577', '1', null, null, '577', null);
INSERT INTO `selectitem` VALUES ('585', 'fa-transgender-alt', '图标', '578', '1', null, null, '578', null);
INSERT INTO `selectitem` VALUES ('586', 'fa-venus-double', '图标', '579', '1', null, null, '579', null);
INSERT INTO `selectitem` VALUES ('587', 'fa-mars-double', '图标', '580', '1', null, null, '580', null);
INSERT INTO `selectitem` VALUES ('588', 'fa-venus-mars', '图标', '581', '1', null, null, '581', null);
INSERT INTO `selectitem` VALUES ('589', 'fa-mars-stroke', '图标', '582', '1', null, null, '582', null);
INSERT INTO `selectitem` VALUES ('590', 'fa-mars-stroke-v', '图标', '583', '1', null, null, '583', null);
INSERT INTO `selectitem` VALUES ('591', 'fa-mars-stroke-h', '图标', '584', '1', null, null, '584', null);
INSERT INTO `selectitem` VALUES ('592', 'fa-neuter', '图标', '585', '1', null, null, '585', null);
INSERT INTO `selectitem` VALUES ('593', 'fa-genderless', '图标', '586', '1', null, null, '586', null);
INSERT INTO `selectitem` VALUES ('594', 'fa-facebook-official', '图标', '587', '1', null, null, '587', null);
INSERT INTO `selectitem` VALUES ('595', 'fa-pinterest-p', '图标', '588', '1', null, null, '588', null);
INSERT INTO `selectitem` VALUES ('596', 'fa-whatsapp', '图标', '589', '1', null, null, '589', null);
INSERT INTO `selectitem` VALUES ('597', 'fa-server', '图标', '590', '1', null, null, '590', null);
INSERT INTO `selectitem` VALUES ('598', 'fa-user-plus', '图标', '591', '1', null, null, '591', null);
INSERT INTO `selectitem` VALUES ('599', 'fa-user-times', '图标', '592', '1', null, null, '592', null);
INSERT INTO `selectitem` VALUES ('600', 'fa-hotel', '图标', '593', '1', null, null, '593', null);
INSERT INTO `selectitem` VALUES ('601', 'fa-bed', '图标', '594', '1', null, null, '594', null);
INSERT INTO `selectitem` VALUES ('602', 'fa-viacoin', '图标', '595', '1', null, null, '595', null);
INSERT INTO `selectitem` VALUES ('603', 'fa-train', '图标', '596', '1', null, null, '596', null);
INSERT INTO `selectitem` VALUES ('604', 'fa-subway', '图标', '597', '1', null, null, '597', null);
INSERT INTO `selectitem` VALUES ('605', 'fa-medium', '图标', '598', '1', null, null, '598', null);
INSERT INTO `selectitem` VALUES ('606', 'fa-yc', '图标', '599', '1', null, null, '599', null);
INSERT INTO `selectitem` VALUES ('607', 'fa-y-combinator', '图标', '600', '1', null, null, '600', null);
INSERT INTO `selectitem` VALUES ('608', 'fa-optin-monster', '图标', '601', '1', null, null, '601', null);
INSERT INTO `selectitem` VALUES ('609', 'fa-opencart', '图标', '602', '1', null, null, '602', null);
INSERT INTO `selectitem` VALUES ('610', 'fa-expeditedssl', '图标', '603', '1', null, null, '603', null);
INSERT INTO `selectitem` VALUES ('611', 'fa-battery-4', '图标', '604', '1', null, null, '604', null);
INSERT INTO `selectitem` VALUES ('612', 'fa-battery-full', '图标', '605', '1', null, null, '605', null);
INSERT INTO `selectitem` VALUES ('613', 'fa-battery-3', '图标', '606', '1', null, null, '606', null);
INSERT INTO `selectitem` VALUES ('614', 'fa-battery-three-quarters', '图标', '607', '1', null, null, '607', null);
INSERT INTO `selectitem` VALUES ('615', 'fa-battery-2', '图标', '608', '1', null, null, '608', null);
INSERT INTO `selectitem` VALUES ('616', 'fa-battery-half', '图标', '609', '1', null, null, '609', null);
INSERT INTO `selectitem` VALUES ('617', 'fa-battery-1', '图标', '610', '1', null, null, '610', null);
INSERT INTO `selectitem` VALUES ('618', 'fa-battery-quarter', '图标', '611', '1', null, null, '611', null);
INSERT INTO `selectitem` VALUES ('619', 'fa-battery-0', '图标', '612', '1', null, null, '612', null);
INSERT INTO `selectitem` VALUES ('620', 'fa-battery-empty', '图标', '613', '1', null, null, '613', null);
INSERT INTO `selectitem` VALUES ('621', 'fa-mouse-pointer', '图标', '614', '1', null, null, '614', null);
INSERT INTO `selectitem` VALUES ('622', 'fa-i-cursor', '图标', '615', '1', null, null, '615', null);
INSERT INTO `selectitem` VALUES ('623', 'fa-object-group', '图标', '616', '1', null, null, '616', null);
INSERT INTO `selectitem` VALUES ('624', 'fa-object-ungroup', '图标', '617', '1', null, null, '617', null);
INSERT INTO `selectitem` VALUES ('625', 'fa-sticky-note', '图标', '618', '1', null, null, '618', null);
INSERT INTO `selectitem` VALUES ('626', 'fa-sticky-note-o', '图标', '619', '1', null, null, '619', null);
INSERT INTO `selectitem` VALUES ('627', 'fa-cc-jcb', '图标', '620', '1', null, null, '620', null);
INSERT INTO `selectitem` VALUES ('628', 'fa-cc-diners-club', '图标', '621', '1', null, null, '621', null);
INSERT INTO `selectitem` VALUES ('629', 'fa-clone', '图标', '622', '1', null, null, '622', null);
INSERT INTO `selectitem` VALUES ('630', 'fa-balance-scale', '图标', '623', '1', null, null, '623', null);
INSERT INTO `selectitem` VALUES ('631', 'fa-hourglass-o', '图标', '624', '1', null, null, '624', null);
INSERT INTO `selectitem` VALUES ('632', 'fa-hourglass-1', '图标', '625', '1', null, null, '625', null);
INSERT INTO `selectitem` VALUES ('633', 'fa-hourglass-start', '图标', '626', '1', null, null, '626', null);
INSERT INTO `selectitem` VALUES ('634', 'fa-hourglass-2', '图标', '627', '1', null, null, '627', null);
INSERT INTO `selectitem` VALUES ('635', 'fa-hourglass-half', '图标', '628', '1', null, null, '628', null);
INSERT INTO `selectitem` VALUES ('636', 'fa-hourglass-3', '图标', '629', '1', null, null, '629', null);
INSERT INTO `selectitem` VALUES ('637', 'fa-hourglass-end', '图标', '630', '1', null, null, '630', null);
INSERT INTO `selectitem` VALUES ('638', 'fa-hourglass', '图标', '631', '1', null, null, '631', null);
INSERT INTO `selectitem` VALUES ('639', 'fa-hand-grab-o', '图标', '632', '1', null, null, '632', null);
INSERT INTO `selectitem` VALUES ('640', 'fa-hand-rock-o', '图标', '633', '1', null, null, '633', null);
INSERT INTO `selectitem` VALUES ('641', 'fa-hand-stop-o', '图标', '634', '1', null, null, '634', null);
INSERT INTO `selectitem` VALUES ('642', 'fa-hand-paper-o', '图标', '635', '1', null, null, '635', null);
INSERT INTO `selectitem` VALUES ('643', 'fa-hand-scissors-o', '图标', '636', '1', null, null, '636', null);
INSERT INTO `selectitem` VALUES ('644', 'fa-hand-lizard-o', '图标', '637', '1', null, null, '637', null);
INSERT INTO `selectitem` VALUES ('645', 'fa-hand-spock-o', '图标', '638', '1', null, null, '638', null);
INSERT INTO `selectitem` VALUES ('646', 'fa-hand-pointer-o', '图标', '639', '1', null, null, '639', null);
INSERT INTO `selectitem` VALUES ('647', 'fa-hand-peace-o', '图标', '640', '1', null, null, '640', null);
INSERT INTO `selectitem` VALUES ('648', 'fa-trademark', '图标', '641', '1', null, null, '641', null);
INSERT INTO `selectitem` VALUES ('649', 'fa-registered', '图标', '642', '1', null, null, '642', null);
INSERT INTO `selectitem` VALUES ('650', 'fa-creative-commons', '图标', '643', '1', null, null, '643', null);
INSERT INTO `selectitem` VALUES ('651', 'fa-gg', '图标', '644', '1', null, null, '644', null);
INSERT INTO `selectitem` VALUES ('652', 'fa-gg-circle', '图标', '645', '1', null, null, '645', null);
INSERT INTO `selectitem` VALUES ('653', 'fa-tripadvisor', '图标', '646', '1', null, null, '646', null);
INSERT INTO `selectitem` VALUES ('654', 'fa-odnoklassniki', '图标', '647', '1', null, null, '647', null);
INSERT INTO `selectitem` VALUES ('655', 'fa-odnoklassniki-square', '图标', '648', '1', null, null, '648', null);
INSERT INTO `selectitem` VALUES ('656', 'fa-get-pocket', '图标', '649', '1', null, null, '649', null);
INSERT INTO `selectitem` VALUES ('657', 'fa-wikipedia-w', '图标', '650', '1', null, null, '650', null);
INSERT INTO `selectitem` VALUES ('658', 'fa-safari', '图标', '651', '1', null, null, '651', null);
INSERT INTO `selectitem` VALUES ('659', 'fa-chrome', '图标', '652', '1', null, null, '652', null);
INSERT INTO `selectitem` VALUES ('660', 'fa-firefox', '图标', '653', '1', null, null, '653', null);
INSERT INTO `selectitem` VALUES ('661', 'fa-opera', '图标', '654', '1', null, null, '654', null);
INSERT INTO `selectitem` VALUES ('662', 'fa-internet-explorer', '图标', '655', '1', null, null, '655', null);
INSERT INTO `selectitem` VALUES ('663', 'fa-tv', '图标', '656', '1', null, null, '656', null);
INSERT INTO `selectitem` VALUES ('664', 'fa-television', '图标', '657', '1', null, null, '657', null);
INSERT INTO `selectitem` VALUES ('665', 'fa-contao', '图标', '658', '1', null, null, '658', null);
INSERT INTO `selectitem` VALUES ('666', 'fa-amazon', '图标', '659', '1', null, null, '659', null);
INSERT INTO `selectitem` VALUES ('667', 'fa-calendar-plus-o', '图标', '660', '1', null, null, '660', null);
INSERT INTO `selectitem` VALUES ('668', 'fa-calendar-minus-o', '图标', '661', '1', null, null, '661', null);
INSERT INTO `selectitem` VALUES ('669', 'fa-calendar-times-o', '图标', '662', '1', null, null, '662', null);
INSERT INTO `selectitem` VALUES ('670', 'fa-calendar-check-o', '图标', '663', '1', null, null, '663', null);
INSERT INTO `selectitem` VALUES ('671', 'fa-industry', '图标', '664', '1', null, null, '664', null);
INSERT INTO `selectitem` VALUES ('672', 'fa-map-pin', '图标', '665', '1', null, null, '665', null);
INSERT INTO `selectitem` VALUES ('673', 'fa-map-signs', '图标', '666', '1', null, null, '666', null);
INSERT INTO `selectitem` VALUES ('674', 'fa-map-o', '图标', '667', '1', null, null, '667', null);
INSERT INTO `selectitem` VALUES ('675', 'fa-map', '图标', '668', '1', null, null, '668', null);
INSERT INTO `selectitem` VALUES ('676', 'fa-commenting', '图标', '669', '1', null, null, '669', null);
INSERT INTO `selectitem` VALUES ('677', 'fa-commenting-o', '图标', '670', '1', null, null, '670', null);
INSERT INTO `selectitem` VALUES ('678', 'fa-houzz', '图标', '671', '1', null, null, '671', null);
INSERT INTO `selectitem` VALUES ('679', 'fa-vimeo', '图标', '672', '1', null, null, '672', null);
INSERT INTO `selectitem` VALUES ('680', 'fa-black-tie', '图标', '673', '1', null, null, '673', null);
INSERT INTO `selectitem` VALUES ('681', 'fa-fonticons', '图标', '674', '1', null, null, '674', null);
INSERT INTO `selectitem` VALUES ('696', 'fa-shopping-bag', '图标', '689', '1', null, null, '689', null);
INSERT INTO `selectitem` VALUES ('697', 'fa-shopping-basket', '图标', '690', '1', null, null, '690', null);
INSERT INTO `selectitem` VALUES ('698', 'fa-hashtag', '图标', '691', '1', null, null, '691', null);
INSERT INTO `selectitem` VALUES ('699', 'fa-bluetooth', '图标', '692', '1', null, null, '692', null);
INSERT INTO `selectitem` VALUES ('700', 'fa-bluetooth-b', '图标', '693', '1', null, null, '693', null);
INSERT INTO `selectitem` VALUES ('701', 'fa-percent', '图标', '694', '1', null, null, '694', null);
INSERT INTO `selectitem` VALUES ('702', 'fa-gitlab', '图标', '695', '1', null, null, '695', null);
INSERT INTO `selectitem` VALUES ('703', 'fa-wpbeginner', '图标', '696', '1', null, null, '696', null);
INSERT INTO `selectitem` VALUES ('704', 'fa-wpforms', '图标', '697', '1', null, null, '697', null);
INSERT INTO `selectitem` VALUES ('705', 'fa-envira', '图标', '698', '1', null, null, '698', null);
INSERT INTO `selectitem` VALUES ('706', 'fa-universal-access', '图标', '699', '1', null, null, '699', null);
INSERT INTO `selectitem` VALUES ('707', 'fa-wheelchair-alt', '图标', '700', '1', null, null, '700', null);
INSERT INTO `selectitem` VALUES ('708', 'fa-question-circle-o', '图标', '701', '1', null, null, '701', null);
INSERT INTO `selectitem` VALUES ('709', 'fa-blind', '图标', '702', '1', null, null, '702', null);
INSERT INTO `selectitem` VALUES ('710', 'fa-audio-description', '图标', '703', '1', null, null, '703', null);
INSERT INTO `selectitem` VALUES ('711', 'fa-volume-control-phone', '图标', '704', '1', null, null, '704', null);
INSERT INTO `selectitem` VALUES ('712', 'fa-braille', '图标', '705', '1', null, null, '705', null);
INSERT INTO `selectitem` VALUES ('713', 'fa-assistive-listening-systems', '图标', '706', '1', null, null, '706', null);
INSERT INTO `selectitem` VALUES ('714', 'fa-asl-interpreting', '图标', '707', '1', null, null, '707', null);
INSERT INTO `selectitem` VALUES ('715', 'fa-american-sign-language-interpreting', '图标', '708', '1', null, null, '708', null);
INSERT INTO `selectitem` VALUES ('716', 'fa-deafness', '图标', '709', '1', null, null, '709', null);
INSERT INTO `selectitem` VALUES ('717', 'fa-hard-of-hearing', '图标', '710', '1', null, null, '710', null);
INSERT INTO `selectitem` VALUES ('718', 'fa-deaf', '图标', '711', '1', null, null, '711', null);
INSERT INTO `selectitem` VALUES ('719', 'fa-glide', '图标', '712', '1', null, null, '712', null);
INSERT INTO `selectitem` VALUES ('720', 'fa-glide-g', '图标', '713', '1', null, null, '713', null);
INSERT INTO `selectitem` VALUES ('721', 'fa-signing', '图标', '714', '1', null, null, '714', null);
INSERT INTO `selectitem` VALUES ('722', 'fa-sign-language', '图标', '715', '1', null, null, '715', null);
INSERT INTO `selectitem` VALUES ('723', 'fa-low-vision', '图标', '716', '1', null, null, '716', null);
INSERT INTO `selectitem` VALUES ('724', 'fa-viadeo', '图标', '717', '1', null, null, '717', null);
INSERT INTO `selectitem` VALUES ('725', 'fa-viadeo-square', '图标', '718', '1', null, null, '718', null);
INSERT INTO `selectitem` VALUES ('726', 'fa-snapchat', '图标', '719', '1', null, null, '719', null);
INSERT INTO `selectitem` VALUES ('727', 'fa-snapchat-ghost', '图标', '720', '1', null, null, '720', null);
INSERT INTO `selectitem` VALUES ('728', 'fa-snapchat-square', '图标', '721', '1', null, null, '721', null);
INSERT INTO `selectitem` VALUES ('729', 'fa-pied-piper', '图标', '722', '1', null, null, '722', null);
INSERT INTO `selectitem` VALUES ('730', 'fa-first-order', '图标', '723', '1', null, null, '723', null);
INSERT INTO `selectitem` VALUES ('731', 'fa-yoast', '图标', '724', '1', null, null, '724', null);
INSERT INTO `selectitem` VALUES ('732', 'fa-themeisle', '图标', '725', '1', null, null, '725', null);
INSERT INTO `selectitem` VALUES ('733', 'fa-google-plus-circle', '图标', '726', '1', null, null, '726', null);
INSERT INTO `selectitem` VALUES ('734', 'fa-google-plus-official', '图标', '727', '1', null, null, '727', null);
INSERT INTO `selectitem` VALUES ('735', 'fa-fa', '图标', '728', '1', null, null, '728', null);
INSERT INTO `selectitem` VALUES ('736', 'fa-font-awesome', '图标', '729', '1', null, null, '729', null);

-- ----------------------------
-- Table structure for upload_file_info
-- ----------------------------
DROP TABLE IF EXISTS `upload_file_info`;
CREATE TABLE `upload_file_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `CATEGORYID` int(11) DEFAULT NULL,
  `CATEGORY` varchar(50) DEFAULT NULL,
  `SRCFILENAME` varchar(200) DEFAULT NULL,
  `NEWFILENAME` varchar(200) DEFAULT NULL,
  `FILEPATH` varchar(200) DEFAULT NULL,
  `UPLOADTIME` varchar(20) DEFAULT NULL,
  `USERID` int(11) DEFAULT NULL,
  `USERNAME` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UPLOAD_FILE_INFO_PK_ID` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of upload_file_info
-- ----------------------------
INSERT INTO `upload_file_info` VALUES ('1', null, 'menufile', '2.doc', '2.doc', '\\documents\\2.doc', null, null, null);
INSERT INTO `upload_file_info` VALUES ('2', null, 'menufile', '2.doc', '3.doc', '\\documents\\3.doc', null, null, null);

-- ----------------------------
-- Function structure for getChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getChildList`(rootId varchar(1000),tbName varchar(50)) RETURNS varchar(1000) CHARSET utf8
BEGIN 
	 DECLARE pTemp VARCHAR(1000);  
	 DECLARE cTemp VARCHAR(1000); 
	
	 SET pTemp = '-1';  
	 SET cTemp =cast(rootId as CHAR);  
	
	 WHILE cTemp <>0 do 
		 SET pTemp = concat(pTemp,',',cTemp);
		 case tbName
		    when 'core_menu' then
					 SELECT group_concat(id) INTO cTemp FROM core_menu WHERE FIND_IN_SET(parentid,cTemp)>0;
		    when 'core_department' then 
					 SELECT group_concat(deptid) INTO cTemp FROM Core_Department WHERE FIND_IN_SET(parentid,cTemp)>0; 
				when 'selectitem' THEN
					 SELECT group_concat(id) INTO cTemp FROM selectitem WHERE FIND_IN_SET(parentid,cTemp)>0;
			end case;
	 END WHILE;  
	 RETURN pTemp;  
 END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getDeptParentList
-- ----------------------------
DROP FUNCTION IF EXISTS `getDeptParentList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `getDeptParentList`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN
	DECLARE sTemp VARCHAR(1000);
	DECLARE sTempPar VARCHAR(1000); 
	SET sTemp = ''; 
	SET sTempPar =rootId; 
	
	#循环递归
	WHILE sTempPar is not null DO 
		#判断是否是第一个，不加的话第一个会为空
		IF sTemp != '' THEN
			SET sTemp = concat(sTemp,',',sTempPar);
		ELSE
			SET sTemp = sTempPar;
		END IF;

		SELECT group_concat(parentid) INTO sTempPar FROM core_department where parentid<>id and FIND_IN_SET(id,sTempPar)>0; 
	END WHILE; 
	
RETURN sTemp; 
END
;;
DELIMITER ;
