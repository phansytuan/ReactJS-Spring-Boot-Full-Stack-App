import React, { useEffect, useState } from "react";
import { createEmployee, getEmployee, updateEmployee } from "../services/EmployeeService";
import { useNavigate, useParams } from "react-router-dom";

const EmployeeComponent = () => {

    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState('')
    const [department, setDepartment] = useState('')

    // function handleFirstName(event) {
    //     setFirstName(event.target.value);
    // }
    
    // const handleFirstName = (e) => setFirstName(e.target.value);

    const {id} = useParams();

    // Track Errors Using useState, Each field has a corresponding error message in the errors object.
    const [errors, setErrors] = useState({
        firstName: '',
        lastName: '',
        email: '',
        department: ''
    })
    
    const navigator = useNavigate();

    // if id is present then we'll populate the update employee form with the response of the REST API
    // pass in (callback funtion, dependency list)
    useEffect(() => {
        
        if(id) {
            getEmployee(id).then((response) => {
                // set the data from response to these state variables
                setFirstName(response.data.firstName);
                setLastName(response.data.lastName);
                setEmail(response.data.email);
                setDepartment(response.data.department);
            }).catch(error => {
                console.error(error);
            })
        }
    }, [id])
    
    // form submission event handler
    function saveOrUpdateEmployee(event) {
        event.preventDefault();

        if(validateForm()) { // Trigger Validation on Form Submit

            const employee = {firstName, lastName, email, department}
            console.log(employee)

            // support both update & add employee operation
            if(id) {
                updateEmployee(id, employee).then((response) => { // call updateEmployee fromm EmployeeService
                    console.log(response.data);
                    navigator("/employees");
                }).catch(error => {
                    console.error(error);
                })
            } else {
                // add employee logic 
                createEmployee(employee).then((response) => { // call createEmployee() method from EmployeeService
                    console.log(response.data);
                    navigator('/employees') // now we have navigated user to the ListEmployeeComponent, whenever a user submit this Add employee form. 
                }).catch(error => {
                    console.error(error);
                })
            }
        }
    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {...errors}

        if(firstName.trim()){
            errorsCopy.firstName = ""
        } else {
            errorsCopy.firstName = "First name is required";
            valid = false;
        }

        if(lastName.trim()){
            errorsCopy.lastName = "";
        } else {
            errorsCopy.lastName = "Last name is required";
            valid = false;
        }

        if(email.trim()){
            errorsCopy.email = "";
        } else {
            errorsCopy.email = "Email is required";
            valid = false;
        }

        if(department.trim()){
            errorsCopy.department = "";
        } else {
            errorsCopy.department = "Department is required";
            valid = false;
        }

        setErrors(errorsCopy);
        return valid;
    }
    // If any field is empty, it adds an error message and sets valid = false.

    function pageTitle() {
        if(id) {
            return <h2 className="text-center">Update Employee</h2>
        } else {
            return <h2 className="text-center">Add Employee</h2>
        }
    }

    return (
        <div className="container"> 
        <br /><br />
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3 card-header">
                    {
                        pageTitle()
                    }
                    <div className="card-body">
                        <form>
                            <div className='form-group mb-2'>
                                <label className='form-label'>First Name:</label>
                                <input
                                    type='text'
                                    placeholder='Enter Employee First Name'
                                    name='firstName'
                                    value={firstName}
                                    className={`form-control ${errors.firstName ? 'is-invalid' : ''}`} 
                                    onChange={(e) => setFirstName(e.target.value)}
                                /> 
                                {errors.firstName && <div className='invalid-feedback'>{errors.firstName}</div>}
                            </div>
                            {/* If there's an error (errors.firstName), the input gets is-invalid class (from Bootstrap css) / invalid-feedback */}

                            <div className='form-group mb-2'>
                                <label className='form-label'>Last Name:</label>
                                <input
                                    type='text'
                                    placeholder='Enter Employee Last Name'
                                    name='lastName'
                                    value={lastName}
                                    className={`form-control ${errors.lastName ? 'is-invalid' : ''}`}
                                    onChange={(e) => setLastName(e.target.value)}
                                />
                                {errors.lastName && <div className='invalid-feedback'>{errors.lastName}</div>}
                            </div>

                            <div className='form-group mb-2'>
                                <label className='form-label'>Email:</label>
                                <input
                                    type='email'
                                    placeholder='Enter Employee Email'
                                    name='email'
                                    value={email}
                                    className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                                {errors.email && <div className='invalid-feedback'>{errors.email}</div>}
                            </div>

                            <div className='form-group mb-2'>
                                <label className='form-label'>Department:</label>
                                <input
                                    type='text'
                                    placeholder='Enter Employee Department'
                                    name='department'
                                    value={department}
                                    className={`form-control ${errors.department ? 'is-invalid' : ''}`}
                                    onChange={(e) => setDepartment(e.target.value)}
                                />
                                {errors.department && <div className='invalid-feedback'>{errors.department}</div>}
                            </div>
                            <button className="btn btn-success" onClick={saveOrUpdateEmployee}>Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmployeeComponent;