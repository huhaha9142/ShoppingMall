import axios from "axios";
import { useState ,useEffect} from "react";



function ImageLodingwithdataEx() {
    const [image, setImage] = useState("");
    const [product, setProduct] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    useEffect(() => {
        setImage(null);
        const fetchUsers = async () => {
          try {
            // 요청이 시작 할 때에는 error 와 users 를 초기화하고
            setError(null);
            setImage(null);
            // loading 상태를 true 로 바꿉니다.
            setLoading(true);
           
            // 비동기 통신 GET 응답은 response.data 에 저장됩니다.
            // JSON 내부에 byte[] + String 형식으로 들어옵니다.
            // <img src= 태그에 사용하기 위해서
            // 참고
            const response = await axios({
                    method:'GET',
                    url:`http://pvpvpvpvp.gonetis.com:8080/sample/products`,
                    // responseType:'blob',
            });
            // 제품 이름 
            // console.log("size byte[]",response);
            // 이미지 byte[]
            // console.log(response.data.image);
            setProduct(response.data.product);
            // 이미지를 base64 형식으로 디코딩
            const url = "data:image/png;base64,"+response.data.image;
            // console.log(url);
            setImage(url);
            // 데이터는 response.data 안에 들어있습니다.
          } catch (e) {
            setError(e);
          }
          setLoading(false);
        };
    
        fetchUsers();
      }, []);
    if (!image) return null;
    return( <><a href="http://pvpvpvpvp.gonetis.com:8080/sample/get">{product} <img src={image}/></a></>
)
}

export default ImageLodingwithdataEx;
