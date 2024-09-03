package org.main;

import services.AppService;
import services.UserService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static boolean exit = false;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        AppService.loadData();
        //util.startListCrypto();
        
        do {

            System.out.println("\nBienvenido\nEscoge una opcion\n[1] Registrarse" +
                    "\n[2] Login\n[3] Salir\n");
            String response = input.nextLine();
            String name, email, password;
            switch (response){
                case "1":

                    System.out.println("Ingrese nombre");
                    name = input.nextLine();
                    System.out.println("Ingrese correo");
                    email = input.nextLine();
                    System.out.println("Ingrese password");
                    password = input.nextLine();
                    UserService.UserRegistration(name, email, password);
                    break;
                case "2":
                    System.out.println("Ingrese nombre de usuario");
                    name = input.nextLine();
                    System.out.println("Ingrese password");
                    password = input.nextLine();
                    if(UserService.UserLogin(name, password))
                        Options();
                    else
                        System.out.println("Datos erroneos");
                    break;
                case "3":
                    exit = true;
                    break;
                default:

            }
        }while (!exit);

    }


    private static void Options(){
        boolean exitOptions = false;
        do {

            System.out.println("\nBienvenido " + AppService.data.getUser().getName() +"\nEscoge una opcion\n[1] Revisar mercado" +
                    "\n[2] Comprar\n[3] Vender\n[4] Tu Wallet\n[5] Depositar\n[6] Restart\n[7] Libro de ordenes\n[8] Logout");
            String response = input.nextLine();
            switch (response){
                case "1":
                    OptionsExchange();
                    break;
                case "2":
                    BuyOptions();
                    break;
                case "3":
                    SellOptions();
                    break;
                case "4":
                    System.out.println(AppService.data.getUser().getWallet().toString());
                    break;
                case "5":
                    DepositOptions();
                    break;
                case "6":
                    //TransactionHistoryOptions();
                    AppService.data.startListCrypto();
                    System.out.println("Reinicio de datos completado..");
                    break;
                case "7":
                    BookOrdersOptions();
                    break;
                case "8":
                    AppService.saveData();
                    exitOptions = true;
                    break;
                default:
            }
        }while (!exitOptions);
    }
    
    private static <T> String printList(ArrayList<T> list){
        String res = "";
        for (int i = 0; i < list.size(); i++) {
            res += "\n["+ ( i + 1 ) +"] " + list.get(i).toString();
        }
        return res;
    }
    
    private static void OptionsExchange(){
        System.out.println("Mercado:" + printList(AppService.data.getListCrypto()) + "[0] Salir\n Seleccione una opcion:");
        int response = Integer.parseInt(input.nextLine());
        if(response == 0)
            return;
        System.out.println("\nIngrese cantidad a comprar:");
        String amount = input.nextLine();
        AppService.data.buyCrytoExchange(response, new BigDecimal(amount));
    }
    
    private static void BuyOptions(){
        System.out.println("[Compra de Crytos]:" + printList(AppService.data.getListCrypto()) + "\n[0] Salir\nSelecciona una opcion:");
        int response = Integer.parseInt(input.nextLine());
        if(response == 0)
            return;
        System.out.println("\nIngrese cantidad a comprar:");
        String amount = input.nextLine();
        System.out.println("\nIngrese precio que esta dispuesto a pagar(Unidad Cryto):");
        String userPrice = input.nextLine();
        AppService.data.buyCrytoCurrency(response, new BigDecimal(amount), new BigDecimal(userPrice));
    }
    
    private static void SellOptions(){
        String message = "Venta de Crytos\nSeleccione la crytomoneda que desea vender:\n";
        if(!AppService.data.getUser().getWallet().getCrytoMoney().isEmpty())
            for (int i = 0; i < AppService.data.getUser().getWallet().getCrytoMoney().size(); i++) {
                message += "["+ (i + 1) +"]"+AppService.data.getUser().getWallet().getCrytoMoney().get(i).toString() + "\n";
            }
        else
            message += "[Wallet vacia]";
        System.out.println(message + "\n[0] Salir");
        int response = Integer.parseInt(input.nextLine()) - 1;
        if(response == -1)
            return;
        System.out.println("\nIngrese cantidad a vender:");
        String amount1 = input.nextLine();
        System.out.println("\nIngrese precio que esta dispuesto a vender(Unidad Cryto):");
        String priceMin = input.nextLine();
        AppService.data.sellCryto(AppService.data.getUser().getWallet().getCrytoMoney().get(response), new BigDecimal(amount1), new BigDecimal(priceMin));
    }
    
    private static void DepositOptions(){
        System.out.println("Tu saldo fiduciario es: " + AppService.data.getUser().getWallet().getMoney() + "\nIngresa el monto a depositar:");
        String deposit = input.nextLine();
        AppService.data.getUser().getWallet().setMoney(AppService.data.getUser().getWallet().getMoney().add(new BigDecimal(deposit)));
        MessageWait();
        System.out.print("\nDeposito exitoso!\n");
    }
    
    private static void MessageWait(){
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private static void TransactionHistoryOptions(){
        System.out.println("Historial de transacciones:\n");
    }
    
    private static void BookOrdersOptions(){
        System.out.println("Libro de ordenes de compra y venta de los usuarios:"
                + "" + AppService.data.printOrders()+ "\n[0] Salir\nSeleccione una de las opciones:");
        int response = Integer.parseInt(input.nextLine());
        if(response == 0)
            return;
        AppService.data.buyCrytoExchange(response, new BigDecimal(1));
    }
    
    

}