CREATE TABLE `sy_permission` (
                                 `id` bigint(11) NOT NULL,
                                 `permission_name` varchar(32) NULL COMMENT '权限名称',
                                 `permission_code` varchar(32) NULL COMMENT '权限编码',
                                 `permission_path` varchar(200) NULL COMMENT '权限路径',
                                 `seq` int(11) NULL DEFAULT 0 COMMENT '排序',
                                 `description` varchar(200) NULL COMMENT '描述',
                                 `parent_id` bigint(11) NULL COMMENT '所属权限',
                                 `method_type` tinyint(4) NULL COMMENT '方法类型',
                                 `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 PRIMARY KEY (`id`) ,
                                 UNIQUE INDEX `uk_permission_code` (`permission_code` ASC) USING BTREE
)
    COMMENT = '权限表';
CREATE TABLE `sy_role_permission_rel` (
                                          `id` bigint(11) NOT NULL,
                                          `role_id` bigint(11) NOT NULL COMMENT '角色ID',
                                          `permission_id` bigint(11) NOT NULL COMMENT '权限ID',
                                          PRIMARY KEY (`id`) ,
                                          UNIQUE INDEX `uk_role_id_permission_id` (`role_id` ASC, `permission_id` ASC) USING HASH
)
    COMMENT = '角色权限关联关系';
CREATE TABLE `sy_role` (
                           `id` bigint(11) NOT NULL,
                           `role_name` varchar(32) NULL COMMENT '角色名称',
                           `role_code` varchar(32) NULL COMMENT '角色编码',
                           `description` varchar(200) NULL COMMENT '描述',
                           `seq` int(11) NULL DEFAULT 0 COMMENT '排序',
                           `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`id`) ,
                           UNIQUE INDEX `uk_role_name` (`role_name` ASC) USING BTREE,
                           UNIQUE INDEX `uk_role_code` (`role_code` ASC) USING BTREE
)
    COMMENT = '角色表';
CREATE TABLE `sy_role_menu_rel` (
                                    `id` bigint(11) NOT NULL,
                                    `role_id` bigint(11) NOT NULL COMMENT '角色ID',
                                    `menu_id` bigint(11) NOT NULL COMMENT '菜单ID',
                                    PRIMARY KEY (`id`) ,
                                    UNIQUE INDEX `uk_role_id_menu_id` (`role_id` ASC, `menu_id` ASC) USING HASH
)
    COMMENT = '角色菜单关联关系';
CREATE TABLE `sy_menu` (
                           `id` bigint(11) NOT NULL,
                           `menu_name` varchar(32) NULL COMMENT '菜单名称',
                           `menu_code` varchar(32) NULL COMMENT '菜单编码',
                           `menu_icon` varchar(128) NULL COMMENT '菜单图标',
                           `menu_path` varchar(200) NULL COMMENT '页面路径',
                           `parent_id` bigint(11) NULL COMMENT '所属菜单',
                           `description` varchar(200) NULL COMMENT '描述',
                           `redirect` varchar(200) NULL COMMENT '重定向',
                           `component` varchar(200) NULL COMMENT '组件',
                           `seq` int(11) NULL DEFAULT 0 COMMENT '排序',
                           `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `hidden` tinyint(1) NULL DEFAULT 0 COMMENT '是否隐藏',
                           PRIMARY KEY (`id`)
)
    COMMENT = '菜单表';
CREATE TABLE `sy_user_role_rel` (
                                    `id` bigint(11) NOT NULL,
                                    `user_id` bigint(11) NOT NULL COMMENT '用户ID',
                                    `role_id` bigint(11) NOT NULL COMMENT '角色ID',
                                    PRIMARY KEY (`id`) ,
                                    UNIQUE INDEX `uk_user_id_role_id` (`user_id` ASC, `role_id` ASC) USING HASH
)
    COMMENT = '用户角色关系表';
CREATE TABLE `sy_user` (
                           `id` bigint(11) NOT NULL,
                           `username` varchar(32) NULL COMMENT '用户名',
                           `password` varchar(128) NULL COMMENT '密码',
                           `mobile` varchar(32) NULL COMMENT '手机号',
                           `email` varchar(128) NULL COMMENT '邮箱',
                           `nickname` varchar(32) NULL COMMENT '昵称',
                           `real_name` varchar(16) NULL COMMENT '真实姓名',
                           `avatar` varchar(255) NULL COMMENT '头像',
                           `work_no` varchar(32) NULL COMMENT '工号',
                           `gender` tinyint(4) NULL DEFAULT 0 COMMENT '性别',
                           `birth_day` date NULL COMMENT '生日',
                           `enable` tinyint(1) NULL DEFAULT 1 COMMENT '可用状态',
                           `locked` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态',
                           `login_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
                           `login_ip` varchar(32) NULL COMMENT '最后登录IP',
                           `expire_at` datetime NULL COMMENT '过期时间',
                           `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`id`) ,
                           UNIQUE INDEX `uk_username` (`username` ASC) USING BTREE,
                           UNIQUE INDEX `uk_mobile` (`mobile` ASC) USING BTREE,
                           UNIQUE INDEX `uk_email` (`email` ASC) USING BTREE
)
    COMMENT = '用户表';
CREATE TABLE `sy_dept` (
                           `id` bigint(11) NOT NULL,
                           `dept_name` varchar(32) NULL COMMENT '部门名称',
                           `dept_code` varchar(32) NULL COMMENT '部门编码',
                           `parent_id` bigint(11) NULL COMMENT '所属部门',
                           `address` varchar(200) NULL COMMENT '部门地址',
                           `fax` varchar(32) NULL COMMENT '传真',
                           `phone` varchar(32) NULL COMMENT '电话',
                           `description` varchar(200) NULL COMMENT '描述',
                           `seq` int(11) NULL COMMENT '排序',
                           `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`id`)
)
    COMMENT = '部门表';
CREATE TABLE `sy_user_dept_rel` (
                                    `id` bigint(11) NOT NULL,
                                    `user_id` bigint(11) NOT NULL COMMENT '用户ID',
                                    `dept_id` bigint(11) NOT NULL COMMENT '部门ID',
                                    PRIMARY KEY (`id`) ,
                                    UNIQUE INDEX `uk_user_id_dept_id` (`user_id` ASC, `dept_id` ASC) USING HASH
)
    COMMENT = '用户部门关系表';
CREATE TABLE `sy_dept_role_rel` (
                                    `id` bigint(11) NOT NULL,
                                    `dept_id` bigint(11) NOT NULL COMMENT '部门ID',
                                    `role_id` bigint(11) NOT NULL COMMENT '角色ID',
                                    PRIMARY KEY (`id`) ,
                                    UNIQUE INDEX `uk_dept_id_role_id` (`dept_id` ASC, `role_id` ASC) USING HASH
)
    COMMENT = '部门角色关系表';
CREATE TABLE `sy_dict` (
                           `id` bigint(11) NOT NULL,
                           `dict_name` varchar(32) NULL COMMENT '字典名称',
                           `dict_code` varchar(32) NULL COMMENT '字典编码',
                           `description` varchar(200) NULL COMMENT '描述',
                           `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`id`) ,
                           UNIQUE INDEX `uk_dict_name` (`dict_name` ASC) USING BTREE,
                           UNIQUE INDEX `uk_dict_code` (`dict_code` ASC) USING BTREE
)
    COMMENT = '数据字典';
CREATE TABLE `sy_dict_entry` (
                                 `id` bigint(11) NOT NULL,
                                 `dict_code` varchar(32) NULL COMMENT '字典编码',
                                 `dict_entry_name` varchar(32) NULL COMMENT '字典项名称',
                                 `dict_entry_value` varchar(32) NULL COMMENT '字典项值',
                                 `description` varchar(200) NULL COMMENT '描述',
                                 `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `enable` tinyint(1) NULL DEFAULT 1 COMMENT '可用状态',
                                 `seq` int(11) NULL DEFAULT 0 COMMENT '排序',
                                 PRIMARY KEY (`id`)
)
    COMMENT = '字典项';
CREATE TABLE `qa_question_store` (
                                     `id` bigint(11) NOT NULL,
                                     `question_store_name` varchar(32) NULL COMMENT '题库名称',
                                     `description` varchar(200) NULL COMMENT '描述',
                                     `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标记',
                                     PRIMARY KEY (`id`) ,
                                     UNIQUE INDEX `uk_question_store_name` (`question_store_name` ASC) USING BTREE
)
    COMMENT = '试题题库';
CREATE TABLE `qa_question` (
                               `id` bigint(11) NOT NULL,
                               `question_store_id` bigint(11) NULL COMMENT '题库编号',
                               `question_type` tinyint(4) NULL COMMENT '问题类型',
                               `question_content` text NULL COMMENT '问题内容',
                               `question_answer_items` varchar(255) NULL COMMENT '答案选项',
                               `question_answer` varchar(32) NULL COMMENT '正确答案',
                               `question_score` int(11) NULL COMMENT '分值',
                               `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标记',
                               PRIMARY KEY (`id`)
)
    COMMENT = '试题';
CREATE TABLE `qa_question_pager_template` (
                                              `id` bigint(11) NOT NULL,
                                              `pager_template_name` varchar(32) NULL COMMENT '模板名称',
                                              `question_store_id` bigint(11) NULL COMMENT '题库编号',
                                              `question_count` int(11) NULL COMMENT '题目数量',
                                              `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                              `enable` tinyint(1) NULL DEFAULT 1 COMMENT '启用状态',
                                              `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态',
                                              `description` varchar(200) NULL COMMENT '描述',
                                              PRIMARY KEY (`id`)
)
    COMMENT = '试卷模板';
CREATE TABLE `qa_driver_question_pager` (
                                            `id` bigint(11) NOT NULL,
                                            `template_id` bigint(11) NULL COMMENT '模板ID',
                                            `driver_id` bigint(11) NULL COMMENT '司机ID',
                                            `question_store_id` bigint(11) NULL COMMENT '题库ID',
                                            `total_score` int(11) NULL COMMENT '总分数',
                                            `question_count` int(11) NULL COMMENT '答题数量',
                                            `time_duration` int(11) NULL COMMENT '总用时',
                                            `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `submit_at` datetime NULL COMMENT '提交时间',
                                            `driver_score` int(11) NULL DEFAULT 0 COMMENT '司机得分',
                                            `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态',
                                            `effect` tinyint(1) NULL DEFAULT 0 COMMENT '是否有效',
                                            PRIMARY KEY (`id`)
)
    COMMENT = '用户答题试卷';
CREATE TABLE `qa_driver_pager_answer` (
                                          `id` bigint(11) NOT NULL,
                                          `question_id` bigint(11) NULL COMMENT '问题ID',
                                          `question_pager_id` bigint(11) NULL COMMENT '试卷ID',
                                          `driver_id` bigint(11) NULL COMMENT '司机ID',
                                          `answer` varchar(200) NULL COMMENT '答案',
                                          `question_score` int(11) NULL COMMENT '问题分数',
                                          `right` tinyint(1) NULL COMMENT '是否正确',
                                          `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          PRIMARY KEY (`id`)
)
    COMMENT = '司机答题表';
CREATE TABLE `sb_station` (
                              `id` bigint(11) NOT NULL,
                              `station_name` varchar(32) NULL COMMENT '车站名称',
                              `station_code` varchar(32) NULL COMMENT '车站编码',
                              `next_station_id` bigint(11) NULL COMMENT '下站ID',
                              `next_station_distance` float(10,2) NULL COMMENT '下站距离',
                              `seq` int(11) NULL DEFAULT 0 COMMENT '排序',
                              `description` varchar(200) NULL COMMENT '描述',
                              PRIMARY KEY (`id`) ,
                              UNIQUE INDEX `uk_station_name` (`station_name` ASC) USING BTREE,
                              UNIQUE INDEX `uk_station_code` (`station_code` ASC) USING BTREE
)
    COMMENT = '车站表';
CREATE TABLE `sb_runtime_table` (
                                    `id` bigint(11) NOT NULL,
                                    `table_name` varchar(32) NULL COMMENT '时刻表名称',
                                    `description` varchar(200) NULL COMMENT '描述',
                                    `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    PRIMARY KEY (`id`) ,
                                    UNIQUE INDEX `uk_table_name` (`table_name` ASC) USING BTREE
)
    COMMENT = '时刻表';
CREATE TABLE `sb_runtime_item` (
                                   `id` bigint(11) NOT NULL,
                                   `table_id` bigint(11) NULL COMMENT '所属时刻表',
                                   `start_station_id` bigint(11) NULL COMMENT '始发站ID',
                                   `end_station_id` bigint(11) NULL COMMENT '终点站ID',
                                   `train_no` varchar(32) NULL COMMENT '车次',
                                   `service_no` varchar(32) NULL COMMENT '服务号',
                                   `description` varchar(200) NULL COMMENT '备注',
                                   `start_at` time NULL COMMENT '开点',
                                   `end_at` time NULL COMMENT '到点',
                                   `distance` float(10,2) NULL COMMENT '总里程',
                                   `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `up` tinyint(1) NULL COMMENT '是否上行',
                                   PRIMARY KEY (`id`)
)
    COMMENT = '时刻表数据项';

ALTER TABLE `sy_permission` ADD CONSTRAINT `fk_sy_permission_sy_permission_1` FOREIGN KEY (`parent_id`) REFERENCES `sy_permission` (`id`);
ALTER TABLE `sy_role_permission_rel` ADD CONSTRAINT `fk_sy_role_permission_rel_sy_permission_1` FOREIGN KEY (`permission_id`) REFERENCES `sy_permission` (`id`);
ALTER TABLE `sy_role_permission_rel` ADD CONSTRAINT `fk_sy_role_permission_rel_sy_role_1` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`);
ALTER TABLE `sy_user_role_rel` ADD CONSTRAINT `fk_sy_user_role_rel_sy_user_1` FOREIGN KEY (`user_id`) REFERENCES `sy_user` (`id`);
ALTER TABLE `sy_user_role_rel` ADD CONSTRAINT `fk_sy_user_role_rel_sy_role_1` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`);
ALTER TABLE `sy_role_menu_rel` ADD CONSTRAINT `fk_sy_role_menu_rel_sy_role_1` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`);
ALTER TABLE `sy_role_menu_rel` ADD CONSTRAINT `fk_sy_role_menu_rel_sy_menu_1` FOREIGN KEY (`menu_id`) REFERENCES `sy_menu` (`id`);
ALTER TABLE `sy_menu` ADD CONSTRAINT `fk_sy_menu_sy_menu_1` FOREIGN KEY (`parent_id`) REFERENCES `sy_menu` (`id`);
ALTER TABLE `sy_user_dept_rel` ADD CONSTRAINT `fk_sy_user_dept_rel_sy_user_1` FOREIGN KEY (`user_id`) REFERENCES `sy_user` (`id`);
ALTER TABLE `sy_user_dept_rel` ADD CONSTRAINT `fk_sy_user_dept_rel_sy_dept_1` FOREIGN KEY (`dept_id`) REFERENCES `sy_dept` (`id`);
ALTER TABLE `sy_dept_role_rel` ADD CONSTRAINT `fk_sy_dept_role_rel_sy_dept_1` FOREIGN KEY (`dept_id`) REFERENCES `sy_dept` (`id`);
ALTER TABLE `sy_dept_role_rel` ADD CONSTRAINT `fk_sy_dept_role_rel_sy_role_1` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`);
ALTER TABLE `sy_dept` ADD CONSTRAINT `fk_sy_dept_sy_dept_1` FOREIGN KEY (`parent_id`) REFERENCES `sy_dept` (`id`);
ALTER TABLE `sy_dict_entry` ADD CONSTRAINT `fk_sy_dict_entry_sy_dict_1` FOREIGN KEY (`dict_code`) REFERENCES `sy_dict` (`dict_code`);
ALTER TABLE `qa_question` ADD CONSTRAINT `fk_qa_question_qa_question_store_1` FOREIGN KEY (`question_store_id`) REFERENCES `qa_question_store` (`id`);
ALTER TABLE `qa_question_pager_template` ADD CONSTRAINT `fk_qa_question_pager_template_qa_question_store_1` FOREIGN KEY (`question_store_id`) REFERENCES `qa_question_store` (`id`);
ALTER TABLE `qa_driver_question_pager` ADD CONSTRAINT `fk_qa_driver_question_pager_qa_question_store_1` FOREIGN KEY (`question_store_id`) REFERENCES `qa_question_store` (`id`);
ALTER TABLE `qa_driver_pager_answer` ADD CONSTRAINT `fk_qa_driver_pager_answer_qa_question_1` FOREIGN KEY (`question_id`) REFERENCES `qa_question` (`id`);
ALTER TABLE `qa_driver_pager_answer` ADD CONSTRAINT `fk_qa_driver_pager_answer_qa_driver_question_pager_1` FOREIGN KEY (`question_pager_id`) REFERENCES `qa_driver_question_pager` (`id`);
ALTER TABLE `qa_driver_question_pager` ADD CONSTRAINT `fk_qa_driver_question_pager_qa_question_pager_template_1` FOREIGN KEY (`template_id`) REFERENCES `qa_question_pager_template` (`id`);
ALTER TABLE `sb_station` ADD CONSTRAINT `fk_sb_station_sb_station_1` FOREIGN KEY (`next_station_id`) REFERENCES `sb_station` (`id`);
ALTER TABLE `sb_runtime_item` ADD CONSTRAINT `fk_sb_runtime_item_sb_runtime_table_1` FOREIGN KEY (`table_id`) REFERENCES `sb_runtime_table` (`id`);
ALTER TABLE `sb_runtime_item` ADD CONSTRAINT `fk_sb_runtime_item_sb_station_1` FOREIGN KEY (`start_station_id`) REFERENCES `sb_station` (`id`);
ALTER TABLE `sb_runtime_item` ADD CONSTRAINT `fk_sb_runtime_item_sb_station_2` FOREIGN KEY (`end_station_id`) REFERENCES `sb_station` (`id`);

