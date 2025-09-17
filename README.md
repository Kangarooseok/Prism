# 📡 PRISM — CCTV 상태 자동화 관제 플랫폼 README

<img width="1920" height="1080" alt="Prism" src="https://github.com/user-attachments/assets/62876cda-6637-4e1d-a945-e4d0ea3a18ea" />


- 배포 URL : (사내/온프레미스 운영 — 공개 URL 비공개)
- Demo 계정 : (필요 시 제공)  
- 문의 : test@prism.com

<br>

## 프로젝트 소개

- **PRISM**는 CCTV의 **상태 감지 → 장애 판별 → 담당자 알림 → 이력 기록**을 **이벤트 드리븐(EDA)** 방식으로 자동화하는 관제 플랫폼입니다.  
- Ping/RTSP 기반 지표를 10초 주기로 수집하고, 룰 엔진으로 **네트워크/장비 도메인**을 분리해 심각도를 판정한 뒤 **Slack/Jira/Gmail**로 알림을 전파합니다.  
- **Kafka·CQRS·CDC·Kubernetes**를 결합해 대규모 환경에서도 **확장성·신뢰성**을 확보했습니다.

> 배경: 기존 관제는 사람이 화면을 보며 판단해 **인지 지연/누락**이 잦았습니다. PRISM은 이 흐름을 데이터·이벤트 중심 체계로 전환했습니다.


<br>

## 개발 인원

<div align="center">

| **강민석** | **김시현** | **최현제** | **한선영** |
| :------: |  :------: | :------: | :------: |
| [<img src="https://github.com/user-attachments/assets/6e7bdf44-e4eb-4ca1-bf29-7fc73e955458" height=150 width=150> <br/> @kangroosek](https://github.com/Kangarooseok) | [<img src="https://github.com/user-attachments/assets/bb1c3856-ebad-4896-9065-5938d628d568" height=150 width=150> <br/> @kimsihyon](https://github.com/kimsihyon) | [<img src="https://github.com/user-attachments/assets/bebe11d2-b96b-4dcf-81fc-2e6645f05a91" height=150 width=150> <br/> @choihyunjae](https://github.com/) | [<img src="https://github.com/user-attachments/assets/778c9b04-d272-4f98-b459-6c2177521888" height=150 width=150> <br/> @HanSeonyoung](https://github.com/HanSeonyoung) |

</div>

<br>

## 1. 개발 환경

- **Front** : React, Vite, Recharts, HLS.js, Tailwind/Lucide
- **Back-end** : Spring Boot (Web, Validation, Scheduler), Spring Data JPA, Drools
- **Stream/DB** : Kafka, Kafka Connect, Debezium CDC, MariaDB (쓰기/읽기 분리)
- **Infra** : Kubernetes(EKS/온프레), Proxmox, RAID, Galera Cluster, MaxScale, Vault
- **보안/네트워크** : Fortigate IPSec Site‑to‑Site VPN, VLAN, VPC
- **형상/협업** : GitHub (Projects/Issues), Jira, Notion, Slack, GoogleDrive
  
<br>

## 2. 채택한 개발 기술과 브랜치 전략

### EDA + Kafka, CQRS + CDC
- 서비스 간 **느슨한 결합**과 **무중단 확장**을 위해 Kafka를 이벤트 허브로 사용.  
- **CQRS**로 쓰기/읽기 DB 분리, **Debezium CDC**로 변경 이벤트를 스트림화해 조회 성능과 일관성 확보.

### Rule Engine & 상태 감지
- ICMP/RTSP 지표(응답/RTT/손실/세션)를 **10초 주기**로 수집하고 **Drools**로 진단 코드·심각도 부여.

### 브랜치 전략
- **main / develop / feature** 기반 Git‑flow 변형. 기능 단위 PR → CI 빌드 → 통합 테스트 후 병합.

<br>

## 3. 프로젝트 구조

```
prism/
├── backend/
│   ├── controller/
│   │   ├── .gradle/
│   │   ├── .idea/
│   │   ├── .vscode/
│   │   ├── aws/
│   │   ├── build/
│   │   ├── gradle/
│   │   ├── kubernetes/
│   │   │   ├── db-config.yaml
│   │   │   ├── db-secret.yaml
│   │   │   ├── deployment.yaml
│   │   │   └── service.yaml
│   │   └── src/
│   │       └── main/
│   │           └── java/
│   │               └── prism/
│   │                   ├── config/
│   │                   │   ├── ErrorDbConfig.java
│   │                   │   ├── JpaTxConfig.java
│   │                   │   ├── MainDbConfig.java
│   │                   │   ├── SubscriptionDbConfig.java
│   │                   │   ├── UserDbConfig.java
│   │                   │   └── WebConfig.java
│   │                   ├── domain/
│   │                   │   ├── cctv/
│   │                   │   │   ├── command/
│   │                   │   │   │   ├── DeleteCctvCommand.java
│   │                   │   │   │   ├── ModifyCctvCommand.java
│   │                   │   │   │   └── RegisterCctvCommand.java
│   │                   │   │   ├── model/
│   │                   │   │   │   └── Cctv.java
│   │                   │   │   └── repository/
│   │                   │   │       ├── CctvErrorStatusRepository.java
│   │                   │   │       └── CctvRepository.java
│   │                   │   ├── group/
│   │                   │   │   ├── command/
│   │                   │   │   │   ├── DeleteCctvGroupCommand.java
│   │                   │   │   │   ├── ModifyCctvGroupCommand.java
│   │                   │   │   │   └── RegisterCctvGroupCommand.java
│   │                   │   │   ├── model/
│   │                   │   │   │   └── CctvGroup.java
│   │                   │   │   └── repository/
│   │                   │   │       └── CctvGroupRepository.java
│   │                   │   ├── subscription/
│   │                   │   │   └── repository/
│   │                   │   │       └── SubscriptionDao.java
│   │                   │   └── user/
│   │                   │       ├── dto/
│   │                   │       │   └── UserDto.java
│   │                   │       └── repository/
│   │                   │           └── UserReadDao.java
│   │                   └── infra/
│   │                       ├── bootstrap/
│   │                       │   └── UnassignedBackfillRunner.java
│   │                       └── controller/
│   │                           ├── CctvController.java
│   │                           ├── CctvDiagnosisController.java
│   │                           ├── CctvGroupController.java
│   │                           ├── CctvStatsController.java
│   │                           ├── HealthCheckController.java
│   │                           ├── SubscriptionsController.java
│   │                           └── UsersController.java
│   ├── status_check/           # 파일 아직 없음
│   ├── error_detection/        # 파일 아직 없음
│   └── LoginBackend/           # 파일 아직 없음
└── frontend/
    ├── dist/
    ├── frontend-kubenetes/
    │   ├── frontend-deployment.yaml
    │   ├── frontend-ingress.yaml
    │   └── frontend-service.yaml
    ├── node_modules/
    ├── public/
    │   ├── assets/
    │   │   └── images/
    │   │       ├── Prism_dark.svg
    │   │       └── Prism_light.svg
    │   └── index.html
    ├── src/
    │   ├── assets/
    │   │   ├── PrismDarkLogo.jsx
    │   │   └── PrismLightLogo.jsx
    │   ├── components/
    │   │   ├── CCTVGroupManagement.jsx
    │   │   ├── CCTVManagement.jsx
    │   │   ├── CctvPlayer.jsx
    │   │   ├── Dashboard.jsx
    │   │   ├── LoginPage.jsx
    │   │   └── UserManagement.jsx
    │   ├── app.js
    │   ├── index.css
    │   └── index.js
    ├── .babelrc
    ├── .env.development
    ├── .env.production
    ├── .gitignore
    ├── Dockerfile
    ├── package-lock.json
    ├── package.json
    ├── postcss.config.js
    ├── README
    ├── tailwind.config.js
    └── webpack.config.js

```

<br>

## 4. 역할 분담

### 🍊강민석

- 프런트엔드 총괄: UI 아키텍처·라우팅 설계, 대시보드/관리 화면, 상태·이력 시각화, 빌드(Webpack/Vite) E2E 구현
- 기획/PRD 주도: 감지→판별→알림→복구→기록 엔드투엔드 흐름 설계, 무중단 관제·책임추적 목표에 맞춘 범위/우선순위 확정
- 대시보드·지표: 상태 집계 API 연동, 전일 대비 증감률/비율 계산, 분모 0 예외 처리 및 UI 반영
- CCTV 관리(CRUD): 등록·수정·삭제·조회, DTO/Validation, 그룹 연동, 소프트 삭제(@SQLDelete) 적용
- 그룹 관리: 생성·수정·삭제, ‘미정’ 그룹 보호, 배정/해제 diff 로직 및 일괄 이동 처리
- 사용자/권한: 사용자 CRUD, 역할 기반(관리자/네트워크/운영) 화면·액션 제어
- 구독(알림 대상): CCTV·그룹 ↔ 담당자 구독 생성/해제, 중복·충돌 방지 검증
- 스트리밍: Hls.js 플레이어 래퍼(치명/비치명 오류 분류, 자동 복구·수동 재시도, 상태 오버레이)
- 배포(앱 레벨): Docker 빌드 → K8s 매니페스트(Deployment/Service/ConfigMap/Secret) 작성/적용, readiness/liveness 프로브, DB 연결 설정 분리
- 라우팅 구성(앱 레벨): Ingress + ExternalName Service로 FE–BE 분리, /api 프록시·리라이트 규칙 표준화 → 운영 동선 단순화

<br>
    
### 👻김시현 (차후에 작성예정)

- 상태 감지 백엔드 개발
- 데이터모델링
- CQRS  설계

<br>

### 😎최현제 (차후에 작성예정)

- Kubernetes 구축:
- Kafka-CQRS 구축:
- 네트워크/보안:
- 성능/비용 최적화:
- DR 디자인:

<br>

### 🐬한선영 (차후에 작성예정)

- 상태 감지 백엔드 개발
- 알림 백엔드 개발
- 시스템 설계
- 데이터모델링
- 일정 관리
    
<br>

## 5. 개발 기간 및 작업 관리

### 전체 개발 기간 : 2025.07.21(월) ~ 2025.09.12(금) · 총 8주
<br>

### 작업 관리 : GitHub Projects & Issues(칸반) · 데일리 스탠드업(15분) · 주간 리캡(리스크/일정 조정)
<br>

### 주차 구간(참고)
- W1: 07/21–07/27 · W2: 07/28–08/03 · W3: 08/04–08/10 · W4: 08/11–08/17
- W5: 08/18–08/24 · W6: 08/25–08/31 · W7: 09/01–09/07 · W8: 09/08–09/12
<br>

### 일정(간트 요약)

> 블록(█)이 해당 주에 수행된 작업을 의미합니다.

| 프로젝트 항목         |  W1 |  W2 |  W3 |  W4 |  W5 |  W6 |  W7 |  W8 | 기간(날짜)      |
| --------------- | :-: | :-: | :-: | :-: | :-: | :-: | :-: | :-: | ----------- |
| 프로젝트 계획/분석      |  █  |     |     |     |     |     |     |     | 07/21–07/25 |
| 프로젝트 설계         |  █  |  █  |     |     |     |     |     |     | 07/23–07/31 |
| 프로젝트 개발(S/W)    |     |  █  |  █  |  █  |     |     |     |     | 07/28–08/17 |
| 인프라 구축          |     |     |  █  |  █  |  █  |  █  |     |     | 08/04–08/31 |
| 각 기능 테스트        |     |     |     |  █  |  █  |  █  |  █  |     | 08/11–09/07 |
| 최종 산출물 정리       |     |     |     |  █  |  █  |  █  |  █  |  █  | 08/11–09/12 |
| 프로젝트 발표 및 시연 준비 |     |     |     |     |  █  |  █  |  █  |  █  | 08/18–09/12 |


<br>

## 6. 신경 쓴 부분

- **하이브리드 DR**: 온프레미스 Active ↔ AWS Passive 자동 전환 시나리오
- **보안/망 구성**: IPSec **Site‑to‑Site VPN**, 내부 VLAN 분리, VPC 서브넷 구조
- **운영 표준화**: PXE Boot + Kickstart로 OS 설치 자동화, 환경·경로 규칙 일원화
- **가용성·일관성**: Galera Cluster + MaxScale, CDC 기반 읽기 모델 동기화

<br>

## 7. 페이지별 기능

### [로그인]
- 서비스 접속 초기화면입니다. 이메일/비밀번호를 입력하면 로그인 버튼이 활성화됩니다.
- 인증 실패 시 입력창 하단에 경고 문구가 노출됩니다.
- 인증 성공 시 **사용자 역할(Role)**에 따라 서로 다른 대시보드로 이동합니다.
- 하단의 비밀번호를 잊으셨나요? 링크를 통해 재설정 플로우로 진입합니다.
  <br>
- 권한(역할) 및 초기 랜딩

| 역할                            | 랜딩 페이지          | 주요 메뉴 노출                                          | 권한 범위(예시)                                          |
| ----------------------------- | --------------- | ------------------------------------------------- | -------------------------------------------------- |
| **관리자 (admin)**               | 관리자 대시보드        | 대시보드 · CCTV 그룹 관리 · CCTV 관리 · CCTV 장애 이력 · 사용자 관리 | 전 메뉴 **읽기/쓰기** · 사용자/권한 관리 · 장애이력 확인                 |
| **네트워크 담당자 (network\_admin)** | 네트워크/장애 중심 대시보드 | 대시보드                | 네트워크 상태 모니터링, 장애이력 확인           |
| **장치 담당자 (operator)**         | 장치 운영 대시보드      | 대시보드                                    | 일정관리, 장애 원인 기록 |


| 로그인 |
|----------|
|<img width="1680" height="930" alt="로그인화면" src="https://github.com/user-attachments/assets/360dd97a-df7e-4bca-98b6-e358c5fba41f" />|



<br>

### [관리자 대시보드]
- 좌측 사이드바:
  · 대시보드
  · CCTV 그룹 관리
  · CCTV 관리
  · CCTV 장애 이력
사용자 관리 메뉴가 고정 노출됩니다.
하단에는 관리자 계정 정보, 다크모드 토글, 로그아웃 버튼이 있습니다.

- 상단 요약 카드 4종:
  · 전체 CCTV(예: 4대, 전일 대비 +33.3%)
  · 온라인 상태(예: 2대, -50.0%)
  · 장애 발생(예: 1건, +100.0%)
  · 주의 필요(예: 1건, +100.0%)
각 카드 우측 상단에 증감 퍼센트가 화살표와 함께 표시됩니다.

- 실시간 CCTV 목록:
  중앙 영역에 썸네일 그리드가 표시되며, 각 카드에 상태 배지(예: ACTIVE, OFFLINE, WARNING)와 위치 라벨(예: ALPHA 강의실, 서버실1) 및 IP가 함께 표시됩니다.
  상단에는 **총 대수(예: 총 4대)**와 LIVE 녹색 표시가 노출됩니다. OFFLINE인 경우 카메라 아이콘과 함께 화면이 비활성 상태로 표시됩니다.

- 우측 장애 알림 패널:
  실시간 알림 리스트가 심각도 배지(예: 위험, 주의)와 영역 태그(예: 강의실, 서버실)로 구분되어 표시됩니다.
  각 항목은 발생 경과 시간(예: 4분 전)과 담당자(예: 강민석, 이상은) 정보를 함께 보여주며, 상단에는 **활성 알림 수(예: 3 활성)**가 표시됩니다.

상단 글로벌 알림: 우측 상단 종 아이콘에 읽지 않은 알림 수(예: 3)가 뱃지로 표시됩니다.

| 관리자 대시보드 |
|----------|
|<img width="1680" height="930" alt="관리자 대시보드" src="https://github.com/user-attachments/assets/6ca43ed4-6bc6-41a6-b0e1-2e92d5d07726" />|


<br>

### [CCTV 그룹관리]
- 그룹 패널:
  · 그룹 검색 입력창과 그룹 생성 버튼이 상단에 있고, 아래로 그룹 리스트가 표시됩니다.
  · 각 항목에는 **그룹명과 설명, CCTV 수, 담당자 수, 등록일자가 함께 보여요.

- 미정 그룹:
  · 잠금 아이콘과 함께 표시되며, 이름 변경/삭제가 비활성화됩니다.

- 그룹 수정:
  · 그룹명과 설명, 편집 · 삭제 버튼이 위치합니다.
  · 그룹 삭제 및 cctv 제외 시엔 소속 CCTV를 자동으로 ‘미정’ 그룹으로 이동합니다.
  · 이미 다른 그룹에 속한 CCTV, 담당자는 목록에서 안보이며, 오직 미정 그룹에만 있는 CCTV, 담당자만 선택 가능하도록 예외 처리됩니다.



| CCTV 그룹관리 |
|----------|
|https://github.com/user-attachments/assets/15946a34-a03a-49d9-9873-d61929de3b1a|





<br>









## 8. 트러블 슈팅

- [탭메뉴 프로필 버튼 이슈](https://github.com/likelion-project-README/README/wiki/README-8.%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85_%ED%83%AD%EB%A9%94%EB%89%B4-%ED%94%84%EB%A1%9C%ED%95%84-%EB%B2%84%ED%8A%BC-%EC%9D%B4%EC%8A%88)

- [프로필 수정 이슈](https://github.com/likelion-project-README/README/wiki/README-8.%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85_%ED%94%84%EB%A1%9C%ED%95%84-%EC%88%98%EC%A0%95-%EC%9D%B4%EC%8A%88)

<br>

## 9. 개선 목표

- API 모듈화 : API를 불러오는 코드의 반복이 많아 모듈화할 예정
- lighthouse Performance 증진
    - 모든 페이지에서 특히 Best Practices & SEO 점수는 90~100으로 우수
    - Performance 점수가 대체적으로 미흡한 문제
    
    ![KakaoTalk_Photo_2023-01-04-16-55-30](https://user-images.githubusercontent.com/112460466/210591134-09bf8efd-3c34-4b99-a3d7-895ca99e1457.png)
    
- **23-01-17 성능 개선 내용**
    
    ![성능개선 후](https://user-images.githubusercontent.com/106502312/212872369-7ceeb2cf-d551-41d2-bfb0-01e35e9903fe.png)
    
    - 이미지 최적화
        - `<img>` 요소에 `width` , `height` 속성값을 명시해 불필요한 Reflow를 방지했습니다.
        - browser-image-compression 라이브러리를 사용해 유저가 업로드하는 이미지를 압축했습니다.
        - Intersection Observer API를 사용해 Lazy Loading 기법을 적용하여 홈 피드의 게시글 이미지가 viewport 내에 들어오는 순간 로딩되도록 변경했습니다.
    - 웹폰트 최적화
        - WOFF2 포맷을 추가하고 가장 우선적으로 적용되도록 선언했습니다.
        - 서브셋 폰트로 교체해 용량을 줄였습니다.
    
<br>

## 10. 프로젝트 후기

### 🍊 고지연

깃헙을 통한 협업에 익숙해지는 것, 서로 감정 상하지 않고 무사히 마무리하는 것이 1차적인 목표였어서 항상 이 부분을 명심하면서 작업했습니다.
각자 페이지를 작업하고 합치는 과정에서 마주친 버그들이 몇 있었는데, 시간에 쫓기느라 해결하기에 급급해서 제대로 트러블슈팅 과정을 기록하지 못한 게 살짝 아쉬운 부분으로 남습니다. 그래도 2022년 한 해 동안 가장 치열하게 살았던 한 달인 것 같습니다. 조원들 모두에게 고생했다고 전하고 싶습니다🧡

<br>

### 👻 김민제

여러모로 많은 것들을 배울 수 있었던 한 달이었습니다. 혼자서는 할 수 없었던 일이라는 것을 너무 잘 알기에 팀원들에게 정말 감사하다는 말 전하고 싶습니다. 개인적으로 아쉬웠던 부분은 기한 내에 기능을 구현하는 데에만 집중하면서 트러블 슈팅이나 새로 배웠던 것들을 체계적으로 기록하지 못했다는 점입니다. 이렇게 느낀 바가 있으니 이후의 제가 잘 정리하면서 개발할 거라 믿습니다… 하하 다들 수고하셨습니다!!!!

<br>

### 😎 양희지

팀 프로젝트 시작에 앞서 초기 설정을 진행하며 체계적인 설계의 중요성을 느꼈습니다. 앞으로는 점점 더 체계적이고 효율적으로 프로젝트를 진행할 수 있도록 발전하고 싶습니다.
정규 수업 직후에 프로젝트를 진행하면서 배운 내용을 직접 구현하는 과정이 어색했지만 어떤 부분이 부족한지 알 수 있는 기회였습니다. 스스로 최대한 노력해보고 팀원들과 함께 해결해 나가면서 협업의 장점을 체감할 수 있었습니다. 하지만 빠르게 작업을 진행하면서 팀원들과 함께 해결한 이슈가 어떤 이슈이며 어떻게 해결했는지에 대해 자세히 작성하지 못한 것이 아쉽습니다.
’멋쟁이 사자처럼’이라는 같은 목표를 가진 집단에서 프로젝트에 함께할 수 있는 소중한 경험이었습니다. 함께 고생한 조원들 모두 고생하셨습니다! 앞으로도 화이팅해서 함께 목표를 이뤄가고 싶습니다.

<br>

### 🐬 지창언

컨벤션을 정하는 것부터 Readme 파일 작성까지 전 과정을 진행하려니 처음 생각보다 많은 에너지를 썼어요. 좋은 의미로 많이 썼다기보다, 제 능력을 십분 발휘하지 못해서 아쉬움이 남는 쪽입니다. 개발한다고 개발만 해서는 안 된다는 것을 몸소 느껴보는 기간이었던 것 같습니다. 이번 기회로 프로젝트를 진행하면서, 제가 잘하는 점과 부족한 점을 확실하게 알고 가는 건 정말 좋습니다. 기술적인 부분에 있어서는 리액트의 컴포넌트화가 주는 장점을 알았습니다. 조금 느린 개발이 되었을지라도 코드 가독성 부분에 있어서 좋았고, 오류가 발생해도 전체가 아닌 오류가 난 컴포넌트와 근접한 컴포넌트만 살펴보면 수정할 수 있는 부분이 너무 편했습니다. 모두 고생 참 많으셨고 리팩토링을 통해 더 나은 프로젝트 완성까지 화이팅입니다.


