package likelion14th.lte.follow.dto;

import likelion14th.lte.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowUserRequest {
    private Long toUserId;
    
}
