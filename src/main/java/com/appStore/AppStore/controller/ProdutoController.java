package com.appStore.AppStore.controller;

import com.appStore.AppStore.model.Funcionario;
import com.appStore.AppStore.model.Produto;
import com.appStore.AppStore.repository.CidadeRepository;
import com.appStore.AppStore.repository.FuncionarioRepository;
import com.appStore.AppStore.repository.ProdutoRepository;
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
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("administrativo/produtos/cadastrar")
    public ModelAndView cadastrar(Produto produto) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto", produto);
        mv.addObject("listaProdutos", produtoRepository.findAll());
        return mv;
    }

    @GetMapping("administrativo/produtos/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
        mv.addObject("listaProdutos", produtoRepository.findAll());
        return mv;
    }

    @PostMapping("administrativo/produtos/salvar")
    public ModelAndView salvar(@Valid Produto produto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cadastrar(produto);
        } else {
            produtoRepository.saveAndFlush(produto);
            return cadastrar(new Produto());
        }
    }

    @GetMapping("administrativo/produtos/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return cadastrar(produto.get());
    }

    @GetMapping("administrativo/produtos/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        produtoRepository.delete(produto.get());
        return listar();
    }
}
