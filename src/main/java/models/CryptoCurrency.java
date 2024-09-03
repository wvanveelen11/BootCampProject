package models;

import java.io.Serializable;
import java.math.BigDecimal;

public class CryptoCurrency implements Identity, Serializable{
    private final int id;
    private final String acronym;
    private final String name;
    private final BigDecimal price;
    private BigDecimal available;

    public CryptoCurrency(int id, String acronym, String name, BigDecimal price, BigDecimal available) {
        this.id = id;
        this.acronym = acronym;
        this.name = name;
        this.price = price;
        this.available = available;
    }

    @Override
    public int getId() {
        return id;
    }
    
    public String getAcronym() {
        return acronym;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }
    
    public String show() {
        return
                "acronym='" + acronym + '\'' +
                ", name='" + name + '\'';
    }

    @Override
    public String toString() {
        return
                "acronym='" + acronym + '\'' +
                ", name='" + name + '\'' +
                ", unit price=" + price +
                ", available=" + available;
    }

}
