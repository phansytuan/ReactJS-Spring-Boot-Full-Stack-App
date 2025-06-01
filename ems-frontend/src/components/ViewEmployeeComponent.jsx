import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import { getEmployee } from '../services/EmployeeService';

const ViewEmployeeComponent = () => {
    
    const [employee, setEmployee] = useState({
        id: '',
        firstName: '',
        lastName: '',
        email: '',
        department: ''
    });

    const { id } = useParams();
    const navigator = useNavigate();

    useEffect(() => {
        
        if (id) {
            getEmployee(id).then((response) => {
                setEmployee(response.data);
            }).catch(error => {
                console.error(error);
            });
        }
    }, [id]);

    const backToList = () => {
        navigator('/employees');
    }

    return (
      <div className="container mt-5">
        <div className="card mx-auto" style={{ maxWidth: "500px" }}>
            <div className="card-header text-center">
                <h4>Employee Details</h4>
            </div>
            <div className="card-body">
                <p><strong>ID:</strong> {employee.id}</p>
                <p><strong>First Name:</strong> {employee.firstName}</p>
                <p><strong>Last Name:</strong> {employee.lastName}</p>
                <p><strong>Email:</strong> {employee.email}</p>
                <p><strong>Department:</strong> {employee.department}</p>
                <button className="btn btn-warning mt-3" onClick={backToList}>Back to List</button>
            </div>
        </div>
    </div>
    );
}

export default ViewEmployeeComponent;