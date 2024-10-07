package com.jafar.api.domain.oauth2.service;

import com.jafar.api.domain.member.entity.Member;
import com.jafar.api.domain.member.repository.MemberRepository;
import com.jafar.api.domain.oauth2.dto.CustomOauth2User;
import com.jafar.api.domain.oauth2.dto.GoogleResponse;
import com.jafar.api.domain.oauth2.dto.NaverResponse;
import com.jafar.api.domain.oauth2.dto.OAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerOauth2UserService extends DefaultOAuth2UserService {

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

        String role = null;

        String userName = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        Member existData = memberRepository.findByUserName(userName);

        if(existData == null){
            Member member = new Member(userName, oAuth2Response.getEmail(), "ROLE_USER");
            memberRepository.save(member);
        } else{
            role = existData.getRole();
            existData.setUserName(userName);
            existData.setLoginId(oAuth2Response.getEmail());
            existData.setRole("ROLE_USER");

        }

        return new CustomOauth2User(oAuth2Response, role);
    }
}
