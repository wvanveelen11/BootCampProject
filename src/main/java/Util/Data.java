package Util;

import models.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Data implements Serializable{
    private ArrayList<User> ListUsers = new ArrayList<>();
    private User user = new User();
    private ArrayList<CryptoCurrency> ListCrypto = new ArrayList<>();
    private ArrayList<Order> OrderBook = new ArrayList<>();

    public Data() {
        //startListCrypto();
    }
    
    public void startListCrypto() {
        ListCrypto = new ArrayList<>(){{
            add(new CryptoCurrency(1,"BTC","Bitcoin",new BigDecimal("60000"),new BigDecimal("200")));
            add(new CryptoCurrency(2,"ETH","Ethereum",new BigDecimal("30000"),new BigDecimal("150")));
        }};
        ArrayList<CrytoWallet> usercryto = new ArrayList<>(){{
            add(new CrytoWallet(ListCrypto.get(0), new BigDecimal("8")));
            add(new CrytoWallet(ListCrypto.get(1), new BigDecimal("12")));
        }};
        Wallet wa = new Wallet(new BigDecimal("60000"), usercryto);
        ListUsers = new ArrayList<>(){{
            add(new User(1,"ariel","ariel.garcia@gmail.com","123", new Wallet()));
            add(new User(2,"erick","erick.garcia@gmail.com","123", wa));
        }};
        OrderBook = new ArrayList<>(){{
            add(new Order(1,2,ListCrypto.get(1),"SELL", new BigDecimal("10"),new BigDecimal("27000"),new Date()));
            add(new Order(2,2,ListCrypto.get(0),"BUY", new BigDecimal("2"),new BigDecimal("24000"),new Date()));
            add(new Order(3,2,ListCrypto.get(0),"SELL", new BigDecimal("5"),new BigDecimal("59000"),new Date()));
            add(new Order(4,2,ListCrypto.get(0),"BUY", new BigDecimal("13"),new BigDecimal("61000"),new Date()));
        }};
    }

    public String printCryto(){
        String res = "";
        for (int i = 0; i < ListCrypto.size(); i++) {
            res = res + "\n" + "["+ ( i + 1 ) +"]" +  ListCrypto.get(i).show();
        }
        return res;
    }
    

    public String printOrderSell(){
        String res = "";
        for (int i = 0; i < OrderBook.size(); i++) {
            if(OrderBook.get(i).getType().equals("SELL")){
                res = res + "\n" + "["+ ( i + 1 ) +"]" +  OrderBook.get(i).toString();
            }
        }
        return res;
    }
    
    public String printOrders(){
        String res = "";
        for (int i = 0; i < OrderBook.size(); i++) {
            res = res + "\n" + "["+ ( i + 1 ) +"]" +  OrderBook.get(i).toString();
        }
        return res;
    }



    public ArrayList<CryptoCurrency> getListCrypto() {
        return ListCrypto;
    }

    public void setListCrypto(ArrayList<CryptoCurrency> listCrypto) {
        ListCrypto = listCrypto;
    }

    public ArrayList<User> getListUsers() {
        return ListUsers;
    }

    public void setListUsers(ArrayList<User> listUsers) {
        ListUsers = listUsers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void buyCrytoExchange(int Cryto, BigDecimal amount){
        int pos = Cryto - 1;
        if(ListCrypto.get(pos) != null){
            if(ListCrypto.get(pos).getAvailable().compareTo(amount) > 0){
                BigDecimal tot = ListCrypto.get(pos).getPrice().multiply(amount);
                if(tot.compareTo(user.getWallet().getMoney()) <= 0){

                    int min = Integer.MIN_VALUE;
                    for(int i = 0; i < OrderBook.size(); i++){
                        if(OrderBook.get(i).getId() > min){
                            min = OrderBook.get(i).getId();
                        }
                    }
                    OrderBook.add(new Order(min, user.getId(), ListCrypto.get(pos), "BUY", amount, ListCrypto.get(pos).getPrice(), new Date()));
                    ListCrypto.get(pos).setAvailable(ListCrypto.get(pos).getAvailable().subtract(amount));
                    user.getWallet().insertCryto(ListCrypto.get(pos), amount);
                    user.getWallet().setMoney(user.getWallet().getMoney().subtract(tot));
                    System.out.println("Operacion exitosa");
                }else
                    System.out.println("Sin fondos necesarios para la operacion");

            }else
                System.out.println("No hay la cantidad de Cryto monedas solicitadas");
        }
    }

    
    //pide la pos de la cryto en la ListCrypto y el monto que el usuario quiere comprar
    public void buyCrytoCurrency(int Cryto, BigDecimal amount, BigDecimal userPrice){
        int pos = Cryto - 1;
        if(ListCrypto.get(pos) != null){
            CryptoCurrency cryptoCurrency = ListCrypto.get(pos);
            if(cryptoCurrency.getAvailable().compareTo(amount) > 0){
                BigDecimal tot = cryptoCurrency.getPrice().multiply(amount);
                if(tot.compareTo(user.getWallet().getMoney()) <= 0){
                    int id = generateID(OrderBook);
                    //tomando en cuenta el precio unitario de la moneda en el mercado y no el total(monto*precioUsuario)
                    if(userPrice.compareTo(cryptoCurrency.getPrice()) >= 0){
                        cryptoCurrency.setAvailable(cryptoCurrency.getAvailable().subtract(amount));
                        user.getWallet().insertCryto(cryptoCurrency, amount);
                        user.getWallet().setMoney(user.getWallet().getMoney().subtract(tot));
                        System.out.println("Operacion exitosa");
                    }else{
                        //estoy comprando al usuario que tiene la cripto
                        int userid = find(cryptoCurrency, "SELL", userPrice);
                        if(userid > 0){
                            User us = getUserID(userid);
                            us.getWallet().subtractCryto(cryptoCurrency, amount);
                            us.getWallet().setMoney(us.getWallet().getMoney().add(userPrice));
                            //ListCrypto.get(pos).setAvailable(ListCrypto.get(pos).getAvailable().add(amount));
                            user.getWallet().insertCryto(cryptoCurrency, amount);
                            user.getWallet().setMoney(user.getWallet().getMoney().subtract(userPrice.multiply(amount)));
                            System.out.println("Oferta encontrada en el libro de ordenes. Operacion exitosa");
                        }else{
                            OrderBook.add(new Order(id, user.getId(), findCrytoCurrency(ListCrypto.get(pos).getName()), "BUY", amount, userPrice, new Date()));
                            System.out.println("Registrada en el libro de ordenes\n" + OrderBook.toString());
                        }
                    }
                }else
                    System.out.println("Sin fondos necesarios para la operacion");

            }else
                System.out.println("No hay la cantidad de Cryto monedas solicitadas");
        }
    }
    
    //pide la pos de la cryto en la ListCrypto y el monto que el usuario quiere comprar
    public void sellCrytoCurrency(int Cryto, BigDecimal amount, BigDecimal userPrice){
        int pos = Cryto - 1;
        if(ListCrypto.get(pos) != null){
            CryptoCurrency cryptoCurrency = ListCrypto.get(pos);
            if(cryptoCurrency.getAvailable().compareTo(amount) > 0){
                BigDecimal tot = cryptoCurrency.getPrice().multiply(amount);
                if(tot.compareTo(user.getWallet().getMoney()) <= 0){
                    int id = generateID(OrderBook);
                    //tomando en cuenta el precio unitario de la moneda en el mercado y no el total(monto*precioUsuario)
                    if(userPrice.compareTo(cryptoCurrency.getPrice()) >= 0){
                        cryptoCurrency.setAvailable(cryptoCurrency.getAvailable().subtract(amount));
                        user.getWallet().insertCryto(cryptoCurrency, amount);
                        user.getWallet().setMoney(user.getWallet().getMoney().subtract(tot));
                        System.out.println("Operacion exitosa");
                    }else{
                        OrderBook.add(new Order(id, user.getId(), cryptoCurrency, "BUY", amount, userPrice, new Date()));
                        System.out.println("Registrada en el libro de ordenes\n" + OrderBook.toString());
                    }
                }else
                    System.out.println("Sin fondos necesarios para la operacion");

            }else
                System.out.println("No hay la cantidad de Cryto monedas solicitadas");
        }
    }
    
    private <T extends Identity> int generateID(ArrayList<T> list){
        int min = Integer.MIN_VALUE;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getId() > min){
                min = list.get(i).getId();
            }
        }
        return 0;
    }
    
    /*public static void buyCryto(int Cryto, BigDecimal amount){
        int pos = Cryto - 1;
        if(OrderBook.get(pos) != null){
            if(OrderBook.get(pos).getCryptoCurrency().getAvailable().compareTo(amount) > 0){
                BigDecimal var = OrderBook.get(pos).getPrice().multiply(amount);
                if(var.compareTo(user.getWallet().getMoney()) <= 0){

                    int min = Integer.MIN_VALUE;
                    for(int i = 0; i < OrderBook.size(); i++){
                        if(OrderBook.get(i).getId() > min){
                            min = OrderBook.get(i).getId();
                        }
                    }

                    //OrderBook.add(new Order(min, user.getId(), ListCrypto.get(pos), "BUY", amount, ListCrypto.get(pos).getPrice(), new Date()));
                    OrderBook.get(pos).setCryptoAmount(OrderBook.get(pos).getCryptoAmount().subtract(amount));
                    user.getWallet().insertCryto(OrderBook.get(pos).getCryptoCurrency(), amount);
                    user.getWallet().setMoney(user.getWallet().getMoney().subtract(var));
                    clearBook();
                    System.out.println("Operacion exitosa");

                    

                    if(maxPrice.compareTo(var) >= 0){
                        ListCrypto.get(pos).setAvailable(ListCrypto.get(pos).getAvailable().subtract(amount));

                        user.getWallet().insertCryto(ListCrypto.get(pos), amount);

                        //falta verificar en el caso que ya este ingresada una cryto porque no suma si no que lo incializa otra vez
                        user.getWallet().setMoney(user.getWallet().getMoney().subtract(ListCrypto.get(pos).getPrice()));
                        System.out.println("Operacion exitosa");
                    }else
                        System.out.println("Registrada en el libro de ordenes\n" + OrderBook.toString());
                }else
                    System.out.println("Sin fondos necesarios para la operacion");

            }else
                System.out.println("No hay la cantidad de Cryto monedas solicitadas");
        }
    }*/

    public void sellCryto(CrytoWallet Cryto, BigDecimal amount, BigDecimal minPrice){

        if(Cryto != null){
            if(Cryto.getUserCount().compareTo(amount) >= 0){
                if(Cryto.getPrice().compareTo(minPrice) >= 0){
                    Cryto.setAvailable(Cryto.getAvailable().add(amount));
                    user.getWallet().subtractCryto(Cryto, amount);
                    user.getWallet().setMoney(user.getWallet().getMoney().add(minPrice.multiply(amount)));
                    System.out.println("Operacion exitosa");
                    /*if(find(Cryto, "BUY", minPrice)){
                        Cryto.setAvailable(Cryto.getAvailable().add(amount));
                        user.getWallet().subtractCryto(Cryto, amount);
                        user.getWallet().setMoney(user.getWallet().getMoney().add(Cryto.getPrice()));
                        System.out.println("Operacion exitosa");
                    }else{
                        int id = generateID(OrderBook);
                        OrderBook.add(new Order(id, user.getId(), findCrytoCurrency(Cryto.getName()), "SELL", amount, minPrice, new Date()));
                        System.out.println("Registrada en el libro de ordenes\n" + OrderBook.toString());
                    }*/
                }else{
                    
                    if((find(Cryto, "BUY", minPrice) > 0)){
                        Cryto.setAvailable(Cryto.getAvailable().add(amount));
                        user.getWallet().subtractCryto(Cryto, amount);
                        user.getWallet().setMoney(user.getWallet().getMoney().add(Cryto.getPrice()));
                        System.out.println("Operacion exitosa");
                    }else{
                        int id = generateID(OrderBook);
                        OrderBook.add(new Order(id, user.getId(), findCrytoCurrency(Cryto.getName()), "SELL", amount, minPrice, new Date()));
                        System.out.println("Registrada en el libro de ordenes\n" + OrderBook.toString());
                    }
                }
            }else
                System.out.println("Sin cryptos suficientes");
        }
    }

    private CryptoCurrency findCrytoCurrency(String name){
        for (int i = 0; i < ListCrypto.size(); i++) {
            if(ListCrypto.get(i).getName().equals(name)){
                return ListCrypto.get(i);
            }
        }
        return null;
    }
    
    private int find(CryptoCurrency cc, String type, BigDecimal userPrice){
        for (int i = 0; i < OrderBook.size(); i++) {
            if(OrderBook.get(i).getCryptoCurrency().getName().equals(cc.getName()) 
                    && OrderBook.get(i).getPrice().equals(userPrice) && OrderBook.get(i).getType().equals(type)){
                return OrderBook.get(i).getUserId();
            }
        }
        return -1;
    }
    
    private User getUserID(int id){
        for (int i = 0; i < ListUsers.size(); i++) {
            if(ListUsers.get(i).getId() == id){
                return ListUsers.get(i);
            }
        }
        return null;
    }
    
    private void clearBook(){
        for (Order order : OrderBook){
            if(order.getCryptoAmount().compareTo(BigDecimal.ZERO) <= 0 ){
                OrderBook.remove(order);
                return;
            }
        }
    }

}
