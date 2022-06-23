// import React, {useEffect, useState} from 'react';
// import RentForm from './RentForm';
// import SearchForm from './SearchForm';
// import AuthService from '../services/auth.service';

// export default function HomePage() {
//     const [currentUser, setCurrentUser] = useState(undefined);
//     const [showModeratorBoard, setShowModeratorBoard] = useState(false);
//     const [showAdminBoard, setShowAdminBoard] = useState(false);
//     const [username, setUsername] = useState(undefined)

//     useEffect(() => {
//         const user = AuthService.getCurrentUser();

//         if (user) {
//             setCurrentUser(user)
//             setUsername(user.username)
//             setShowModeratorBoard(user.roles.includes("ROLE_MODERATOR"));
//             setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
//         }
//     }, []);

//     return (
//         <div className="Home">
//             <div className="welcome">
//                 <a>Welcome, 
//                     <span>
//                         {username}
//                     </span>
//                 </a>
//             </div>
//         </div>
//     )
// }

// //<h1> {userEmail} </h1>

// /* <div className="welcome">
// <label>Welcome, <span>{userEmail}</span></label>
// <button>Logout</button>
// </div> */
