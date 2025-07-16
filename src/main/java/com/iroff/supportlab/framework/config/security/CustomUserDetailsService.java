package com.iroff.supportlab.framework.config.security;

import com.iroff.supportlab.adapter.user.out.persistence.UserJpaRepository;
import com.iroff.supportlab.adapter.user.out.persistence.UserMapper;
import com.iroff.supportlab.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userJpaRepository.findByEmail(email)
                .map(userMapper::mapToDomain)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 없습니다."));
        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserById(Long userId) {
        User user = userJpaRepository.findById(userId)
                .map(userMapper::mapToDomain)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 없습니다."));
        return new CustomUserDetails(user);
    }
}