import axios from "axios";
import { useState ,useEffect} from "react";



function ImageLodingEx() {
    const [image, setImage] = useState("");
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
           
            const response = await axios({
                    method:'GET',
                    url:`http://192.168.0.45:8080/sample/get`,
                    responseType:'blob',
            });
            const url = URL.createObjectURL(response.data);
            setImage(url);
            console.log(response.data);
            // 데이터는 response.data 안에 들어있습니다.
          } catch (e) {
            setError(e);
          }
          setLoading(false);
        };
    
        fetchUsers();
      }, []);
    if (!image) return null;
    return( <><a href="http://192.168.0.45:8080/sample/get"> <img src={image}/></a></>
)
}

export default ImageLodingEx;
