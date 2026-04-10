USE recruit_platform;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS sys_role_permission;
DROP TABLE IF EXISTS sys_permission;

CREATE TABLE sys_permission (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'permission id',
    permission_code VARCHAR(64) NOT NULL COMMENT 'permission code',
    permission_name VARCHAR(128) NOT NULL COMMENT 'permission name',
    permission_type VARCHAR(32) NOT NULL DEFAULT 'API' COMMENT 'permission type: API/MENU/BUTTON',
    api_path VARCHAR(255) DEFAULT NULL COMMENT 'api path',
    http_method VARCHAR(16) DEFAULT NULL COMMENT 'http method',
    permission_status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT 'permission status',
    sort_no INT NOT NULL DEFAULT 0 COMMENT 'sort number',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_permission_code (permission_code),
    KEY idx_sys_permission_type_status (permission_type, permission_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='system permission table';

CREATE TABLE sys_role_permission (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'primary id',
    role_id BIGINT NOT NULL COMMENT 'role id',
    permission_id BIGINT NOT NULL COMMENT 'permission id',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_role_permission_role_permission (role_id, permission_id),
    KEY idx_sys_role_permission_permission_id (permission_id),
    CONSTRAINT fk_sys_role_permission_role FOREIGN KEY (role_id) REFERENCES sys_role (id),
    CONSTRAINT fk_sys_role_permission_permission FOREIGN KEY (permission_id) REFERENCES sys_permission (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='role permission relation table';

SET FOREIGN_KEY_CHECKS = 1;
