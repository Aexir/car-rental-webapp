import React, {useCallback, useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom"
import SearchTable from './SearchTable';
import axios from 'axios'
import SearchForm from './SearchForm';

export default function SearchPage({passRentCarId}) {
    const [data, setData] = useState([])

    let navigate = useNavigate();

    const API_URL = "http://localhost:8080/table";

    const fetchDataHandler = useCallback(async showroom => {
        try {
            console.log("handler: " + showroom)
            const response = await axios.post(API_URL, {
                showroom
            }).catch(err => console.log(err))

            if (response) {
                const data = response.data
                setData(data)
            }

            console.log("data: " + data)
            return response.data;

        } catch (err) {
            console.log(err.message);
        }
    }, [])

    useEffect(() => {
        const showroom = JSON.parse(localStorage.getItem("showroom"));
        if (showroom) {
            // console.log("storage: " + showroom)
            fetchDataHandler(showroom)
        }
    }, []);


    return (
        <div>
            <SearchForm/>
            <SearchTable data={data} passRentCarId={passRentCarId}/>
        </div>
    )
}