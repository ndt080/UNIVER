package utils;

import models.entities.Admin;

import javax.persistence.EntityTransaction;

public class EntityTransactionUtil<T> {
    public static void rollbackTransaction(EntityTransaction entityTransaction) {
        if (entityTransaction != null) entityTransaction.rollback();
    }
}
