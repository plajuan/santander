package me.dio.santander.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.santander.model.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
}
