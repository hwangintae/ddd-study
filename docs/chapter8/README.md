## 8.1 aggregate와 transaction
한 주문 aggregate에 대해 운영자는 배송 상태로 변경할 때
사용자는 배송지 주소를 변경하면 어떻게 될까?

운영자 thread와 고객 thread는 개념적으로 동일한 aggregate지만 물리적으로 서로 다른 aggregate 객체를 사용한다. 때문에 운영자 thread는 고객 thread가 사용하는 주문 aggregate 객체에는 영향을 주지 않는다.

이 상황에서 두 thread는 각각 transaction을 commit 할 때 수정한 내용을 DB에 반영한다. 즉, aggregate의 일관성이 깨진다.

일관성을 유지하기 위해서는 다음과 같은 선택을 해야한다.
- 운영자가 배송지 정보를 조회하고 상태를 변경하는 동안, 고객이 aggregate를 수정하지 못하게 막는다.
- 운영자가 배송지 정보를 조회한 이후에 고객이 정보를 변경하면, 운영자가 aggregate를 다시 조회한 뒤 수정하도록 한다.

이 두 가지는 aggregate 자체의 transaction과 관련이 있다. DBMS가 지원하는 transaction과 함께 aggregate를 위한 추가적인 transaction 처리 기법이 필요하다.

aggregate에 대해 사용할 수 있는 대표적인 transaction 처리 방식에는 선점 잠금과 비선점 잠금의 두 가지 방식이 있다.

## 8.2 선점 잠금
선점 잠금은 **먼저 aggregate를 구한 thread가 aggregate 사용이 끝날 때까지 다른 thread가 해당 aggregate를 수정하지 못하게 막는 방식**

선점한 thread가 aggregate에 대한 잠금을 해제할 때까지 블로킹 된다.

운영자 스레드가 먼저 선점 밤금 방식으로 주문 aggregate를 구하면 운영자 thread가 잠금을 해제할 때까지 고객 thread는 대기 상태가 된다.

잠금이 해제된 시점에 고객 thread의 주문 aggregate는 배송지 변경 시 에러를 발생하고 transaction은 실패하게 된다.

**선점 잠금은 보통 DBMS가 제공하는 행단위 잠금을 사용해서 구현**한다. 다수의 DBMS가 for update와 같은 query를 사용해서 특정 record에 한 connection만 접근할 수 있는 잠금장치를 제공한다.

선점 잠금에 따른 교착 상태를 방지하도록 잠금을 구할 때 최대 대기 시간을 지정해야 한다.

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout",
			value = "5000")})
Optional<Member> findByIdForUpdate(MemberId memberId);
```

## 8.3 비선점 잠금
1. 운영자가 배송지 정보를 조회하고 배송 상태로 변경하는 사이에 고객이 배송지를 변경함
2. 운영자는 고객이 변경하기 전 배송지 정보를 이용하여 배송 준비를 한 뒤에 배송 상태로 변경
3. 즉, 배송 상태 변경 전에 배송지를 한 번 더 확인하지 않으면 운영자는 다른 배송지로 물건을 발송

비선점 잠금은 동시에 접근하는 것을 막는 대신 변경한 데이터를 실제 DBMS에 반영하는 시점에 변경 가능 여부를 확인
```sql
update aggtable set version = version + 1, colx = ?, coly = ?
where aggid = ? and version = 현재버전
```

이 query는 수정할 aggregate와 매핑되는 테이블의 버전 값이 현재 aggregate의 버전과 동일한 경우에만 데이터를 수정. 그리고 수정에 성공하면 버전 값을 1 증가 시킴

JPA는 버전을 이용한 비선점 잠금 기능을 지원한다.
```java
@Entity
public class Order {

	@EmbeddedID
	private OrderNo number;

	@Version
	private long version;
}
```

#### 8.3.1 강제 버전 증가
aggregate에 aggregate root 외 다른 entity가 존재할 때, root가 아닌 다른 antity의 값이 변경됐다고 하자.

이 경우 JPA는 root entity의 version 값을 증가시키지 않는다. 연관된 entity의 값이 변경된다고 해도 root entity 자체의 값은 바뀌는 것이 없으므로 root entity의 version 값은 갱신하지 않는 것이다.

이런 JPA의 특징은 aggregate 관점에서 보면 문제가 된다. root entity의 값이 바뀌지 않았더라도 aggregate의 구성요소 중 일부 값이 바뀌면 논리적으로 그 aggregate는 바뀐 것이다. 따라서 aggregate 내에 어떤 구성요소의 상태가 바뀌면 root aggregate의 version 값이 증가해야 비선점 잠금이 올바르게 동작한다.

```java
@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
Optional<Member> findByIdForUpdate(MemberId memberId);
```

## 8.4 오프라인 선점 잠금
단일 transaction에서 동시 변경을 막는 선점 잠금 방식과 달리 오프라인 선점 잠금은 여러 transaction에 걸쳐 동시 변경을 막는다. 첫 번째 transaction을 시작할 때 오프라인 잠금을 선점하고, 마지막 transaction에서 잠금을 해제한다.

사용자 A가 수정 요청을 수행하지 않고 프로그램을 종료하면 잠금을 해제하지 않으므로 다른 사용자는 영원히 잠금을 구할 수 없다. 이런 사태를 방지하기 위해 오프라인 선점 방식은 잠금 유효 시간을 가져야 한다.

#### 8.4.1 오프라인 선점 잠금을 위한 LockManager interface와 관련 class
오프라인 선점 잠금은 크게
1. 잠금 선점 시도
2. 잠금 확인
3. 잠금 해제
4. 잠금 유효시간 연장
   네 가지 기능이 필요하다. 이 기능을 위한 LockManager interface는 다음과 같다.
```java
public interface LockManger {
	LockId tryLock(String type, String id) throw LockException;

	void checkLock(LockId lockId) throw LockException;

	void releaseLock(LockId lockId) throw LockException;

	void extendLockExpiration(LockId lockId, long inc) throw LockException;
}
```

tryLock() 메서드는 type과 id를 파라미터로 갖는다. 이 두 파라미터에는 각각 잠글 대상 type과 식별자를 값으로 전달하면 된다.

예를들어 식별자가 10인  Article에 대한 잠금을 구하고 싶다면 tryLock()을 실행할 때 'domain.Article'을 type 값으로 주고 '10'을 id 값으로 주면 된다.

일단 잠금을 구하면 잠금을 해제하거나 잠금이 유효한지 검사하거나 잠금 유효 시간을 늘릴 때 LockId를 사용한다.
```java
public class LockId {
	private String value;

	public LockId(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
```

