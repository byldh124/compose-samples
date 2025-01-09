1. 프로젝트 개요
   - compose를 이용해 google, naver, kakao 맵을 불러오고 맵을 제어한다.
     
2. dependencies
   - com.google.maps.android:maps-compose (구글 맵)
   - com.google.android.gms.play-services-location (위치정보)
   - io.github.fornewid:naver-map-compose (네이버 맵 : compose용)
   - io.github.fornewid:naver-map-location (네이버 맵 위치정보 : play-services-location과 버전 일치)
   - com.kakao.maps.open:android (카카오 맵)
  
3. 사용법
   - local.properites 파일 내부에 api key 등록 </br>
     google.api.key=${googlea api key} </br>
     naver.client.id=${naver client id} </br>
     kakao.client.id=${kakao client id} </br>

   ex) 랜덤으로 쓴거라 이렇게 기입하면 동작되진 않습니다. </br>
     google.api.key=A3aisdfij2Mejidoi </br>
     naver.client.id=ckemij58jd </br>
     kakao.client.id=f1381923859fjeiaojsd2445678 </br>
