import axios from "axios";
import { useState ,useEffect} from "react";



function ImageLodingwithdataEx(){
    const [products, setProduct] = useState(null);
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
                    url:`http://pvpvpvpvp.gonetis.com:8080/sample/reviews`,
            });
            console.log("============");
            console.log(response.data);
            setProduct(response.data); 
            // 데이터는 response.data 안에 들어있습니다.
          } catch (e) {
            setError(e);
            console.error(e);
          }
          setLoading(false);
        };
      fetchUsers();
      }, []);
    if(loading) return (<p>로딩중</p>)
    if(error) return (<p>error</p>)
    if (!products) return null;

    console.log("pro:",products);
    return(
     <ul>
       {products.reviews.map(re => (
         <li>{re.product}<img src={re.images.image}/></li>
       ))}
      {/* {products.products.map(product => (
        <li key={[product.index]}>{product.product}  {product.image}
          <a href={product.image}>
            <img src={product.image}/>
          </a>
          {product.colors.color.map(color => (
            <div style={{backgroundColor:`${color}`,
                  width:'100px',height:'30px'
                  }}>{color}
            </div>
          ))}
        </li>
        
      ))} */}

     </ul>
     )
}

export default ImageLodingwithdataEx;
