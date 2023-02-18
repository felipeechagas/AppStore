package com.appStore.AppStore.controller;

import com.appStore.AppStore.model.Estado;
import com.appStore.AppStore.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class EstadoController {

  @Autowired
  private EstadoRepository estadoRepository;

  @GetMapping("administrativo/estados/cadastrar")
  public ModelAndView cadastrar(Estado estado) {
    ModelAndView mv = new ModelAndView("administrativo/estados/cadastro");
    mv.addObject("estado", estado);
    return mv;
  }

  @GetMapping("administrativo/estados/listar")
  public ModelAndView listar() {
    ModelAndView mv = new ModelAndView("administrativo/estados/lista");
    mv.addObject("listaEstados", estadoRepository.findAll());
    return mv;
  }

  @PostMapping("administrativo/estados/salvar")
  public ModelAndView salvar(@Valid Estado estado, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return cadastrar(estado);
    } else {
      estadoRepository.save(estado);
      return cadastrar(new Estado());
    }
  }

  @GetMapping("administrativo/estados/editar/{id}")
  public ModelAndView editar(@PathVariable("id") Long id) {
    Optional<Estado> estado = estadoRepository.findById(id);
    return cadastrar(estado.get());
  }

  @GetMapping("administrativo/estados/remover/{id}")
  public ModelAndView remover(@PathVariable("id") Long id) {
    Optional<Estado> estado = estadoRepository.findById(id);
    estadoRepository.delete(estado.get());
    return listar();
  }
}
