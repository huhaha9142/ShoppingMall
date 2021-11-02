import axios from "axios";
import React, { useState } from "react"; 

function ImageUploadingEx() 
{ //파일 미리볼 url을 저장해줄 state 
    const [image, setImage] = useState("");
    const [viewImage, setViewImage] = useState("");
    const saveImage = (event) =>{
        setImage(event.target.files[0]);
        setViewImage(URL.createObjectURL(event.target.files[0]));
    }
    const sendImage = async () =>{ //데이터를 비동기로 보내는 함수
        const formdata = new FormData();
        formdata.append('files',image);
        formdata.append('files',image); // 파일(img)는 FormData() 사용해 선언 후 데이터를 넣어야 전송가능.!
        try{
            //비동기 통신 POST
            const send = await axios({
                method:'POST',
                url:`http://pvpvpvpvp.gonetis.com:8080/sample/send`,
                data:formdata,
            });
        }
        catch(e){
        }

    }

    return (<><input type="file" onChange={saveImage}/>
            {viewImage && (<img src={viewImage}/>)}
            <button onClick={sendImage}>보내기</button>
           </>)
} 
export default ImageUploadingEx;
