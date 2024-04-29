import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const handleregister = () => {
    navigate("/register");
  };
  async function login(event) {
    event.preventDefault();

    if (!email || !password) {
      alert("Email and password are required");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8085/api/v1/user/login",
        {
          email: email,
          password: password,
        }
      );

      if (response.data.message === "Email not exists") {
        alert("User does not exist. Please register.");
      } else if (response.data.message === "Login Success") {
        navigate("/home");
      } else {
        alert("Incorrect email or password");
      }
    } catch (err) {
      alert("Error: " + err.message);
    }
  }

  return (
    <>
      <div className="container">
        
          <h2>Login</h2>
        

        <form>
          <input
            type="email"
            // className="form-control"
            id="email"
            placeholder="Enter your email"
            required
            value={email}
            onChange={(event) => {
              setEmail(event.target.value);
            }}
          />

          <input
            type="password"
            // className=""
            id="password"
            placeholder="Enter password"
            required
            value={password}
            onChange={(event) => {
              setPassword(event.target.value);
            }}
          />

          <button type="submit" onClick={login}>
            Login
          </button>
          <button onClick={handleregister}>Register</button>
        </form>
      </div>
    </>
  );
}

export default Login;