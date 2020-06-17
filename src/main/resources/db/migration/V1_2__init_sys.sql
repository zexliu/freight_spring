INSERT INTO `sy_user` VALUES (1265829314040346666,
                              'admin',
                              '$2a$10$kNUhHY7c.IBnYxGvEaBqeuMDAdhUvzX5OZZliqPTtpJX3RYpt/tYy',
                              '18713888898',
                              'zexliu@icloud.com',
                              'zex',
                              '刘泽',
                              'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=101753226,3098745374&fm=26&gp=0.jpg',
                              '1',
                              1,
                              '2020-05-28', 1, 0,  NULL, NULL, NULL, '2020-05-28 10:02:52');

INSERT INTO `sy_role` VALUES (1265829314040348888,'管理员','ADMIN','系统管理员',0,'2020-05-28 10:02:52');
INSERT INTO `sy_user_role_rel` VALUES (1265829314040349999,1265829314040346666,1265829314040348888);




INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267613035315785729, '仪表盘', 'dashboard', 'bxAnaalyse', '/dashboard', NULL, '', '/dashboard/workspace', 'RouteView', 9999, '2020-06-02 08:24:33', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267615579739959298, '工作台', 'WorkSpace', '', '/dashboard/workspace', 1267613035315785729, '', '', 'workSpace', 9999, '2020-06-02 08:34:40', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267615997123538945, '系统管理', 'System', 'setting', '/system', NULL, '', '', 'PageView', 8888, '2020-06-02 08:36:19', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267616400892407810, '用户管理', 'UserManage', '', '/system/user', 1267615997123538945, '', '', 'systemUser', 9999, '2020-06-02 08:37:55', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267616680786702337, '角色管理', 'RoleManage', '', '/system/role', 1267615997123538945, '', '', 'systemRole', 7777, '2020-06-02 08:39:02', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267616921988542465, '部门管理', 'DeptManage', '', '/system/dept', 1267615997123538945, '', '', 'systemDept', 8888, '2020-06-02 08:40:00', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267617169876103170, '权限管理', 'PermissionManage', '', '/system/permission', 1267615997123538945, '', '', 'systemPermission', 6666, '2020-06-02 08:40:59', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267617373954158593, '菜单管理', 'MenuManage', '', '/system/menu', 1267615997123538945, '', '', 'systemMenu', 5555, '2020-06-02 08:41:47', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267751023211372546, '异常页', 'Exception', 'warning', '/exception', NULL, '', '/exception/403', 'RouteView', 0, '2020-06-02 17:32:52', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267751338962771969, '403', '403', '', '/exception/403', 1267751023211372546, '', '', 'exception403', 3, '2020-06-02 17:34:07', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267751475692888066, '404', '404', '', '/exception/404', 1267751023211372546, '', '', 'exception404', 2, '2020-06-02 17:34:40', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267751581037027329, '500', '500', '', '/exception/500', 1267751023211372546, '', '', 'exception500', 1, '2020-06-02 17:35:05', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1267752705852891138, '字典管理', 'Dict', '', '/system/dict', 1267615997123538945, '', '', 'systemDict', 4444, '2020-06-02 17:39:33', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1268718547927207938, '考试管理', 'Exam', 'question-circle', '/exam', NULL, '', '/exam/question/store', 'PageView', 7777, '2020-06-05 09:37:28', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1268718911724359682, '题库管理', 'QuestionStore', '', '/exam/store', 1268718547927207938, '', '', 'questionStore', 9999, '2020-06-05 09:38:54', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1268719318722842626, '问题管理', 'Question', '', '/exam/question', 1268718547927207938, '', '', 'question', 8888, '2020-06-05 09:40:31', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1268796209190313985, '试卷模板', 'PagerTemplate', '', '/exam/template', 1268718547927207938, '', '', 'pagerTemplate', 7777, '2020-06-05 14:46:04', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1268831899617767425, '答题记录', 'PagerAnswer', '', '/exam/answer', 1268718547927207938, '', '', 'pagerAnswer', 5555, '2020-06-05 17:07:53', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1268835985939410946, '考试记录', 'Pager', '', '/exam/pager', 1268718547927207938, '', '', 'pager', 6666, '2020-06-05 17:24:07', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1271252850498273281, '排班管理', 'Arrange', 'build', '/arrange', NULL, '', '/arrange/station', 'PageView', 6666, '2020-06-12 09:27:52', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1271253153457045506, '车站管理', 'Station', '', '/arrange/station', 1271252850498273281, '', '', 'station', 7777, '2020-06-12 09:29:05', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1272452171184902145, '时刻表管理', 'RuntimeTable', '', '/arrange/table', 1271252850498273281, '', '', 'runtimeTable', 9999, '2020-06-15 16:53:33', 0);
INSERT INTO `sy_menu`(`id`, `menu_name`, `menu_code`, `menu_icon`, `menu_path`, `parent_id`, `description`, `redirect`, `component`, `seq`, `create_at`, `hidden`) VALUES (1272452475225804801, '时刻项管理', 'RuntimeItem', '', '/arrange/item', 1271252850498273281, '', '', 'runtimeItem', 8888, '2020-06-15 16:54:45', 0);





INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538291359746, 1265829314040348888, 1267613035315785729);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538295554049, 1265829314040348888, 1267615579739959298);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538295554050, 1265829314040348888, 1267615997123538945);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538295554051, 1265829314040348888, 1267616400892407810);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538299748353, 1265829314040348888, 1267616680786702337);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538295554052, 1265829314040348888, 1267616921988542465);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538299748354, 1265829314040348888, 1267617169876103170);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538299748355, 1265829314040348888, 1267617373954158593);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538303942658, 1265829314040348888, 1267751023211372546);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538303942659, 1265829314040348888, 1267751338962771969);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538303942660, 1265829314040348888, 1267751475692888066);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538303942661, 1265829314040348888, 1267751581037027329);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538303942657, 1265829314040348888, 1267752705852891138);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538303942662, 1265829314040348888, 1268718547927207938);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538303942663, 1265829314040348888, 1268718911724359682);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538303942664, 1265829314040348888, 1268719318722842626);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538312331266, 1265829314040348888, 1268796209190313985);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538312331268, 1265829314040348888, 1268831899617767425);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538312331267, 1265829314040348888, 1268835985939410946);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538312331272, 1265829314040348888, 1271252850498273281);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538312331269, 1265829314040348888, 1271253153457045506);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538312331270, 1265829314040348888, 1272452171184902145);
INSERT INTO `sy_role_menu_rel`(`id`, `role_id`, `menu_id`) VALUES (1272452538312331271, 1265829314040348888, 1272452475225804801);