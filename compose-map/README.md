1. 프로젝트 개요
   - compose를 이용해 google, naver, kakao 맵을 불러오고 맵을 제어한다.
     
2. dependencies
   - com.google.maps.android:maps-compose (구글 맵)
   - com.google.android.gms.play-services-location (위치정보)
   - io.github.fornewid:naver-map-compose (네이버 맵 : compose용)
   - io.github.fornewid:naver-map-location (네이버 맵 위치정보 : play-services-location과 버전 일치)
   - com.kakao.maps.open:android (카카오 맵)
  
3. 사용법
   - local.properites
     google.api.key=${googlea api key}
     naver.client.id=${naver client id}
     kakao.client.id=${kakao client id}

   ex) 랜덤으로 쓴거라 실제로 이렇게 기입해도 동작되진 않습니다.
     google.api.key=A3aisdfij2Mejidoi
     naver.client.id=ckemij58jd
     kakao.client.id=f1381923859fjeiaojsd2445678
