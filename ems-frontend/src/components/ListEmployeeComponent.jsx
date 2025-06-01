import { deleteEmployee, listEmployees } from '../services/EmployeeService';
import { useEffect, useState } from 'react';

import { useNavigate } from 'react-router-dom';

// functional component dùng để hiển thị danh sách nhân viên
const ListEmployeeComponent = () => {

    /* Khai báo một state tên là 'employees' để lưu danh sách nhân viên
       - Mặc định là mảng rỗng []
       - setEmployees là hàm dùng để cập nhật state */
    const [employees, setEmployees] = useState([]);

    const navigator = useNavigate();

    /* useEffect chạy sau khi component render lần đầu tiên
       (bởi vì mảng dependency là [] rỗng nên chỉ chạy 1 lần duy nhất) */
    useEffect(() => {
        getAllEmployees();
    }, []); // Dependency array [] rỗng: chỉ chạy khi component mount

    
    function getAllEmployees() {
        // Gọi API từ service để lấy dữ liệu danh sách nhân viên
        listEmployees()
            .then((response) => {
                /* Nếu gọi API thành công:
                   - response.data chứa mảng các nhân viên trả về từ backend
                   - cập nhật state 'employees' với dữ liệu nhận được */
                setEmployees(response.data);
            })
            .catch((error) => {
                console.error("Error fetching employee data:", error);
        });        
    }

    function addNewEmployee() {
        navigator('/add-employee')
    }

    function updateEmployee(id) {
        navigator(`/edit-employee/${id}`)
    }

    function removeEmployee(id) {
        console.log(id);

        deleteEmployee(id).then((response) => {
            console.log("Delete success:", response);
            getAllEmployees();
        }).catch(error => {
            console.error(error);
        })
    }
    
    function viewEmployee(id) {
        navigator(`/view-employee/${id}`);
    }

    return (
        <div className="container">
            <h2 className="text-center">List of Employees</h2>
            <button className="btn btn-primary mb-2" onClick={addNewEmployee}>Add Employee</button>
            <table className="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>Employee ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Department</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {/* Lặp qua mảng employees để hiển thị từng nhân viên */}
                    {employees.map((employee) => (
                        <tr key={employee.id}>
                            <td>{employee.id}</td>
                            <td>{employee.firstName}</td>
                            <td>{employee.lastName}</td>
                            <td>{employee.email}</td>
                            <td>{employee.department}</td>
                            <td>
                                <button className="btn btn-info" onClick={() => updateEmployee(employee.id)}>Update</button>
                                <button className="btn btn-danger" onClick={() => removeEmployee(employee.id)} style={{marginLeft: "10px"}}>Delete</button>
                                <button className="btn btn-success" onClick={() => viewEmployee(employee.id)} style={{marginLeft: "10px"}}>View</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <div className="container mt-4">
                <h2>🚀 Project Overview</h2>
                <p>Based on the project structure and files, you're building a <strong>full-stack Employee Management System (EMS)</strong> using:</p>
                <ul>
                    <li><strong>Backend:</strong> Spring Boot (Java)</li>
                    <li><strong>Frontend:</strong> React with Vite</li>
                </ul>

                <p>Here are suggestions for improvements and additional features you can consider:</p>

                <h4>🔐 Authentication & Authorization</h4>
                <ul>
                    <li>JWT-based authentication</li>
                    <li>Role-based access (Admin, Employee)</li>
                    <li>Login/logout functionality</li>
                    <li>Password reset feature</li>
                </ul>

                <h4>👤 Enhanced Employee Features</h4>
                <ul>
                    <li>Employee profile management</li>
                    <li>Document uploads (resume, certificates)</li>
                    <li>Attendance tracking</li>
                    <li>Leave management</li>
                    <li>Performance reviews</li>
                    <li>Skill and certification tracking</li>
                </ul>

                <h4>🏢 Department & Team Management</h4>
                <ul>
                    <li>Department hierarchy and structure</li>
                    <li>Team and role assignments</li>
                    <li>Department-wise reports</li>
                    <li>Resource allocation</li>
                </ul>

                <h4>📊 Reporting & Analytics</h4>
                <ul>
                    <li>Employee statistics dashboard</li>
                    <li>Attendance and performance reports</li>
                    <li>Department summaries</li>
                    <li>Export to PDF/Excel</li>
                </ul>

                <h4>💬 Communication Features</h4>
                <ul>
                    <li>Internal messaging system</li>
                    <li>Company-wide announcement board</li>
                    <li>Email notifications</li>
                    <li>Meeting scheduler</li>
                </ul>

                <h4>📁 Project & Task Management</h4>
                <ul>
                    <li>Project assignment</li>
                    <li>Task tracking</li>
                    <li>Time tracking</li>
                    <li>Status updates</li>
                </ul>

                <h4>🛠️ Technical Improvements</h4>
                <ul>
                    <li>Unit and integration testing</li>
                    <li>Error logging and monitoring</li>
                    <li>API documentation (Swagger/OpenAPI)</li>
                    <li>Caching for performance</li>
                    <li>Data validation and sanitization</li>
                    <li>Pagination for large datasets</li>
                </ul>

                <h4>🎨 UI/UX Enhancements</h4>
                <ul>
                    <li>Dark/light theme support</li>
                    <li>Responsive design</li>
                    <li>Loading states and animations</li>
                    <li>Improved form validation</li>
                    <li>Data visualization with charts</li>
                </ul>

                <h4>➕ Additional Functionalities</h4>
                <ul>
                    <li>Calendar integration</li>
                    <li>Employee onboarding process</li>
                    <li>Training and development tracking</li>
                    <li>Asset and inventory management</li>
                    <li>Expense tracking</li>
                    <li>Payroll integration</li>
                </ul>

                <h4>🔒 Security Enhancements</h4>
                <ul>
                    <li>Rate limiting</li>
                    <li>Two-factor authentication (2FA)</li>
                    <li>Audit logging</li>
                    <li>Regular security updates</li>
                    <li>Data encryption at rest and in transit</li>
                </ul>

                <p>Would you like help implementing any of these features? I can guide you step-by-step.</p>
            </div>

        </div>
    )
}

export default ListEmployeeComponent;