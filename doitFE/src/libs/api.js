import axios from "axios";
import configData from "../config/config.json";

const API_URL = configData.SERVER_URL;

let $TOKEN = "";
let $USER = "";

const parseResponse = (res) => {
    if (res.success) {
        return res.data;
    }
    if (res.error) {
        throw new Error(res.error);
    }
    throw new Error("Invalid respondse")
}

export default {

    get: async (path) => {
        const result = await axios.get(API_URL + path, {
            headers: { "X-Auth": $TOKEN }
        });
        return parseResponse(result.data);
    },
    put: async (path, data) => {
        const result = await axios.put(API_URL + path, data, {
            headers: { "X-Auth": $TOKEN }
        });
        return parseResponse(result.data);
    },
    delete: async (path) => {
        const result = await axios.delete(API_URL + path, {
            headers: { "X-Auth": $TOKEN }
        });
        return parseResponse(result.data);
    },
    post: async (path, data) => {
        const result = await axios.post(API_URL + path, data, {
            headers: { "X-Auth": $TOKEN }
        });
        return parseResponse(result.data);
    },
    login: async (data) => {
        const result = await axios.post(API_URL + "/public/login", data);
        $TOKEN = result.data.token;
        $USER = result.data.username;
    },
    getUtente: () => {
        return $USER;
    }
}
