package com.soomee.notesjwt.dto;

import com.soomee.notesjwt.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private RoleType name;
}
