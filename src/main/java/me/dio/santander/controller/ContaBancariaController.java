package me.dio.santander.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import me.dio.santander.model.Banco;
import me.dio.santander.model.Cliente;
import me.dio.santander.model.ContaCorrente;
import me.dio.santander.model.ContaPoupanca;
import me.dio.santander.repository.BancoRepository;
import me.dio.santander.repository.ClienteRepository;
import me.dio.santander.repository.ContaCorrenteRepository;
import me.dio.santander.repository.ContaPoupancaRepository;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class ContaBancariaController {

    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    @Autowired
    private ContaPoupancaRepository contaPoupancaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BancoRepository bancoRepository;

    // Endpoints para criação de Banco, Cliente, Conta Corrente e Conta Poupança

    @PostMapping("/banco/criar")
    public Banco criarBanco(@RequestParam String nome) {
        Banco banco = new Banco();
        banco.setNome(nome);
        return bancoRepository.save(banco);
    }

    @PostMapping("/cliente/criar")
    public Cliente criarCliente(@RequestParam String nome) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        return clienteRepository.save(cliente);
    }

    @PostMapping("/conta/corrente/criar")
    public ContaCorrente criarContaCorrente(@RequestParam String numero, @RequestParam Long clienteId, @RequestParam Long bancoId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Banco banco = bancoRepository.findById(bancoId).orElseThrow(() -> new RuntimeException("Banco não encontrado"));

        ContaCorrente conta = new ContaCorrente();
        conta.setNumero(numero);
        conta.setCliente(cliente);
        conta.setBanco(banco);

        return contaCorrenteRepository.save(conta);
    }

    @PostMapping("/conta/poupanca/criar")
    public ContaPoupanca criarContaPoupanca(@RequestParam String numero, @RequestParam Long clienteId, @RequestParam Long bancoId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Banco banco = bancoRepository.findById(bancoId).orElseThrow(() -> new RuntimeException("Banco não encontrado"));

        ContaPoupanca conta = new ContaPoupanca();
        conta.setNumero(numero);
        conta.setCliente(cliente);
        conta.setBanco(banco);

        return contaPoupancaRepository.save(conta);
    }

    // Endpoints para operações de Conta Corrente e Conta Poupança

    @GetMapping("/conta/corrente/saldo/{id}")
    public BigDecimal getSaldoCorrente(@PathVariable Long id) {
        ContaCorrente conta = contaCorrenteRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta Corrente não encontrada"));
        return conta.getSaldo();
    }

    @GetMapping("/conta/poupanca/saldo/{id}")
    public BigDecimal getSaldoPoupanca(@PathVariable Long id) {
        ContaPoupanca conta = contaPoupancaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta Poupança não encontrada"));
        return conta.getSaldo();
    }

    @PostMapping("/conta/corrente/deposito/{id}")
    public String depositarCorrente(@PathVariable Long id, @RequestParam BigDecimal valor) {
        ContaCorrente conta = contaCorrenteRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta Corrente não encontrada"));
        conta.depositar(valor);
        contaCorrenteRepository.save(conta);
        return "Depósito de R$ " + valor + " realizado com sucesso na Conta Corrente.";
    }

    @PostMapping("/conta/poupanca/deposito/{id}")
    public String depositarPoupanca(@PathVariable Long id, @RequestParam BigDecimal valor) {
        ContaPoupanca conta = contaPoupancaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta Poupança não encontrada"));
        conta.depositar(valor);
        contaPoupancaRepository.save(conta);
        return "Depósito de R$ " + valor + " realizado com sucesso na Conta Poupança.";
    }

    @PostMapping("/conta/corrente/saque/{id}")
    public String sacarCorrente(@PathVariable Long id, @RequestParam BigDecimal valor) {
        ContaCorrente conta = contaCorrenteRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta Corrente não encontrada"));
        String resultado = conta.sacar(valor);
        contaCorrenteRepository.save(conta);
        return resultado;
    }

    @PostMapping("/conta/poupanca/saque/{id}")
    public String sacarPoupanca(@PathVariable Long id, @RequestParam BigDecimal valor) {
        ContaPoupanca conta = contaPoupancaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta Poupança não encontrada"));
        String resultado = conta.sacar(valor);
        contaPoupancaRepository.save(conta);
        return resultado;
    }

    @PostMapping("/conta/corrente/chequeespecial/{id}")
    public String ativarChequeEspecial(@PathVariable Long id, @RequestParam BigDecimal limite) {
        ContaCorrente conta = contaCorrenteRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta Corrente não encontrada"));
        conta.ativarChequeEspecial(limite);
        contaCorrenteRepository.save(conta);
        return "Cheque especial ativado com limite de R$ " + limite;
    }
}
