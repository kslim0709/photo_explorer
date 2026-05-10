# Photo Explorer 📸

Unsplash API를 활용한 Android Photo Explorer 애플리케이션입니다.  
Jetpack Compose 기반으로 구현하였으며, 사진 탐색 / 상세 조회 / 좋아요(로컬 저장) 기능을 제공합니다.

---

#  주요 기능

## 1. 사진 목록 화면 (Photo List)

### 구현 기능
- 최신 사진 리스트 조회
- LazyVerticalGrid 기반 사진 목록 구성
- 무한 스크롤 페이징 처리
- 좋아요(관심 사진) 토글 기능(이미지 다운로드 포함)
- 사진 클릭 시 상세 화면 이동
  
---

## 2. 사진 상세 화면 (Photo Detail)

### 구현 기능
- 고해상도 이미지 표시
- 작가 정보 표시
- 좋아요 수 / 다운로드 수 표시
- 해시태그(Tag) 표시
- 좋아요 토글 기능(이미지 다운로드 포함)

---

## 3. 좋아요 목록 화면 (Favorite Photos)

### 구현 기능
- 로컬 DB(Room)에 저장된 사진만 표시
- 오프라인 상태에서도 확인 가능

---

### 추가 구현
- 네트워크 상태 감지
- 오프라인 상태 배너 표시
- Compose Recomposition 최소화
- 실제 로컬 이미지 존재 여부 검증
- 삭제된 로컬 이미지 자동 필터링
- 잘못된 로컬 경로 예외 처리

---

# Architecture

본 프로젝트는 Clean Architecture + MVI 패턴 기반으로 구성하였습니다.

```text
app
 ├─ MainActivity
 └─ PhotoExplorerApplication

data
 ├─ database
 ├─ di
 ├─ local
 │   ├─ dao
 │   ├─ datasource
 │   ├─ entity
 │   └─ mapper
 ├─ network
 │   ├─ interceptor
 │   ├─ result
 │   └─ NetworkManager
 ├─ remote
 │   ├─ api
 │   ├─ datasource
 │   ├─ mapper
 │   └─ response
 └─ repository

domain
 ├─ model
 ├─ repository
 ├─ result
 └─ usecase

presentation
 ├─ common
 ├─ navigation
 ├─ theme
 ├─ ui
 │   ├─ component
 │   ├─ model
 │   ├─ photolist
 │   ├─ photodetail
 │   └─ photofavorite
 └─ PhotoExplorerApp
```
---

## 기술 스택 (Tech Stack)

| Category | Tech |
| :--- | :--- |
| **Language** | Kotlin |
| **UI** | Jetpack Compose |
| **Architecture** | Clean Architecture + MVI |
| **Async** | Kotlin Coroutines + Flow |
| **DI** | Hilt |
| **Network** | Retrofit2 + OkHttp |
| **Serialization** | Kotlin Serialization |
| **Image Loader** | Coil |
| **Local DB** | Room |
| **Navigation** | Navigation Compose |
