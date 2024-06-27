package me.dio.santander.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;

@Entity
public class ContaPoupanca extends ContaBancaria {

    @Override
    public String sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            return "Valor de saque inválido.";
        }

        if (saldo.compareTo(valor) >= 0) {
            saldo = saldo.subtract(valor);
            return "Saque de R$ " + valor + " realizado com sucesso.";
        } else {
            return "Saldo insuficiente.";
        }
    }
}
