package models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order implements Serializable, Identity{
    private int id;
    private int userId;
    private CryptoCurrency cryptoCurrency;
    private String type;
    private BigDecimal cryptoAmount;
    private BigDecimal price;
    private Date date;

    public Order(int id, int userId, CryptoCurrency cryptoCurrency, String type, BigDecimal cryptoAmount, BigDecimal price, Date date) {
        this.id = id;
        this.userId = userId;
        this.cryptoCurrency = cryptoCurrency;
        this.type = type;
        this.cryptoAmount = cryptoAmount;
        this.price = price;
        this.date = date;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCryptoAmount() {
        return cryptoAmount;
    }

    public void setCryptoAmount(BigDecimal cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return
                "[" + cryptoCurrency +
                "], type='" + type + '\'' +
                ", cryptoAmount=" + cryptoAmount +
                ", unit price=" + price;
    }
}
