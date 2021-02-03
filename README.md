# Daum-Image-Search
카카오 'Daum 검색 - 이미지 검색' API를 사용한 이미지 검색 앱입니다.

## 개발 환경, 사용 라이브러리
  * 기본 환경
    * JVM target : Java-1.8
    * Kotlin 1.3.72
    * Gradle : 4.1.1
  
  * AndroidX 라이브러리
    * Core 1.3.2
    * navigation 2.3.3
    * Lifecycle 2.2.0
    * Palette 1.0.0
 

  * 기타 라이브러리
    * Dagger 2.24     // Dependency Injection
    * mockito 2.11.0  // For Unit Test - Mock Object 
    * Retrofit 2.9.0    // REST API 
    * Rxjava 2.9.0    // For Handling Async Task (REST API)
    * Glide 4.11.0      // Image Loading Tool
    * Palette 1.0.0    // For getting Image Color
  
  * MVVM 아키텍쳐 디자인 패턴 적용


## 프로젝트 구성 - Directory 

```
/com/keepseung/daumimagesearch
│
│
├── di              ---------> # dependency injection를 위한 컴포넌트, 모듈
├── model           ---------> # 데이터를 가져오고 처리하기 위한 Model, Service, Api
├── viewmodel    ---------> # 리스트 Fragment에서 사용하는 viewmodel 
├── view         ---------> # view (activity, fragment, adpater, listener)
└── util   ---------> # 이미지 로딩하기 위한 함수들
```
