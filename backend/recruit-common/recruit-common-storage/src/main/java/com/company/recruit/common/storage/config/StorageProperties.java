package com.company.recruit.common.storage.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "storage.local")
public class StorageProperties {

    private String baseDir = "uploads";
    private String tempDir = "uploads/tmp";
    private String avatarDir = "uploads/avatar";
    private String resumeDir = "uploads/resume";
    private String companyLicenseDir = "uploads/company-license";
    private Integer maxAvatarSizeMb = 2;
    private Integer maxResumeSizeMb = 10;
    private Integer maxCompanyLicenseSizeMb = 5;
    private Integer multipartChunkSizeMb = 2;
    private List<String> resumeAllowedExtensions = List.of("pdf", "doc", "docx");
    private boolean resumeHashDeduplicate = true;
    private boolean asyncResumeParse = true;
}