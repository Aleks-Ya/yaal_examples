package com.lesavon;

/**
 * Заказ.
 */
public interface RemoteOrder {
    Order[] getOrderList(String userName) throws SavonException;
}
