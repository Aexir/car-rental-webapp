import {Button} from '@material-ui/core';
import React, {useCallback, useEffect, useState} from 'react';
import {MapContainer, Marker, Popup, TileLayer} from 'react-leaflet';
import {useNavigate} from "react-router-dom"

export default function Map() {
    const [showrooms, setShowrooms] = useState([])

    let navigate = useNavigate();

    const fetchDataHandler = useCallback(async () => {
        try {
            const result = await fetch(`http://localhost:8080/showroom/all`)

            if (!result.ok) {
                throw new Error("Nie udało się pobrać danych")
            }

            const resultData = await result.json()
            setShowrooms(resultData)

        } catch (err) {
            console.log(err.message);
        }

    }, [])

    useEffect(() => {
        fetchDataHandler()
    }, []);

    const clickEvent = (props) => {
        localStorage.setItem("showroom", JSON.stringify(props.showroom.name));
        navigate("/search")
    };

    return (
        <div id="map" className="leafletContainer">
            <MapContainer style={{height: '100%', width: '100%'}} center={[52.0, 19.4]} zoom={6} scrollWheelZoom={true}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {
                    showrooms.map(showroom => (
                        <Marker key={showroom.id} position={[showroom.latitude, showroom.longitude]}>
                            <Popup>
                                <div>
                                    <label>{showroom.name}  </label>
                                    <Button onClick={() => clickEvent({showroom})}>Select</Button>
                                </div>
                            </Popup>
                        </Marker>
                    ))}
            </MapContainer>
        </div>
    )
}
