import React, {useCallback, useEffect, useState} from 'react';
import '../styles/PricingPage.css'

export default function PricingPage() {
    const [cars, setCars] = useState([])
    const clickEvent = (props) => {

        console.log(props.slide.carId)
        /*navigate("/info/" + props.slide.carId)*/
    }
    const fetchDataHandler = useCallback(async () => {
        try {
            const result = await fetch(`http://localhost:8080/cars/unique`)

            if (!result.ok) {
                throw new Error("Nie uda�o si� pobra� danych")
            }

            const resultData = await result.json()
            setCars(resultData)

        } catch (err) {
            console.log(err.message);
        }

    }, [])

    useEffect(() => {
        fetchDataHandler()
    }, []);
    return (
        <div>
            <div className="info-section">
                <h1>Pricing Page</h1>
                <p> Presented prices apply to the specific car <br/> and represent the cost of renting a car for one
                    day. </p>
            </div>
            <div id="cards">
                {
                    cars.map((slide, index) => (
                        <div id="card" key={index} onClick={() => clickEvent({slide})}>
                            <div className="card-image"
                                 style={{backgroundImage: `url(${slide.url})`, backgroundSize: 'cover'}}></div>
                            <p className="card-brand">{slide.brand} {slide.model}</p>
                            <p className="card-engine">{slide.engine}</p>
                            <hr/>
                            <p className="card-price">Price per day: {slide.price} PLN</p>

                        </div>
                    ))}
            </div>
        </div>
    )
}