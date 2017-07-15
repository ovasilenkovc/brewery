import qs from 'qs';
import 'isomorphic-fetch';


export const ENDPOINT = {};

export function getDefaultOptions(token, params) {
  const headers = new Headers();

  headers.append('Authorization', token);
  if (params) {
    if (params.headers) {
      params.headers.forEach((item) => {
        headers.append(item.key, item.value);
      });
    }
  }

  return {
    ...params,
    headers: headers
  };
}

export function buildParams(endpoint, params) {
  return `${endpoint}?${qs.stringify(params, { encode: false })}`;
}

const callApi = (endpoint, token, headers, params) => {
  const builtOptions = getDefaultOptions(token, headers);
  const finaleEndpoint = (params) ? buildParams(endpoint, params) : endpoint;

  return fetch(finaleEndpoint, builtOptions)
    .then(response =>
      response.json().then(json => ({ json, response }))
    )
    .then(({ json, response }) => {
      if (!response.ok) {
        return Promise.reject(json);
      }
      return json;
    })
    .then(
      response => ({ response }),
      error => ({ error: error.errors || 'Something bad happened.' })
    );
};

export default callApi;
