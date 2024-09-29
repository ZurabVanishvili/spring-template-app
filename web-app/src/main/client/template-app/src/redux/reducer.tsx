import * as types from "./actionTypes";
import { anonymousConfigType } from "./actions";

const INITIAL_STATE = {
  config: { authType: "" },
};

interface Action {
  type: string;
  config: anonymousConfigType;
}

export function reducer(state = INITIAL_STATE, action: Action) {
  switch (action.type) {
    case types.LOAD_SETTINGS:
      return {
        ...state,
        config: action.config,
      };

    default:
      return { ...state };
  }
}

export default reducer;
