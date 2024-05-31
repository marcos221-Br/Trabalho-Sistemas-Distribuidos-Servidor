package com.example.trabalhosistemasdistribuidos;

import java.net.*;

import org.json.JSONObject;

import com.example.trabalhosistemasdistribuidos.banco.CandidatoDAO;
import com.example.trabalhosistemasdistribuidos.banco.EmpresaDAO;
import com.example.trabalhosistemasdistribuidos.banco.TokenDAO;
import com.example.trabalhosistemasdistribuidos.modelo.Candidato;
import com.example.trabalhosistemasdistribuidos.modelo.Empresa;
import com.example.trabalhosistemasdistribuidos.modelo.LoginCandidato;
import com.example.trabalhosistemasdistribuidos.modelo.LoginEmpresa;
import com.example.trabalhosistemasdistribuidos.modelo.Tokens;

import java.io.*;

public class SocketServer{
    protected Socket clienteSocket;
    private ServerSocket serverSocket;
    private int porta;
    private Thread conexao;

    public SocketServer(int porta){
        this.porta = porta;
    }

    public void iniciar(){
        try{
            serverSocket = new ServerSocket(this.porta);
            System.out.println("Socket de conexão criado!");
        }catch(IOException IOE){
            System.out.println("Impossível abrir servidor na porta: " + porta);
        }
    }

    public void abrirConexao(){
        System.out.println("Esperando conexão");
        try{
            while (true) {
                this.clienteSocket = serverSocket.accept();
                conexao = new Thread(){
                    String ip;
                    public void run(){
                        ToJson jsonRecebido;
                        ToJson jsonEnviado;
                        TokenGenerator token;
                        Candidato candidato;
                        CandidatoDAO jpaCandidato;
                        Empresa empresa;
                        EmpresaDAO jpaEmpresa;
                        Tokens tokenClass;
                        TokenDAO jpaToken;
                        ip = clienteSocket.getInetAddress().getHostAddress();
                        System.out.println("Conexão com: " + ip);
                        jsonRecebido = new ToJson();
                        try{
                            token = new TokenGenerator();
                            PrintWriter output = new PrintWriter(clienteSocket.getOutputStream(), true);
                            BufferedReader in = new BufferedReader(new InputStreamReader( clienteSocket.getInputStream()));

                            String inputLine;

                            while ((inputLine = (String) in.readLine()) != null){
                                System.out.println(ip + " enviou: " + inputLine);
                                try{
                                    jsonRecebido.setJson(new JSONObject(inputLine));
                                }catch(Exception e){
                                    System.out.println("JSON enviado de forma errada");
                                    output.println("JSON enviado de forma errada");
                                }
                                switch (jsonRecebido.getOperacao()) {
                                    case "loginEmpresa":
                                        LoginEmpresa loginEmpresa;
                                        loginEmpresa = new LoginEmpresa();
                                        jpaToken = new TokenDAO();
                                        loginEmpresa.setLogin(jsonRecebido.getFuncao("email"));
                                        loginEmpresa.setSenha(jsonRecebido.getFuncao("senha"));
                                        if(loginEmpresa.buscar()){
                                            String[] funcoes = {"token"};
                                            tokenClass = new Tokens(token.createToken(loginEmpresa.getLogin()));
                                            String[] valores = {tokenClass.getToken()};
                                            try {
                                                jpaToken.cadastrar(tokenClass);
                                            } catch (Exception e) {
                                                System.out.println("Impossível criar token");
                                            }
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 200);
                                        }else{
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"Login ou senha incorretos"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 401);
                                        }
                                        jsonEnviado.montarJson();
                                        System.out.println("Enviado: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;
                                    
                                    case "loginCandidato":
                                        LoginCandidato loginCandidato;
                                        loginCandidato = new LoginCandidato();
                                        jpaToken = new TokenDAO();
                                        loginCandidato.setLogin(jsonRecebido.getFuncao("email"));
                                        loginCandidato.setSenha(jsonRecebido.getFuncao("senha"));
                                        if(loginCandidato.buscar()){
                                            String[] funcoes = {"token"};
                                            tokenClass = new Tokens(token.createToken(loginCandidato.getLogin()));
                                            String[] valores = {tokenClass.getToken()};
                                            try {
                                                jpaToken.cadastrar(tokenClass);
                                            } catch (Exception e) {
                                                System.out.println("Impossível criar token");
                                            }
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 200);
                                        }else{
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"Login ou senha incorretos"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 401);
                                        }
                                        jsonEnviado.montarJson();
                                        System.out.println("Enviado: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;
                                    
                                    case "logout":
                                        jpaToken = new TokenDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token"));
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            try {
                                                jpaToken.excluir(tokenClass);
                                            } catch (Exception e) {
                                                System.out.println("Impossível excluir token");
                                            }
                                            jsonEnviado = new ToJson("logout");
                                            jsonEnviado.adicionarJson("status", 204);
                                        }else{
                                            jsonEnviado = new ToJson("logout");
                                            jsonEnviado.adicionarJson("status", 204);
                                            jsonEnviado.adicionarJson("mensagem", "Token inválido");
                                        }
                                        System.out.println("Enviado: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;
                                    
                                    case "visualizarCandidato":
                                        jpaToken = new TokenDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token"));
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email"));
                                            jpaCandidato = new CandidatoDAO();
                                            candidato = jpaCandidato.buscarIdCandidato(candidato);
                                            if(candidato != null){
                                                String[] funcoes = {"nome","senha"};
                                                String[] valores = {candidato.getNome(),candidato.getSenha()};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 201);
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"E-mail não encontrado"};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 404);
                                            }
                                        }else{
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"Token inválido"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 401);
                                        }
                                        jsonEnviado.montarJson();
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "apagarCandidato":
                                        jpaToken = new TokenDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token"));
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email"));
                                            jpaCandidato = new CandidatoDAO();
                                            candidato = jpaCandidato.buscarIdCandidato(candidato);
                                            try{
                                                jpaCandidato.excluir(candidato);
                                                try {
                                                    jpaToken.excluir(tokenClass);
                                                } catch (Exception e) {
                                                    System.out.println("Impossível excluir token");
                                                }
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao());
                                                jsonEnviado.adicionarJson("status", 201);
                                            }catch(Exception e){
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"E-mail não encontrado"};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 404);
                                                jsonEnviado.montarJson();
                                            }
                                        }else{
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"Token inválido"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 401);
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "atualizarCandidato":
                                        jpaToken = new TokenDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token"));
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email"));
                                            jpaCandidato = new CandidatoDAO();
                                            candidato = jpaCandidato.buscarIdCandidato(candidato);
                                            candidato.setNome(jsonRecebido.getFuncao("nome"));
                                            candidato.setSenha(jsonRecebido.getFuncao("senha"));
                                            try{
                                                if(!(candidato.getEmail().contains("@")) || candidato.getEmail().length() < 7 || candidato.getEmail().length() > 50 ||
                                                    candidato.getSenha().length()<3 || candidato.getSenha().length()>8 || candidato.getNome().length() < 6 ||
                                                    candidato.getNome().length() > 30){
                                                        throw new Exception();
                                                    }
                                                jpaCandidato.editar(candidato);
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao());
                                                jsonEnviado.adicionarJson("status", 201);
                                            }catch(Exception e){
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"E-mail não encontrado"};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 404);
                                                jsonEnviado.montarJson();
                                            }
                                        }else{
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"Token inválido"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 401);
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "cadastrarCandidato":
                                        jpaToken = new TokenDAO();
                                        candidato = new Candidato(jsonRecebido.getFuncao("email"),jsonRecebido.getFuncao("senha"));
                                        jpaCandidato = new CandidatoDAO();
                                        candidato = jpaCandidato.buscarIdCandidato(candidato);
                                        if(candidato != null){
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"E-mail já cadastrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(), funcoes, valores);
                                            jsonEnviado.adicionarJson("status", 422);
                                        }else{
                                            try{
                                                candidato = new Candidato(jsonRecebido.getFuncao("email"), jsonRecebido.getFuncao("senha"));
                                                candidato.setNome(jsonRecebido.getFuncao("nome"));
                                                if(!(candidato.getEmail().contains("@")) || candidato.getEmail().length() < 7 || 
                                                    candidato.getEmail().length() > 50 || candidato.getSenha().length()<3 || candidato.getSenha().length()>8 ||
                                                    candidato.getNome().length() < 6 || candidato.getNome().length() > 30){
                                                    throw new Exception();
                                                }
                                                int totalCandidato = 0;
                                                for(Candidato candidatoFor : jpaCandidato.buscarTodos()){
                                                    if(candidatoFor != null){
                                                        totalCandidato = candidatoFor.getIdCandidato()+1;
                                                    }
                                                }
                                                candidato.setIdCandidato(totalCandidato);
                                                jpaCandidato.cadastrar(candidato);
                                                String[] funcoes = {"status","token"};
                                                tokenClass = new Tokens(token.createToken(candidato.getEmail()));
                                                String[] valores = {"201",tokenClass.getToken()};
                                                try {
                                                    jpaToken.cadastrar(tokenClass);
                                                } catch (Exception e) {
                                                    System.out.println("Impossível criar token");
                                                }
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 201);
                                            }catch(Exception e){
                                                System.err.println(e);
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {""};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 404);
                                            }
                                        }
                                        jsonEnviado.montarJson();
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "visualizarEmpresa":
                                        jpaToken = new TokenDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token"));
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email"));
                                            jpaEmpresa = new EmpresaDAO();
                                            empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                            if(empresa != null){
                                                String[] funcoes = {"razaoSocial","cnpj","senha","descricao","ramo"};
                                                String[] valores = {empresa.getRazaoSocial(),empresa.getCNPJ(),empresa.getSenha(),empresa.getDescricao(),empresa.getRamo()};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 201);
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"E-mail não encontrado"};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 404);
                                            }
                                        }else{
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"Token inválido"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 401);
                                        }
                                        jsonEnviado.montarJson();
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "apagarEmpresa":
                                        jpaToken = new TokenDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token"));
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email"));
                                            jpaEmpresa = new EmpresaDAO();
                                            empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                            try{
                                                jpaEmpresa.excluir(empresa);
                                                try {
                                                    jpaToken.excluir(tokenClass);
                                                } catch (Exception e) {
                                                    System.out.println("Impossível excluir token");
                                                }
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao());
                                                jsonEnviado.adicionarJson("status", 201);
                                            }catch(Exception e){
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"E-mail não encontrado"};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 404);
                                                jsonEnviado.montarJson();
                                            }
                                        }else{
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"Token inválido"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 401);
                                            jsonEnviado.montarJson();
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "atualizarEmpresa":
                                        jpaToken = new TokenDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token"));
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email"));
                                            jpaEmpresa = new EmpresaDAO();
                                            empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                            empresa.setCNPJ(jsonRecebido.getFuncao("cnpj"));
                                            empresa.setSenha(jsonRecebido.getFuncao("senha"));
                                            empresa.setDescricao(jsonRecebido.getFuncao("descricao"));
                                            empresa.setRamo(jsonRecebido.getFuncao("ramo"));
                                            empresa.setRazaoSocial(jsonRecebido.getFuncao("razaoSocial"));
                                            try{
                                                if(!(empresa.getEmail().contains("@")) || empresa.getEmail().length() < 7 || 
                                                    empresa.getEmail().length() > 50 || empresa.getSenha().length()<3 || empresa.getSenha().length()>8){
                                                    throw new Exception();
                                                }
                                                jpaEmpresa.editar(empresa);
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao());
                                                jsonEnviado.adicionarJson("status", 201);
                                            }catch(Exception e){
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"E-mail não encontrado"};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                jsonEnviado.adicionarJson("status", 404);
                                                jsonEnviado.montarJson();
                                            }
                                        }else{
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"Token inválido"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            jsonEnviado.adicionarJson("status", 401);
                                            jsonEnviado.montarJson();
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "cadastrarEmpresa":
                                        jpaToken = new TokenDAO();
                                        empresa = new Empresa(jsonRecebido.getFuncao("email"),jsonRecebido.getFuncao("senha"));
                                        jpaEmpresa = new EmpresaDAO();
                                        empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                        if(empresa != null){
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"E-mail já cadastrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(), funcoes, valores);
                                            jsonEnviado.adicionarJson("status", 422); 
                                        }else{
                                            empresa = new Empresa();
                                            empresa.setCNPJ(jsonRecebido.getFuncao("cnpj"));
                                            empresa = jpaEmpresa.buscarCNPJEmpresa(empresa);
                                            if(empresa != null){
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"CNPJ já cadastrado"};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(), funcoes, valores);
                                                jsonEnviado.adicionarJson("status", 422);
                                            }else{
                                                try{
                                                    empresa = new Empresa(jsonRecebido.getFuncao("email"), jsonRecebido.getFuncao("senha"));
                                                    empresa.setRazaoSocial(jsonRecebido.getFuncao("razaoSocial"));
                                                    empresa.setCNPJ(jsonRecebido.getFuncao("cnpj"));
                                                    empresa.setDescricao(jsonRecebido.getFuncao("descricao"));
                                                    empresa.setRamo(jsonRecebido.getFuncao("ramo"));
                                                    if(!(empresa.getEmail().contains("@")) || empresa.getEmail().length() < 7 || 
                                                        empresa.getEmail().length() > 50 || empresa.getSenha().length()<3 || empresa.getSenha().length()>8){
                                                        throw new Exception();
                                                    }
                                                    int totalEmpresa = 0;
                                                    for(Empresa empresaFor : jpaEmpresa.buscarTodos()){
                                                        if(empresaFor != null){
                                                            totalEmpresa = empresaFor.getIdEmpresa()+1;
                                                        }
                                                    }
                                                    empresa.setIdEmpresa(totalEmpresa);
                                                    jpaEmpresa.cadastrar(empresa);
                                                    String[] funcoes = {"status","token"};
                                                    tokenClass = new Tokens(token.createToken(empresa.getEmail()));
                                                    String[] valores = {"201",tokenClass.getToken()};
                                                    try {
                                                        jpaToken.cadastrar(tokenClass);
                                                    } catch (Exception e) {
                                                        System.out.println("Impossível criar token");
                                                    }
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 201);
                                                }catch(Exception e){
                                                    System.err.println(e);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {""};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 404);
                                                }
                                            }
                                        }
                                        jsonEnviado.montarJson();
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    default:
                                        System.out.println("Enviado: Chave \"operacao\" incorreta");
                                        output.println("Chave \"operacao\" incorreta");
                                        break;
                                }
                        }

                        output.close();
                        in.close();
                        clienteSocket.close();
                        System.out.println("Usuário " + ip + " desconectado!");
                    }catch(IOException IOE){
                        System.err.println("Usuário " + ip + " enviou null!");
                    }
                }
            };
            conexao.start();
        }
    }catch(Exception IOE){
        System.out.println("Impossível completar conexão!");
    }
}

    public void fecharServer(){
        try{
            serverSocket.close();
            System.out.println("Servidor fechado com sucesso!");
        }catch (IOException IOE){
            System.err.println(IOE);
        }
    }
}
