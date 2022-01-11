package com.lincolnbank.apipix;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pagamento {

    // ID do pagamento – Obrigatório
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    // Status do pagamento - Obrigatório
    @Column(nullable=false)
    private Status status;

    // Data da inclusão - Obrigatório
    @Column(nullable=false)
    private String dataInclusao;

    // Data do pagamento - Obrigatório
    @Column(nullable=false)
    private String dataPagamento;

    // Valor do pagamento - Obrigatório
    @Column(nullable=false)
    private String valor;

    // Descrição do pagamento
    private String descricao;

    // Dados da recorrência
    // - Data final
    private String dataFinal;
    // - Frequência
    private Recorrencia frequencia;

    // Destino do pagamento - Obrigatório
    // - Chave pix
    @Column(nullable=false)
    private String chave;
    // - Tipo chave pix
    @Column(nullable=false)
    private ChavePix.TipoChave tipo;
    //private ChavePix chavePix;

    public Pagamento() { }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDataInclusao() {
        return this.dataInclusao;
    }

    public void setDataInclusao(String dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getDataPagamento() {
        return this.dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataFinal() {
        return this.dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Recorrencia getFrequencia() {
        return this.frequencia;
    }

    public void setFrequencia(Recorrencia frequencia) {
        this.frequencia = frequencia;
    }

    public String getChave() {
        return this.chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public ChavePix.TipoChave getTipo() {
        return this.tipo;
    }

    public void setTipo(ChavePix.TipoChave tipo) {
        this.tipo = tipo;
    }
    
};




                                                                                                 
//                                                                  //                                
//               ,//////                                            */                                
//             ///////////*                    *//////////*    *////,    *////*               ./////  
//          ,////////////////               **              */      ,*          /*          /*        
//        ...  *//////////  .,,,           /.                ./     .*            ./.    ,/           
//     ,////////  /////, ,////////         /                  /     .*               *//*             
//   ,////////////. ,  /////////////.      /                  /     .*               ,//,             
//   ///////////////*///////////////*      /                  /     .*             /*    */           
//    ///////////, ,//  ///////////*       /                ,/      .*          */          /,        
//      ,//////  ///////. *//////.         /  *////////////,        .*   ,/////,              ,/////  
//            /////////////.               /                                                          
//           *//////////////.              /  p o w e r e d     b y     B a n c o     C e n t r a l                                                        
//              /////////*                 /                                                          
//                .///*                                                                               

