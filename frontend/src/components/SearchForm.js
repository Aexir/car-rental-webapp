import React, {useCallback, useEffect, useState} from 'react';
import '../styles/forms.css'
import {useNavigate} from "react-router-dom";

function SearchForm() {
    const [showrooms, setShowrooms] = useState([])
    // const [data, setData] = useState([])
    const [showroom, setShowroom] = useState('');

    let navigate = useNavigate();

    // const API_URL = "http://localhost:8080/table";

    const fetchDataHandler = useCallback(async () => {
        try {
            const result = await fetch('http://localhost:8080/showroom/names')

            if (!result.ok) {
                throw new Error("Nie uda�o si� pobra� danych")
            }

            const resultData = await result.json()
            setShowrooms(resultData)

        } catch (err) {
            console.log(err.message);
        }

    }, [])

    const onChangeShowroom = (e) => {
        const showroom = e.target.value;
        setShowroom(showroom);
        console.log(showroom)
    };

    useEffect(() => {

        fetchDataHandler()
        console.log({showrooms})
        /* Data.filter(val => {
             if (val.name.toLowerCase().includes(Showroom.toLowerCase())) {
                 setOutput(output => [...output, val])
             }
         })
  */


    }, []);

    const submitHandler = () => {
        localStorage.setItem("showroom", JSON.stringify(showroom));
        navigate("/search")
    };

    return (
        <div>
            <form onSubmit={submitHandler}>
                <div id='form-finding'>
                    {/* <h2>Finding form</h2> */}

                    <label>Select showroom from a list:</label>
                    <select
                        type="text"
                        required
                        value={showroom}
                        onChange={onChangeShowroom}
                    >
                        <option className='tohide'></option>
                        {showrooms.map((item) => (
                            <option key={item}>{item}</option>))}

                    </select>
                    {/* {console.log({showrooms})} */}
                    <input value="Select" type="submit"/>
                </div>
            </form>
        </div>

    )
}

export default SearchForm;
