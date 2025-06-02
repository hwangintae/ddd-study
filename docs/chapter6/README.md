## 6.1 presentation 영역과 application 영역
domain 영역을 잘 구현하지 않으면 사용자의 요구를 충족할 수 없다.

하지만 domain 영역을 잘 만든다고 끝나는건 아니다. domain이 제 기능을 하려면 사용자와 domain을 연결해 주는 매개체가 필요하다.

**실제 사용자가 원하는 기능을 제공하는 것은 application 영역에 위치한 service다.**

**사용자와 상호작용은 presentation에서 처리하기 때문에 application service는 presentation에 의존하지 않는다.**

## 6.2 application service의 역할
application service는 사용자의 요청을 처리하기 위해 repository에서 domain 객체를 가여와 사용한다.

application service는 domain 영역과 presentation 영역을 연결해주는 창구 역할을 한다.

application service는 주로 domain 객체 간의 흐름을 제어하기 때문에 다음과 같이 단순한 형태를 갖는다.
```java
public Result doSomeFunc(SomeReq req) {
	// 1. repository에서 aggregate를 구한다.
	SomeAgg agg = someAggRepository.findById(req.getId());
	checkNull(agg);

	// 2. aggregate의 domain 기능을 실행한다.
	agg.doFuc(req.getValue());

	// 3. 결과를 리턴한다.
	return createSuccessResult(agg);
}
```

새로운 aggregate를 생성하는 application service 역시 간단하다.
```java
public Result doSomeCreation(CreateSomeReq req) {
	// 1. 데이터 중복 등 데이터가 유효한지 검사한다.
	validate(req);

	// 2. aggregate를 생성한다.
	SomeAgg newAgg = createSome(req);

	// 3. repository에 aggregate를 저장한다.
	someAggRepository.save(newAgg);

	// 4. 결과를 리턴한다.
	return createSuccessResult(newAgg);
}
```

application service는 transaction 처리도 담당한다. application service는 domain의 상태 변경을 transaction으로 처리해야 한다.
```java
public void blockMembers(String[] blockingIds) {
	if (blockingIds == null || blockingIds.length == 0) return;

	List<Member> members = memberRepository.findByIdIn(bolckingIds);

	for (Member mem : members) {
		mem.block();
	}
}
```

#### 6.2.1 domain 로직 넣지 않기
domain 로직을 domain 영역과 application service에 분산해서 구현하면 코드 품질에 문제가 발생한다.
1. 코드의 응집성이 떨어진다.
    1. 서로 다른 영역에 위치한다는 것은 domain 로직을 파악하기 위해 여러 영역을 분석해야 한다는 것을 의미
2. 여러 application service에서 동일한 domain 로직을 구현할 가능성이 높다.

## 6.3 application service의 구현
application service는 presetation 영역과 domain 영역을 연결하는 매개체 역할을 하는데 이는 디자인 패턴에서 facade와 같은 역할을 한다. application service 자체는 복잡한 로직을 수행하지 않기 때문에 application service의 구현은 어렵지 않다.

#### 6.3.1 application service의 크기
회원 domain을 생각해 보자. application service는 회원 가입하기, 회원 탈퇴하기, 회원 암호 변경하기 , 비밀번호 초기화하기와 같은 기능을 구현하기 위해 domain model을 사용하게 된다.

이 경우 application service는 보통 두 가지 방법 중 한 가지 방식으로 구현한다.
1. 한 application service class에 회원 domain의 모든 기능 구현
2. 구분되는 기능별로 application service class를 따로 구현하기

1의 경우
```java
public class MemberService {
	private MemberRepository memberRepository;

	public void join(MemberJoinrequest joinRequest) {...}
	public void changePassword(String memberId, String curPw, Stirng newPw) {...}
	public void initializePassword(String memberId) {...}
	public void leave(String memberId, String curPw) {...}
}
```

한 domain과 관련된 기능을 구현한 코드가 한 class에 위치하므로 각 기능에서 동일 로직에 대한 코드 중복을 제거할 수 있다는 장점이 있다.

예를 들어 changePassword(), initialixePassword(), leave()는 회원이 존재하지 않으면 NoMemberException을 발생시켜야 한다고 해보자. 이 경우 중복된 로직을 구현한 private 메서드를 구현하고 이를 호출하는 방법으로 중복 로직을 쉽게 제거할 수 있다.

```
public class MemberService {
	private final MemberRepository memberRepository;
	private final Notifier notifier;

	public void changePassword(String memberId, String curentPw, String newPw) {
		Member member = findExistingMember(memberId);
		member.changePassword(currentPw, newPw);
	}

	public void initializePassword(String memberId) {
		Member member = findExistingMember(memberId);
		String newPassword = member.initializePassword();
		notifier.notifyNewPassword(member, newPassword);
	}

	public void leave(String memberId, String curPw) {
		Member member = findExistingMember(memberId);
		member.leave();
	}

	private Member findExistingMember(String memberId) {
		Member member = memberRepository.findById(memberId);
		if (member == null) {
			throw enw NoMemberException(memberId);
		}
		return member;
	}
}
```

각 기능에서 동일한 로직을 위한 코드 중복을 제거하기 쉽다는 것이 장점이지만

한 서비스 class 크기가 커진다느 ㄴ것이 단점이다.

코드의 크기가 커지면 연관성이 적은 코드가 한 class에 함께 위치할 가능성이 높아지게 되는데 결과적으로 관련 없는 코드가 뒤섞여 코드를 이해하는 데 방해가 된다.

게다가 한 class에 코드가 모이기 시작하면 엄연히 분리하는 것이 좋은 상황임에도 습관적으로 기존에 존재하는 class에 억지로 끼워 넣게 된다.

구분되는 기능별로 서비스 class를 구현하는 방식은 한 application service class에서 한 개 내지 2 ~ 3개의 기능을 구현한다.
```java
public class ChangePasswordService {
	private final MemberRepository memberRepository;

	public void changePassword(String memberId, String curPw, String newPw) {
		Member member = memberRepository.findById(memberId);
		if (member == null) throw new NoMemberException(memberId);
		member.changePassword(curPw, newPw);
	}
}
```

각 기능마다 동일한 로직을구현할 경우 여러 class에 중복해서 동일한 코드를 구현할 가능성이 있다. 이 경우 별도 클래스에 로직을 구현해서 코드가 중복되는 것을 방지할 수 있다.

```java
public final class MemberServiceHelper {
	public static Memeber findExistingMember(MemberRepository repo, String memberId) {
		Member member = member Repository.findById(memberId);
		if (member == null) {
			throw enw NoMemberException(memberId);
		}
		return member;
	}
}

// 공통 로직을 제공하는 메서드를 application service에서 사용
import static com.intae.member.application.MemberServiceHelper.*;

public class ChangePasswordService {
	private MemberRepository memberRepository;

	public void changePassword(String memberId, String curPw, String newPw) {
		Member member = findExistingMember(memberRepository, memberId);
		member.changePassword(curPw, newPw);
	}
}
```

#### 6.3.2 application service의 interface와 class
application service를 구현할 때 논쟁이 interface가 필요한가 이다.
```java
public interface ChangePasswordService {
	public void ChangePassword(String memberId, String curPw, String newPw);
}

public class ChangePasswordServiceImpl implements ChangePasswordService {
	// ... 구현
}
```

interface가 필요한 몇 가지 상황이 있는데 그중 하나는 구현 class가 여러 개인 경우다. 구현 class가 다수 존재하거나 런타임에 구현 객체를 교체해야 할 때 interface를 유용하게 사용할 수 있다.

그런데 application service는 런타임에 교체하는 경우가 거의 없고 한 application service의 구현 class가 두 개인 경우도 드물다.

따라서, interface가 명확하게 필요하기 전까지는 application service에 대한 interface를 작성하는 것이 좋은 선택은 아니다.

#### 6.3.3 메서드 파라미터와 값 리턴
application service가 제공하는 메서드는 domain을 이용해서 사용자가 요구한 기능을 실행하는 데 필요한 값을 파라미터로 전달받아야 한다.
```java
public class ChangePasswordService {
	public void changePassword(String memberId, String curPw, String newPw) {
		// ...
	}
}
```

별도 데이터 클래스르 만들어 전달받을 수도 있다.
```java
public class ChangePasswordRequest {
	private String memberId;
	private String currentPassword;
	private String newPassword;

	// ...
}

public class ChangePasswordService {
	public void changePassword(ChangePasswordRequest req) {
		Member member = findExistingMember(req.getMemberId());
		member.changePassword(req.getCurrentPassword(), req.getNewPassword());
	}
}
```

> [!note]
> DDD 보단 Spring에 가까운 이야기라 생략

#### 6.3.4 presentation 영역에 의존하지 않기
application service의 파라미터 type을 결정할 때 주의할 점은 presentation 영역과 관련된 type을 사용하면 안 된다.

예를 들어 다음과 같이 presentation 영역에 해당하는 HttpServletRequest나 HttpSession을 application service에 파라미터로 전달하면 안된다.

```java
@Controller
@RequestMapping("/member/changePassword")
public class MemberPasswordController {

	@PostMapping
	public String submit(HttpServletRequest request) {
		try {
			// application service가 presentation 영역을 의존하면 안 됨!
			changePasswordService.changePassword(request);
		} catch(NoMemberException ex) {
			// 알맞은 ex 처리 및 응답
		}
	}
}
```

1. application service에서 presentation 영역에 대한 의존이 발생하면 application service만 단독으로 테스트하기가 어려워진다.
2. presentation 영역의 구현이 변경되면 application service의 구현도 함께 변경해야 하는 문제도 발생한다.

이 두 문제보다 더 심각한 것은 application service가 presentation 영역의 역할까지 대신하는 상황이 벌어질 수도 있다.
```java
public class AuthenticationService {
	public void authenticate(HttpServletRequest request) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		if (checkIdPasswordMatching(id, password)) {
			HttpSession session = request.getSession();
			session.setAttribute("auth", new Authentication(id));
		}
	}
}
```

HttpSession이나 쿠키는 presentation 영역의 상태에 해당하는데 이 상태를 application service에서 변경해 버리면 presentation 영역의 코드만으로 상태가 어떻게 변경되는지 추적하기 어려워진다.

## 6.5 값 검증
application service에서 값을 검사하는 시점에 첫 번쩨 값이 올바르지 않아 exception이 발생시키면 나머지 항목에 대해서는 값을 검사하지 않게 된다.

이러면 사용자는 첫 번째 값에 대한 에러 메시지만 보게 되고 나머지 항목에 대해서는 값이 올바른지 알 수 없게 된다.

이런 사용자 불편을 해소하기 위해 application servie에서 error code를 모아 하나의 exception으로 발생 시키는 방법도 있다.
```java
@Transactional
public OrderNo placeOrder(OrderRequest orderRequest) {
	List<ValidationError> errors = new ArrayList<>();

	if (orderRequest == null) {
		errors.add(ValidationError.of("empty"));
	} else {
		if (orderRequest.getOrdererMemberId() == null) {
			errors.add(ValidationError.of("ordererMemberId", "empty"));
		}
		if (orderRequest.getOrderProducts() == null) {
			errors.add(ValidationError.of("orderProducts", "empty"));
		}
		if (orderRequest.getOrderProducts().isEmpty()) {
			errors.add(ValidationError.of("orderProducts", "empty"));
		}
	}

	if (!errors.isEmpty()) throw new ValidationErrorException(errors);
}

@PostMapping("/orders/order")
public String order(@ModelAttribute("orderReq") OrderRequest orderRequest,
					BindingResult bindingResult,
					ModelMap modelMap) {
	User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

	orderRequest.setOrdererMemberId(MemberId.of(user.getUsername()));

	try {
		OrderNo orderNo = placeOrderServie.placeOrder(orderRequest);
	}
}
```
