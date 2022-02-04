package com.fitmate.backend.advice;

import com.fitmate.backend.advice.exception.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RequiredArgsConstructor
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {
    @ExceptionHandler({NotFoundAuthentication.class})
    protected ResponseEntity<ApiError> authenticationNotFound(final NotFoundAuthentication e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .code("E001")
                        .message("SecurityContext 에 인증 정보가 없습니다. ")
                        .build());
    }

    @ExceptionHandler({NotFoundUserInformation.class})
    protected ResponseEntity<ApiError> userNotFoundException(final NotFoundUserInformation e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .code("C001")
                        .message("This user is not found!!")
                        .build());
    }
    @ExceptionHandler({DuplicatedNicknameException.class})
    protected ResponseEntity<ApiError> cannotSignupMember(final DuplicatedNicknameException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiError.builder()
                        .status(HttpStatus.CONFLICT)
                        .code("C002")
                        .message("This nickname already exists...")
                        .build());
    }
    @ExceptionHandler({DuplicatedEmailException.class})
    protected ResponseEntity<ApiError> cannotSignupMember(final DuplicatedEmailException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiError.builder()
                        .status(HttpStatus.CONFLICT)
                        .code("C003")
                        .message("This email already exists...")
                        .build());
    }
    @ExceptionHandler({SocialCommunicationException.class})
    protected ResponseEntity<ApiError> notFoundSocialMember(){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .code("D001")
                        .message("This token is not valid...")
                        .build());

    }
    @ExceptionHandler({MultipartFileException.class})
    protected ResponseEntity<ApiError> cannotUploadMultipartFile(){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .code("F001")
                        .message("error: MultipartFile -> File convert fail")
                        .build());
    }
    @ExceptionHandler({IntegrityConstraintViolationException.class})
    protected ResponseEntity<ApiError> cannotMakePortfoiloByUniqueProperty(){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .code("G001")
                        .message("error: This portfolio cannot make due to its unique property.")
                        .build());
    }
    @ExceptionHandler({NotFoundPortfolioException.class})
    protected ResponseEntity<ApiError> cannotReadPortfolioById(){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .code("G002")
                        .message("error: This portfolio is not found!!")
                        .build());
    }
    @ExceptionHandler({NotFoundStyleException.class})
    protected ResponseEntity<ApiError> cannotReadStyleById(){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .code("G003")
                        .message("error: This style is not found!!")
                        .build());
    }
    @ExceptionHandler({NotFoundPortfolioReviewException.class})
    protected ResponseEntity<ApiError> cannotReadReviewById(){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .code("G004")
                        .message("error: This review is not found!!")
                        .build());
    }
}
