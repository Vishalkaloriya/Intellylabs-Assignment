import { useNavigate } from "react-router-dom";

function Home() {
  const navigate=useNavigate();
    const handleaccount=()=>{

      navigate("/")
    }
    return (
      <div className="home">
       <h1>Home</h1>
       <button  className="logout" onClick={handleaccount}>Logout</button>
      </div>
    );
  }
  
  export default Home;