package likelion14th.lte.follow.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import likelion14th.lte.user.entity.User;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowUserResponse {
    private long userId;
    private String username;
    private String profileImageurl;
    private String introduction;

    public static FollowUserResponse from(User user) {
        return new FollowUserResponse(
            user.getId(),
            user.getUsername() + "#" + user.getUserTag(),
            user.getProfileImage(),
            user.getIntroduction()
        );
    }

}
