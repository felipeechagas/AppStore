package com.appStore.AppStore.controller;

import com.appStore.AppStore.model.Produto;
import com.appStore.AppStore.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class ProdutoController {

  private static String caminhoImagens = "/home/frank/Documents/imagens/";

  @Autowired
  private ProdutoRepository produtoRepository;

  @GetMapping("/administrativo/produtos/cadastrar")
  public ModelAndView cadastrar(Produto produto) {
    ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
    mv.addObject("produto", produto);
    return mv;
  }

  @GetMapping("/administrativo/produtos/listar")
  public ModelAndView listar() {
    ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
    mv.addObject("listaProdutos", produtoRepository.findAll());
    return mv;
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

  @GetMapping("administrativo/produtos/mostrarImagem/{imagem}")
  @ResponseBody
  public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
//		System.out.println(imagem);
    File imagemArquivo = new File(caminhoImagens + imagem);
    if (imagem != null || imagem.trim().length() > 0) {
      System.out.println("No IF");
      return Files.readAllBytes(imagemArquivo.toPath());
    }
    return null;
  }

  @PostMapping("/administrativo/produtos/salvar")
  public ModelAndView salvar(@Valid Produto produto, BindingResult result, @RequestParam("file") MultipartFile arquivo) {
    if (result.hasErrors()) {
      // Se houver erros de validação, retornar para a página de cadastro
      return cadastrar(produto);
    }

    try {
      if (!arquivo.isEmpty()) {
        // Salvar a imagem apenas se um arquivo foi enviado
        byte[] bytes = arquivo.getBytes();
        // Definir um caminho para salvar a imagem (de preferência, um caminho relativo)
        String caminhoImagem = "/caminho/para/salvar/imagens/" + arquivo.getOriginalFilename();
        // Salvar os bytes da imagem no caminho especificado
        Files.write(Paths.get(caminhoImagem), bytes);
        // Definir o nome da imagem no produto
        produto.setNomeImagem(arquivo.getOriginalFilename());
      }

      // Salvar o produto no banco de dados
      produtoRepository.save(produto);

    } catch (IOException e) {
      // Tratar exceções de E/S (ex: erro ao ler/escrever o arquivo)
      e.printStackTrace();
      // Se ocorrer uma exceção, redirecionar para a página de cadastro com uma mensagem de erro
      ModelAndView mv = cadastrar(produto);
      mv.addObject("erro", "Erro ao salvar o produto. Por favor, tente novamente.");
      return mv;
    }

    // Redirecionar para a página de listagem após salvar o produto
    return new ModelAndView("redirect:/administrativo/produtos/listar");
  }



}
