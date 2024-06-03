package com.appStore.AppStore.controller;

import com.appStore.AppStore.model.Permissao;
import com.appStore.AppStore.repository.FuncionarioRepository;
import com.appStore.AppStore.repository.PapelRepository;
import com.appStore.AppStore.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class PermissaoController {

  @Autowired
  private PermissaoRepository permissaoRepository;
  @Autowired
  private PapelRepository papelRepository;
  @Autowired
  private FuncionarioRepository funcionarioRepository;

  @GetMapping("administrativo/permissoes/cadastrar")
  public ModelAndView cadastrar(Permissao permissao) {
    ModelAndView mv = new ModelAndView("administrativo/permissoes/cadastro");
    mv.addObject("permissao", permissao);
    mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
    mv.addObject("listaPapeis", papelRepository.findAll());
    return mv;
  }

  @GetMapping("administrativo/permissoes/listar")
  public ModelAndView listar() {
    ModelAndView mv = new ModelAndView("administrativo/permissoes/lista");
    mv.addObject("listaPermissoes", permissaoRepository.findAll());
    return mv;
  }

  @PostMapping("administrativo/permissoes/salvar")
  public ModelAndView salvar(@Valid Permissao permissao, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return cadastrar(permissao);
    } else {
      permissaoRepository.save(permissao);
      return cadastrar(new Permissao());
    }
  }

  @GetMapping("administrativo/permissoes/editar/{id}")
  public ModelAndView editar(@PathVariable("id") Long id) {
    Optional<Permissao> permissao = permissaoRepository.findById(id);
    return cadastrar(permissao.get());
  }

  @GetMapping("administrativo/permissoes/remover/{id}")
  public ModelAndView remover(@PathVariable("id") Long id) {
    Optional<Permissao> permissao = permissaoRepository.findById(id);
    permissaoRepository.delete(permissao.get());
    return listar();
  }
}
