import './App.css'

import {BrowserRouter, Route, Routes} from 'react-router-dom'

import EmployeeComponent from './components/EmployeeComponent'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListEmployeeComponent from './components/ListEmployeeComponent'
import ViewEmployeeComponent from './components/ViewEmployeeComponent'

function App() {

  return (
    <> 
      <BrowserRouter>
        <HeaderComponent />

          <Routes>
            <Route path='/' element = { <ListEmployeeComponent />}></Route>  {/* http://localhost:3000/ */}
            <Route path='/employees' element = { <ListEmployeeComponent />}></Route>
            <Route path='/add-employee' element = { <EmployeeComponent />}></Route>
            <Route path='/edit-employee/:id' element = { <EmployeeComponent />}></Route>
            <Route path='/view-employee/:id' element = { <ViewEmployeeComponent />}></Route>  
          </Routes>

        <FooterComponent />
      </BrowserRouter>  
    </>  
    // the fragment tag
  )
}

export default App