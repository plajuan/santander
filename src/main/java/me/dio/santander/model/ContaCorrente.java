package me.dio.santander.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;

@Entity
public class ContaCorrente extends ContaBancaria {

    private BigDecimal chequeEspecialLimite;
    private boolean chequeEspecialAtivo;

    public ContaCorrente() {
        super();
        this.chequeEspecialLimite = BigDecimal.ZERO;
        this.chequeEspecialAtivo = false;
    }

    public void ativarChequeEspecial(BigDecimal limite) {
        if (limite.compareTo(BigDecimal.ZERO) > 0) {
            this.chequeEspecialLimite = limite;
            this.chequeEspecialAtivo = true;
        }
    }

    @Override
    public String sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            return "Valor de saque inválido.";
        }

        BigDecimal saldoDisponivel = saldo;
        if (chequeEspecialAtivo) {
            saldoDisponivel = saldoDisponivel.add(chequeEspecialLimite);
        }

        if (saldoDisponivel.compareTo(valor) >= 0) {
            saldo = saldo.subtract(valor);
            return "Saque de R$ " + valor + " realizado com sucesso.";
        } else {
            return "Saldo insuficiente.";
        }
    }
  
    public BigDecimal getChequeEspecialLimite() {
        return chequeEspecialLimite;
    }

    public boolean isChequeEspecialAtivo() {
        return chequeEspecialAtivo;
    }
}
