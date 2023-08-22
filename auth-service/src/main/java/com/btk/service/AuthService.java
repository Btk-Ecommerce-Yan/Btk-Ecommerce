package com.btk.service;

import com.btk.dto.request.ActivateRequestDto;
import com.btk.dto.request.LoginRequestDto;
import com.btk.dto.request.RegisterUserRequestDto;
import com.btk.dto.response.LoginResponseDto;
import com.btk.dto.response.ToAuthPasswordChangeDto;
import com.btk.entity.Auth;
import com.btk.entity.enums.ERole;
import com.btk.entity.enums.EStatus;
import com.btk.exception.AuthManagerException;
import com.btk.exception.ErrorType;
import com.btk.manager.ISaleManager;
import com.btk.manager.IUserProfileManager;
import com.btk.mapper.IAuthMapper;
import com.btk.repository.IAuthRepository;
import com.btk.util.CodeGenerator;
import com.btk.util.JwtTokenProvider;
import com.btk.util.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final IUserProfileManager userProfileManager;
    private final ISaleManager saleManager;

    public AuthService(IAuthRepository authRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, IUserProfileManager userProfileManager, ISaleManager saleManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userProfileManager = userProfileManager;
        this.saleManager = saleManager;
    }

    public String registerUser(RegisterUserRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmail(dto.getEmail());
        if (!optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.DUPLICATE_USER);
        Auth auth = IAuthMapper.INSTANCE.fromUserRequestDtoToAuth(dto);
        auth.setRoles(List.of(ERole.USER));
        if (dto.getPassword().equals(dto.getRepassword())) {
            auth.setPassword(passwordEncoder.encode(dto.getPassword()));
            auth.setActivationCode(CodeGenerator.generateCode());
            save(auth);
            userProfileManager.createUser(IAuthMapper.INSTANCE.fromAuthNewCreateUserRequestDto(auth));
            //String token= jwtTokenProvider.createToken(optionalAuth.get().getAuthId()).get();
            // Balance oluşturmak için yapılan method
            saleManager.createBalance(auth.getAuthId());
        } else {
            throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
        }
        return "Hesabınızı aktif edeceğiniz aktivasyon kodunuz: " + auth.getActivationCode();
    }

    public String activateStatus(ActivateRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmail(dto.getEmail());
        if (optionalAuth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        else if (!optionalAuth.get().getActivationCode().equals(dto.getActivationCode()))
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        optionalAuth.get().setStatus(EStatus.ACTIVE);
        update(optionalAuth.get());
        userProfileManager.activateStatus(optionalAuth.get().getAuthId());
        return "Hesabınız aktif edilmiştir";
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findOptionalByEmail(dto.getEmail());
        if (auth.isEmpty() || !passwordEncoder.matches(dto.getPassword(), auth.get().getPassword())) {
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        } else if (!auth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
        List<String> roleList = auth.get().getRoles().stream().map(x -> x.toString()).collect(Collectors.toList());
        System.out.println(roleList);
        String token = jwtTokenProvider.createToken(auth.get().getAuthId(), roleList)
                .orElseThrow(() -> {
                    throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
                });
        return LoginResponseDto.builder().roles(roleList).token(token).build();
    }

    public String forgotPassword(String email) {
        Optional<Auth> auth = authRepository.findOptionalByEmail(email);
        if (auth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        else if (!auth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
        String randomPassword = UUID.randomUUID().toString();
        auth.get().setPassword(passwordEncoder.encode(randomPassword));
        update(auth.get());
        userProfileManager.forgotPassword(IAuthMapper.INSTANCE.fromAuthToForgotPasswordUserRequestDto(auth.get()));
        return "Yeni şifreniz: " + randomPassword + "/nLütfen en kısa sürede değiştiriniz.";
    }

    //UserService'den openfeign ile gelen metod
    public Boolean changePassword(ToAuthPasswordChangeDto dto) {
        Optional<Auth> auth = findById(dto.getAuthId());
        if (auth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        auth.get().setPassword(dto.getPassword());
        update(auth.get());
        return true;
    }
}
