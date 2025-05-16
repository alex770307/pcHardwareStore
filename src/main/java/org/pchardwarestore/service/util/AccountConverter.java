package org.pchardwarestore.service.util;

import org.pchardwarestore.dto.accountDto.AddUserRequest;
import org.pchardwarestore.dto.accountDto.UserResponse;
import org.pchardwarestore.entity.accountEntity.FileInfo;
import org.pchardwarestore.entity.accountEntity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountConverter {

    public User toUser(AddUserRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }

    public UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .status(user.getStatus().toString())
                .photoLink(user.getPhotoLink())
                .photoLinks(user.getPhotos()
                        .stream()
                        .map(FileInfo::getLink)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public List<UserResponse> fromUsers(List<User> users) {
        return users.stream()
                .map(user -> fromUser(user))
                .collect(Collectors.toList());
    }
}
