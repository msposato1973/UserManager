import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import UserDataService from "../services/UserDataService";

const UpdateUser = () =>{
    const navigate = useNavigate();

    const { id } = useParams();
    const [userData, setUserData ] = useState({
        id: id,
        firstName: "",
        lastName: "",
        emailId: "",
     });

     const handlechange = (e) =>{
        const value = e.target.value;
        setUserData({...userData, [e.target.name]: value });
     }

     useEffect(()=> {
        const fetchData = async() => {
            try {
                const response = await UserDataService.getUserDataById(id);
                setUserData(response.data);
            } catch (error) {
                console.log(error);
            }
            
        };
        fetchData();
    }, []);

    const updateUserData = (e) =>{
        e.preventDefault();
        UserDataService.updateUser(userData, id)
        .then((response) => {
            navigate("/userDataList");
        })
        .catch((error) => {
            console.log(error);
        })
     };


     const resetUserData = (e) =>{
        e.preventDefault();
        setUserData({
            id: 0,
            firstName: "",
            lastName: "",
            emailId: "",
         });
     };

   

    return  <div className="container mx-auto my-8">
                <div className="heading">Welcome to the App - Company List users</div>
                <div className="flex max-w-2xl mx-auto shadow border-bottom">
                        <div className="px-8 py-8">
                            <div className="font-thin text-2xl tracking-wider">
                                <h1>Update User details</h1>
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
                            <button onClick={updateUserData} className="rounded text-white font-semibold bg-green-400 hover:bg-green-800 py-2 px-6">Update</button>    
                            <button onClick={() =>navigate("/userDataList")} className="rounded text-white font-semibold bg-red-400 hover:bg-red-800 py-2 px-6">Cancel</button>  
                        </div>
                    </div>
                </div>
            </div> 
}

export default UpdateUser;