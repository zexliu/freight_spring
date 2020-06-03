
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
                           `menu_name` varchar(32) NOT NULL COMMENT '菜单名称',
                           `menu_code` varchar(32) NOT NULL COMMENT '菜单编码',
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
                           `gender` tinyint(4) NULL COMMENT '性别',
                           `birth_day` date NULL COMMENT '生日',
                           `enable` tinyint(1) NULL DEFAULT 1 COMMENT '可用状态',
                           `locked` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态',
                           `login_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
                           `login_ip` varchar(32) NULL COMMENT '最后登录IP',
                           `expire_at` datetime NULL COMMENT '过期时间',
                           `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`id`) ,
                           UNIQUE INDEX `username` (`username` ASC) USING BTREE,
                           UNIQUE INDEX `mobile` (`mobile` ASC) USING BTREE,
                           UNIQUE INDEX `email` (`email` ASC) USING BTREE
)
    COMMENT = '用户表';
CREATE TABLE `sy_user_dept_rel` (
                                    `id` bigint(11) NOT NULL,
                                    `user_id` bigint(11) NOT NULL COMMENT '用户ID',
                                    `dept_id` bigint(11) NOT NULL COMMENT '部门ID',
                                    PRIMARY KEY (`id`) ,
                                    INDEX `uk_user_id_dept_id` (`user_id` ASC, `dept_id` ASC) USING HASH
)
    COMMENT = '用户部门关系表';
CREATE TABLE `sy_dept` (
                           `id` bigint(11) NOT NULL,
                           `dept_name` varchar(32) NOT NULL COMMENT '部门名称',
                           `dept_code` varchar(32) NULL COMMENT '部门编码',
                           `parent_id` bigint(11) NULL COMMENT '所属部门',
                           `address` varchar(200) NULL COMMENT '部门地址',
                           `fax` varchar(32) NULL COMMENT '传真',
                           `phone` varchar(32) NULL COMMENT '电话',
                           `description` varchar(200) NULL COMMENT '描述',
                           `seq` int(11) NULL DEFAULT 0 COMMENT '排序',
                           `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`id`)
)
    COMMENT = '部门表';
CREATE TABLE `sy_dept_role_rel` (
                                    `id` bigint(11) NOT NULL,
                                    `dept_id` bigint(11) NOT NULL COMMENT '部门ID',
                                    `role_id` bigint(11) NOT NULL COMMENT '角色ID',
                                    PRIMARY KEY (`id`)
)
    COMMENT = '部门角色关系表';
CREATE TABLE `sy_dict` (
                           `id` bigint(11) NOT NULL,
                           `dict_name` varchar(30) NOT NULL COMMENT '字典名称',
                           `dict_code` varchar(30) NOT NULL COMMENT '字典编码',
                           `description` varchar(200) NULL COMMENT '描述',
                           `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`id`) ,
                           UNIQUE INDEX `uk_dict_name` (`dict_name` ASC) USING BTREE,
                           UNIQUE INDEX `uk_dict_code` (`dict_code` ASC) USING BTREE
)
    COMMENT = '数据字典';
CREATE TABLE `sy_dict_entry` (
                                 `id` bigint(11) NOT NULL,
                                 `dict_code` varchar(30) NULL COMMENT '字典编码',
                                 `dict_entry_name` varchar(30) NULL COMMENT '字典项名称',
                                 `dict_entry_value` varchar(30) NULL COMMENT '字典项值',
                                 `description` varchar(200) NULL COMMENT '描述',
                                 `create_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `enable` tinyint(1) NULL DEFAULT 1 COMMENT '可用状态',
                                 `seq` int(11) NULL DEFAULT 0 COMMENT '排序',
                                 PRIMARY KEY (`id`)
)
    COMMENT = '字典项';

ALTER TABLE `sy_permission` ADD CONSTRAINT `fk_sy_permission_sy_permission_1` FOREIGN KEY (`parent_id`) REFERENCES `sy_permission` (`id`);
ALTER TABLE `sy_role_permission_rel` ADD CONSTRAINT `fk_sy_role_permission_rel_sy_permission_1` FOREIGN KEY (`permission_id`) REFERENCES `sy_permission` (`id`);
ALTER TABLE `sy_role_permission_rel` ADD CONSTRAINT `fk_sy_role_permission_rel_sy_role_1` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`);
ALTER TABLE `sy_role_menu_rel` ADD CONSTRAINT `fk_sy_role_menu_rel_sy_role_1` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`);
ALTER TABLE `sy_role_menu_rel` ADD CONSTRAINT `fk_sy_role_menu_rel_sy_role_menu_1` FOREIGN KEY (`menu_id`) REFERENCES `sy_menu` (`id`);
ALTER TABLE `sy_user_role_rel` ADD CONSTRAINT `fk_sy_user_role_rel_sy_role_1` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`);
ALTER TABLE `sy_user_role_rel` ADD CONSTRAINT `fk_sy_user_role_rel_sy_user_1` FOREIGN KEY (`user_id`) REFERENCES `sy_user` (`id`);
ALTER TABLE `sy_user_dept_rel` ADD CONSTRAINT `fk_sy_user_dept_rel_sy_user_1` FOREIGN KEY (`user_id`) REFERENCES `sy_user` (`id`);
ALTER TABLE `sy_user_dept_rel` ADD CONSTRAINT `fk_sy_user_dept_rel_sy_dept_1` FOREIGN KEY (`dept_id`) REFERENCES `sy_dept` (`id`);
ALTER TABLE `sy_dept_role_rel` ADD CONSTRAINT `fk_sy_dept_role_rel_sy_dept_1` FOREIGN KEY (`dept_id`) REFERENCES `sy_dept` (`id`);
ALTER TABLE `sy_dept_role_rel` ADD CONSTRAINT `fk_sy_dept_role_rel_sy_role_1` FOREIGN KEY (`role_id`) REFERENCES `sy_role` (`id`);
ALTER TABLE `sy_menu` ADD CONSTRAINT `fk_sy_role_menu_sy_role_menu_1` FOREIGN KEY (`parent_id`) REFERENCES `sy_menu` (`id`);
ALTER TABLE `sy_dept` ADD CONSTRAINT `fk_sy_dept_sy_dept_1` FOREIGN KEY (`parent_id`) REFERENCES `sy_dept` (`id`);
ALTER TABLE `sy_dict_entry` ADD CONSTRAINT `fk_table_1_sy_dict_1` FOREIGN KEY (`dict_code`) REFERENCES `sy_dict` (`dict_code`);

