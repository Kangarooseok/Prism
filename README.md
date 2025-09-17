# 📡 PRISM — CCTV 상태 자동화 관제 플랫폼 README

![readme_mockup2](https://user-images.githubusercontent.com/112460466/210706312-6a44b60d-a42e-4210-b334-9e5983f70fb3.png)

- 배포 URL : (사내/온프레미스 운영 — 공개 URL 비공개)
- Demo 계정 : (필요 시 제공)  
- 문의 : team-prism@example.com

<br>

## 프로젝트 소개

- **PRISM**는 CCTV의 **상태 감지 → 장애 판별 → 담당자 알림 → 이력 기록**을 **이벤트 드리븐(EDA)** 방식으로 자동화하는 관제 플랫폼입니다.  
- Ping/RTSP 기반 지표를 10초 주기로 수집하고, 룰 엔진으로 **네트워크/장비 도메인**을 분리해 심각도를 판정한 뒤 **Slack/Jira/Gmail**로 알림을 전파합니다.  
- **Kafka·CQRS·CDC·Kubernetes**를 결합해 대규모 환경에서도 **확장성·신뢰성**을 확보했습니다.

> 배경: 기존 관제는 사람이 화면을 보며 판단해 **인지 지연/누락**이 잦았습니다. PRISM은 이 흐름을 데이터·이벤트 중심 체계로 전환했습니다.


<img width="943" height="1016" alt="image (1)" src="https://github.com/user-attachments/assets/bebe11d2-b96b-4dcf-81fc-2e6645f05a91" />


<br>

## 개발 인원

<div align="center">

| **강민석** | **김시현** | **최현제** | **한선영** |
| :------: |  :------: | :------: | :------: |
| [<img src="https://github.com/user-attachments/assets/6e7bdf44-e4eb-4ca1-bf29-7fc73e955458" height=150 width=150> <br/> @kangroosek](https://github.com/Kangarooseok) | [<img src="https://github.com/user-attachments/assets/bb1c3856-ebad-4896-9065-5938d628d568" height=150 width=150> <br/> @kimsihyon](https://github.com/kimsihyon) | [<img src="https://github.com/user-attachments/assets/bebe11d2-b96b-4dcf-81fc-2e6645f05a91" height=150 width=150> <br/> @choihyunjae](https://github.com/) | [<img src="https://github.com/user-attachments/assets/778c9b04-d272-4f98-b459-6c2177521888" height=150 width=150> <br/> @HanSeonyoung](https://github.com/HanSeonyoung) |

</div>


