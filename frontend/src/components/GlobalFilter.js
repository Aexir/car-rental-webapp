import React, {useState} from "react";
import {useAsyncDebounce} from "react-table";
import '../styles/GlobalFilter.css'

export default function GlobalFilter({
                                         preGlobalFilteredRows,
                                         globalFilter,
                                         setGlobalFilter
                                     }) {
    const [value, setValue] = useState(globalFilter)

    const count = preGlobalFilteredRows.length

    const onChange = useAsyncDebounce((value) => {
        setGlobalFilter(value || undefined)
    }, 300)

    return (
        <div className="search-container">
            <label className="search-label">Search:</label>
            <input value={value || ""} className="search-input" onChange={(e) => {
                setValue(e.target.value)
                onChange(e.target.value)
            }} placeholder={`${count} records...`}/>
        </div>)
}