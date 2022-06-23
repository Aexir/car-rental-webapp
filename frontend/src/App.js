import React, {useEffect, useState} from 'react';
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import StartPage from './components/StartPage';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import UserPage from './components/UserPage';
import HomePage from './components/HomePage';
import SearchPage from './components/SearchPage';
import PricingPage from './components/PricingPage';
import AboutPage from './components/AboutPage';
import RentForm from './components/RentForm';
import CarInfo from './components/CarInfo';
import AuthService from "./services/auth.service";
import AdminPage from './components/AdminPage';
import AdminManagePage from './components/AdminManagePage';
import MyRentalsPage from './components/MyRentalsPage';

import "./styles/styles.css"
import golomb from "./golomb-watoski.png"



export default function App() {
    // const [showModeratorBoard, setShowModeratorBoard] = useState(false);
    const [showAdminBoard, setShowAdminBoard] = useState(false);
    const [currentUser, setCurrentUser] = useState(undefined);
    const [showroom, setShowroom] = useState('');
    const [rentCarId, setRentCarId] = useState(undefined)

    let navigate = useNavigate();

    useEffect(() => {
        const user = AuthService.getCurrentUser();

        if (user) {
            setCurrentUser(user);
            // setShowModeratorBoard(user.roles.includes("ROLE_MODERATOR"));
            setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
        }
    }, []);

    const logoutHandler = () => {
        AuthService.logout();
        // setShowModeratorBoard(false);
        // setShowAdminBoard(false);
        // setCurrentUser(undefined);
    };

    const passRentCarId = (rentCarId) => {
        setRentCarId(rentCarId)
    }

    return (
        <div>
            {/*<nav className="nav">*/}
            {/*    <a className="site-title" onClick={() => ("/home")}>Car rental</a>*/}
            {/*    <ul>*/}
            {/*        <li>*/}
            {/*            <a onClick={() => navigate("/about")}>About</a>*/}
            {/*        </li>*/}
            {/*        <li>*/}
            {/*            <a onClick={() => navigate("/pricing")}>Pricing</a>*/}
            {/*        </li>*/}
            {/*        <li>*/}
            {/*            <a onClick={logoutHandler}>Logout</a>*/}
            {/*        </li>*/}
            {/*    </ul>*/}
            {/*</nav>*/}

            <nav className="nav">
                <ul>
                    <Link to={"/"} className="site-title">
                        <img src={golomb} className='golomb'/>
                        Car rental
                    </Link>
                </ul>
                <ul>
                    <li>
                        <Link to={"/"} className="nav-link">
                            Home
                        </Link>
                    </li>
                    <li>
                        <Link to={"/search"} className="nav-link">
                            Search
                        </Link>
                    </li>
                    <li>
                        <Link to={"/pricing"} className="nav-link">
                            Pricing
                        </Link>
                    </li>
                    <li>
                        <Link to={"/about"} className="nav-link">
                            About
                        </Link>
                    </li>
                    

                    {showAdminBoard ? (
                        <li className="nav-item">
                            <Link to={"/admin"} className="nav-link">
                                Admin board
                            </Link>
                        </li>
                    ) : ( currentUser && (
                        <li className="nav-item">
                            <Link to={"/myrentals"} className="nav-link">
                                My rentals
                            </Link>
                        </li>)
                    )}

                    {/* {currentUser && (
                        <li className="nav-item">
                            <Link to={"/user"} className="nav-link">
                                User
                            </Link>
                        </li>
                    )} */}
                </ul>

                <ul>
                    {currentUser ? (
                        <ul>
                            <li className="nav-item">
                                <Link to={"/user"} className="nav-link">
                                    {currentUser.username}
                                </Link>
                            </li>
                            <li className="nav-item">
                                <a href="/login" className="nav-link" onClick={logoutHandler}>
                                    Logout
                                </a>
                            </li>
                        </ul>
                    ) : (
                        <ul>
                            <li className="nav-item">
                                <Link to={"/login"} className="nav-link">
                                    Login
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to={"/register"} className="nav-link">
                                    Register
                                </Link>
                            </li>
                        </ul>
                    )}
                </ul>
            </nav>
            {/* <a>Welcome, 
                    <span>
                        {currentUser.username}
                    </span>
            </a> */}

            <Routes>
                <Route exact path="/" element={<StartPage/>}/>
                <Route exact path="/login" element={<LoginForm/>}/>
                <Route exact path="/register" element={<RegisterForm/>}/>
                {/* <Route exact path="/home" element={<HomePage/>}/> */}
                <Route exact path="/search" element={<SearchPage passRentCarId={passRentCarId}/>}/>
                <Route exact path="/about" element={<AboutPage/>}/>
                <Route exact path="/pricing" element={<PricingPage/>}/>
                <Route exact path="/myrentals" element={<MyRentalsPage/>}/>
                <Route exact path="/admin" element={<AdminPage/>}/>
                <Route exact path="/adminmanage" element={<AdminManagePage/>}/>
                <Route exact path="/user" element={<UserPage/>}/>
                <Route exact path="/rent" element={<RentForm rentCarId={rentCarId}/>}/>
                <Route exact path="/info/:id" element={<CarInfo/>}/>
                
            </Routes>
        </div>
    )
}