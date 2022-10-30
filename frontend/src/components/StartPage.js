import React from 'react';
import {useNavigate} from "react-router-dom"
import SearchForm from './SearchForm';
import Map from './Map';
import ReactCardSlider from './ReactCardSlider';
import "../styles/StartPage.css"

export default function StartPage({FindCars}) {

    let navigate = useNavigate();

    return (
        <div className='startpage-container'>
            <div className='startpage-inner'>
                <h3>Our cars</h3>
                <ReactCardSlider/>
            </div>
            <div className='startpage-inner'>
                <h3>Our showrooms</h3>
                <div className='startpage-group'>
                    <SearchForm/>
                </div>
                <div className='startpage-group'>
                    <h4 className='select-on-map'>Or select on the map:</h4>
                    <Map/>
                </div>
            </div>
        </div>
    )
}