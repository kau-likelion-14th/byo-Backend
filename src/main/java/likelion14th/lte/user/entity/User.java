package likelion14th.lte.user.entity;

import jakarta.persistence.*;
import likelion14th.lte.Entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
// [Q1. @NoArgsConstructor는 매개변수가 없는 기본 생성자를 만듭니다.
// 그런데 왜 누구나 쓸 수 있게 PUBLIC으로 열어두지 않고, 굳이 PROTECTED로 막아두었을까요? (객체 생성의 안전성과 JPA 관점)]
// 답변: JPA는 프록시 객체 생성을 위해 기본 생성자를 필요로 하지만, 외부에서 아무런 인자 없이 객체를 생성하는 것은 객체의 무결성을 해칠 수 있습니다. PROTECTED로 설정하면 JPA 스펙을 준수하면서도 외부에서의 무분별한 객체 생성을 막아 안전성을 높일 수 있습니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // [Q2. @Column(nullable = false) 어노테이션이 DB와 자바 코드 사이에서 하는 역할은 무엇인가요?]
    // 답변: DB 테이블 생성 시 해당 컬럼에 NOT NULL 제약 조건을 추가하는 역할임. 해당 필드가 필수값임을 명시하여 데이터의 정합성을 보장
    @Column(nullable = false)
    private String username;

    @Column(length = 16, nullable = false, unique = true)
    private String userTag;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Builder(access = AccessLevel.PUBLIC)
    private User (String username, String introduction, String userTag){
        this.username = username;
        this.userTag = userTag;
        this.introduction = introduction;
    }

    // [Q3. @Setter를 위 @Getter 처럼 사용하면 모든 맴버들에 setIntruduction() 같은 setter 메서드가 생성됩니다. 하지만 왜 @Setter를 쓰지않고 updateIntroduction() 이라는 명확한 메서드를 만든 객체지향적인 이유는 무엇인가요?]
    // 답변: 단순 Setter는 객체의 상태를 어디서든 변경할 수 있게 하여 캡슐화 정도를 낮춤. updateIntroduction과 같이 비즈니스 의미가 담긴 메서드를 사용하면 스스로 자신의 상태를 관리하게 할 수 있음.
    public void updateIntroduction(String introduction){
        this.introduction = introduction;
    }
}