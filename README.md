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
