SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `description` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图链接',
  `content_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容文件链接',
  `view_count` int NOT NULL DEFAULT 0 COMMENT '浏览量',
  `user_id` int NOT NULL COMMENT '作者id',
  `category_id` int NOT NULL COMMENT '分类id',
  `top` tinyint NOT NULL DEFAULT 0 COMMENT '置顶，0否，1是，默认0',
  `close_comment` tinyint NOT NULL DEFAULT 0 COMMENT '关闭评论，0否，1是，默认0',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `article_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '个人简介', '简单写一下自己大学以来的经历，后续会随时更新！', 'https://qiniu.zhongjiachen.cn/image/cdbff788-473d-45d1-a553-827606cfda1a.jpg', 'https://qiniu.zhongjiachen.cn/article/31311424-720a-4721-ad0d-85de8f9f0b58.md', 0, 1, 2, 0, 0, '2022-08-19 11:06:23', '2023-04-03 10:48:08', 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '技术分享', '2022-08-18 04:20:33', '2022-09-03 13:09:30', 0);
INSERT INTO `category` VALUES (2, '生活随笔', '2022-08-18 04:21:47', '2022-08-18 04:21:47', 0);
INSERT INTO `category` VALUES (3, '说点梦话', '2022-08-18 04:22:07', '2022-08-18 04:22:07', 0);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `top` tinyint NOT NULL DEFAULT 0 COMMENT '置顶，0否，1是，默认0',
  `user_id` int NOT NULL COMMENT '用户id',
  `article_id` int NOT NULL COMMENT '文章id',
  `parent_id` int NULL DEFAULT NULL COMMENT '父评论id，顶级评论为空',
  `reply_user_id` int NULL DEFAULT NULL COMMENT '回复用户id，顶级评论为空',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE,
  INDEX `comment_ibfk_4`(`reply_user_id`) USING BTREE,
  INDEX `comment_ibfk_3`(`parent_id`) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_4` FOREIGN KEY (`reply_user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (53, '随便写写，瞎混混到今天...', 1, 1, 1, NULL, NULL, '2022-09-24 16:16:56', '2023-03-29 14:00:32', 0);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单代码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单组件',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `type` tinyint NOT NULL DEFAULT 0 COMMENT '菜单类型，0具体菜单，1菜单组，默认0',
  `level` tinyint NOT NULL DEFAULT 0 COMMENT '菜单层级，0顶层，正数代表具体层级，默认0',
  `parent_id` int NULL DEFAULT NULL COMMENT '父级id，null没有父级，即处于顶层',
  `hidden` tinyint NOT NULL DEFAULT 0 COMMENT '是否隐藏，0否，1是，默认0',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 'index', '用户指引', '/', '/index/index.vue', 'el-icon-s-home', 0, 0, NULL, 0, '2022-08-18 03:54:52', '2022-09-14 11:15:35', 0);
INSERT INTO `menu` VALUES (2, 'dashboard', '首页', '/', '/dashboard/index.vue', 'el-icon-s-data', 0, 0, NULL, 0, '2022-04-09 13:04:06', '2022-08-18 04:06:22', 0);
INSERT INTO `menu` VALUES (3, 'article', '文章管理', '/article-module', 'Layout', 'el-icon-s-promotion', 1, 0, NULL, 0, '2022-04-09 13:05:15', '2022-04-09 13:05:15', 0);
INSERT INTO `menu` VALUES (4, 'notice', '消息管理', '/notice-module', 'Layout', 'el-icon-info', 1, 0, NULL, 0, '2022-08-09 17:47:48', '2022-08-15 16:05:33', 0);
INSERT INTO `menu` VALUES (5, 'security', '安全控制', '/security-module', 'Layout', 'el-icon-s-operation', 1, 0, NULL, 0, '2022-04-14 21:20:23', '2022-04-14 21:20:26', 0);
INSERT INTO `menu` VALUES (6, 'individual', '个人中心', '/individual', '/individual/index.vue', 'el-icon-user-solid', 0, 0, NULL, 0, '2022-08-09 17:47:48', '2022-08-14 15:24:09', 0);
INSERT INTO `menu` VALUES (21, 'user', '用户管理', '/user', '/user/index.vue', 'el-icon-user', 0, 1, 5, 0, '2022-04-09 12:52:41', '2022-08-15 16:07:19', 0);
INSERT INTO `menu` VALUES (22, 'role', '角色管理', '/role', '/role/index.vue', 'el-icon-stopwatch', 0, 1, 5, 0, '2022-04-09 12:55:06', '2022-08-15 16:08:21', 0);
INSERT INTO `menu` VALUES (23, 'permission', '权限管理', '/permission', '/permission/index.vue', 'el-icon-connection', 0, 1, 5, 0, '2022-04-09 12:55:23', '2022-08-15 16:09:52', 0);
INSERT INTO `menu` VALUES (24, 'menu', '菜单管理', '/menu', '/menu/index.vue', 'el-icon-notebook-2', 0, 1, 5, 0, '2022-04-09 12:55:38', '2022-08-15 16:09:16', 0);
INSERT INTO `menu` VALUES (27, 'article_add', '文章发布', '/article/add', '/article/add/index.vue', 'el-icon-document-add', 0, 1, 3, 0, '2022-04-09 13:06:34', '2022-08-15 15:56:26', 0);
INSERT INTO `menu` VALUES (31, 'article_list', '文章列表', '/article', '/article/index.vue', 'el-icon-document-copy', 0, 1, 3, 0, '2022-08-09 17:44:28', '2022-08-15 15:56:48', 0);
INSERT INTO `menu` VALUES (32, 'comment', '评论管理', '/comment', '/comment/index.vue', 'el-icon-chat-dot-square', 0, 1, 4, 0, '2022-08-09 17:51:19', '2022-08-18 04:44:18', 0);
INSERT INTO `menu` VALUES (33, 'message', '留言管理', '/message', '/message/index.vue', 'el-icon-message', 0, 1, 4, 0, '2022-08-09 17:51:19', '2022-08-18 04:44:25', 0);
INSERT INTO `menu` VALUES (38, 'article_update', '文章修改', '/article/update/:id', '/article/update/index.vue', 'el-icon-edit-outline', 0, 1, 3, 1, '2022-08-15 15:52:40', '2022-08-15 16:05:55', 0);
INSERT INTO `menu` VALUES (39, 'category', '分类管理', '/category', '/category/index.vue', 'el-icon-collection', 0, 1, 3, 0, '2022-08-15 16:03:05', '2022-08-15 16:03:05', 0);
INSERT INTO `menu` VALUES (40, 'tag', '标签管理', '/tag', '/tag/index.vue', 'el-icon-collection-tag', 0, 1, 3, 0, '2022-08-15 16:04:42', '2022-08-15 16:04:42', 0);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id，游客为空',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (4, '针不戳~', NULL, '2022-09-19 13:37:53', '2022-09-19 13:37:53', 0);
INSERT INTO `message` VALUES (5, '66666', NULL, '2022-09-19 13:38:12', '2022-09-19 13:38:12', 0);
INSERT INTO `message` VALUES (6, '6666666666', NULL, '2022-09-19 13:38:26', '2022-09-19 13:38:26', 0);
INSERT INTO `message` VALUES (8, '做的还可以', NULL, '2022-09-19 15:57:00', '2022-09-19 15:57:00', 0);
INSERT INTO `message` VALUES (9, '到此一游', NULL, '2022-09-19 16:03:10', '2022-09-19 16:03:10', 0);
INSERT INTO `message` VALUES (10, '来了来了', 1, '2022-09-23 08:28:37', '2022-09-23 08:28:37', 0);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限路径，权限组通常为空',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `type` tinyint NOT NULL DEFAULT 0 COMMENT '权限类型，0具体权限，1权限组，默认0',
  `level` tinyint NOT NULL DEFAULT 0 COMMENT '权限层级，0顶层，正数代表具体层级，默认0',
  `parent_id` int NULL DEFAULT NULL COMMENT '父级id，null没有父级，即处于顶层',
  `anonymous` tinyint NOT NULL DEFAULT 0 COMMENT '是否支持匿名访问，0否，1是，默认0',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_url`(`url`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `permission_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 165 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (45, '/user/register', '用户注册', 0, 1, 47, 1, '2022-08-12 16:27:37', '2023-03-29 15:15:45', 0);
INSERT INTO `permission` VALUES (46, '/user/password', '用户修改密码', 0, 1, 47, 0, '2022-08-12 16:28:54', '2022-08-14 09:51:52', 0);
INSERT INTO `permission` VALUES (47, NULL, '用户模块', 1, 0, NULL, 0, '2022-08-12 16:29:50', '2023-03-29 15:14:41', 0);
INSERT INTO `permission` VALUES (68, NULL, '管理端用户模块', 1, 0, NULL, 0, '2022-08-14 09:55:26', '2022-08-14 09:55:26', 0);
INSERT INTO `permission` VALUES (69, NULL, '管理端角色模块', 1, 0, NULL, 0, '2022-08-14 09:55:42', '2022-08-14 09:55:42', 0);
INSERT INTO `permission` VALUES (70, NULL, '管理端权限模块', 1, 0, NULL, 0, '2022-08-14 09:55:55', '2022-08-14 09:55:55', 0);
INSERT INTO `permission` VALUES (71, NULL, '管理端菜单模块', 1, 0, NULL, 0, '2022-08-14 09:56:06', '2022-08-14 09:56:06', 0);
INSERT INTO `permission` VALUES (72, '/user/info', '获取用户信息', 0, 1, 47, 0, '2022-08-14 15:27:50', '2022-08-14 15:52:55', 0);
INSERT INTO `permission` VALUES (73, '/admin/user/page', '后台获取用户列表', 0, 1, 68, 0, '2022-08-14 15:29:24', '2023-03-29 09:49:10', 0);
INSERT INTO `permission` VALUES (74, '/admin/user/update/*', '后台更新用户', 0, 1, 68, 0, '2022-08-14 15:30:29', '2022-08-14 15:53:03', 0);
INSERT INTO `permission` VALUES (75, '/admin/user/update/*/deleted', '后台更新用户禁用状态', 0, 1, 68, 0, '2022-08-14 15:31:46', '2022-08-14 15:53:04', 0);
INSERT INTO `permission` VALUES (76, '/admin/role/page', '后台获取角色列表', 0, 1, 69, 0, '2022-08-14 15:34:45', '2022-08-14 15:53:09', 0);
INSERT INTO `permission` VALUES (77, '/admin/role/option', '后台获取角色选项', 0, 1, 69, 0, '2022-08-14 15:35:14', '2022-08-14 15:53:10', 0);
INSERT INTO `permission` VALUES (78, '/admin/role/save', '后台新增角色', 0, 1, 69, 0, '2022-08-14 15:35:31', '2022-08-14 15:53:11', 0);
INSERT INTO `permission` VALUES (79, '/admin/role/remove/*', '后台删除角色', 0, 1, 69, 0, '2022-08-14 15:35:49', '2022-08-14 15:53:12', 0);
INSERT INTO `permission` VALUES (80, '/admin/role/update/*', '后台更新角色', 0, 1, 69, 0, '2022-08-14 15:36:04', '2022-08-14 15:53:14', 0);
INSERT INTO `permission` VALUES (81, '/admin/role/update/*/deleted', '后台更新角色禁用状态', 0, 1, 69, 0, '2022-08-14 15:36:40', '2022-08-14 15:53:13', 0);
INSERT INTO `permission` VALUES (82, '/admin/role/update/*/menu', '后台角色菜单绑定', 0, 1, 69, 0, '2022-08-14 15:37:42', '2022-08-14 15:53:15', 0);
INSERT INTO `permission` VALUES (83, '/admin/role/update/*/permission', '后台角色权限绑定', 0, 1, 69, 0, '2022-08-14 15:38:05', '2022-08-14 15:53:16', 0);
INSERT INTO `permission` VALUES (84, '/admin/permission/list', '后台获取权限列表', 0, 1, 70, 0, '2022-08-14 15:38:51', '2022-08-14 15:53:19', 0);
INSERT INTO `permission` VALUES (85, '/admin/permission/option', '后台获取权限选项', 0, 1, 70, 0, '2022-08-14 15:44:06', '2022-08-14 15:53:20', 0);
INSERT INTO `permission` VALUES (86, '/admin/permission/save', '后台新增权限', 0, 1, 70, 0, '2022-08-14 15:44:41', '2022-08-14 15:53:21', 0);
INSERT INTO `permission` VALUES (87, '/admin/permission/remove/*', '后台删除权限', 0, 1, 70, 0, '2022-08-14 15:45:16', '2022-08-14 15:53:23', 0);
INSERT INTO `permission` VALUES (88, '/admin/permission/update/*', '后台更新权限', 0, 1, 70, 0, '2022-08-14 15:45:41', '2022-08-14 15:53:24', 0);
INSERT INTO `permission` VALUES (89, '/admin/permission/update/*/deleted', '后台更新权限禁用状态', 0, 1, 70, 0, '2022-08-14 15:46:19', '2022-08-14 15:46:19', 0);
INSERT INTO `permission` VALUES (90, '/admin/menu/list', '后台获取菜单列表', 0, 1, 71, 0, '2022-08-14 15:47:04', '2022-08-14 15:53:30', 0);
INSERT INTO `permission` VALUES (91, '/admin/menu/option', '后台获取菜单选项', 0, 1, 71, 0, '2022-08-14 15:47:27', '2022-08-14 15:53:28', 0);
INSERT INTO `permission` VALUES (92, '/admin/menu/user', '后台获取用户菜单', 0, 1, 71, 0, '2022-08-14 15:47:55', '2022-08-14 15:53:28', 0);
INSERT INTO `permission` VALUES (93, '/admin/menu/save', '后台新增菜单', 0, 1, 71, 0, '2022-08-14 15:48:15', '2022-08-14 15:48:15', 0);
INSERT INTO `permission` VALUES (94, '/admin/menu/remove/*', '后台删除菜单', 0, 1, 71, 0, '2022-08-14 15:48:30', '2022-08-14 15:48:30', 0);
INSERT INTO `permission` VALUES (95, '/admin/menu/update/*', '后台更新菜单', 0, 1, 71, 0, '2022-08-14 15:49:26', '2022-08-14 15:49:26', 0);
INSERT INTO `permission` VALUES (96, '/admin/menu/update/*/deleted', '后台更新菜单禁用状态', 0, 1, 71, 0, '2022-08-14 15:49:47', '2022-08-14 15:49:47', 0);
INSERT INTO `permission` VALUES (97, NULL, '七牛云存储', 1, 0, NULL, 0, '2022-08-15 05:45:44', '2022-08-15 05:45:44', 0);
INSERT INTO `permission` VALUES (98, '/qiniu/token', '获取token', 0, 1, 97, 0, '2022-08-15 05:46:20', '2022-08-15 05:46:20', 0);
INSERT INTO `permission` VALUES (99, NULL, '管理端分类模块', 1, 0, NULL, 0, '2022-08-18 05:16:56', '2022-08-18 05:16:56', 0);
INSERT INTO `permission` VALUES (100, NULL, '管理端标签模块', 1, 0, NULL, 0, '2022-08-18 05:17:13', '2022-08-18 05:17:13', 0);
INSERT INTO `permission` VALUES (101, NULL, '管理端留言模块', 1, 0, NULL, 0, '2022-08-18 05:17:30', '2022-08-18 05:17:30', 0);
INSERT INTO `permission` VALUES (102, NULL, '管理端评论模块', 1, 0, NULL, 0, '2022-08-18 05:18:02', '2022-08-18 05:18:02', 0);
INSERT INTO `permission` VALUES (103, NULL, '管理端文章模块', 1, 0, NULL, 0, '2022-08-18 05:18:10', '2022-08-18 05:18:10', 0);
INSERT INTO `permission` VALUES (104, NULL, '管理端数据监控', 1, 0, NULL, 0, '2022-09-03 12:43:09', '2022-09-03 12:43:09', 0);
INSERT INTO `permission` VALUES (105, '/admin/dashboard/index', '获取系统统计指标', 0, 1, 104, 0, '2022-09-03 12:44:16', '2022-09-03 13:21:52', 0);
INSERT INTO `permission` VALUES (106, '/admin/dashboard/view', '获取浏览量榜首文章', 0, 1, 104, 0, '2022-09-03 12:45:41', '2022-09-03 13:18:05', 0);
INSERT INTO `permission` VALUES (107, '/admin/dashboard/role', '获取用户角色分布', 0, 1, 104, 0, '2022-09-03 12:46:34', '2022-09-03 12:46:34', 0);
INSERT INTO `permission` VALUES (108, '/admin/dashboard/category', '获取文章分类', 0, 1, 104, 0, '2022-09-03 12:46:57', '2022-09-03 12:48:29', 1);
INSERT INTO `permission` VALUES (109, '/admin/dashboard/tag', '获取所有文章标签', 0, 1, 104, 0, '2022-09-03 12:47:18', '2022-09-03 12:48:27', 1);
INSERT INTO `permission` VALUES (110, '/admin/article/page', '后台获取文章列表', 0, 1, 103, 0, '2022-09-03 12:52:42', '2022-09-18 13:58:54', 0);
INSERT INTO `permission` VALUES (111, '/admin/article/*', '后台获取指定文章', 0, 1, 103, 0, '2022-09-03 12:53:08', '2022-09-03 12:53:08', 0);
INSERT INTO `permission` VALUES (112, '/admin/article/save', '新增文章', 0, 1, 103, 0, '2022-09-03 12:53:42', '2022-09-03 12:53:42', 0);
INSERT INTO `permission` VALUES (113, '/admin/article/remove/*', '删除文章', 0, 1, 103, 0, '2022-09-03 12:54:19', '2022-09-03 12:54:19', 0);
INSERT INTO `permission` VALUES (114, '/admin/article/update/*', '更新文章', 0, 1, 103, 0, '2022-09-03 12:54:45', '2022-09-03 12:54:45', 0);
INSERT INTO `permission` VALUES (115, '/admin/article/update/*/deleted', '隐藏文章', 0, 1, 103, 0, '2022-09-03 12:55:34', '2022-09-03 12:55:34', 0);
INSERT INTO `permission` VALUES (116, '/admin/comment/page', '后台获取评论列表', 0, 1, 102, 0, '2022-09-03 12:56:38', '2022-09-03 12:56:38', 0);
INSERT INTO `permission` VALUES (117, '/admin/comment/remove/*', '后台删除评论', 0, 1, 102, 0, '2022-09-03 12:57:05', '2022-09-03 12:57:05', 0);
INSERT INTO `permission` VALUES (118, '/admin/comment/update/*', '后台更新评论', 0, 1, 102, 0, '2022-09-03 12:57:33', '2022-09-03 12:57:33', 0);
INSERT INTO `permission` VALUES (119, '/admin/comment/update/*/deleted', '后台禁用评论', 0, 1, 102, 0, '2022-09-03 12:57:53', '2022-09-03 12:57:53', 0);
INSERT INTO `permission` VALUES (120, '/admin/message/page', '后台获取留言列表', 0, 1, 101, 0, '2022-09-03 12:58:28', '2022-09-03 12:58:28', 0);
INSERT INTO `permission` VALUES (121, '/admin/message/remove/*', '后台删除留言', 0, 1, 101, 0, '2022-09-03 12:58:54', '2022-09-03 12:58:54', 0);
INSERT INTO `permission` VALUES (122, '/admin/message/update/*/deleted', '后台禁用留言', 0, 1, 101, 0, '2022-09-03 12:59:26', '2022-09-03 12:59:26', 0);
INSERT INTO `permission` VALUES (123, '/admin/category/page', '后台获取分类列表', 0, 1, 99, 0, '2022-09-03 13:00:09', '2022-09-03 13:00:09', 0);
INSERT INTO `permission` VALUES (124, '/admin/category/query', '后台快速查询分类', 0, 1, 99, 0, '2022-09-03 13:00:35', '2022-09-14 11:11:32', 0);
INSERT INTO `permission` VALUES (125, '/admin/category/option', '后台获取分类选项', 0, 1, 99, 0, '2022-09-03 13:01:11', '2022-09-03 13:01:11', 0);
INSERT INTO `permission` VALUES (126, '/admin/category/save', '新增分类', 0, 1, 99, 0, '2022-09-03 13:01:30', '2022-09-03 13:01:30', 0);
INSERT INTO `permission` VALUES (127, '/admin/category/remove/*', '删除分类', 0, 1, 99, 0, '2022-09-03 13:01:46', '2022-09-03 13:01:46', 0);
INSERT INTO `permission` VALUES (128, '/admin/category/update/*', '更新分类', 0, 1, 99, 0, '2022-09-03 13:02:10', '2022-09-03 13:02:10', 0);
INSERT INTO `permission` VALUES (129, '/admin/category/update/*/deleted', '禁用分类', 0, 1, 99, 0, '2022-09-03 13:02:52', '2022-09-03 13:02:52', 0);
INSERT INTO `permission` VALUES (130, '/admin/tag/page', '后台获取标签列表', 0, 1, 100, 0, '2022-09-03 13:03:42', '2022-09-03 13:03:42', 0);
INSERT INTO `permission` VALUES (131, '/admin/tag/query', '后台快速查询标签', 0, 1, 100, 0, '2022-09-03 13:04:03', '2022-09-14 11:11:45', 0);
INSERT INTO `permission` VALUES (132, '/admin/tag/option', '后台获取标签选项', 0, 1, 100, 0, '2022-09-03 13:04:39', '2022-09-03 13:04:39', 0);
INSERT INTO `permission` VALUES (133, '/admin/tag/save', '新增标签', 0, 1, 100, 0, '2022-09-03 13:05:11', '2022-09-03 13:05:11', 0);
INSERT INTO `permission` VALUES (134, '/admin/tag/remove/*', '删除标签', 0, 1, 100, 0, '2022-09-03 13:05:33', '2022-09-03 13:05:33', 0);
INSERT INTO `permission` VALUES (135, '/admin/tag/update/*', '更新标签', 0, 1, 100, 0, '2022-09-03 13:06:04', '2022-09-03 13:06:04', 0);
INSERT INTO `permission` VALUES (136, '/admin/tag/update/*/deleted', '禁用标签', 0, 1, 100, 0, '2022-09-03 13:06:32', '2022-09-03 13:06:32', 0);
INSERT INTO `permission` VALUES (137, NULL, '文章模块', 1, 0, NULL, 0, '2022-09-17 06:37:45', '2022-09-17 06:37:45', 0);
INSERT INTO `permission` VALUES (138, NULL, '分类模块', 1, 0, NULL, 0, '2022-09-17 06:37:58', '2022-09-17 06:37:58', 0);
INSERT INTO `permission` VALUES (139, NULL, '评论模块', 1, 0, NULL, 0, '2022-09-17 06:38:06', '2022-09-17 06:38:06', 0);
INSERT INTO `permission` VALUES (140, NULL, '留言模块', 1, 0, NULL, 0, '2022-09-17 06:38:26', '2022-09-17 06:38:26', 0);
INSERT INTO `permission` VALUES (141, '/article/statistic', '获取文章统计数据', 0, 1, 137, 1, '2022-09-17 06:45:00', '2022-09-17 06:49:01', 0);
INSERT INTO `permission` VALUES (142, '/article/page', '获取文章分页', 0, 1, 137, 1, '2022-09-17 06:45:34', '2022-09-17 06:49:02', 0);
INSERT INTO `permission` VALUES (143, '/article/*', '获取单个文章', 0, 1, 137, 1, '2022-09-17 06:46:00', '2022-09-17 06:49:03', 0);
INSERT INTO `permission` VALUES (144, '/article/praise/*', '点赞文章', 0, 1, 137, 0, '2022-09-17 06:46:18', '2022-09-17 06:46:18', 0);
INSERT INTO `permission` VALUES (145, '/article/cancel_praise/*', '取消点赞文章', 0, 1, 137, 0, '2022-09-17 06:46:34', '2022-09-17 06:46:34', 0);
INSERT INTO `permission` VALUES (146, '/category/option', '获取分类选项列表', 0, 1, 138, 1, '2022-09-17 06:47:09', '2022-09-17 07:13:36', 0);
INSERT INTO `permission` VALUES (147, '/message/count', '获取留言总数', 0, 1, 140, 1, '2022-09-17 06:50:10', '2022-09-17 06:50:59', 0);
INSERT INTO `permission` VALUES (148, '/message/page', '获取留言分页', 0, 1, 140, 1, '2022-09-17 06:50:29', '2022-09-17 06:50:57', 0);
INSERT INTO `permission` VALUES (149, '/message/save', '新增留言', 0, 1, 140, 1, '2022-09-17 06:50:47', '2022-09-17 06:50:56', 0);
INSERT INTO `permission` VALUES (150, '/comment/page', '获取评论分页', 0, 1, 139, 1, '2022-09-17 06:51:35', '2022-09-17 06:52:37', 0);
INSERT INTO `permission` VALUES (151, '/comment/save', '新增评论', 0, 1, 139, 0, '2022-09-17 06:51:50', '2022-09-17 06:51:50', 0);
INSERT INTO `permission` VALUES (152, '/comment/praise/*', '点赞评论', 0, 1, 139, 0, '2022-09-17 06:52:10', '2022-09-17 06:52:10', 0);
INSERT INTO `permission` VALUES (153, '/comment/cancel_praise/*', '取消点赞评论', 0, 1, 139, 0, '2022-09-17 06:52:27', '2022-09-17 06:52:27', 0);
INSERT INTO `permission` VALUES (154, '/admin/user/remove/*', '后台删除用户', 0, 1, 68, 0, '2022-09-24 14:45:34', '2022-09-24 14:45:34', 0);
INSERT INTO `permission` VALUES (155, '/comment/remove/*', '删除本人评论', 0, 1, 139, 0, '2022-09-24 14:48:39', '2022-09-24 14:48:39', 0);
INSERT INTO `permission` VALUES (157, '/user/code', '请求发送验证码', 0, 1, 47, 1, '2022-10-07 14:53:40', '2022-10-07 14:53:57', 0);
INSERT INTO `permission` VALUES (158, '/logout', '用户登出', 0, 1, 47, 0, '2023-02-24 06:47:54', '2023-02-24 06:47:54', 0);

-- ----------------------------
-- Table structure for relation_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `relation_article_tag`;
CREATE TABLE `relation_article_tag`  (
  `article_id` int NOT NULL,
  `tag_id` int NOT NULL,
  PRIMARY KEY (`article_id`, `tag_id`) USING BTREE,
  INDEX `tag_id`(`tag_id`) USING BTREE,
  CONSTRAINT `relation_article_tag_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_article_tag_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for relation_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `relation_role_menu`;
CREATE TABLE `relation_role_menu`  (
  `role_id` int NOT NULL,
  `menu_id` int NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
  INDEX `role_menu_ibfk_1`(`role_id`) USING BTREE,
  INDEX `role_menu_ibfk_2`(`menu_id`) USING BTREE,
  CONSTRAINT `relation_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation_role_menu
-- ----------------------------
INSERT INTO `relation_role_menu` VALUES (1, 2);
INSERT INTO `relation_role_menu` VALUES (1, 3);
INSERT INTO `relation_role_menu` VALUES (1, 4);
INSERT INTO `relation_role_menu` VALUES (1, 5);
INSERT INTO `relation_role_menu` VALUES (1, 6);
INSERT INTO `relation_role_menu` VALUES (1, 21);
INSERT INTO `relation_role_menu` VALUES (1, 22);
INSERT INTO `relation_role_menu` VALUES (1, 23);
INSERT INTO `relation_role_menu` VALUES (1, 24);
INSERT INTO `relation_role_menu` VALUES (1, 27);
INSERT INTO `relation_role_menu` VALUES (1, 31);
INSERT INTO `relation_role_menu` VALUES (1, 32);
INSERT INTO `relation_role_menu` VALUES (1, 33);
INSERT INTO `relation_role_menu` VALUES (1, 38);
INSERT INTO `relation_role_menu` VALUES (1, 39);
INSERT INTO `relation_role_menu` VALUES (1, 40);
INSERT INTO `relation_role_menu` VALUES (2, 1);
INSERT INTO `relation_role_menu` VALUES (2, 3);
INSERT INTO `relation_role_menu` VALUES (2, 4);
INSERT INTO `relation_role_menu` VALUES (2, 6);
INSERT INTO `relation_role_menu` VALUES (2, 31);
INSERT INTO `relation_role_menu` VALUES (2, 32);
INSERT INTO `relation_role_menu` VALUES (2, 33);
INSERT INTO `relation_role_menu` VALUES (2, 38);
INSERT INTO `relation_role_menu` VALUES (2, 39);
INSERT INTO `relation_role_menu` VALUES (2, 40);
INSERT INTO `relation_role_menu` VALUES (3, 1);
INSERT INTO `relation_role_menu` VALUES (3, 6);

-- ----------------------------
-- Table structure for relation_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `relation_role_permission`;
CREATE TABLE `relation_role_permission`  (
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `role_permission_ibfk_1`(`role_id`) USING BTREE,
  INDEX `role_permission_ibfk_2`(`permission_id`) USING BTREE,
  CONSTRAINT `relation_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation_role_permission
-- ----------------------------
INSERT INTO `relation_role_permission` VALUES (1, 45);
INSERT INTO `relation_role_permission` VALUES (1, 46);
INSERT INTO `relation_role_permission` VALUES (1, 47);
INSERT INTO `relation_role_permission` VALUES (1, 68);
INSERT INTO `relation_role_permission` VALUES (1, 69);
INSERT INTO `relation_role_permission` VALUES (1, 70);
INSERT INTO `relation_role_permission` VALUES (1, 71);
INSERT INTO `relation_role_permission` VALUES (1, 72);
INSERT INTO `relation_role_permission` VALUES (1, 73);
INSERT INTO `relation_role_permission` VALUES (1, 74);
INSERT INTO `relation_role_permission` VALUES (1, 75);
INSERT INTO `relation_role_permission` VALUES (1, 76);
INSERT INTO `relation_role_permission` VALUES (1, 77);
INSERT INTO `relation_role_permission` VALUES (1, 78);
INSERT INTO `relation_role_permission` VALUES (1, 79);
INSERT INTO `relation_role_permission` VALUES (1, 80);
INSERT INTO `relation_role_permission` VALUES (1, 81);
INSERT INTO `relation_role_permission` VALUES (1, 82);
INSERT INTO `relation_role_permission` VALUES (1, 83);
INSERT INTO `relation_role_permission` VALUES (1, 84);
INSERT INTO `relation_role_permission` VALUES (1, 85);
INSERT INTO `relation_role_permission` VALUES (1, 86);
INSERT INTO `relation_role_permission` VALUES (1, 87);
INSERT INTO `relation_role_permission` VALUES (1, 88);
INSERT INTO `relation_role_permission` VALUES (1, 89);
INSERT INTO `relation_role_permission` VALUES (1, 90);
INSERT INTO `relation_role_permission` VALUES (1, 91);
INSERT INTO `relation_role_permission` VALUES (1, 92);
INSERT INTO `relation_role_permission` VALUES (1, 93);
INSERT INTO `relation_role_permission` VALUES (1, 94);
INSERT INTO `relation_role_permission` VALUES (1, 95);
INSERT INTO `relation_role_permission` VALUES (1, 96);
INSERT INTO `relation_role_permission` VALUES (1, 97);
INSERT INTO `relation_role_permission` VALUES (1, 98);
INSERT INTO `relation_role_permission` VALUES (1, 99);
INSERT INTO `relation_role_permission` VALUES (1, 100);
INSERT INTO `relation_role_permission` VALUES (1, 101);
INSERT INTO `relation_role_permission` VALUES (1, 102);
INSERT INTO `relation_role_permission` VALUES (1, 103);
INSERT INTO `relation_role_permission` VALUES (1, 104);
INSERT INTO `relation_role_permission` VALUES (1, 105);
INSERT INTO `relation_role_permission` VALUES (1, 106);
INSERT INTO `relation_role_permission` VALUES (1, 107);
INSERT INTO `relation_role_permission` VALUES (1, 108);
INSERT INTO `relation_role_permission` VALUES (1, 109);
INSERT INTO `relation_role_permission` VALUES (1, 110);
INSERT INTO `relation_role_permission` VALUES (1, 111);
INSERT INTO `relation_role_permission` VALUES (1, 112);
INSERT INTO `relation_role_permission` VALUES (1, 113);
INSERT INTO `relation_role_permission` VALUES (1, 114);
INSERT INTO `relation_role_permission` VALUES (1, 115);
INSERT INTO `relation_role_permission` VALUES (1, 116);
INSERT INTO `relation_role_permission` VALUES (1, 117);
INSERT INTO `relation_role_permission` VALUES (1, 118);
INSERT INTO `relation_role_permission` VALUES (1, 119);
INSERT INTO `relation_role_permission` VALUES (1, 120);
INSERT INTO `relation_role_permission` VALUES (1, 121);
INSERT INTO `relation_role_permission` VALUES (1, 122);
INSERT INTO `relation_role_permission` VALUES (1, 123);
INSERT INTO `relation_role_permission` VALUES (1, 124);
INSERT INTO `relation_role_permission` VALUES (1, 125);
INSERT INTO `relation_role_permission` VALUES (1, 126);
INSERT INTO `relation_role_permission` VALUES (1, 127);
INSERT INTO `relation_role_permission` VALUES (1, 128);
INSERT INTO `relation_role_permission` VALUES (1, 129);
INSERT INTO `relation_role_permission` VALUES (1, 130);
INSERT INTO `relation_role_permission` VALUES (1, 131);
INSERT INTO `relation_role_permission` VALUES (1, 132);
INSERT INTO `relation_role_permission` VALUES (1, 133);
INSERT INTO `relation_role_permission` VALUES (1, 134);
INSERT INTO `relation_role_permission` VALUES (1, 135);
INSERT INTO `relation_role_permission` VALUES (1, 136);
INSERT INTO `relation_role_permission` VALUES (1, 137);
INSERT INTO `relation_role_permission` VALUES (1, 138);
INSERT INTO `relation_role_permission` VALUES (1, 139);
INSERT INTO `relation_role_permission` VALUES (1, 140);
INSERT INTO `relation_role_permission` VALUES (1, 141);
INSERT INTO `relation_role_permission` VALUES (1, 142);
INSERT INTO `relation_role_permission` VALUES (1, 143);
INSERT INTO `relation_role_permission` VALUES (1, 144);
INSERT INTO `relation_role_permission` VALUES (1, 145);
INSERT INTO `relation_role_permission` VALUES (1, 146);
INSERT INTO `relation_role_permission` VALUES (1, 147);
INSERT INTO `relation_role_permission` VALUES (1, 148);
INSERT INTO `relation_role_permission` VALUES (1, 149);
INSERT INTO `relation_role_permission` VALUES (1, 150);
INSERT INTO `relation_role_permission` VALUES (1, 151);
INSERT INTO `relation_role_permission` VALUES (1, 152);
INSERT INTO `relation_role_permission` VALUES (1, 153);
INSERT INTO `relation_role_permission` VALUES (1, 154);
INSERT INTO `relation_role_permission` VALUES (1, 155);
INSERT INTO `relation_role_permission` VALUES (1, 157);
INSERT INTO `relation_role_permission` VALUES (1, 158);
INSERT INTO `relation_role_permission` VALUES (2, 45);
INSERT INTO `relation_role_permission` VALUES (2, 46);
INSERT INTO `relation_role_permission` VALUES (2, 72);
INSERT INTO `relation_role_permission` VALUES (2, 92);
INSERT INTO `relation_role_permission` VALUES (2, 110);
INSERT INTO `relation_role_permission` VALUES (2, 111);
INSERT INTO `relation_role_permission` VALUES (2, 116);
INSERT INTO `relation_role_permission` VALUES (2, 120);
INSERT INTO `relation_role_permission` VALUES (2, 123);
INSERT INTO `relation_role_permission` VALUES (2, 124);
INSERT INTO `relation_role_permission` VALUES (2, 125);
INSERT INTO `relation_role_permission` VALUES (2, 130);
INSERT INTO `relation_role_permission` VALUES (2, 131);
INSERT INTO `relation_role_permission` VALUES (2, 132);
INSERT INTO `relation_role_permission` VALUES (2, 137);
INSERT INTO `relation_role_permission` VALUES (2, 138);
INSERT INTO `relation_role_permission` VALUES (2, 139);
INSERT INTO `relation_role_permission` VALUES (2, 140);
INSERT INTO `relation_role_permission` VALUES (2, 141);
INSERT INTO `relation_role_permission` VALUES (2, 142);
INSERT INTO `relation_role_permission` VALUES (2, 143);
INSERT INTO `relation_role_permission` VALUES (2, 144);
INSERT INTO `relation_role_permission` VALUES (2, 145);
INSERT INTO `relation_role_permission` VALUES (2, 146);
INSERT INTO `relation_role_permission` VALUES (2, 147);
INSERT INTO `relation_role_permission` VALUES (2, 148);
INSERT INTO `relation_role_permission` VALUES (2, 149);
INSERT INTO `relation_role_permission` VALUES (2, 150);
INSERT INTO `relation_role_permission` VALUES (2, 151);
INSERT INTO `relation_role_permission` VALUES (2, 152);
INSERT INTO `relation_role_permission` VALUES (2, 153);
INSERT INTO `relation_role_permission` VALUES (2, 155);
INSERT INTO `relation_role_permission` VALUES (2, 158);
INSERT INTO `relation_role_permission` VALUES (3, 45);
INSERT INTO `relation_role_permission` VALUES (3, 46);
INSERT INTO `relation_role_permission` VALUES (3, 47);
INSERT INTO `relation_role_permission` VALUES (3, 72);
INSERT INTO `relation_role_permission` VALUES (3, 92);
INSERT INTO `relation_role_permission` VALUES (3, 97);
INSERT INTO `relation_role_permission` VALUES (3, 98);
INSERT INTO `relation_role_permission` VALUES (3, 137);
INSERT INTO `relation_role_permission` VALUES (3, 138);
INSERT INTO `relation_role_permission` VALUES (3, 139);
INSERT INTO `relation_role_permission` VALUES (3, 140);
INSERT INTO `relation_role_permission` VALUES (3, 141);
INSERT INTO `relation_role_permission` VALUES (3, 142);
INSERT INTO `relation_role_permission` VALUES (3, 143);
INSERT INTO `relation_role_permission` VALUES (3, 144);
INSERT INTO `relation_role_permission` VALUES (3, 145);
INSERT INTO `relation_role_permission` VALUES (3, 146);
INSERT INTO `relation_role_permission` VALUES (3, 147);
INSERT INTO `relation_role_permission` VALUES (3, 148);
INSERT INTO `relation_role_permission` VALUES (3, 149);
INSERT INTO `relation_role_permission` VALUES (3, 150);
INSERT INTO `relation_role_permission` VALUES (3, 151);
INSERT INTO `relation_role_permission` VALUES (3, 152);
INSERT INTO `relation_role_permission` VALUES (3, 153);
INSERT INTO `relation_role_permission` VALUES (3, 155);
INSERT INTO `relation_role_permission` VALUES (3, 157);
INSERT INTO `relation_role_permission` VALUES (3, 158);

-- ----------------------------
-- Table structure for relation_user_article
-- ----------------------------
DROP TABLE IF EXISTS `relation_user_article`;
CREATE TABLE `relation_user_article`  (
  `user_id` int NOT NULL,
  `article_id` int NOT NULL,
  PRIMARY KEY (`user_id`, `article_id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE,
  CONSTRAINT `relation_user_article_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_user_article_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation_user_article
-- ----------------------------

-- ----------------------------
-- Table structure for relation_user_comment
-- ----------------------------
DROP TABLE IF EXISTS `relation_user_comment`;
CREATE TABLE `relation_user_comment`  (
  `user_id` int NOT NULL,
  `comment_id` int NOT NULL,
  PRIMARY KEY (`user_id`, `comment_id`) USING BTREE,
  INDEX `comment_id`(`comment_id`) USING BTREE,
  CONSTRAINT `relation_user_comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_user_comment_ibfk_2` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation_user_comment
-- ----------------------------

-- ----------------------------
-- Table structure for relation_user_role
-- ----------------------------
DROP TABLE IF EXISTS `relation_user_role`;
CREATE TABLE `relation_user_role`  (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `relation_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of relation_user_role
-- ----------------------------
INSERT INTO `relation_user_role` VALUES (1, 1);
INSERT INTO `relation_user_role` VALUES (2, 2);
INSERT INTO `relation_user_role` VALUES (3, 3);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色代码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '管理员', '拥有系统后台权限', '2021-12-06 14:09:16', '2022-04-17 15:49:37', 0);
INSERT INTO `role` VALUES (2, 'test', '测试', '用于系统测试', '2021-12-06 14:10:09', '2022-09-14 11:08:29', 0);
INSERT INTO `role` VALUES (3, 'user', '用户', '仅支持客户端权限', '2021-12-06 14:10:21', '2022-04-17 15:49:47', 0);

-- ----------------------------
-- Table structure for table_model
-- ----------------------------
DROP TABLE IF EXISTS `table_model`;
CREATE TABLE `table_model`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of table_model
-- ----------------------------

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, 'Vue', '2022-08-18 04:24:27', '2022-08-18 04:31:37', 0);
INSERT INTO `tag` VALUES (2, 'Spring', '2022-08-18 04:24:38', '2022-08-18 04:31:40', 0);
INSERT INTO `tag` VALUES (3, 'Go', '2022-08-18 04:24:54', '2022-08-18 04:31:44', 0);
INSERT INTO `tag` VALUES (4, 'Python', '2022-08-18 04:25:01', '2022-08-18 04:31:49', 0);
INSERT INTO `tag` VALUES (5, 'Mybatis', '2022-08-18 04:25:34', '2022-08-18 04:31:53', 0);
INSERT INTO `tag` VALUES (6, 'Redis', '2022-08-18 04:25:50', '2022-08-18 04:31:59', 0);
INSERT INTO `tag` VALUES (7, 'Docker', '2022-08-18 04:32:19', '2022-08-18 04:32:19', 0);
INSERT INTO `tag` VALUES (8, 'Nginx', '2022-08-18 04:32:24', '2022-08-18 04:32:24', 0);
INSERT INTO `tag` VALUES (9, 'React', '2022-08-18 04:33:21', '2022-08-18 04:33:21', 0);
INSERT INTO `tag` VALUES (10, 'SpringSecurity', '2022-08-18 04:34:08', '2022-08-18 04:34:08', 0);
INSERT INTO `tag` VALUES (11, 'SpringBoot', '2022-08-18 04:34:21', '2022-08-18 04:34:21', 0);
INSERT INTO `tag` VALUES (12, 'We-App', '2022-08-18 04:34:43', '2022-08-18 04:34:43', 0);
INSERT INTO `tag` VALUES (13, 'Uni-App', '2022-08-18 04:34:48', '2022-08-18 04:34:48', 0);
INSERT INTO `tag` VALUES (14, 'PyTorch', '2022-08-18 04:35:07', '2022-08-18 04:35:07', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像链接',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '13800000000', '$2a$10$aR90eqGgMVB08H9E5FrxAOG5YIzcF5mdqCNvEdGMlhqWaJ83k.Vpe', '管理员(密码123)', 'https://qiniu.zhongjiachen.cn/avatar/ddfd0d3d-2538-4354-b2f3-cfc686745c1f.jpg', '13800000000', '2021-12-05 11:01:43', '2022-09-24 14:23:00', 0);
INSERT INTO `user` VALUES (2, '15800000000', '$2a$10$aR90eqGgMVB08H9E5FrxAOG5YIzcF5mdqCNvEdGMlhqWaJ83k.Vpe', '测试(密码123)', 'https://qiniu.zhongjiachen.cn/avatar/6df46dfd-10e4-4f6e-91e3-61398d46e4c7.png', '15800000000', '2021-12-05 03:21:29', '2022-09-24 14:28:24', 0);
INSERT INTO `user` VALUES (3, '18800000000', '$2a$10$aR90eqGgMVB08H9E5FrxAOG5YIzcF5mdqCNvEdGMlhqWaJ83k.Vpe', '用户(密码123)', 'https://qiniu.zhongjiachen.cn/avatar/6df46dfd-10e4-4f6e-91e3-61398d46e4c7.png', '18800000000', '2023-02-26 08:12:45', '2023-02-26 08:12:45', 0);

SET FOREIGN_KEY_CHECKS = 1;
