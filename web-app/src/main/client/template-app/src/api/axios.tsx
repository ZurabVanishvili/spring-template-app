import axios from "axios";
import {BASE_REST_URL} from "../AppUtil";


const instance = axios.create({
    baseURL: BASE_REST_URL,
    timeout: 30_000
})


export default instance;