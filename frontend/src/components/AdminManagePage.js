import React, {useCallback, useEffect, useState} from 'react';
import axios from "axios";
/*import RentForm from './RentForm';
import FindForm from './FindForm';*/
import InfoPopup from './InfoPopup.js'
import AuthService from '../services/auth.service';
import "../styles/UserPage.css"

export default function AdminManagePage() {
    const [showrooms, setShowrooms] = useState([])
    const [cars, setCars] = useState([])

    const [popupTrigger, setPopupTrigger] = useState(false);
    const [popupMessage, setPopupMessage] = useState("")

    const [currentUser, setCurrentUser] = useState(undefined);
    const [showAdminBoard, setShowAdminBoard] = useState(false);

    const [carIdAddTo, setCarIdAddTo] = useState("")
    const [showroomAddTo, setShowroomAddTo] = useState("")

    const [carIdRemoveFrom, setCarIdRemoveFrom] = useState("")
    const [showroomRemoveFrom, setShowroomRemoveFrom] = useState("")

    const [carIdRemove, setCarIdRemove] = useState("")

    const [showroomRemove, setShowroomRemove] = useState("")

    const [brand, setBrand] = useState("");
    const [carType, setCarType] = useState("");
    const [engine, setEngine] = useState("");
    const [image, setImage] = useState();
    const [fuel, setFuel] = useState("");
    const [model, setModel] = useState("");
    const [plate, setPlate] = useState("");
    const [price, setPrice] = useState(0);
    const [seats, setSeats] = useState(0);
    const [transmission, setTransmission] = useState("");
    const [vin, setVin] = useState("");

    const [latitude, setLatitude] = useState("");
    const [longitude, setLongitude] = useState("");
    const [showroom, setShowroom] = useState("");


    const onChangeCarIdAddTo = (e) => {
        const carIdAddTo = e.target.value;
        setCarIdAddTo(carIdAddTo);
    };
    const onChangeShowroomAddTo = (e) => {
        const showroomAddTo = e.target.value;
        setShowroomAddTo(showroomAddTo);
    };
    const onChangeCarIdRemoveFrom = (e) => {
        const carIdRemoveFrom = e.target.value;
        setCarIdRemoveFrom(carIdRemoveFrom);
    };
    const onChangeShowroomRemoveFrom = (e) => {
        const showroomRemoveFrom = e.target.value;
        setShowroomRemoveFrom(showroomRemoveFrom);
    };
    const onChangeCarIdRemove = (e) => {
        const carIdRemove = e.target.value;
        setCarIdRemove(carIdRemove);
    };
    const onChangeShowroomRemove = (e) => {
        const showroomRemove = e.target.value;
        setShowroomRemove(showroomRemove);
    };
    const onChangeBrand = (e) => {
        const brand = e.target.value;
        setBrand(brand);

    };
    const onChangeEngine = (e) => {
        const engine = e.target.value;
        setEngine(engine);

    };
    const onChangeModel = (e) => {
        const model = e.target.value;
        setModel(model);
    };
    const onChangeCarType = (e) => {
        const carType = e.target.value;
        setCarType(carType);
    };
    const onChangeTransmission = (e) => {
        const transmission = e.target.value;
        setTransmission(transmission);
    };
    const onChangeVin = (e) => {
        const vin = e.target.value;
        setVin(vin);
    };
    const onChangeFuel = (e) => {
        const fuel = e.target.value;
        setFuel(fuel);
    };
    const onChangeLatitude = (e) => {
        const latitude = e.target.value;
        setLatitude(latitude);
    };
    const onChangeLongitude = (e) => {
        const longitude = e.target.value;
        setLongitude(longitude);
    };
    const onChangePrice = (e) => {
        const price = e.target.value;
        setPrice(price);
    };
    const onChangePlate = (e) => {
        const plate = e.target.value;
        setPlate(plate);
    };
    const onChangeImage = (e) => {
        setImage(e.target.files[0]);
        console.log(e.target.files[0])
    };
    const onChangeSeats = (e) => {
        const seats = e.target.value;
        setSeats(seats);
    };
    const onChangeShowroom = (e) => {
        const showroom = e.target.value;
        setShowroom(showroom);
    };

    const submitCarAddToHandler = async (e) => {
        e.preventDefault();

        const response = await axios
            .post("http://localhost:8080/showroom/" + showroomAddTo + "/" + carIdAddTo, {}, {});
        if (response) {
            if (response.status == 200) {
                setPopupMessage("Car added to showroom!");
            } else {
                setPopupMessage("Call failed");
            }
            setPopupTrigger(true)
        }
        return response;
    }

    const submitCarRemoveFromHandler = async (e) => {
        e.preventDefault();

        const response = await axios
            .delete("http://localhost:8080/showroom/remove/" + showroomRemoveFrom + "/" + carIdRemoveFrom, {}, {});
        if (response) {
            if (response.status == 200) {
                setPopupMessage("Car removed from showroom!");
            } else {
                setPopupMessage("Call failed");
            }
            setPopupTrigger(true)
        }
        return response;
    }

    const submitCarRemoveHandler = async (e) => {
        e.preventDefault();

        const response = await axios
            .delete("http://localhost:8080/cars/remove/" + carIdRemove, {}, {});
        if (response) {
            if (response.status == 200) {
                setPopupMessage("Car removed!");
            } else {
                setPopupMessage("Call failed");
            }
            setPopupTrigger(true)
        }
        return response;
    }

    const submitShowroomRemoveHandler = async (e) => {
        e.preventDefault();

        const response = await axios
            .delete("http://localhost:8080/showroom/remove/" + showroomRemove, {}, {});
        if (response) {
            if (response.status == 200) {
                setPopupMessage("Showroom removed!");
            } else {
                setPopupMessage("Call failed");
            }
            setPopupTrigger(true)
        }
        return response;
    }

    const submitCarHandler = async (e) => {
        e.preventDefault();

        const formData = new FormData()
        formData.append("brand", brand)
        formData.append("carType", carType)
        formData.append("engine", engine)
        formData.append("file", image)
        formData.append("fuel", fuel)
        formData.append("model", model)
        formData.append("plate", plate)
        formData.append("price", price)
        formData.append("seats", seats)
        formData.append("transmission", transmission)
        formData.append("vin", vin)

        const config = {
            headers: {
                "content-type": "multipart/form-data"
            }
        }
        const response = await axios.post("http://localhost:8080/cars/add/", formData, config);
        if (response) {
            if (response.status == 200) {
                setPopupMessage("Car added!");
            } else {
                setPopupMessage("Call failed");
            }
            setPopupTrigger(true)
        }
        return response;
    }

    const submitShowroomHandler = async (e) => {
        e.preventDefault();

        console.log(latitude)
        console.log(longitude)
        console.log(showroom)

        const response = await axios.post("http://localhost:8080/showroom/add/", {
            latitude,
            longitude,
            name: showroom,
        });
        if (response) {
            if (response.status == 200) {
                setPopupMessage("Showroom added!");
            } else {
                setPopupMessage("Call failed");
            }
            setPopupTrigger(true)
        }
        return response;
    }

    const fetchShowroomsHandler = useCallback(async () => {
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

    const fetchCarsHandler = useCallback(async () => {
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

    useEffect(() => {
        fetchShowroomsHandler()
        console.log({showrooms})

        const user = AuthService.getCurrentUser();

        if (user) {
            setCurrentUser(user)
            setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
        }
    }, []);

    return (
        showAdminBoard ? (
            <div>
                <InfoPopup trigger={popupTrigger} setTrigger={setPopupTrigger}>
                    <h3>{popupMessage}</h3>
                </InfoPopup>
                <h2 className='page-title'>Admin management</h2>
                <div className="user-container">
                    <div className="form">
                        <div className="form-inner">
                            <h3 className='car-add-to'>Add a car to showroom</h3>
                            <div className="form-group">
                                <label htmlFor="carIdAddTo">Car ID:</label>
                                <input name="carIdAddTo" id="carIdAddTo" value={carIdAddTo}
                                       onChange={onChangeCarIdAddTo}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="showroomAddTo">Showroom name:</label>
                                <input name="showroomAddTo" id="showroomAddTo" value={showroomAddTo}
                                       onChange={onChangeShowroomAddTo}/>
                            </div>
                            <button className="form-submit" onClick={submitCarAddToHandler}>Add</button>
                        </div>
                        <div className="form-inner">
                            <h3 className='car-add-to'>Remove a car from showroom</h3>
                            <div className="form-group">
                                <label htmlFor="carIdRemoveFrom">Car ID:</label>
                                <input name="carIdRemoveFrom" id="carIdRemoveFrom" value={carIdRemoveFrom}
                                       onChange={onChangeCarIdRemoveFrom}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="showroomRemoveFrom">Showroom name:</label>
                                <input name="showroomRemoveFrom" id="showroomRemoveFrom" value={showroomRemoveFrom}
                                       onChange={onChangeShowroomRemoveFrom}/>
                            </div>
                            <button className="form-submit" onClick={submitCarRemoveFromHandler}>Remove</button>
                        </div>
                        <div className="form-inner">
                            <h3 className='car-remove'>Remove a car</h3>
                            <div className="form-group">
                                <label htmlFor="carIdRemove">Car ID:</label>
                                <input name="carIdRemove" id="carIdRemove" value={carIdRemove}
                                       onChange={onChangeCarIdRemove}/>
                            </div>
                            <button className="form-submit" onClick={submitCarRemoveHandler}>Remove</button>
                        </div>
                        <div className="form-inner">
                            <h3 className='showroom-remove'>Remove a showroom</h3>
                            <div className="form-group">
                                <label htmlFor="showroomRemove">Showroom name:</label>
                                <input name="showroomRemove" id="showroomRemove" value={showroomRemove}
                                       onChange={onChangeShowroomRemove}/>
                            </div>
                            <button className="form-submit" onClick={submitShowroomRemoveHandler}>Remove</button>
                        </div>
                    </div>
                    <div className="form">
                        <div className="form-inner">
                            <h3 className='car-add'>Add a car</h3>
                            <div className="form-group">
                                <label htmlFor="brand">Brand:</label>
                                <input name="brand" id="brand" value={brand}
                                       onChange={onChangeBrand}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="CarType">Car Type:</label>
                                <input name="CarType" id="CarType" value={carType}
                                       onChange={onChangeCarType}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="Engine">Engine:</label>
                                <input name="Engine" id="Engine" value={engine}
                                       onChange={onChangeEngine}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="Image">Image:</label>
                                <input type="file" name="Image" id="Image"
                                       onChange={onChangeImage}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="Fuel">Fuel:</label>
                                <input name="Fuel" id="Fuel" value={fuel}
                                       onChange={onChangeFuel}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="Model">Model:</label>
                                <input name="Model" id="Model" value={model}
                                       onChange={onChangeModel}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="Plate">Plate:</label>
                                <input name="Plate" id="Plate" value={plate}
                                       onChange={onChangePlate}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="Price">Price:</label>
                                <input name="Price" id="Price" value={price}
                                       onChange={onChangePrice}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="Seats">Seats:</label>
                                <input name="Seats" id="Seats" value={seats}
                                       onChange={onChangeSeats}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="Transmission">Transmission:</label>
                                <input name="Transmission" id="Transmission" value={transmission}
                                       onChange={onChangeTransmission}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="Vin">Vin:</label>
                                <input name="Vin" id="Vin" value={vin}
                                       onChange={onChangeVin}/>
                            </div>
                            <button className="form-submit" onClick={submitCarHandler}>Add</button>
                        </div>
                    </div>
                    <div className="form">
                        <div className="form-inner">
                            <h3 className='showroom-add'>Add a showroom </h3>
                            <div className="form-group">
                                <label htmlFor="latitude">Latitude:</label>
                                <input name="latitude" id="latitude" value={latitude}
                                       onChange={onChangeLatitude}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="longitude">Longitude:</label>
                                <input name="longitude" id="longitude" value={longitude}
                                       onChange={onChangeLongitude}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="showroom">Showroom name:</label>
                                <input name="showroom" id="showroom" value={showroom}
                                       onChange={onChangeShowroom}/>
                            </div>
                            <button className="form-submit" onClick={submitShowroomHandler}>Add</button>
                        </div>
                    </div>
                </div>
            </div>) : (
            <div className="user-container">
                <h3 className='user-profile'>You can't access the admin page</h3>
            </div>
        )
    )
}