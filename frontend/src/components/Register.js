import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Register() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
const navigate=useNavigate();
  async function save(event) {
    event.preventDefault();
    try {
      await axios.post("http://localhost:8085/api/v1/user/save", {
        username: username,
        email: email,
        password: password,
      });
      
      // alert("User Registration Successful");
      navigate('/')
    } catch (err) {
      if (err.response) {
        if (err.response.status === 400) {
          alert("Please fill in the required fields");
        } else {
          alert("Error: " + err.message);
        }
      } else {
        alert("Error: " + err.message);
      }
    }
  }

  return (
    <div>
      <div className="container">
        
          <h1>User Registration</h1>
          <form>
            
              <input
                type="text"
               
                id="username"
                placeholder="Enter Username"
                value={username}
                onChange={(event) => setUsername(event.target.value)}
                required
              />
            

           
              <input
                type="email"
                
                id="email"
                placeholder="Enter email"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                required
              />
           
           
              <input
                type="password"
                
                id="password"
                placeholder="Enter Password"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                required
              />
            

            <button
              type="submit"
              
              onClick={save}
            >
              Submit
            </button>
          </form>
        
      </div>
    </div>
  );
}

export default Register;