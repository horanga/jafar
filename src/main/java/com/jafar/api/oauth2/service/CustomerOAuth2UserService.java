package com.jafar.api.oauth2.service;

import com.jafar.api.domain.member.entity.Member;
import com.jafar.api.domain.member.repository.MemberRepository;
import com.jafar.api.oauth2.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;

        if(registrationId.equals("naver")){
            oAuth2Response = new NaverResponse(oauth2User.getAttributes());

        } else if(registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oauth2User.getAttributes());

        } else {

            return null;
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        Member existData = memberRepository.findByUserName(username);

        if (existData == null) {

            Member userEntity = new Member(oAuth2Response.getEmail(), username, oAuth2Response.getName(), "ROLE_USER");
            memberRepository.save(userEntity);

            UserDto userDTO = new UserDto();
            userDTO.setUserName(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(userDTO);
        }
        else {

            existData.setEmail(oAuth2Response.getEmail());
            existData.setUserName(oAuth2Response.getName());

            memberRepository.save(existData);

            UserDto userDTO = new UserDto();
            userDTO.setUserName(existData.getUserName());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole());

            return new CustomOAuth2User(userDTO);
        }
    }
}
