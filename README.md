# Project_Fundegi
<img src="./img/img.gif">

<br/>

## 펀데기 앱 서비스
저축에 대한 고민을 일상 속에서 쉽고 재밌게 해결하고 목표달성이라는 일석이조의 성취감을 줄 수 있는 테마저축 앱
| 저축 테마 |설명 |  
|----------------|-------------------------------| 
|건강 - 다이어트 저축|하루동안 외식·배달 내역이 없을 때, 설정한 금액 저축  |
|바른생활 - 얼리버드 저축 |하루에 한번 얼리버드 아이콘을 누르면 설정한 금액 저축 |
|자기개발 - 공부 저축 |스톱워치로 측정된 누적시간이 1시간 이상이면 설정한 금액 저축|
|365 - 데일리 저축 |오늘 날짜의 금액을 매일매일 저축|

<br/>

## 개발목표
1. **MVVM** 디자인패턴을 이용한 UI와 데이터의 분리
2. 바인딩어댑터와 옵저버패턴(라이브데이터)을 이용한 **데이터바인딩**
3. 파이어베이스와 연동하여 ML kit, Cloud messaging 이용
4. **서버를 사용할 수 없는 환경**을 대체하기 위한 Shared Preferences, Room Database를 이용한 로컬 데이터 처리<br/>
→ 관계형데이터베이스(Relational DataBase)를 이용해야 했기 때문
<img src="./img/develop.png">
<br/>

## 주요기능
>  **로그인**
 - 자동 로그인
 - PIN 번호 설정 및 PIN 번호 로그인 → PinNumberViewModel
>  **저축 시작** → SavingViewModel
- Firebase ML kit - OCR을 이용한 카드번호 스캔(건강-다이어트 저축)
- Firebase Cloud messaging : 바른생활-얼리버드 저축을 시작했을 시에만 푸시알림 등록
 - 저축의 목표금액과 1회 저축시 저축될 금액 설정(365-데일리 저축 제외)
>  **저축 진행** → SavingViewModel
- 저축 시작시 설정한 금액으로 목표금액 달성 전까지 자동 저축
- 현재 진행중인 저축들과 저축금액 알림. 없을 경우 진행중인 저축이 없다고 알림
- 저축규칙 도움말 말풍선 제공
- 총 저축금액, 테마별 총 저축금액, 목표금액 알림
  - **건강-다이어트 저축** 
    - 하루동안 야식 및 배달업체 소비 내역이 없을 경우 23시 59분에 저축
  - **바른생활-얼리버드 저축**
    - Firebase Cloud messaging 매일 아침 9시 푸시알림
    - 매일 하루에 한번씩만 적립할 수 있는 얼리버드 스탬프
    - 얼리버드 스탬프를 적립하였을 경우 자동 저축
  - **자기개발-공부 저축**
    - 공부시간 측정을 위한 스톱워치와 공부시간 저장
    - 23시 59분까지 하루 공부 시간이 1시간 이상일 경우 23시 59분에 저축
  - **365-데일리 저축**
    - 오늘 날짜의 금액을 계산하여, 자동 저축 ex) 07월 28일인 경우 728원 적립
    - 저축 경과일 알림
>  **저축 완료** → SavingViewModel
- 저축된 누적 금액이 내가 설정한 목표금액 이상일 때 저축이 완료
- 완료된 저축
   - 중단 : 저축을 완료함
   - 이어서 계속하기 : 목표금액을 상향하고 계속 누적해서 저축
>  **마이페이지**
 - 저축 관리 : 현재 진행중인 저축 및 완료 저축 관리. 저축 삭제 및 수정 → SavingViewModel
-  PIN번호 재설정
- 로그아웃

<br/>

## 추후계획
- [ ] 서버연동 및 오픈뱅킹 API 연동으로 완전한 저축 기능 구현
- [ ] 더 많은 수의 저축 규칙 추가
- [ ] OCR 인식 정확도 보완
- [ ] 개인 보안을 강화하기 위한 지문인증과 같은 인증수단 추가
<br/>

## 활용 라이브러리
androidx.core:core-ktx:1.5.0 | androidx.appcompat:appcompat:1.3.0 | com.google.android.material:material:1.3.0 | androidx.constraintlayout:constraintlayout:2.0.4 |  com.github.bumptech.glide:glide:4.11.0 | androidx.activity:activity-ktx:1.1.0 | androidx.fragment:fragment-ktx:1.2.5 | androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1 | androidx.lifecycle:lifecycle-livedata-ktx:2.3.1 | androidx.viewpager2:viewpager2:1.0.0-alpha01 | me.relex:circleindicator:2.1.6 | androidx.camera:camera-camera2:1.0.0 | androidx.camera:camera-lifecycle:1.0.0 | androidx.camera:camera-view:1.0.0-alpha24 | androidx.room:room-runtime:2.3.0 | androidx.room:room-compiler:2.3.0 | androidx.room:room-ktx:2.3.0 | com.google.firebase:firebase-bom:28.2.1 | com.google.firebase:firebase-auth-ktx | com.google.firebase:firebase-analytics-ktx | com.google.firebase:firebase-messaging-ktx | com.google.android.gms:play-services-mlkit-text-recognition:16.3.0 | androidx.recyclerview:recyclerview:1.2.1 | androidx.recyclerview:recyclerview-selection:1.1.0 | com.github.skydoves:balloon:1.3.5

<br/>

## Download APK
[<img src="./img/icon.png">](https://drive.google.com/file/d/1gDKWbb1hDOcY-GL3tghQogqeRJYZdMCM/view?usp=sharing) [click here!](https://drive.google.com/file/d/1gDKWbb1hDOcY-GL3tghQogqeRJYZdMCM/view?usp=sharing)
