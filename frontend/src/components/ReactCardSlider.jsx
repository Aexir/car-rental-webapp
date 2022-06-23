import React, { useCallback, useState, useEffect } from 'react';
import { MdChevronLeft, MdChevronRight } from 'react-icons/md';
import {useNavigate} from "react-router-dom"
import '../styles/Slider.css';
import axios from "axios";
import HomePage from './HomePage';

function ReactCardSlider() {
    const [cars, setCars] = useState([])
    const [name, setName] = useState("")

    let navigate = useNavigate();

    const fetchDataHandler = useCallback(async () => {
        try {
            const result = await fetch(`http://localhost:8080/files/slider`)

            if (!result.ok) {
                throw new Error("Nie udało się pobrać danych")
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


    //Object.keys(cars).forEach(car => slides.push({image: url, title: name, desctiption: id}))

    // slides = [{ image: 'https://picsum.photos/200/300', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/200/300', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/200/400', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/300/300', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/200/500', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/200/600', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/200/100', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/100/300', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/200/400', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/200/700', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/200/600', title: "this is a title", description: "This is description analiza" },
    //     { image: 'https://picsum.photos/200/200', title: "this is a title", description: "This is description analiza" }];

    const slideLeft = () => {
        var slider = document.getElementById("slider");
        slider.scrollLeft = slider.scrollLeft - 300;
    }

    const slideRight = () => {
        var slider = document.getElementById("slider");
        slider.scrollLeft = slider.scrollLeft + 300;
    }

    const clickEvent = (props) => {

        console.log(props.slide.carId)
        navigate("/info/" + props.slide.carId)
    }

    return (
        <div id="main-slider-container">
            {/* {console.log(cars)} */}
            <MdChevronLeft size={40} className="slider-icon left" onClick={slideLeft} />
            <div id='slider' style={{ justifyContent: 'center' }}>
                {
                    cars.map((slide, index) => (
                        <div className="slider-card" key={index} onClick={() => clickEvent({ slide })}>
                            <div className="slider-card-image" style={{ backgroundImage: `url(${slide.url})`, backgroundSize: 'cover' }}> </div>
                            <p className="slider-card-title">{slide.brand} {slide.model}</p>
                            <p className="slider-card-description">{slide.engine}</p>
                        </div>
                    )
                    )
                }
            </div>
            <MdChevronRight size={40} className="slider-icon right" onClick={slideRight} />
        </div>
    )
}
export default ReactCardSlider;
//42~~