package com.lincolnbank.apipix;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PagamentoRepository extends CrudRepository<Pagamento, Long> {

  List<Pagamento> findAll();

  List<Pagamento> findByValorAndDataPagamentoAndChave(String valor, String dataPagamento, String chave);

  List<Pagamento> findByStatus(Status status);

  Pagamento findById(int id);

}