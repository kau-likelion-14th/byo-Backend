package likelion14th.lte.test;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name="Test",description = "테스트용 API")
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/health")
    public String health() {
        return "아기사자화이팅!!";
    }
    @GetMapping("/items/{itemId}")
    public String getItem(
        @Parameter(description = "조회아이템 ID", example = "1")
        @PathVariable Long itemId){
            return itemId + "번";
        }
    @GetMapping("/items")
    public String searchItems(
            @Parameter(description = "검색 키워드", example = "사자")
            @RequestParam String keyword,
            @Parameter(description = "카테고리", example = "food")
            @RequestParam(required = false) String category) {
        return keyword + "검색 결과 (카테고리: " + category + ")";
    }
}
