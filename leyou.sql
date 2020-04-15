CREATE TABLE `areainfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `site` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `tel` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `opentime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `closetime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `date` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `price` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `nums` int(255) DEFAULT NULL,
  `photo` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `descs` text CHARACTER SET utf8 COLLATE utf8_bin,
  `principal` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `principal_tel` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `star` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `article` text CHARACTER SET utf8 COLLATE utf8_bin,
  `reprint` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `times` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `good` int(255) DEFAULT NULL,
  `collect` int(255) DEFAULT NULL,
  `dis` int(11) DEFAULT NULL,
  `city` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `click` int(11) DEFAULT NULL,
  `img` text CHARACTER SET utf8 COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `times` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `zan` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `oid` int(11) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_bin,
  `star` int(11) DEFAULT NULL,
  `times` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `back` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `complaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `oid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `adid` int(11) DEFAULT NULL,
  `starttime` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `dealtime` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `types` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `additional` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `feedback` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `orderitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  `nums` int(11) DEFAULT NULL,
  `account` float(255,0) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `refund` int(11) DEFAULT NULL,
  `times` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `payWay` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `paypool` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  `oid` int(11) DEFAULT NULL,
  `money` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `times` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `toarea` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;




CREATE TABLE `search` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  `click` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `sell_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `outs` int(255) DEFAULT NULL,
  `other` int(255) DEFAULT NULL,
  `ordernums` int(255) DEFAULT NULL,
  `aveprice` int(10) DEFAULT NULL,
  `times` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `shop` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `aid` int(5) DEFAULT NULL,
  `click` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `step` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `active` int(11) DEFAULT '0',
  `isForm` int(11) DEFAULT NULL,
  `isFile` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `salt` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `perms` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `role` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;