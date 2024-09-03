package services;

import Util.Data;
import java.io.*;

public class AppService {

    public static Data data = new Data();

    public static void saveData()
    {
        try {
            FileOutputStream fout = new FileOutputStream("data.dat");
            ObjectOutputStream output = new ObjectOutputStream(fout);
            output.writeObject(data);
            output.flush();
            output.close();
            fout.close();
            System.out.println("Guardado exitosamente");
        } catch (FileNotFoundException e) {
            System.out.println("ruta no encontrada: " + e.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadData()
    {
        try {
            FileInputStream fin = new FileInputStream("data.dat");
            ObjectInputStream input = new ObjectInputStream(fin);
            data = (Data) input.readObject();
            data.setUser(null);
            fin.close();
            input.close();
            System.out.println("Cargado exitosamente");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
}
