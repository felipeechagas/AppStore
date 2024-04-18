package com.appStore.AppStore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Table(name = "funcionario")
public class Funcionario implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private Double salarioBruto;
  @Temporal(TemporalType.DATE) //consegue armazenar a data
  private Date dataEntrada;
  @Temporal(TemporalType.DATE) //consegue armazenar a data
  private Date dataSaida;
  private String cargo;
  @ManyToOne
  private Cidade cidade;
  private String logradouro;
  private String numero;
  private String complemento;
  private String bairro;
  private String uf;
  private String cep;
  private String email;
  private String senha;

  public Funcionario() {
    super();
  }
}
