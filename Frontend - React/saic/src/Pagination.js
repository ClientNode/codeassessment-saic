import React from 'react';

const Pagination = ({itemsPerPage, totalItems, paginate}) => { 
    const pageNumbers = [];

    for (let i = 1; i <= Math.ceil(totalItems / itemsPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <nav aria-label="Page navigation">
            <ul className="pagination pagination-lg">
                {
                    pageNumbers.map(number => (
                        <li key={number} className="page-item">
                            <a href className="page-link" onClick={() => paginate(number)} >
                                {number}
                            </a>
                        </li>
                    ))
                }
            </ul>
            </nav>
    );
}

export default Pagination;