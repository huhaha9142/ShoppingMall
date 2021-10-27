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
    const sendImage = async () =>{
        const formdata = new FormData();
        formdata.append('files',image);
        try{
            const send = await axios({
                method:'POST',
                url:`http://192.168.0.45:8080/sample/send`,
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
