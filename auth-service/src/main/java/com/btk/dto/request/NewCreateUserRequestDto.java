package com.btk.dto.request;

import com.btk.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCreateUserRequestDto {
    private Long authId;
    private String password;
    private String email;
    private String name;
    private String middleName;
    private String surname;
    private EStatus status;
}
