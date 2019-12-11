import axios from 'axios';
import ls from 'local-storage'


class ApiService{

    login(user){
        axios.post("http://localhost:8092/token",user)
        .then(res => ls.set('token', res.data))
        .catch(err => console.error(err))
    }
}
export default new ApiService();