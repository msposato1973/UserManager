import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import UserDataService from "../services/UserDataService";
import UserData from "../components/UserData";
import ReactPaginate from "react-paginate";


const UserDataList = () => {
    const itemsPerPage = 5;
    const navigate = useNavigate();
    const [loading, setLoading ] = useState(true);
    const [usersData, setUsersData ] = useState(null);
    const [currentItems, setCurrentItems] = useState(1);
    const [items, setItems] = useState([]);
    const [totalItems, setTotalItems] = useState([]);
    
    const [pageCount, setPageCount] = useState([]);
    const [itemOffset, setItemOffset] = useState(0);
    
    const [searchQuery, setSearchQuery] =  useState("");
    const [page, setPage] = useState(1);
    const [pageNumber, setPageNumber] = useState(0);
    const pageVisited = pageNumber * itemsPerPage;

    const onChangeSearchQuery = (e) => {
        const searchQuery = e.target.value;
        setSearchQuery(searchQuery);
      };

    const findByQuery = () => {
        setPage(1);
        fetchComments(searchQuery);
    };

    const responseObject = (data) => {
        let count = data.count;
        let total = data.total;
        setTotalItems(total);
        let list = data.list;
        let offset = Math.ceil(total / itemsPerPage);

        setUsersData(list);
        setItemOffset(Math.ceil(total / itemsPerPage));
        setPageCount(offset);
        setItems(list);
   };

    useEffect(()=> {
        const fetchData = async() => {
            setLoading(true);
            try {
                const response = await UserDataService.allUserData();
                let data = await response.data;
                responseObject(data);
               
            } catch (error) {
                console.log(error);
            }
            setLoading(false);
        };
        fetchData();
    }, []);

   

    const changePage = async (data) => {
        console.log('data.selected');
        console.log(data.selected);
        setPage(data.selected + 1);
        setPageNumber(data.selected);
    };

    const handleSort = async (sortProperty) => {
        setPage(1);
        try {
            const response = await UserDataService.allUserDataFetchPagSort(searchQuery, pageNumber, itemsPerPage, sortProperty);
            let data = await response.data;
            responseObject(data);
        } catch (error) {
            console.log(error);
        }
      }

      
     
    const sortArray = sortProperty => {
        console.log('sortProperty');
        console.log(sortProperty);
       
        handleSort(sortProperty);
        
    };
     

    const fetchComments = async () => {
        try {
            const response = await UserDataService.allUserDataFetchPag(searchQuery, pageNumber, itemsPerPage);
            let data = await response.data;
            responseObject(data);
        } catch (error) {
            console.log(error);
        }
    };
    
    const editUser =  (e, id) => {
        e.preventDefault();
    }

    const deleteUser =  (e, id) => {
        e.preventDefault();
        UserDataService.deleteUserData(id).then((res) => {
           if(usersData){
                setUsersData((prevElement) => {
                    return prevElement.filter((userData) => userData.id !== id);
                });
           }
        });
    };

   

    return (
    <div className="container mx-auto my-8">
        <div className="heading">Welcome to the App - Company List users</div>  
        
        <div className="list row">
            <div className="col-md-8">
                <div className="input-group mb-3 search">
                    <div className="input-group-append">
                        <button onClick={()=>navigate("/addUserData")} className="h-10 rounded text-white bg-slate-600 hover:bg-slate-800 py-2  px-6 font-semibold">
                            Add UserData
                        </button>
                    </div>
                    <input type="text" className="form-control rounded" value={searchQuery || ""} placeholder="Search by ...." onChange={onChangeSearchQuery} />
                    <div className="input-group-append">
                        <button className="btn btn-outline-secondary"  type="button" onClick={findByQuery} >
                            Search
                        </button>
                    </div>
                </div>
            </div>
        </div>
        

        <div className="shadow">
            {!loading && (
                <table className="min-w-full table-hover">
                        <thead className= "thead">
                            <tr>
                                <th  className="text-left font-medium text-gray-500 uppercase tracking-wider py-3 px-6" key={'Id'} onClick={(e) => sortArray('Id')}>Id </th>
                                <th  className="text-left font-medium text-gray-500 uppercase tracking-wider py-3 px-6 " key={'firstName'} onClick={(e) => sortArray('firstName')} >First Name</th>
                                <th  className="text-left font-medium text-gray-500 uppercase tracking-wider py-3 px-6" key={'lastName'} onClick={(e) => sortArray('lastName')}>Last Name</th>
                                <th  className="text-left font-medium text-gray-500 uppercase tracking-wider py-3 px-6" key={'emailId'} onClick={(e) => sortArray('emailId')}>E-mail id</th>
                                <th  className="text-right font-medium text-gray-500 uppercase tracking-wider py-3 px-6">Actions</th>
                            </tr>
                        </thead>
                        <tbody className="bg-white">
                            {usersData.slice(pageVisited, pageVisited + itemsPerPage).map((userData) => (
                                    <UserData userData={userData} deleteUser={deleteUser} key={userData.id}></UserData>
                            ))}
                        </tbody>
                </table>
            )}
        </div>
       
        <div>
                {!loading ? (
                <div className="flex shadow border-b">
                    <ReactPaginate
                        previousLabel="< previous"
                        nextLabel="next >"
                        breakLabel={"...."}
                        breakClassName={"break-me"}
                        pageCount={pageCount}
                        onPageChange={changePage}
                        containerClassName={"pagination justify-content-center"}
                        previousLinkClassName={"page-link"}
                        nextClassName={"page-item"}
                        disabledClassName={"paginationDisable"}
                        activeClassName={"paginationActive"}
                        pageRangeDisplayed={itemsPerPage}
                        displayedPageRange = {itemsPerPage}  
                        renderOnZeroPageCount={null}
                        subContainerClassName="pages pagination"
                        pageClassName={"page-item"}
                        pageLinkClassName={"page-link"}
                        previousClassName={"page-item"}
                        nextLinkClassName={"page-link"}
                        breakLinkClassName={"page-link"}
                        forcePage={currentItems}
                    />
   
                <div className="font-thin text-2xl tracking-wider justify-content-center">
                        <div className="text-inherit font-small px-20">Page to display {page} of {itemOffset} -  tot. records: {totalItems}</div>
                    </div>  
                </div>
             ) : (
                <div className="px-8 py-8">
                    <div className="font-thin text-2xl tracking-wider">
                        <div>Nothing to display</div>
                    </div>
				</div>
			)} 
        </div> 
       
    </div>
    );
} 
export default UserDataList;