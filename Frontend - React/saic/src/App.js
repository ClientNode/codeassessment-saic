import './App.css';
import axios from "axios";
import React, {useState} from 'react';
import Table from 'react-bootstrap/Table';
import Spinner from 'react-bootstrap/Spinner';
import Data from './Data';
import Pagination from './Pagination';

//CORS-ANYWHERE ignores allows origins errors
const api = 'https://xyzproxy.herokuapp.com/https://rl3fehaadk.execute-api.us-east-2.amazonaws.com/dev/trigger-compare';
const headers = {
  "X-Requested-With": "XMLHttpRequest",
  "x-api-key": "kKguv61FNqToHbKQLv5C2Ig22eWFt9D15mgvbbz1",
  'Access-Control-Allow-Origin' : '*',
  'Access-Control-Allow-Headers':'Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,accessToken',
  'Access-Control-Allow-Credentials' : 'true',
  'Content-Type': 'application/json'
}

function App() {
  //store json and error data
  const [json, setJsonData] = useState([]);
  const [error, setLastError] = useState();
  const [loading, setLoading] = useState(false);

  //Pages
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);
  
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = json.slice(indexOfFirstItem, indexOfLastItem);

  //paginate function
  const paginate = (pageNumber) => setCurrentPage(pageNumber);


  const fetchData = (async() => {
    setLoading(true);
    try {
      //Attemp to make rest call to api
      console.log("Trying to make rest call to api")
      const result = await axios.post(api, null, {headers: headers});

      //Stringify response to pretty print
      //let jsonData = JSON.stringify(result.data, null, "\t");
      //console.log(jsonData);

      console.log(result.data);
      setJsonData(result.data);
    } catch (err) {
      //if response error, display under table
      console.log(err);
      setLastError(err.message);
    }
    setLoading(false);
  });

  return (
    <div className="App">
        <br/>
        <button onClick={() => fetchData()}>Get Results</button>
        <br/>
        <br/>
        { loading && <Spinner animation="border"/> }
        { error && <p>{error}</p> }
        <>
        <div className="table-format">
          {
            json.length > 0 &&
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Source Image</th>
                  <th>Target Image</th>
                  <th>Similarity</th>
                  <th>Confidence</th>
                </tr>
              </thead>
            <Data data={currentItems}/>
            </Table>
          }
          <Pagination itemsPerPage={itemsPerPage} totalItems={json.length} paginate={paginate}/>
        </div>
        </>
        <br/>
        <br/>
    </div>
  );
}

export default App;
