import React from 'react'
import '../styles/InfoPopup.css'

export default function ReturnPopup(props) {

    return (props.trigger) ? (
        <div className='popup'>
            {console.log(props.message)}
            <div className='popup-inner'>

                <button className='return-button' onClick={() => props.returnCar(props.id)}>
                    Return
                </button>
                <button className='cancel-button' onClick={() => props.setTrigger(false)}>
                    Cancel
                </button>
                <h3>Return car?</h3>
                {props.children}
            </div>
        </div>
    ) : ""
}