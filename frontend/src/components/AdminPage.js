import React, { useCallback, useState, useEffect } from 'react';
import {Link, Route, Routes, useNavigate} from "react-router-dom";
import axios from "axios";
/*import RentForm from './RentForm';
import FindForm from './FindForm';*/
import InfoPopup from './InfoPopup.js'
import AuthService from '../services/auth.service';
import AdminTable from './AdminTable.js';
import "../styles/UserPage.css"
import ReturnPopup from './ReturnPopup.js';

export default function AdminPage() {
    const [data, setData] = useState([])


    const [popupTrigger, setPopupTrigger] = useState(false);
    const [popupMessage, setPopupMessage] = useState("")
   
    const [currentUser, setCurrentUser] = useState(undefined);
    const [showAdminBoard, setShowAdminBoard] = useState(false);

    let navigate = useNavigate();

    const fetchDataHandler = useCallback(async () => {
        try {
            const result = await fetch("http://localhost:8080/rent/all")

            if (!result.ok) {
                throw new Error("Nie udało się pobrać danych")
            }

            const resultData = await result.json()
            setData(resultData)

        } catch (err) {
            console.log(err.message);
        }
    }, [])

    useEffect(() => {
        const user = AuthService.getCurrentUser();

        if (user) {
            setCurrentUser(user)
            setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
            fetchDataHandler()
        }
    }, []);

    return (
        showAdminBoard ? (
        <div>
            <InfoPopup trigger={popupTrigger} setTrigger={setPopupTrigger}>
                <h3>{popupMessage}</h3>
            </InfoPopup>
            <h2 className='page-title'>Admin board</h2>
            <button className='manage-button' onClick={() => navigate("/adminmanage")}>
                Manage resources
            </button>
            <AdminTable data={data}/>
        </div>) : (
            <div className="user-container">
                <h3 className='user-profile'>You can't access the admin page</h3>
            </div>
        )
    )
}