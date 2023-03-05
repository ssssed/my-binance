import axios from "axios";

class AxiosService {

  #baseUrl = process.env.VUE_APP_BASE_API_URL;
  #axios = axios;
  #headers = {
    'Content-Type': 'application/json',
    'Connection': 'keep-alive',
    'Access-Control-Allow-Origin': '*',
  };

  async get(url, data) {
    return await this.#axios.get(this.#baseUrl + url,
        data, this.#headers);
  }

  async post(url, data) {
    return await this.#axios.post(this.#baseUrl + url, data, this.#headers);
  }

  async patch(url, data) {
    return await this.#axios.patch(this.#baseUrl + url,
        data, this.#headers);
  }

  async delete(url, data) {
    return await this.#axios.delete(this.#baseUrl + url,
        data, this.#headers);
  }
}

export const axiosService = new AxiosService();