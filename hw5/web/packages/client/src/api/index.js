import axios from "axios";
import httpStatusCodes from "http-status-codes";

let instance = null;

const getInstance = () => {
  if (!instance) {
    instance = axios.create({
      baseURL: "/api", // package.json specifies the proxy to the server
      headers: {
        "Content-Type": "application/json"
      },
      withCredentials: true
    });
  }
  return instance;
};

class ApiError extends Error {}

const buildApiError = (error) => {
  if (error.response) {
    return new ApiError(httpStatusCodes.getStatusText(error.response.status));
  } else {
    return new ApiError("Client or network errored");
  }
};

export const search = async (query, type) => {
  try {
    const response = await getInstance().get("/search", {
      params: {
        query,
        type
      }
    });
    return response.data;
  } catch (error) {
    console.log(error);
    throw buildApiError(error);
  }
};

export const suggest = async (query, type) => {
  try {
    const response = await getInstance().get("/suggest", {
      params: {
        query,
        type
      }
    });
    return response.data;
  } catch (error) {
    console.log(error);
    throw buildApiError(error);
  }
};

export const Api = {
  search,
  suggest
};

export const ApiStatus = {
  INITIAL: "INITIAL",
  LOADING: "LOADING",
  SUCCESS: "SUCCESS",
  ERROR: "ERROR"
};
