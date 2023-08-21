package com.btk.mapper;

import com.btk.dto.request.ForgotPasswordUserRequestDto;
import com.btk.dto.request.NewCreateUserRequestDto;
import com.btk.dto.request.RegisterUserRequestDto;
import com.btk.entity.Auth;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth fromUserRequestDtoToAuth(final RegisterUserRequestDto dto);
    NewCreateUserRequestDto fromAuthToNewCreateUserDto(final RegisterUserRequestDto dto);
/*    RegisterResponseDto fromAuthToResponseDto(final Auth auth);

    Auth fromManagerRequestDtoToAuth(final RegisterManagerRequestDto dto);
*/
    ForgotPasswordUserRequestDto fromAuthToForgotPasswordUserRequestDto(final Auth auth);
/*
    RegisterMailHelloModel fromAuthToRegisterMailHelloModel(final Auth auth);

    Auth fromCreatePersonelProfileDtotoAuth(final AuthCreatePersonnelProfileResponseDto dto);


    RegisterMailModel fromAuthToRegisterMailModel(final Auth auth);
*/
    NewCreateUserRequestDto fromAuthNewCreateUserRequestDto(final Auth auth);
/*
    NewCreateManagerUserRequestDto fromRegisterManagerRequestDtoToNewCreateManagerUserRequestDto(final RegisterManagerRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBecauseOfUserProfile(PersonelUpdateUserProfileToAuthRequestDto dto, @MappingTarget Auth auth);

    SubscribeCompanyRequestDto fromRegisterManagerRequestDtoToSubscribeCompanyRequestDto(final RegisterManagerRequestDto dto);
 */

}
