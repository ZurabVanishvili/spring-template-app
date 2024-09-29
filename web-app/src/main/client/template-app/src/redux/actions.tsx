import * as types from "./actionTypes";
import axios from "../api/axios";

export interface anonymousConfigType {
  authType: string;
}

export const loadConfigSuccess = (resp: anonymousConfigType) => {
  return {
    type: types.LOAD_SETTINGS,
    config: resp,
  };
};

export const loadAnonymousConfig = () => {
  return async function (
    dispatch: (arg0: { type: string; config: anonymousConfigType }) => void
  ) {
    try {
      const resp = await axios.get("/anonymous/config");
      dispatch(loadConfigSuccess(resp.data));
    } catch (err) {
      console.error(err);
    }
  };
};
