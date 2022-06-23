import React from 'react'
import '../styles/InfoPopup.css'

export default function InfoPopup(props) {
    return (props.trigger) ? (
        <div className='popup'>
            <div className='popup-inner'>
                <button className='close-button' onClick={() => props.setTrigger(false)}>
                    OK
                </button>
                {props.children}
            </div>
        </div>
    ) : ""
}