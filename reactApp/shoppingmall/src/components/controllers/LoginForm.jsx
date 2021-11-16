import axios from "axios";
import React, { useState } from "react"; 

function LoginForm() 
{ //파일 미리볼 url을 저장해줄 state 
    const [image, setImage] = useState("");
    const [viewImage, setViewImage] = useState("");
    const saveImage = (event) =>{
        setImage(event.target.value);
    }
    const sendImage = async () =>{ //데이터를 비동기로 보내는 함수
        const formdata = new FormData();
        formdata.append('id',image);
        formdata.append('password','11');
        try{
            //비동기 통신 POST
            const send = await axios({
                method:'POST',
                url:`http://pvpvpvpvp.gonetis.com:8080/sample/user-login`,
                data:formdata,
            });
            console.log(send.headers);
            console.log(send.data);
        }
        catch(e){
        }

    }

    return (<><input type="text" onChange={saveImage}/>
            <button onClick={sendImage}>보내기</button>
           </>)
} 
export default LoginForm;
