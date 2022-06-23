import React, { useCallback, useState, useEffect, useMemo } from 'react';
import {useNavigate} from "react-router-dom"
import axios from 'axios'
import "../styles/Table.css"
import { useTable, useSortBy, useGlobalFilter } from 'react-table';
import GlobalFilter from './GlobalFilter';

const MyRentalsTable = ({ data }) => {
    let navigate = useNavigate();

    const columns = useMemo(
        () => [
        {
            Header: "Rental ID",
            accessor: "id",
        },
        {
            Header: "Showroom",
            accessor: "showroomName"
        },
        {
            Header: "Car ID",
            accessor: "carId"
        },
        {
            Header: "Start date",
            accessor: "startDate"
        },
        {
            Header: "End date",
            accessor: "endDate"
        },
        {
            Header: "Price",
            accessor: "price"
        },
        {
            Header: "Status",
            accessor: "rentStatus"
        },
    ], [])

    const tableInstance = useTable(
        { columns, data },
        useGlobalFilter,
        useSortBy,
    )

    const { 
        getTableProps, 
        getTableBodyProps, 
        headerGroups, 
        rows, 
        prepareRow, 
        preGlobalFilteredRows, 
        setGlobalFilter, 
        state } = tableInstance

    // const rentHandler = (e) => {
    //     navigate("/rent");
    // };

    return (
        <div className="table-container">
        <GlobalFilter preGlobalFilteredRows={preGlobalFilteredRows} setGlobalFilter={setGlobalFilter} globalFilter={state.globalFilter}/>
        <table className="table" {...getTableProps()}> 
            <thead className="thead">
                {headerGroups.map((headerGroup) => (
                    <tr className="trHead" {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map((column) => (
                            <th className="th" {...column.getHeaderProps(column.getSortByToggleProps())}>
                                {column.render("Header")}
                                {column.isSorted ? (column.isSortedDesc ? " ▼" : " ▲") : ""}
                            </th>
                        ))}
                    </tr>
                ))}
            </thead>
            <tbody className="tbody" {...getTableBodyProps()}>
                {rows.map((row, idx) => {
                    prepareRow(row)
                    return ( 
                    <tr className="trBody" {...row.getRowProps()}>
                        {row.cells.map((cell, idx) => (
                            <td className="td" {...cell.getCellProps()}>
                                {cell.render("Cell")}
                            </td>
                        ))}
                    </tr>
                    )
                })}
            </tbody>
        </table>
    </div>

    )
}

export default MyRentalsTable