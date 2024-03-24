package com.example.demo.common.oauth.provider;

import com.example.demo.common.Constant;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.oauth.model.SocialOauth;
import com.example.demo.utils.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;

import static com.example.demo.common.model.response.BaseResponseStatus.INVALID_OAUTH_TYPE;

@Component
@RequiredArgsConstructor
public class OAuthStrategy {

    public SocialOauth getOauthStrategy(Constant.SocialLoginType socialLoginType) {
        try {
            return (SocialOauth) BeanUtil.getBean(socialLoginType.name());
        } catch (NoSuchBeanDefinitionException e) {
            throw new BaseException(INVALID_OAUTH_TYPE);
        }
    }
}
