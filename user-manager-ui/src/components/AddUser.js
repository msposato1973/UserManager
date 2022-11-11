import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import UserDataService from "../services/UserDataService";

const AddUser = () => {
    const navigate = useNavigate();
    const [messageFailure, setMessageFailure] =  useState("");

    const [errorFirstName, setErrorFirstName] = useState(false);
    const [errorSurname, setErrorSurname] = useState(false);
    const [errorEmail, setErrorEmail] = useState(false);
    const emailRegex = /\S+@\S+\.\S+/;
    
    const [exist, setExist] = useState(0);

    const [userData, setUserData ] = useState({
        id: "",
        firstName: "",
        lastName: "",
        emailId: "",
     });

       
      function validateEmail(email) { 
        const value = emailRegex.test(email);
        return value;
      }
       

     const handlechange = (e) =>{
        const targetName = e.target.name ;
        const value = e.target.value;
  
       setUserData({...userData, [targetName]: value });
     }

      

     async function  veryfyData(userData) {
        console.log('veryfyData');
        const result = await beforeSave(userData);
        console.log('result');
        console.log(result);
        setExist(result);
        console.log('result');
        return result;
      }
      
     const beforeSave = async (userData) => {
       const result = 0;
        try {
            const response = await UserDataService.countBeforeInsert(userData);
            let data = await response.data;
            result = data.count;
            
            return result ; 
        } catch (error) {
            console.log(error);
            return result;
        }
      }

     const saveUserData = (e) =>{
        e.preventDefault();
      
        const data =   veryfyData(userData);
        console.log(data);

        
        console.log(data);
        setMessageFailure("");
        if(exist==0){
                UserDataService.saveDataService(userData).then((response) => {
                    console.log(response);
                    navigate("/userDataList");
                }).catch((error) => {
                    console.log(error);
                })
        } else {
            setMessageFailure("User already exist ");
        }
     };

     const resetUserData = (e) =>{
        e.preventDefault();
        setUserData({
            id: 0,
            firstName: "",
            lastName: "",
            emailId: "",
         });

         setMessageFailure("");
     };

    return <div className="container mx-auto my-8">
                <div className="heading">Welcome to the App - Company List users</div>
                    <div className="flex max-w-2xl mx-auto shadow border-bottom">
                            <div className="px-8 py-8">
                                <div className="font-thin text-2xl tracking-wider">
                                    <h1>Add New User</h1>
                                </div>
                                <div className="item-center justify-center h-14 w-full my-4">
                                    <label className="block text-gray-600 text-small font-normal">First Name:</label>
                                    <input name = "firstName" value={userData.firstName} type="text"  onChange={(e) => handlechange(e)}
                                    className="h-10 w-96 border mt-2 px-2 py-2 text-gray-600 text-small font-normal"></input>
                                </div>
                                <div className="item-center justify-center h-14 w-full my-4">
                                    <label className="block text-gray-600 text-small font-normal">Last Name:</label>
                                    <input name = "lastName" value={userData.lastName} type="text"  onChange={(e) => handlechange(e)} 
                                    className="h-10 w-96 border mt-2 px-2 py-2 text-gray-600 text-small font-normal"></input>
                                </div>
                                <div className="item-center justify-center h-14 w-full my-4">
                                    <label className="block text-gray-600 text-small font-normal">E-Mail:</label>
                                    <input name = "emailId" value={userData.emailId} type="email"  onChange={(e) => handlechange(e)} 
                                    className="h-10 w-96 border mt-2 px-2 py-2 text-gray-600 text-small font-normal"></input>
                                </div>

                                <div className="item-center justify-center h-14 w-full my-4 space-x-4 pt-4">
                                <button onClick={saveUserData} className="rounded text-white font-semibold bg-green-400 hover:bg-green-800 py-2 px-6">Save</button>    
                                <button onClick={resetUserData} className="rounded text-white font-semibold bg-red-400 hover:bg-red-800 py-2 px-6">Clear</button>  
                            </div>
                    </div>
                     {messageFailure && (
                     <div className="error-summary">{messageFailure}</div>
                    )}
                </div>
               
            </div>
}

export default AddUser;