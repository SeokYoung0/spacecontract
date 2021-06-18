# 전자 계약서 앱 서비스

## 1. 안드로이드 및 eform-api를 이용하여 제작

## 2. 진행도

### 로그인 요청 화면
   - ![image](image/캡처1.PNG)
   
### 메인 화면
   - ![image](image/캡처2.PNG)
   
### 메뉴 화면
   - ![image](image/캡처3.PNG)
   
### 계정 정보 화면
   - ![image](image/캡처4.PNG)
   
## 2. 구조

- 1. Activity
    - Main
    - Login
    - Intro

- 2. Fragment
    - Home
        - fragment_how_to_use(가)
        - fragment_write_contract(가제)
        
    - Menu
        - fragment_account_setting
        
    - Consulting
    
    - Notice
   
## 3. 기능
    
- 1. MainActivity
    - BottumNav 기능 구현
    - Home, Consulting, Notice, Menu 순으로 제공
    - MenuFragment에서 여러 항목 중 계정 정보(AccountSetting)이동 가능제
    - 화면 구성도 변
    
- 2. LoginActivity
   - Google, Naver, Kakao Login 기능 구축 예정
   - Naver/Google 로그 기능 구현 완료, Logcat을 통한 response값 확인 
  
- 3. IntroActivity
    - 앱 실행 시 intro화면이 나오는 기능 구현
    - 1500ms 동안 화면에 출력
    
- 4. MenuFragment
    - MenuFragment에서 fragment_account_setting으로 이동 가능
    - 지난 번(다시 MenuFragment)로 돌아오지 못하는 버그 해결
    
- 5. HomeFragment
    - 계약서 작성법 fragment & 계약서 작성 fragment로 변경될 수 있도록 설계 중...
    - 현재 각각 카드 뷰 클릭시 해당 fragment위치를 설정하기 위해 임시 이벤트리스너를 등록
   
## 4. 추가

1. 목표는 계약서 및 시공업체 연결 앱 서비스
2. 현재 계약 앱 서비스 설계 중
    - 1. 계약 성립을 위한 상호간 정보 입력 기능 필요
    - 2. 계약서 작성 전 업체 선정 여부, 하자보증권 등의 자료 첨부 기능 필요
    - 3. 계약서 작성 후 검토는 유료화(결제 시스템 필요)
   
## 5. 버그

- 2021.06.18 해결 :D
    1. AccountSettingFragment에서 MenuFragment로 돌아올시 버그가 있음 
   

    

