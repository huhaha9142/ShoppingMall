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
>json 데이터의 image(url)를 이미지의 src에 넣으면 됩니다.

### Product.POST
*db에 product를 추가하거나 갱신합니다*
>정해진 형식의 formData를 받아야 작동합니다.
>>형식(항목)은 다음과 같습니다(**각 항목의 데이터는 반드시 존재해야함**)
>>>size(int)<br>color(String)<br>kind(String)<br>quantity(int)<br>content(Strinh)<br>imageSmall(file)<br>imageLazy(file)<br>productImage(file)<br>product(String)<br>price(int)<br>위의 항목중  size,color,kind,product가 기존의 등록된 데이터와 같다면 **UPDATE** 됩니다.(기본 **INSERT**)

>>imageSmall은 모델사진,제품사진,제품사진,...,순으로 0번 인덱스는 제품 페이지 메인 이미지입니다.<br>imageLazy는 imageSmall의 고해상도 버전입니다.(null이여도됨)<br>productimage는 제품의 전면 후면 사진입니다.(전,후 순서로 2장만)

>이미지의 모든 포멧은 .png 로 저장됩니다.

``````
http://pvpvpvpvp.gonetis.com:8080/sample/products
``````

products/{product} .GET   
---
___ 

### products/{product}.GET
*DB에 저장된 제품{product}의 content들을 받아옵니다.*
>[JSON 데이터 보러가기{니트 가디건}<<](http://pvpvpvpvp.gonetis.com:8080/sample/products/니트%20가디건)
``````
http://pvpvpvpvp.gonetis.com:8080/sample/products/{product}
``````
