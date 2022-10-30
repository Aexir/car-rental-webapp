import React, {useState} from 'react'
import {useNavigate} from "react-router-dom"
import AuthService from "../services/auth.service";
import InfoPopup from './InfoPopup';
import './UserPage'

export default function LoginForm() {
    const [popupTrigger, setPopupTrigger] = useState(false);
    const [popupMessage, setPopupMessage] = useState("")

    let navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");

    // const form = useRef();
    // const checkBtn = useRef();

    const onChangeUsername = (e) => {
        const username = e.target.value;
        setUsername(username);
    };

    const onChangePassword = (e) => {
        const password = e.target.value;
        setPassword(password);
    };

    const submitHandler = e => {
        e.preventDefault();


        setMessage("");
        setLoading(true);


        console.log(username);
        console.log(password);


        AuthService.login(username, password).then(
            () => {
                navigate("/");
                window.location.reload();
            },
            (error) => {
                const resMessage =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                console.log(resMessage)
                // setLoading(false);
                // setMessage(resMessage);
                setPopupTrigger(true)
                setPopupMessage(resMessage)
            }
        );
    };

    return (
        <div>
            <InfoPopup trigger={popupTrigger} setTrigger={setPopupTrigger}>
                <h3>{popupMessage}</h3>
            </InfoPopup>
            <div className="user-container">
                {/* <form onSubmit={submitHandler} className="form">
                    <h3 className='user-profile'>Login</h3>
                    <div className="form-inner">
                        <div className="form-group">
                            <label htmlFor="name">Name:</label>
                            <input type="text" name="name" id="name" value={username} onChange={onChangeUsername}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password:</label>
                            <input type="password" name="passworc" id="password" value={password}
                                onChange={onChangePassword}/>
                        </div>
                        <input className="form-submit" type="submit" value="Login"/>
                    </div>
                </form> */}
                <div className="form">
                    <h3 className='user-profile'>Login</h3>
                    <div className="form-inner">
                        <div className="form-group">
                            <label htmlFor="name">Name:</label>
                            <input type="text" name="name" id="name" value={username} onChange={onChangeUsername}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password:</label>
                            <input type="password" name="passworc" id="password" value={password}
                                   onChange={onChangePassword}/>
                        </div>
                        <button className="form-submit" onClick={submitHandler}>Login</button>
                    </div>
                </div>
            </div>
        </div>
    )
}