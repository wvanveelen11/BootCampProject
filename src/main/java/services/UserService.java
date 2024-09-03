package services;

import models.User;
import models.Wallet;

public class UserService {
    public static void UserRegistration(String name, String email, String password){
        int min = Integer.MIN_VALUE;
        for(int i = 0; i < AppService.data.getListUsers().size(); i++){
            if(AppService.data.getListUsers().get(i).getId() > min){
                min = AppService.data.getListUsers().get(i).getId();
            }
        }
        User user = new User(min + 1, name, email, password, new Wallet());
        AppService.data.getListUsers().add(user);
        System.out.println("Registro exitoso: " + user.toString());
    }

    public static boolean UserLogin(String name, String password){
        for(int i = 0; i < AppService.data.getListUsers().size(); i++){
            if(AppService.data.getListUsers().get(i).getName().equals(name) && AppService.data.getListUsers().get(i).getPassword().equals(password)){
                AppService.data.setUser(AppService.data.getListUsers().get(i));
                return true;
            }
        }
        return false;
    }

}
