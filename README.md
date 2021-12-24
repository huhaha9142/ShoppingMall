# ShoppingMall

## REST API 가이드
API 리스트
1. [제품(product)](#product)
2. [리뷰(review)](#review)
3. [커스텀(custom)](#custom)
4. [유저(user)](#user)
5. [주문(order)](#order)
6. [장바구니(cart)](#cart)
7. [QNA(qna)]?(#



## product
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
>[JSON 데이터 보러가기{1}<<](http://pvpvpvpvp.gonetis.com:8080/sample/products/1)
``````
http://pvpvpvpvp.gonetis.com:8080/sample/products/{product}
``````

## review
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
>>형식(항목)은 다음과 같습니다(**각 항목의 데이터는 반드시 존재해야함(image만 null허용)**)
>>>content(String)<br>title(String)<br>image(file)<br>productNumber(int)<br/>
>>image는 여러장 넣을 수 있습니다.(제한 없음)<br>image 값이 null 일때 임시데이터가 들어갑니다.

>이미지의 모든 포멧은 .png 로 저장됩니다.

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","insert"}
* 실패시 : {"result","fail"}

reviews{reviewNumber} .GET .POST .DELETE   
---
___    
### Reviews/{reviewNumber}.GET
*DB의 리뷰하나를 받아옵니다.*
>[JSON 데이터 보러가기{1}<<](http://pvpvpvpvp.gonetis.com:8080/sample/reviews/1)
``````
http://pvpvpvpvp.gonetis.com:8080/sample/reviews/{reviewsNumber}
``````
>업데이트 하실때 쓰시면 됩니다.!

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

### ProductReviews/{productNumber}.GET
*DB의 제품기준 리뷰를 불러옵니다.*
>[JSON 데이터 보러가기[100번 상품]<<](http://pvpvpvpvp.gonetis.com:8080/sample/productReviews/100)
``````
http://pvpvpvpvp.gonetis.com:8080/sample/productReviews/{producrReview}
``````
## custom
customs .GET .POST    
---
___    
### Customs.GET
*DB에 저장된 커스텀리스트를 불러옵니다.*
>[JSON 데이터 보러가기<<](http://pvpvpvpvp.gonetis.com:8080/sample/customs)
``````
http://pvpvpvpvp.gonetis.com:8080/sample/customs
``````
>json 데이터의 image(url)를 이미지의 src에 넣으면 됩니다.<br/>
이미지는 **배열형식**으로도 존재할 수 있습니다.!<br/>자세한 형식은 위의 데이터 api의 응답 json를 참고해 주세요 
### Customs.POST
*DB에 리뷰를 추가합니다.*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/customs
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**각 항목의 데이터는 반드시 존재해야함**)
>>>image(file)<br>size(String)<br/>color(String)<br>productNUmber(int)</br>userNumber(int)</br>
>>image는 여러장 넣을 수 있습니다.(제한 없음)<br>

>이미지의 모든 포멧은 .png 로 저장됩니다.

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","insert"}
* 실패시 : {"result","fail"}

customs{customNumber} .POST .DELETE   
---
___    
### Customs/{customNumber}.POST
*DB의 커스텀을 UPDATE 합니다.*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/customs/{customNumber}
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**각 항목의 데이터는 반드시 존재해야함**)
>>>image(file)<br>size(String)<br/>color(String)</br>
>>image는 여러장 넣을 수 있습니다.(제한 없음)<br>

>이미지의 모든 포멧은 .png 로 저장됩니다.

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","update"}
* 실패시 : {"result","fail"}

___    
### Customs/{customNumber}.DELETE
*DB의 리뷰를 삭제합니다.*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/customs/{customNumber}
``````

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","delete"}
* 실패시 : {"result","fail"}

## user

### User-login.POST
*로그인을 합니다*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/user-login
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**각 항목의 데이터는 반드시 존재해야함**)
>>>id(String)<br>password(String)
* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","Success"} + cookie에 JWT 토큰(토큰에는 id정보가 담겨있습니다.)
* 실패시 : {"result","Fail"}

### User-join.POST
*회원가입을 합니다*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/user-join
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**id,password,name은 반드시 존재해야합니다.**)
>>>id(String)<br>password(String)<br>name(String)<br>address(String)<br>phone(String)<br>

>>address,phone은 필수 입력 사항이 아니며 없다면  null값이 입력됩니다.

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","Join"}
* 실패시 : {"result","Fail"}

### User-id.POST
*유저 id 중복검사결과를 보내줍니다.*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/user-id
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**각 항목의 데이터는 반드시 존재해야함**)
>>>id(String)
* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","사용 가능한 아이디입니다."}
* 실패시 : {"result","이미 사용중인 아이디 입니다"}

### User-privacy.POST
*유저의 개인정보를 업데이트 합니다.*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/user-privacy
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**name,userNuber는 반드시 존재해야함**)
>>>id(String)<br>password(String)<br>name(String)<br>address(String)<br>phone(String)<br>nickName(String)<br>userNumber(int)
>>address,phone,nickName은 필수 입력 사항이 아니며<br>
>> address,phone는 없다면 null값이 입력됩니다.
>> nickName default 값으로 설정됩니다.
* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","Update"}
* 실패시 : {"result","Fail"}
### User-password.POST
*유저의 비밀번호를 변경(업데이트) 합니다.*
``````
http://pvpvpvpvp.gonetis.com:8080/sample/user-password
``````
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**name,userNuber는 반드시 존재해야함**)
>>>password(String)<br>userNumber(int)

* 결과는 JSON형식으로 응답됩니다.
* ex) 성공시 : {"result","Update"}
* 실패시 : {"result","Fail"}

### User.DELETE



## order
