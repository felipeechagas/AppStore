package com.appStore.AppStore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "entradaProduto")
public class EntradaProduto implements Serializable {

  public EntradaProduto() {
    super();
  }
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  private Funcionario funcionario;
  private String fornecedor;
  private Date dataEntrada = new Date();
  private String observacao;

}
