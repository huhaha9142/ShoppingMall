import axios from "axios";
import { useState ,useEffect} from "react";



function ImageLodingwithdataEx(){
    const [products, setProduct] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    useEffect(() => {;
        const fetchUsers = async () => {
          try {
            // 요청이 시작 할 때에는 error 와 users 를 초기화하고
            setError(null);
    
            // loading 상태를 true 로 바꿉니다.
            setLoading(true);
           
            // 비동기 통신 GET 응답은 response.data 에 저장됩니다.
        
            const response = await axios({
                    method:'GET',
                    url:`http://pvpvpvpvp.gonetis.com:8080/sample/products`,
                   
            });
            setProduct(response.data); 
            console.log(response.data);
            // 데이터는 response.data 안에 들어있습니다.
          } catch (e) {
            setError(e);
          }
          setLoading(false);
        };
      fetchUsers();
      }, []);
    if (!products) return null;
    return( <>
     <ul>
      {products.products.map(product => (
        <li key={[product.index]}>{product.product}  {product.image}
          <a href={product.image}>
            <img src={product.image}/>
          </a>
        </li>
        
      ))}

     </ul>
     
    </>)
}

export default ImageLodingwithdataEx;
