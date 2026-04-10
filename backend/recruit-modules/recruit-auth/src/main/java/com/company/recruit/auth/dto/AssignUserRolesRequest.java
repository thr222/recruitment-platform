package com.company.recruit.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

@Data
public class AssignUserRolesRequest {
    @NotEmpty
    private List<String> roleCodes;
}