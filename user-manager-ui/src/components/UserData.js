import React from "react";
import { useNavigate } from "react-router-dom";

const UserData = ({userData, deleteUser}) => {

    const navigate = useNavigate();

    const editUser =  (e, id) => {
        e.preventDefault();
        navigate(`/editUserData/${id}`);
    };

    return  (
        <tr key={userData.id}>
            <td  className="text-left py-4 px-6 whitespace-nowrap">
                <div className="text-sm text-gray-500">{userData.id}</div>
            </td>
            <td  className="text-left py-4 px-6 whitespace-nowrap">
                <div className="text-sm text-gray-500">{userData.firstName}</div>
            </td>
            <td  className="text-left py-4 px-6 whitespace-nowrap">
                <div className="text-sm text-gray-500">{userData.lastName}</div>
            </td>
            <td  className="text-left py-4 px-6 font-normal whitespace-nowrap">
                <div className="text-sm text-gray-500">{userData.emailId}</div>
            </td>
            <td className="text-right py-4 px-6 whitespace-nowrap font-medium tx-sm">
                <div class="btn-group" role="group" aria-label="Basic example">
                    <a  onClick={(e, id) => editUser(e, userData.id)} className="text-indigo-600 hover:text-indigo-800 px-4 hover:cursor-pointer">Edit</a>
                    <a  onClick={(e, id) => deleteUser(e, userData.id)}  className="text-indigo-600 hover:text-indigo-800 hover:cursor-pointer">Delete</a>
                </div>
            </td>
        </tr>
      )
}

export default UserData;