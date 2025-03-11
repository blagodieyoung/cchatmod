package net.cchat.cchatmod.capability;

public class CurrencyStorage implements ICurrency {
    private int currency = 0;

    @Override
    public int getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(int amount) {
        this.currency = amount;
    }

    @Override
    public void addCurrency(int amount) {
        this.currency += amount;
    }

    @Override
    public void subtractCurrency(int amount) {
        this.currency -= amount;
        if (this.currency < 0) {
            this.currency = 0;
        }
    }
}