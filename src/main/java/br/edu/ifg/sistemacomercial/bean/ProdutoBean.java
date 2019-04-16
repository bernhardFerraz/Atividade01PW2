package br.edu.ifg.sistemacomercial.bean;

import br.edu.ifg.sistemacomercial.dao.ProdutoDAO;
import br.edu.ifg.sistemacomercial.entity.Categoria;
import br.edu.ifg.sistemacomercial.entity.Produto;
import br.edu.ifg.sistemacomercial.util.JsfUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ProdutoBean extends JsfUtil{
    
    private Produto produto;
    private List<Categoria> categorias;

    private List<Produto> produtos;
    private ProdutoBean.Status statusTela;
    
    private ProdutoDAO produtoDAO;
    
    public ProdutoBean() {
        Categoria c1 = new Categoria();
        c1.setId(1);
        c1.setNome("Higiene");
        Categoria c2 = new Categoria();
        c2.setId(2);
        c2.setNome("Limpeza");
        Categoria c3 = new Categoria();
        c3.setId(3);
        c3.setNome("Industrializado");
        
        categorias = Arrays.asList(c1,c2,c3);
        produto = new Produto();
    }
        private enum Status {
        INSERINDO,
        EDITANDO,
        PESQUISANDO
    }
    
        @PostConstruct
    public void init(){
        produto = new Produto();
        produtos = new ArrayList<>();   
        statusTela = Status.PESQUISANDO;
        produtoDAO = new ProdutoDAO();
    }
        public void novo(){
        statusTela = ProdutoBean.Status.INSERINDO;
        produto = new Produto();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }
    
    public void adicionarProduto(){
        try {
            produtoDAO.salvar(produto);
            produto = new Produto();
            addMensagem("Salvo com sucesso!");
            pesquisar();
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    
        public void pesquisar(){
        try {
            if(!statusTela.equals(ProdutoBean.Status.PESQUISANDO)){
                statusTela = ProdutoBean.Status.PESQUISANDO;
                return;
            }
            produtos = produtoDAO.listar();
            if(produtos == null || produtos.isEmpty()){
                addMensagemAviso("Nenhum usuário cadastrado.");
            }
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    
}
