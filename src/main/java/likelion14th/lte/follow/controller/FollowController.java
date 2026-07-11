package likelion14th.lte.follow.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import likelion14th.lte.follow.dto.FollowUserRequest;
import likelion14th.lte.follow.dto.FollowUserResponse;
import likelion14th.lte.follow.service.FollowService;
import likelion14th.lte.global.api.SuccessCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("api/follow")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "Follow API", description = "팔로우 추가 및 삭제 조회 담당 API")
public class FollowController {
    private final FollowService followService;
    @PostMapping("/follow")
    @Operation(summary = "팔로우 추가", description = "팔로우 추가 API")

    public ApiResponse<FollowUserResponse> addFollow(
        @RequestParam Long userId,
        @RequestBody FollowUserRequest followUserRequest
    ) {
        FollowUserResponse response = followService.followUser(userId, followUserRequest.getToUserId());
        return ApiResponse.onSuccess(SuccessCode.FOLLOW_ADD_SUCCESS, response);
    }
    @GetMapping("/search")
    @Operation(summary = "팔로우 검색", description = "팔로우 검색 API")
    public ApiResponse<Page<FollowUserResponse>> getSearchFollowers(
        @RequestParam Long userId,
        @RequestParam String nickname,
        @ParameterObject @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<FollowUserResponse> response = followService.searchCanFollowers(userId, nickname, pageable);
        return ApiResponse.onSuccess(SuccessCode.FOLLOW_SEARCH_SUCCESS, response);

    }
}
