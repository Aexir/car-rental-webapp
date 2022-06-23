import React from 'react';
import { useCallback, useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import { useParams } from 'react-router-dom';
import "react-datepicker/dist/react-datepicker.css";
import '../styles/forms.css'
import "../styles/UserPage.css"
import AuthService from '../services/auth.service';
import axios from 'axios';

function RentForm({ rentCarId }) {
    const [selectedcollDate, setSelectedcollDate] = useState(new Date);
    const [selectedretDate, setSelectedretDate] = useState("");
    const [Data, setData] = useState([]);
    const [Showroom, setShowroom] = useState('');
    const [username, setUsername] = useState(undefined)
    const [userId, setUserId] = useState(undefined)

    const fetchDataHandler = useCallback(async () => {
        try {
            const result = await fetch('http://localhost:8080/showroom/names')

            if (!result.ok) {
                throw new Error("Nie uda³o siê pobraæ danych")
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
            setUsername(user.username)
            setUserId(user.id)
        }
        fetchDataHandler()
        console.log({ Data })

    }, [Showroom]);

    const submitHandler = async (e) => {
        e.preventDefault();

        console.log( username)
        console.log({ selectedcollDate })
        console.log({ selectedretDate })
        console.log({ Showroom })
        
        const response = await axios
        .post("http://localhost:8080/api/payu", {
            headers: {"Access-Control-Allow-Origin": "*"},
            carId: rentCarId,
            endDate: selectedretDate,
            showroomName: Showroom,
            startDate: selectedcollDate,
            userId: userId,
        }, {});
        if (response) {
            if(response.status==200){
                window.location.replace(response.request.responseURL);
            } else {
                setPopupMessage("Call failed");
            }
            setPopupTrigger(true)
        }
        return response;
    }

    return (
        <div id='form-control'>
            {username ? (
                rentCarId ? (
                <div>
                    {console.log("rent: " + rentCarId)}
                    <h2>Reservation form</h2>
                    <form onSubmit={submitHandler}>
        
                        <label>Car collection date and time:</label>
                        <DatePicker
                            selected={selectedcollDate}
                            onChange={(date) => setSelectedcollDate(date)}
                            dateFormat="dd/MM/yyyy "
                            minDate={new Date}
                            required
                            placeholderText="Date of collecting car"
                        />
        
                        <label>Car return date and time:</label>
                        <DatePicker
                            selected={selectedretDate}
                            onChange={date => setSelectedretDate(date)}
                            dateFormat="dd/MM/yyyy "
                            required
                            placeholderText="Date of returning car"
                            minDate={new Date(selectedcollDate.getFullYear(), selectedcollDate.getMonth(), selectedcollDate.getDate() + 1)}
        
                        />
        
                        <label>Return Showroom:</label>
                        <select
                            type="text"
                            required
                            value={Showroom}
                            onChange={(e) => setShowroom(e.target.value)}
                        >
                            <option className='tohide' ></option>
                            {Data.map((item) => (
                                <option key={item} >{item}</option>))}
        
                        </select>
                        <input value="Confirm" type="submit" />
                    </form>
                </div>) : (
                    <div className="user-container">
                        <h3 className='user-profile'>Go to search page to rent a car</h3>
                    </div>
            )) : (
                <div className="user-container">
                    <h3 className='user-profile'>Login to rent a car</h3>
                </div>
            )}
        </div>
    )
}
export default RentForm;