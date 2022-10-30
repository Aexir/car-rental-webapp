import React, {useMemo} from 'react';
import {useNavigate} from "react-router-dom"
import "../styles/Table.css"
import {useGlobalFilter, useSortBy, useTable} from 'react-table';
import GlobalFilter from './GlobalFilter';

const SearchTable = ({data, passRentCarId}) => {
    let navigate = useNavigate();

    // const keys = ['url', 'showroom', 'brand', 'model', 'type', 'seats', 'transmission', 'fuel', 'id']
    // const columns = ['Image', 'Showroom', 'Brand', 'Model', 'Type', 'Seats', 'Transmission', 'Fuel']

    // usememodata

    const columns = useMemo(
        () => [
            {
                Header: "Image",
                accessor: "url",
                Cell: ({value}) => <img src={value} className="img"/>
            },
            {
                Header: "Showroom",
                accessor: "showroom"
            },
            {
                Header: "Car ID",
                accessor: "id"
            },
            {
                Header: "Brand",
                accessor: "brand"
            },
            {
                Header: "Model",
                accessor: "model"
            },
            {
                Header: "Type",
                accessor: "type"
            },
            {
                Header: "Seats",
                accessor: "seats"
            },
            {
                Header: "Transmission",
                accessor: "transmission"
            },
            {
                Header: "Fuel",
                accessor: "fuel"
            },
        ], [])

    const tableInstance = useTable(
        {columns, data},
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
        state
    } = tableInstance

    const rentHandler = (props) => {
        console.log("search:" + props.row.values.id)
        passRentCarId(props.row.values.id)
        navigate("/rent");
    };

    return (
        <div className="table-container">
            <GlobalFilter preGlobalFilteredRows={preGlobalFilteredRows} setGlobalFilter={setGlobalFilter}
                          globalFilter={state.globalFilter}/>
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
                        <tr className="trBody" onClick={() => rentHandler({row})} {...row.getRowProps()}>
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

export default SearchTable