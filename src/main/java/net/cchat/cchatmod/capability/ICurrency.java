package net.cchat.cchatmod.capability;

public interface ICurrency {
    int getCurrency();
    void setCurrency(int amount);
    void addCurrency(int amount);
    void subtractCurrency(int amount);
}

