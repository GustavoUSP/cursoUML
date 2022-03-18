package com.gustavo.cursoUML;


import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.gustavo.cursoUML.domain.Categoria;
import com.gustavo.cursoUML.domain.Cidade;
import com.gustavo.cursoUML.domain.Cliente;
import com.gustavo.cursoUML.domain.Endereco;
import com.gustavo.cursoUML.domain.Estado;
import com.gustavo.cursoUML.domain.ItemPedido;
import com.gustavo.cursoUML.domain.Pagamento;
import com.gustavo.cursoUML.domain.PagamentoComBoleto;
import com.gustavo.cursoUML.domain.PagamentoComCartao;
import com.gustavo.cursoUML.domain.Pedido;
import com.gustavo.cursoUML.domain.Produto;
import com.gustavo.cursoUML.domain.enums.EstadoPagamento;
import com.gustavo.cursoUML.domain.enums.TipoCliente;
import com.gustavo.cursoUML.domain.repositories.CategoriaRepository;
import com.gustavo.cursoUML.domain.repositories.CidadeRepository;
import com.gustavo.cursoUML.domain.repositories.ClienteRepository;
import com.gustavo.cursoUML.domain.repositories.EnderecoRepository;
import com.gustavo.cursoUML.domain.repositories.EstadoRepository;
import com.gustavo.cursoUML.domain.repositories.ItemPedidoRepository;
import com.gustavo.cursoUML.domain.repositories.PagamentoRepository;
import com.gustavo.cursoUML.domain.repositories.PedidoRepository;
import com.gustavo.cursoUML.domain.repositories.ProdutoRepository;

@SpringBootApplication
@EnableWebMvc
@RestController
public class CursoUmlApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
		
	@Autowired
	private EstadoRepository estadoRepository;
		
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRespository;
	
	@Autowired
	private PagamentoRepository pagamentoRespository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRespository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoUmlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria (null, "Informatica");
		Categoria cat2 = new Categoria (null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));	
				
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "3338938939", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("233895895", "59059509"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardins", "3828929", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "3770929", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2022 10:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2022 10:30"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("01/10/2022 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRespository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRespository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRespository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
	}

}
