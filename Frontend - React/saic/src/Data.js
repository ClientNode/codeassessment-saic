import React from 'react';

const Data = ({data}) => {
    return <tbody>
        {
        data.map((info, index) => {
            return(
            <tr key={index}>
            <td>{info.source_img}</td>
            <td>{info.target_img}</td>
            <td>{info.similarity}</td>
            <td>{info.confidence}</td>
            </tr>);
        })   
        }
    </tbody>
}

export default Data;