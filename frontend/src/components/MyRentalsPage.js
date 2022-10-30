import React, {useCallback, useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom"
import AuthService from "../services/auth.service";
import MyRentalsTable from "./MyRentalsTable"

export default function MyRentalsPage() {
    const [data, setData] = useState([])
    const [currentUser, setCurrentUser] = useState(undefined);

    let navigate = useNavigate();

    const fetchDataHandler = useCallback(async currentUserId => {
        try {
            // console.log(currentUserId)
            // console.log("http://localhost:8080/rent/" + currentUserId)
            const result = await fetch("http://localhost:8080/rent/" + currentUserId)

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
            console.log(user.id)
            fetchDataHandler(user.id)
        }
    }, []);


    return (
        <div>
            <MyRentalsTable data={data}/>
        </div>
    )
}