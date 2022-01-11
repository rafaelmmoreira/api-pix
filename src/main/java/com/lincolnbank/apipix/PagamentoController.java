package com.lincolnbank.apipix;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PagamentoController {

    @Autowired  
    private PagamentoRepository repo; 

    class Resposta implements Serializable {
        private Pagamento dados;
        private String message;

        public Resposta () {}

        public Pagamento getDados(){ return this.dados; }
        public void setDados(Pagamento dados){ this.dados = dados; }

        public String getMessage(){ return this.message; }
        public void setMessage(String message){ this.message = message; }
    }

    private void validaPagamento(Pagamento dados) {
        
        /* Determinar o status (AGENDADO/EFETUADO) */ 
        LocalDate hoje = LocalDate.now();
        LocalDate data = null;
        LocalDate dataFinal = null;
        
        /* Validações de data */
        /* Formato - deve ser AAAA/MM/DD */
        try {
            if (dados.getDataFinal() != null) {
                dataFinal = LocalDate.parse(dados.getDataFinal());
            }
            data = LocalDate.parse(dados.getDataInclusao());
            data = LocalDate.parse(dados.getDataPagamento());
        }
        catch (Exception e) {
            throw new DataInvalidaException();
        }

        /* Seleciona AGENDADO/EFETUADO em função da data de hoje */
        if (data.isAfter(hoje)) {
            dados.setStatus(Status.AGENDADO);
        }
        else {
            dados.setStatus(Status.EFETUADO);
        }   
        
        /* Recorrência */
        if (dataFinal != null && dados.getFrequencia() != null) {
            String valor = dados.getValor().replaceAll("[^0-9]", "");
            int valorInt = Integer.parseInt(valor);

            if (dataFinal.isBefore(data)) throw new DataSuperiorException();

            Period periodo = Period.between(data, dataFinal);

            switch(dados.getFrequencia()) {
                case SEMANAL:
                    System.out.println(periodo.getYears()*12 + " " + periodo.getMonths());
                    if (valorInt < 5000) throw new ValorInferiorException();
                    if (periodo.getYears()*12 + periodo.getMonths() > 12) throw new PeriodoSuperiorException();
                break;

                case MENSAL:
                    if (valorInt < 10000) throw new ValorInferiorException();
                    if (periodo.getYears()*12 + periodo.getMonths() > 24) throw new PeriodoSuperiorException();
                break;

                case TRIMESTRAL:
                    if (valorInt < 13000) throw new ValorInferiorException();
                    if (periodo.getYears()*12 + periodo.getMonths() > 36) throw new PeriodoSuperiorException();
                break;

                case SEMESTRAL:
                    if (valorInt < 15000) throw new ValorInferiorException();
                    if (periodo.getYears()*12 + periodo.getMonths() > 42) throw new PeriodoSuperiorException();
                break;
            }
        }
        /* Determina o tipo de chave */
        dados.setTipo(ChavePix.determinaTipoChave(dados.getChave()));
        /* Limpa a chave (para evitar que 12345678912 e 123.456.789-12 sejam vistos como destinos diferentes) */
        dados.setChave(dados.getChave().toLowerCase());
        if (dados.getTipo() == ChavePix.TipoChave.CPF) dados.setChave(dados.getChave().replaceAll("[^0-9]",""));
    }

    //private static final String template = "Hello, %s!";
	//private final AtomicLong counter = new AtomicLong();
/*
    @GetMapping("/pagamento")
	public Pagamento pagamento(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Pagamento(counter.incrementAndGet(), String.format(template, name));
	}    
    */

    @GetMapping("/pagamento")
    public List<Pagamento> busca(@RequestBody Pagamento dados) {
        if (dados.getId() == null) {
            if (dados.getStatus() == Status.TODOS) return repo.findAll();
            else if (dados.getStatus() != null) return repo.findByStatus(dados.getStatus());
            throw new BuscaInvalidaException();
        }

        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(repo.findById(dados.getId()));

        if (pagamentos.size() == 0) throw new NaoEncontradoException();

        return pagamentos;
    }

    @PostMapping("/pagamento")
    public Resposta cadastraPagamento(@RequestBody Pagamento dados) {   
        Resposta resp = new Resposta();
        Pagamento salvo = null;

        validaPagamento(dados);

        /* Verifica se o pagamento é repetido e salva no banco */
        List<Pagamento> lista = repo.findByValorAndDataPagamentoAndChave(dados.getValor(), dados.getDataPagamento(), dados.getChave());
        salvo = repo.save(dados);
        resp.dados = salvo;
        if (lista.size() > 0 && dados.getStatus() == Status.EFETUADO) {
            resp.message = "ATENÇÃO: Inclusão com sucesso, mas já existe pagamento idêntico.";
        }
        else {
            resp.message = "Inclusão com sucesso.";
        } 

        return resp;
    }

    @DeleteMapping("/pagamento")
    public Resposta removePagamento(@RequestBody Pagamento dados) {

        Resposta resp = new Resposta();

        if (dados.getId() == null) throw new BuscaInvalidaException();
        
        // Busca o dado - apenas para permitir exibir o dado removido na resposta, para validação
        resp.setDados(repo.findById(dados.getId()));
        
        // Se não existe, erro 404
        if (resp.dados == null) throw new NaoEncontradoException();

        // Remove o dado
        repo.delete(resp.dados);

        // Envia o dado removido na response
        resp.setMessage("Removido com sucesso!");
        return resp;
    } 

    @PutMapping("/pagamento")
    public Resposta putPagamento(@RequestBody Pagamento dados) {

        if (dados.getId() == null) throw new UpdateInvalidoException();

        Pagamento salvo = repo.findById(dados.getId());

        if (salvo == null) throw new NaoEncontradoException();

        validaPagamento(dados);

        salvo = repo.save(dados);

        Resposta resp = new Resposta();
        resp.dados = salvo;
        resp.message = "Inclusão com sucesso.";

        return resp;
    }

    @PatchMapping("/pagamento")
    public Resposta patchPagamento(@RequestBody Pagamento dados) {

        if (dados.getId() == null) throw new UpdateInvalidoException();

        Pagamento salvo = repo.findById(dados.getId());

        if (salvo == null) throw new NaoEncontradoException();

        if (dados.getChave() != null && dados.getChave() != salvo.getChave()) salvo.setChave(dados.getChave());
        if (dados.getDataFinal() != null && dados.getDataFinal() != salvo.getDataFinal()) salvo.setDataFinal(dados.getDataFinal());
        if (dados.getDataInclusao() != null && dados.getDataInclusao() != salvo.getDataInclusao()) salvo.setDataInclusao(dados.getDataInclusao());
        if (dados.getDataPagamento() != null && dados.getDataPagamento() != salvo.getDataPagamento()) salvo.setDataPagamento(dados.getDataPagamento());
        if (dados.getDescricao() != null && dados.getDescricao() != salvo.getDescricao()) salvo.setDescricao(dados.getDescricao());
        if (dados.getFrequencia() != null && dados.getFrequencia() != salvo.getFrequencia()) salvo.setFrequencia(dados.getFrequencia());
        if (dados.getStatus() != null && dados.getStatus() != salvo.getStatus()) salvo.setStatus(dados.getStatus());
        if (dados.getTipo() != null && dados.getTipo() != salvo.getTipo()) salvo.setTipo(dados.getTipo());
        if (dados.getValor() != null && dados.getValor() != salvo.getValor()) salvo.setValor(dados.getValor());
        
        validaPagamento(salvo);

        salvo = repo.save(salvo);

        Resposta resp = new Resposta();
        resp.dados = salvo;
        resp.message = "Inclusão com sucesso.";

        return resp;
    }
}