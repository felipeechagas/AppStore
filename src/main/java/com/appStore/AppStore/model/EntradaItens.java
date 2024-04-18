package com.appStore.AppStore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "entradaItens")
public class EntradaItens implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  private EntradaProduto entradaProduto;
  @ManyToOne
  private Produto produto;
  private Double quantidade = 0.0;
  private Double valorProduto = 0.0;
  private Double valorVenda = 0.0;


}
