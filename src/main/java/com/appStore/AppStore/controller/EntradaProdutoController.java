package com.appStore.AppStore.controller;

import com.appStore.AppStore.model.EntradaItens;
import com.appStore.AppStore.model.EntradaProduto;
import com.appStore.AppStore.model.Produto;
import com.appStore.AppStore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class EntradaProdutoController {

    private List<EntradaItens> listaEntradaItens = new ArrayList<EntradaItens>();
    @Autowired
    private EntradaProdutoRepository entradaProdutoRepository;
    @Autowired
    private EntradaItensRepository entradaItensRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("administrativo/entrada/cadastrar")
    public ModelAndView cadastrar(EntradaProduto entradaProduto, EntradaItens entradaItens) {
        ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
        mv.addObject("entradaProduto", entradaProduto);
        mv.addObject("listaEntradaItens", this.listaEntradaItens);
        mv.addObject("entradaItens", entradaItens);
        mv.addObject("listaFuncionarios", funcionarioRepository.findAll());
        mv.addObject("listaProdutos", produtoRepository.findAll());
        return mv;
    }

//  @GetMapping("administrativo/estados/listar")
//  public ModelAndView listar() {
//    ModelAndView mv = new ModelAndView("administrativo/estados/lista");
//    mv.addObject("listaEstados", estadoRepository.findAll());
//    return mv;
//  }

    @PostMapping("administrativo/entrada/salvar")
    public ModelAndView salvar(String acao, EntradaProduto entradaProduto, EntradaItens entradaItens) {
        if (acao.equals("itens")) {
            this.listaEntradaItens.add(entradaItens);
        } else if (acao.equals("salvar")) {
            entradaProdutoRepository.saveAndFlush(entradaProduto);

            for (EntradaItens it : listaEntradaItens) {
                it.setEntradaProduto(entradaProduto);
                entradaItensRepository.saveAndFlush(it);
                Optional<Produto> prod = produtoRepository.findById(it.getProduto().getId());
                Produto produto = prod.get();
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + it.getQuantidade());
                produto.setValorVenda(it.getValorVenda());
                produtoRepository.saveAndFlush(produto);
                this.listaEntradaItens = new ArrayList<>();
            }
            return cadastrar(new EntradaProduto(), new EntradaItens());
        }
        return cadastrar(entradaProduto, new EntradaItens());
    }

//  @GetMapping("administrativo/estados/editar/{id}")
//  public ModelAndView editar(@PathVariable("id") Long id) {
//    Optional<Estado> estado = estadoRepository.findById(id);
//    return cadastrar(estado.get());
//  }

//  @GetMapping("administrativo/estados/remover/{id}")
//  public ModelAndView remover(@PathVariable("id") Long id) {
//    Optional<Estado> estado = estadoRepository.findById(id);
//    estadoRepository.delete(estado.get());
//    return listar();
//  }
}
