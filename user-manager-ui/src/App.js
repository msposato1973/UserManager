import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import AddUser from './components/AddUser';
import UpdateUser from './components/UpdateUser';
import NavBar from './components/Navbar';
import UserDataList from './components/UserDataList';


function App() {
  return  (
    <>
     <BrowserRouter>
      <NavBar /> 
      <Routes>
        <Route index element={<UserDataList/>} />
        <Route path="/" element={<UserDataList/>} /> 
        <Route path="/userDataList" element={<UserDataList/>} /> 
        <Route path="/addUserData" element={<AddUser/>} /> 
        <Route path="/editUserData/:id" element={<UpdateUser/>} />
      </Routes>
     </BrowserRouter>
  </>
  )
}

export default App;
