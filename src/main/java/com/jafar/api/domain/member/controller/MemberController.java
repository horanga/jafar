package com.jafar.api.domain.member.controller;

import com.jafar.api.oauth2.dto.CustomOAuth2User;
import com.jafar.api.oauth2.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class MemberController {

    @GetMapping("/user-info")
    public ResponseEntity<UserDto> getUserInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        if (customOAuth2User == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDto userDto = new UserDto();
        userDto.setEmail(customOAuth2User.getName());
        userDto.setRole(customOAuth2User.getAuthorities().iterator().next().getAuthority());

        return ResponseEntity.ok(userDto);
    }
}
