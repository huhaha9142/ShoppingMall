import {useEffect, useState} from "react";
import axios from 'axios';
let a=0;
function SearchBarAuto(props) {
  if(props===null) return null;
  
  console.log(a=a+1);
  return (<option value={props.value}>{a}</option>)    
  // return (<li>{props.value}</li>)
}

function SearchBarUncontroller(props) {
    console.log("2",props.data);
    const [keyword, setKeyword] = useState(null);
    const changeKeyword= (event) => { 
        setKeyword(event.target.value);
      }
    let data=[];
    if (keyword!="") {// 입력값이 있을때만.!
      
    
    props.data.citys.forEach(element => {
      let string = element.city.toLowerCase(); // 대소문자 상관없게 처리
      let string2 = element.city.toUpperCase();
      let string3 = element.city; //원본
      if (string.indexOf(keyword)!=-1 || string2.indexOf(keyword)!=-1 || string3.indexOf(keyword)!=-1) {
        data.push(element.city);       
      }       
    });
  }
    return (
        <>
            <div>
                <input type="text" placeholder="검색어를 입력하세요" onChange={changeKeyword} list="data"></input> 
                <button>검색</button>
                <datalist id="data">
                {data.map(city => (
                  <SearchBarAuto value={city}/>
                ))}
                </datalist> 
                
                {/* {data.map(city => (
                  <SearchBarAuto value={city}/>
                ))}       */}
            </div>
        </>
    )
}


function SearchBarController() {
    const [citys, setUsers] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    useEffect(() => {
        setUsers(null);
        const fetchUsers = async () => {
          try {
            // 요청이 시작 할 때에는 error 와 users 를 초기화하고
            setError(null);
            setUsers(null);
            // loading 상태를 true 로 바꿉니다.
            setLoading(true);
            const response = await axios.get(
                'http://localhost:8080/myportal/mysql'
            );
            setUsers(response.data); // 데이터는 response.data 안에 들어있습니다.
            console.log("axios",response.data);
          } catch (e) {
            setError(e);
          }
          setLoading(false);
        };
    
        fetchUsers();
      }, []);
    if (!citys) return null;
    return(
    <SearchBarUncontroller data={citys}/>
    )
}

export default SearchBarController;