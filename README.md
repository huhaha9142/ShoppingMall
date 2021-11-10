# ShoppingMall

## REST API 가이드
products .GET .POST .DELETE   
---
___    

### Products.GET
*DB에 저장된 제품의 이름과 이미지를 불러옵니다.*
>[JSON 데이터 보러가기<<](http://pvpvpvpvp.gonetis.com:8080/sample/products)
``````
http://pvpvpvpvp.gonetis.com:8080/sample/products
``````
>json 데이터의 image(url)를 이미지의 src에 넣으면 됩니다.<br/>색상 데이터는 여러색이 가능한 제품이라면 배열형태로 존재합니다.<br/> 자세한 형식은 위의 데이터 api의 응답 json를 참고해 주세요 

### Product.POST
*db에 product를 추가하거나 갱신합니다*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/products
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**각 항목의 데이터는 반드시 존재해야함**)
>>>size(String)<br>color(String)<br>kind(String)<br>quantity(int)<br>content(String)<br>imageSmall(file)<br>imageLazy(file)<br>productImage(file)<br>product(String)<br>price(int)<br>위의 항목 중 product가 기존의 등록된 데이터와 같다면 **UPDATE** 됩니다.(기본 **INSERT**)<br/> **UPDATE** 시 기존의 데이터를 잘 확인하시기 바랍니다.

>>imageSmall은 제품 페이지 메인 이미지입니다.<br>imageLazy는 제품의 소개 이미지들 입니다.<br>productimage는 제품의 전면 후면 사진입니다.(전,후 순서로 2장만)

>이미지의 모든 포멧은 .png 로 저장됩니다.

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","insert"}
* 실패시 : {"result","fail"}



products/{product} .GET   
---
___ 

### products/{product}.GET
*DB에 저장된 제품{product}의 content들을 받아옵니다.*
>[JSON 데이터 보러가기{젤란 라이트 후드(남녀공용)}<<](http://pvpvpvpvp.gonetis.com:8080/sample/products/젤란%20라이트%20후드(남녀공용))
``````
http://pvpvpvpvp.gonetis.com:8080/sample/products/{product}
``````

reviews .GET .POST    
---
___    
### Reviews.GET
*DB에 저장된 리뷰리스트를 불러옵니다.*
>[JSON 데이터 보러가기<<](http://pvpvpvpvp.gonetis.com:8080/sample/reviews)
``````
http://pvpvpvpvp.gonetis.com:8080/sample/reviews
``````
>json 데이터의 image(url)를 이미지의 src에 넣으면 됩니다.<br/>
이미지는 **배열형식**으로도 존재할 수 있습니다.!<br/>자세한 형식은 위의 데이터 api의 응답 json를 참고해 주세요 
### Reviews.POST
*DB에 리뷰를 추가합니다.*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/reviews
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**각 항목의 데이터는 반드시 존재해야함**)
>>>content(String)<br>title(String)<br>image(file)<br>productNumber(int)<br/>
>>image는 여러장 넣을 수 있습니다.(제한 없음)<br>

>이미지의 모든 포멧은 .png 로 저장됩니다.

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","insert"}
* 실패시 : {"result","fail"}

reviews{reviewNumber} .POST .DELETE   
---
___    
### Reviews/{reviewNumber}.POST
*DB의 리뷰를 UPDATE 합니다.*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/reviews/{reviewsNumber}
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**각 항목의 데이터는 반드시 존재해야함**)
>>>content(String)<br>title(String)<br>image(file)<br/>
>>image는 여러장 넣을 수 있습니다.(제한 없음)<br>

>이미지의 모든 포멧은 .png 로 저장됩니다.

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","update"}
* 실패시 : {"result","fail"}

___    
### Reviews/{reviewNumber}.DELETE
*DB의 리뷰를 삭제합니다.*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/reviews/{reviewsNumber}
``````

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","delete"}
* 실패시 : {"result","fail"}