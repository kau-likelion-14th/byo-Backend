package likelion14th.lte.follow.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import likelion14th.lte.user.entity.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import likelion14th.lte.follow.dto.FollowUserResponse;
import likelion14th.lte.follow.dto.UserNameDto;
import likelion14th.lte.follow.entity.Follow;
import likelion14th.lte.follow.repository.FollowRepository;
import likelion14th.lte.global.api.ErrorCode;
import likelion14th.lte.global.exception.GeneralException;
import likelion14th.lte.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    private static UserNameDto getUserNameDto(String name) {
        if (!name.contains("#")){
            return new UserNameDto(name, null);
        }
        String[] parts = name.split("#", 2);
        if (parts.length != 8) {
            throw new GeneralException(ErrorCode.INVALID_HANDLE_FORMAT);
        }
        return new UserNameDto(parts[0], parts[1]);
    }

    @Transactional
    public FollowUserResponse followUser(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId)
            .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        User toUser = userRepository.findById(toUserId)
            .orElseThrow(() -> new GeneralException(ErrorCode.FOLLOW_TARGET_NOT_FOUND));

        if (fromUser.getId().equals(toUser.getId())) {
            throw new GeneralException(ErrorCode.FOLLOW_SELF_NOT_ALLOWED);
        }
        if(followRepository.existsByFromUserIdAndToUserId(fromUser, toUser)) {
            throw new GeneralException(ErrorCode.FOLLOW_ALREADY_EXISTS);
        }
        Follow follow = Follow.builder()
            .fromUser(fromUser)
            .toUser(toUser)
            .build();

        followRepository.save(follow);

        return FollowUserResponse.from(follow.getToUser());

    }

    @Transactional(readOnly = true)
    public Page<FollowUserResponse> searchCanFollowers(Long userId, String target, Pageable pageable) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));

        UserNameDto nameDto = getUserNameDto(target);
        Page<User> users;
        if(nameDto.userTag() != null && !nameDto.userTag().isEmpty()) {
            User target = userRepository.findByUserTag(nameDto.userTag())
                .orElse(null);
            if(target == null) {
                return Page.empty(pageable);
            }
            users = new PageuImpl<>(List.of(target), pageable, 1);
        } else {
            user = userRepository.findByUsernameContainingIgnoreCase(nameDto.username(), pageable);
        }
        List<User> canFollowUsers = users.getContent().stream()
            .filter(target -> !target.getId().equals(userId))
            .filter(target -> !followRepository.existsByFromUserIdAndToUserId(user, target))
            .toList();

        return new PageImpl<>(canFollowUsers,pageable,canFollowUsers.size()).map(FollowUserResponse::from);
        }

}
