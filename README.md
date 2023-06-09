# Not Your Fault

## 프로젝트의 계기

10대가 가출하면 막막한 상황, 청소년들이 숨쉴 곳을 찾아 새출발을 시작해 보자.

부모 없이 자라기 힘든 10대에 부모의 학대, 이혼 사고등으로 인해 당장 머물 곳도 없을 때,

누군가는 쉼터를 찾아가 도움을 받았으면 하는 마음에 api를 이용해 도움을 주고 싶어 만들게 되었다.

## 프로젝트 초안

1. MainActivity → 액티비티로 fragment를 사용하지 않고,
    
    Api를 호출해, 리사이클러뷰로 보여주기. 
    
    - 리사이클러뷰
        - 쉼터명, 입소기간, 입소대상, 정원수, 전화번호, 팩스번호, 주소를 넣어 보여주자.
        - 클릭이벤트시 DetailActivity를 보여주며 상세 페이지를 설명.
2. DetailActivity 
    - 불러온 모든 값들을 보여주고,
    - 지도를 구현해 표시해보자.
    - 전화 및 주소도 링크로 구현해보자.

## https://github.com/incava/NotYourFault.git in Java

## 초안 마무리 후 회고

### 1. API의 filter기능이 없다.

누군가에게 도움을 줄 수있는 API를 1순위로 놓음으로써 누군가에게 필요할 정보를  파싱하는 것에 대한

취지는 좋으나 알고리즘적으로 혹은 기술적으로 무언가 하기에는 페이징 구현과 + a 가 없다.

페이징 구현을 함으로써 더이상 불러올 것이 없을 때까지 불러오지 않는이상, 조회에 대한 필터를 넣을 수가 없는 점이 많이 뼈아팠다. 누군가는 서울에 있는 쉼터를 원할텐데, 혹은 데이터를 많이 소비시키지 않을 페이징구현이 필요할텐데.. 이 두개를 만족시킬 수 없이, 하나는 포기해야했다.

![스크린샷 2023-03-13 오후 3.35.40.png](Not%20Your%20Fault%20fa8ae2ebb7854c66af591e54415dd58e/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2023-03-13_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_3.35.40.png)

개선을 요청하였으나.. 답은 오지 않았다. 결국 내가 커스텀 할 수 밖에.

### 2. 액티비티에 구현하다.

액티비티에 구현을 하다보니 하나의 액티비티에 하나의 구현을 함으로써 뷰만 보여줄 것을

액티비티를 연쇄적으로 사용할 수 밖에 없어졌다.

이에 대해 기본적으로 액티비티에 뷰는 프래그먼트를 사용하는 것이 확장성에 있어 좋다고 판단하였다.

그 부분은 리펙토링을 해야할 것이라 보는 바이다.

### 3. 기능이 없다.

단순 뷰와 상세페이지를 보여주는 것이 다여서  fragment에 찜하기 및 1번에 대한 filter기능을 구현해보려 한다. 없다면 내가 만들어야 겠다.

## 리펙토링 초안

1. Api의 filter기능을 위해, 미리 Api를 받아야만 한다. 또한 객체에 편하게 넣을 수 있도록 json으로 받아,

객체에 바로 넣어줄 것이다.

1. Activity로만이 아닌 fragment를 넣어 이동전환을 편하게 할 것이다.
2. findViewById가 아닌 뷰 바인딩을 통해 넘겨줄 예정이다. 이는 속도차이가 10배 차이나는 것을 스스로 실험을 통해 알아냈기 때문이다. 레거시한 방법을 버리고 사용할 것이다.
    
    [findViewByid를 대체하는 라이브러리 viewBinding](https://incavadeveloper.tistory.com/130)
    
3. Thread를 사용해야하나, 코루틴이라는 경량 쓰레드의 매력이 있고 그로 인해 코틀린을 사용하면 널 안정성,보일러 플레이트 코드 감소, 코드가 줄어들어 편하게 찾을 수 있으므로 코틀린이라는 언어로 리펙토링할 것이다.
4. 레트로핏2를 사용하여 받을 예정이다. 페이징 사용시 지원이 잘 되는 점, 자체 쓰레드로 인해 통신 속도가 빠른점, json파싱을 지원하며 gson과도 잘 맞아 객체에 한번에 넣어주는 간편 라이브러리이므로 사용할 것이다.
5. 기존에는 일일히 눌러서 지도로 확인 했어야 했다. 이러한 불편함을 해소하고자  상세페이지를 BottomSheet로 두고 지도에서 마커를 클릭하면 볼 수있도록 탁 트이게 보여주는 것으로 리펙토링 할 예정이다.
    
    
    |  | 기존 | 리펙토링 |
    | --- | --- | --- |
    | 뷰 | 액티비티 | 1액티비티 + N개 Fragment |
    | 객체 참조 | findViewById | viewBinding  |
    | 쓰레드 | Thread객체 |  Coroutine |
    | 언어 | Java | Kotlin |
    | 파싱 | xml | Json |
    | 파싱방법 | xmlPullParser + Thread  | Retrofit2 + 자체Thread  + Gson |
    | 상세페이지 | Activity에 담아서 Show | Bottom Sheet로 보여주기. |

## 기본 구현 후, 2차 리펙토링

1. 언제올지 모르는 비동기를 처리하기 위해, livedata를 사용해 코드를 분리하고자 viewmodel을 사용하고, room또한 viewmodel에 작성하기 위해, androidViewmodel을 사용할 예정.
2. 현재 찜하기를 위한 room이 없다. 이를 위해 room을 구현하고, (sqlite의 라이브러리, 유지보수면에서 코드를 틀릴 일이 없고, 객체 매핑을 자동으로 구현해준다.) 찜기능을 만들어야 한다.
3. viewPager2로 구현 시, 지도의 제스처와 겹쳐서 불편함이 크다. 그러므로 viewPager2대신 바텀네비게이션과 JetPack의 네비게이션을 이용해 구현하려한다.

## 프로젝트하면서 힘들었던점.

1. fragment없이 activity를 사용한다면 4대 컴포넌트인 액티비티의 과도한 사용으로 무게가 무거워지는 것을 느낌. 이에 대해 변경하기위해서는 유지보수가 매우 힘들다는 것을 알게됨.
    
    → 뷰에 관해서는 fragment를 사용하는 것으로 피드백.
    
2. room의 db관련해 코루틴을 사용해야 했던점(쓰레드 사용을 하면 되지만 room은 코루틴을 권장.)
    
    → 코루틴의 lifecycleScope와 CoroutineScope의 차이점을 인지하는데 시간이 조금 걸렸다.
    
3. viewPager와 지도는 매우 상극이다. 특히 지도를 사용시, scroll이 되는 제스처에 관해서는 신경써야할 필요가 있다.
4. room의 데이터가 꽤 무겁다. 몇개 안넣은것 같은데 20mb는 거뜬히 넘어간다. 그러므로 정말 필요한 것들만 넣어주는 것이 좋겠다.(사실은 서버에서 찜기능도 제공해야하지만, 서버리스이기 때문에)
5. 비동기적인 특성을 콜해주고 그에 대해 자동으로 ui를 변경시켜주고 싶었다. 그렇게 하기 위해 LiveData를 사용하게 되었고, 덤으로 ViewModel위에서 사용해야할 필요가 있기 때문에 ViewModel을 사용했다. ViewModel은 지금 단순히 사용하는 것보다 오래 살아남는다는 특성으로만 사용하고 있으나, 앞으로는 ViewModel은 그저 요청하는 수단, 나중에 Repository를 만들어 생성자를 받는 느낌으로 사용해야할 것으로 보인다. 결국 MVVM으로 가는 진행으로 보일 것이다.
