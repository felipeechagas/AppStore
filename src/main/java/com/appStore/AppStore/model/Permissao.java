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
@Table(name = "permissao")
public class Permissao implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Temporal(TemporalType.DATE)
  private Date dataCadastro = new Date();

  @ManyToOne
  private Funcionario funcionario;

  @ManyToOne
  private Papel papel;

  public Permissao() {
    super();
  }
}
