import axios from "axios";


const USERDATA_API_BASE = "http://localhost:8080/api/v1/";
const USERDATA_API_BASE_URL = "http://localhost:8080/api/v1/userData";
const PAGINATIO_API_URL = "http://localhost:8080/api/v1/usersPage";
const USERDATA_API_ALL_URL = "http://localhost:8080/api/v1/users";

class UserDataService {


    saveDataService(userData){
        return  axios.post(USERDATA_API_BASE_URL, userData);
    }

    allUserData(){
        return  axios.get(USERDATA_API_ALL_URL);
    }


    deleteUserData(id){
        return  axios.delete(USERDATA_API_BASE + "delUserData/" + id);
    }

    getUserDataById(id){
        return  axios.get(USERDATA_API_BASE + "userById/" + id);
    }

    updateUser(userData, id){
        return  axios.put(USERDATA_API_BASE + "updateUserData/" + id, userData);
    }

    countBeforeInsert(userData){
        return  axios.get(USERDATA_API_BASE + "countUserData/", {
            params: {
              firstName: userData.firstName,
              lastName: userData.lastName,
              email: userData.email
            },
          });  
    }

    allUserDataFetchPag(searchQuery, page, pageSize){
        return axios.get(PAGINATIO_API_URL, {
            params: {
              pageNo: page,
              pageSize: pageSize,
              query: searchQuery
            },
          });  
    }

    allUserDataFetchPagSort(searchQuery, page, pageSize, sortBy){
        return axios.get(PAGINATIO_API_URL, {
            params: {
              pageNo: page,
              pageSize: pageSize,
              query: searchQuery,
              sordBy: sortBy
            },
          });  
    }
}
export default new UserDataService();