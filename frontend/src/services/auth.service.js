import axios from "axios";
import authHeader from "./authHeader";

const API_URL = "http://localhost:8080/api/auth/";
const API_URL_UPDATE = "http://localhost:8080/api/users/";

const register = (username, email, password) => {
    return axios.post(API_URL + "signup", {
        username,
        email,
        password,
    });
};

const login = async (username, password) => {
    const response = await axios
        .post(API_URL + "signin", {
            username,
            password,
        });
    // console.log(response.data)
    if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
    }
    return response.data;
};

const updateProfile = async (currentUsername, username, email, password) => {
    const response = await axios
        .post(API_URL_UPDATE + "updateProfile", {
            currentUsername,
            username,
            email,
            password,
        }, {headers: authHeader()});
    // console.log(response.data)
    if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
    }
    return response.data;
};

const updatePassword = async (username, currentPassword, newPassword) => {
    const response = await axios
        .post(API_URL_UPDATE + "updatePassword", {
            username,
            currentPassword,
            newPassword
        }, {headers: authHeader()});
    console.log(response.data);
    if (response.data.message == 'GIT') {
        await login(username, newPassword);
    }
    return response.data;
};


const logout = () => {
    localStorage.removeItem("user");
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
    register,
    login,
    updateProfile,
    updatePassword,
    logout,
    getCurrentUser,
};

export default AuthService;
