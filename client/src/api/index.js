import axios from "axios";

class AxiosService {

  #baseUrl = process.env.VUE_APP_BASE_API_URL;
  #axios = axios;
  #headers = {
    Accept: 'application/json, text/plain, */*',
    'Content-Type': 'application/json',
    'X-Requested-With': 'XMLHttpRequest',
    'Access-Control-Allow-Origin': '*',
  };

  async get(url, data) {
    return await this.#axios.get(this.#baseUrl + url, {
      data
    });
  }

  async post(url, data) {
    console.log(this.#baseUrl);
    return await this.#axios.post(this.#baseUrl + url, {
      data
    });
  }

  async patch(url, data) {
    return await this.#axios.patch(this.#baseUrl + url, {
      data
    });
  }

  async delete(url, data) {
    return await this.#axios.delete(this.#baseUrl + url, {
      data
    });
  }
}

export const axiosService = new AxiosService();