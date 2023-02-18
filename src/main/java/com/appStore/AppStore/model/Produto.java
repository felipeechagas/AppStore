package com.appStore.AppStore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tb_produto")
public class Produto implements Serializable {

  public Produto(){
    super();
  }

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String descricao;
  private Double valorVenda;
  private String categoria;
  private String marca;
  private Double quantidadeEstoque = 0.0;

}
