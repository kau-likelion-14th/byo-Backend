package likelion14th.lte.user.dto.response;

import likelion14th.lte.user.entity.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileResponse{
    private String userName;
    private String profileImageUrl;
    private String introduction;

    // [Q4. Controller가 DB에서 꺼낸 원본 Entity(User)를 클라이언트 화면에 그대로 반환하지 않고,
    // 굳이 from() 메서드를 통해 DTO로 한번 변환해서 내보내는 핵심적인 이유 2가지는 무엇인가요?]
    // 답변: 1. 보안 및 데이터 은닉: 엔티티에는 클라이언트에게 노출하면 안 되는 민감한 정보(비밀번호 등)가 포함될 수 있는데, DTO를 통해 필요한 데이터만 선별적으로 전달할 수 있습니다. 2. 유연한 구조 설계: 엔티티와 API 스펙을 분리함으로써 DB 구조가 변경되더라도 API 스펙이 변하는 것을 막을 수 있고, 화면 요구사항에 맞게 데이터를 가공(예: username + userTag 결합)하여 전달하기 용이합니다.
    public static UserProfileResponse from(User user){
        return new UserProfileResponse(
                user.getUsername() + "#" + user.getUserTag(),
                user.getProfileImage(),
                user.getIntroduction()
        );
    }
}