import {useEffect, useState} from "react";
import axios from 'axios';

function SearchBarAuto(props) {
    
    console.log("auto",props.data);
    return(
    <>
        <p>{props.value}</p>
    </>
        );
}

function SearchBarUncontroller(props) {
    console.log("2",props.data);
    const [keyword, setKeyword] = useState(null);
    const changeKeyword= (event) => {
        setKeyword(event.target.value);
      }
    
    return (
        <>
            <div>
               
                <input type="text" placeholder="검색어를 입력하세요" onChange={changeKeyword}></input>
                <SearchBarAuto value={keyword}  data={props.data}/>
                <button>검색</button>
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