import axios from "axios";
import React, { useState,useEffect } from "react"; 

const LoginForm = () =>
{ //파일 미리볼 url을 저장해줄 state 
    
    const [id, setId] = useState("");
    const [authorization, setAuthorization] = useState("");
    const [code, setCode] = useState("");
    const saveId = (event) =>{
        setId(event.target.value);
    }
    const sendId = async () =>{ //데이터를 비동기로 보내는 함수
        const formdata = new FormData();
        formdata.append('id',id);
        formdata.append('password','123');
        try{
            //비동기 통신 POST
            const send = await axios({
                method:'POST',
                url:`http://pvpvpvpvp.gonetis.com:8080/sample/user-login`,
                data:formdata,
                // withCredentials: "true",
               
            });
            console.log(send);
            console.log(send.data);
            console.log(send.headers.authorization);      
            setAuthorization(send.headers.authorization);      
           
        }
        catch(e){
        }

    }
    const aaa = async () =>{ //데이터를 비동기로 보내는 함수

        try{
            //비동기 통신 POST
            const send = await axios({
                method:'GET',
                url:`http://pvpvpvpvp.gonetis.com:8080/sample/user`,
                headers:{
                    authorization:authorization
                },
            });
            console.log(send.data);
        }
        catch(e){
        }

    }
    useEffect(() => {
        console.log("code is "+code);
        setCode(new URL(window.location.href).searchParams.get('code'));
    });
    useEffect(() => {  
        const fetchUsers = async () => {
          try {
            const response = await axios({
                    method:'GET',
                    url:`http://pvpvpvpvp.gonetis.com:8080/sample/user-kakao?code=${code}`,
            });
            setAuthorization(response.headers.authorization);   
            console.log("set kakao Auth",authorization);   
            console.log(code);
          } catch (e) {  
        };
        fetchUsers();
    }
    },[code]);
    
    return (<><input type="text" onChange={saveId}/>
            <button onClick={sendId}>보내기</button>
            <button onClick={aaa}>토큰 검증 보내기</button>
            
            {<a type="button" href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=8c2835e5881d60b38a8561176852e4e2&redirect_uri=http://localhost:3000">
                카카오 로그인
            </a>}

           </>)
} 
export default LoginForm;
