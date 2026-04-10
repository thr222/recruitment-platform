package com.company.recruit.common.security.context;

import java.util.List;

public class LoginUser {

    private final Long userId;
    private final String username;
    private final List<String> roleCodes;
    private final List<String> permissionCodes;
    private final List<Long> companyIds;

    private LoginUser(Builder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.roleCodes = builder.roleCodes;
        this.permissionCodes = builder.permissionCodes;
        this.companyIds = builder.companyIds;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public List<String> getPermissionCodes() {
        return permissionCodes;
    }

    public List<Long> getCompanyIds() {
        return companyIds;
    }

    public static class Builder {
        private Long userId;
        private String username;
        private List<String> roleCodes;
        private List<String> permissionCodes;
        private List<Long> companyIds;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder roleCodes(List<String> roleCodes) {
            this.roleCodes = roleCodes;
            return this;
        }

        public Builder permissionCodes(List<String> permissionCodes) {
            this.permissionCodes = permissionCodes;
            return this;
        }

        public Builder companyIds(List<Long> companyIds) {
            this.companyIds = companyIds;
            return this;
        }

        public LoginUser build() {
            return new LoginUser(this);
        }
    }
}