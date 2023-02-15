package com.appStore.AppStore.controller;

import com.appStore.AppStore.model.Funcionario;
import com.appStore.AppStore.repository.FuncionarioRepository;
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
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("administrativo/funcionarios/cadastrar")
    public ModelAndView cadastrar(Funcionario funcionario) {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);
        return mv;
    }

    @GetMapping("administrativo/funcionarios/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/lista");
        mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
        return mv;
    }

    @PostMapping("administrativo/funcionarios/salvar")
    public ModelAndView salvar(@Valid Funcionario funcionario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cadastrar(funcionario);
        } else {
            funcionarioRepository.saveAndFlush(funcionario);
            return cadastrar(new Funcionario());
        }
    }

    @GetMapping("administrativo/funcionarios/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return cadastrar(funcionario.get());
    }

    @GetMapping("administrativo/funcionarios/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        funcionarioRepository.delete(funcionario.get());
        return listar();
    }
}
