package models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class Wallet implements Serializable {
    private BigDecimal money;
    private ArrayList<CrytoWallet> crytoMoney;

    public Wallet() {
        this.money = new BigDecimal("60000");
        this.crytoMoney = new ArrayList<>();
    }
    
    public Wallet(BigDecimal money, ArrayList<CrytoWallet> crypto) {
        this.money = money;
        this.crytoMoney = crypto;
    }

    public void insertCryto(CryptoCurrency cryto, BigDecimal amount){
        if(crytoMoney.isEmpty()){
            crytoMoney.add(new CrytoWallet(cryto, amount));
        }else {
            CrytoWallet userCrypto = findCryto(cryto);
            if(userCrypto != null){
                userCrypto.setUserCount(userCrypto.getUserCount().add(amount));
            }else{
                crytoMoney.add(new CrytoWallet(cryto, amount));
            }
        }
    }
    
    private CrytoWallet findCryto(CryptoCurrency cryto){
        for (int i = 0; i < crytoMoney.size(); i++) {
            if(crytoMoney.get(i).getName().equals(cryto.getName())){
                return crytoMoney.get(i);
            }
        }
        return null;
    }

    public void subtractCryto(CryptoCurrency cryto, BigDecimal amount){
        if(!crytoMoney.isEmpty()){
            for (int i = 0; i < crytoMoney.size(); i++) {
                if(crytoMoney.get(i).getName().equals(cryto.getName())){
                    crytoMoney.get(i).setUserCount(crytoMoney.get(i).getUserCount().subtract(amount));
                }
            }
        }
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


    public ArrayList<CrytoWallet> getCrytoMoney() {
        return crytoMoney;
    }

    public void setCrytoMoney(ArrayList<CrytoWallet> crytoMoney) {
        this.crytoMoney = crytoMoney;
    }

    @Override
    public String toString() {
        return
                "money=" + money +
                "\ncrypto=" + crytoMoney;
    }
}

