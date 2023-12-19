package org.project.balabolkka.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.login.dto.LoginRequest;
import org.project.balabolkka.login.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 & 로그아웃 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody LoginRequest loginRequest, HttpServletResponse response){

        String loginToken = loginService.login(loginRequest, response);

        return ResponseEntity.ok(loginToken);
    }

}
