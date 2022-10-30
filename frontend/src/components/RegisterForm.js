import React, {useState} from 'react'
import {useNavigate} from "react-router-dom"
import AuthService from "../services/auth.service";
import InfoPopup from './InfoPopup';
import './UserPage'

export default function RegisterForm() {
    const [popupTrigger, setPopupTrigger] = useState(false);
    const [popupMessage, setPopupMessage] = useState("")

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("");

    let navigate = useNavigate();


    const onChangeUsername = (e) => {
        const username = e.target.value;
        setUsername(username);
    };

    const onChangeEmail = (e) => {
        const email = e.target.value;
        setEmail(email);
    };

    const onChangePassword = (e) => {
        const password = e.target.value;
        setPassword(password);
    };


    const submitHandler = e => {
        e.preventDefault();

        setPopupTrigger(true)
        setPopupMessage("Loading...")

        AuthService.register(username, email, password).then(
            (response) => {
                // setMessage(response.data.message);
                // setSuccessful(true);
                navigate("/login");
                window.location.reload();
            },
            (error) => {
                const resMessage =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                // setMessage(resMessage);
                // setSuccessful(false);
                setPopupTrigger(true)
                setPopupMessage(resMessage)
            }
        );
    }

    return (
        <div className="user-container">
            <InfoPopup trigger={popupTrigger} setTrigger={setPopupTrigger}>
                <h3>{popupMessage}</h3>
            </InfoPopup>
            {/* <form onSubmit={submitHandler} className="form">
                <h3 className='user-profile'>Register</h3>
                <div className="form-inner">
                    <div className="form-group">
                        <label htmlFor="name">Name:</label>
                        <input type="text" name="name" id="name" onChange={onChangeUsername} value={username}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email:</label>
                        <input type="email" name="email" id="email" onChange={onChangeEmail} value={email}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input type="password" name="passworc" id="password" onChange={onChangePassword} value={password}/>
                    </div>
                    <input className="form-submit" type="submit" value="Register"/>
                </div>
            </form> */}
            <div className="form">
                <h3 className='user-profile'>Register</h3>
                <div className="form-inner">
                    <div className="form-group">
                        <label htmlFor="name">Name:</label>
                        <input type="text" name="name" id="name" onChange={onChangeUsername} value={username}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Email:</label>
                        <input type="email" name="email" id="email" onChange={onChangeEmail} value={email}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input type="password" name="passworc" id="password" onChange={onChangePassword}
                               value={password}/>
                    </div>
                    <button className="form-submit" onClick={submitHandler}>Register</button>
                </div>
            </div>
        </div>
    )
}