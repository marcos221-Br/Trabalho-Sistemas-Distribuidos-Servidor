package com.example.trabalhosistemasdistribuidos;

import java.net.*;
import java.util.ArrayList;

import javax.persistence.EntityExistsException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.trabalhosistemasdistribuidos.banco.CandidatoCompetenciaDAO;
import com.example.trabalhosistemasdistribuidos.banco.CandidatoDAO;
import com.example.trabalhosistemasdistribuidos.banco.CompetenciaDAO;
import com.example.trabalhosistemasdistribuidos.banco.EmpresaDAO;
import com.example.trabalhosistemasdistribuidos.banco.IpDAO;
import com.example.trabalhosistemasdistribuidos.banco.TokenDAO;
import com.example.trabalhosistemasdistribuidos.banco.VagaCompetenciaDAO;
import com.example.trabalhosistemasdistribuidos.banco.VagaDAO;
import com.example.trabalhosistemasdistribuidos.modelo.Candidato;
import com.example.trabalhosistemasdistribuidos.modelo.CandidatoCompetencia;
import com.example.trabalhosistemasdistribuidos.modelo.Competencia;
import com.example.trabalhosistemasdistribuidos.modelo.CompetenciaExperiencia;
import com.example.trabalhosistemasdistribuidos.modelo.Empresa;
import com.example.trabalhosistemasdistribuidos.modelo.Filtro;
import com.example.trabalhosistemasdistribuidos.modelo.FiltroCandidato;
import com.example.trabalhosistemasdistribuidos.modelo.FiltroVaga;
import com.example.trabalhosistemasdistribuidos.modelo.Ips;
import com.example.trabalhosistemasdistribuidos.modelo.LoginCandidato;
import com.example.trabalhosistemasdistribuidos.modelo.LoginEmpresa;
import com.example.trabalhosistemasdistribuidos.modelo.Tokens;
import com.example.trabalhosistemasdistribuidos.modelo.Vaga;
import com.example.trabalhosistemasdistribuidos.modelo.VagaCompetencia;
import com.example.trabalhosistemasdistribuidos.modelo.VagaId;

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
                        CandidatoCompetencia candidatoCompetencia;
                        CandidatoCompetenciaDAO jpaCandidatoCompetencia;
                        Competencia competencia;
                        CompetenciaDAO jpaCompetencia;
                        Vaga vaga;
                        VagaDAO jpaVaga;
                        VagaCompetencia vagaCompetencia;
                        VagaCompetenciaDAO jpaVagaCompetencia;
                        Filtro filtro;
                        FiltroVaga filtrovaga;
                        FiltroCandidato filtroCandidato;
                        Ips ipClass;
                        IpDAO jpaIp;
                        ip = clienteSocket.getInetAddress().getHostAddress();
                        jpaIp = new IpDAO();
                        ipClass = new Ips(ip);
                        try {
                            jpaIp.cadastrar(ipClass);
                        } catch (Exception e) {
                            System.out.println("Erro ao adicionar IP ao banco");
                        }
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
                                        loginEmpresa.setLogin(jsonRecebido.getFuncao("email") + "");
                                        loginEmpresa.setSenha(jsonRecebido.getFuncao("senha") + "");
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
                                        loginCandidato.setLogin(jsonRecebido.getFuncao("email") + "");
                                        loginCandidato.setSenha(jsonRecebido.getFuncao("senha") + "");
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
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
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
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email") + "");
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
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email") + "");
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
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email") + "");
                                            jpaCandidato = new CandidatoDAO();
                                            candidato = jpaCandidato.buscarIdCandidato(candidato);
                                            candidato.setNome(jsonRecebido.getFuncao("nome") + "");
                                            candidato.setSenha(jsonRecebido.getFuncao("senha") + "");
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
                                        candidato = new Candidato(jsonRecebido.getFuncao("email") + "",jsonRecebido.getFuncao("senha") + "");
                                        jpaCandidato = new CandidatoDAO();
                                        candidato = jpaCandidato.buscarIdCandidato(candidato);
                                        if(candidato != null){
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"E-mail já cadastrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(), funcoes, valores);
                                            jsonEnviado.adicionarJson("status", 422);
                                        }else{
                                            try{
                                                candidato = new Candidato(jsonRecebido.getFuncao("email") + "", jsonRecebido.getFuncao("senha") + "");
                                                candidato.setNome(jsonRecebido.getFuncao("nome") + "");
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
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email") + "");
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
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email") + "");
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
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email") + "");
                                            jpaEmpresa = new EmpresaDAO();
                                            empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                            empresa.setCNPJ(jsonRecebido.getFuncao("cnpj") + "");
                                            empresa.setSenha(jsonRecebido.getFuncao("senha") + "");
                                            empresa.setDescricao(jsonRecebido.getFuncao("descricao") + "");
                                            empresa.setRamo(jsonRecebido.getFuncao("ramo") + "");
                                            empresa.setRazaoSocial(jsonRecebido.getFuncao("razaoSocial") + "");
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
                                        empresa = new Empresa(jsonRecebido.getFuncao("email") + "",jsonRecebido.getFuncao("senha") + "");
                                        jpaEmpresa = new EmpresaDAO();
                                        empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                        if(empresa != null){
                                            String[] funcoes = {"mensagem"};
                                            String[] valores = {"E-mail já cadastrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(), funcoes, valores);
                                            jsonEnviado.adicionarJson("status", 422); 
                                        }else{
                                            empresa = new Empresa();
                                            empresa.setCNPJ(jsonRecebido.getFuncao("cnpj") + "");
                                            empresa = jpaEmpresa.buscarCNPJEmpresa(empresa);
                                            if(empresa != null){
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"CNPJ já cadastrado"};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(), funcoes, valores);
                                                jsonEnviado.adicionarJson("status", 422);
                                            }else{
                                                try{
                                                    empresa = new Empresa(jsonRecebido.getFuncao("email") + "", jsonRecebido.getFuncao("senha") + "");
                                                    empresa.setRazaoSocial(jsonRecebido.getFuncao("razaoSocial") + "");
                                                    empresa.setCNPJ(jsonRecebido.getFuncao("cnpj") + "");
                                                    empresa.setDescricao(jsonRecebido.getFuncao("descricao") + "");
                                                    empresa.setRamo(jsonRecebido.getFuncao("ramo") + "");
                                                    if(!(empresa.getEmail().contains("@")) || empresa.getEmail().length() < 7 || 
                                                        empresa.getEmail().length() > 50 || empresa.getSenha().length()<3 || empresa.getSenha().length()>8){
                                                        throw new Exception();
                                                    }
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
                                    
                                    case "cadastrarCompetenciaExperiencia":
                                        jpaToken = new TokenDAO();
                                        jpaCandidato = new CandidatoDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaCandidatoCompetencia = new CandidatoCompetenciaDAO();
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email") + "");
                                            candidato = jpaCandidato.buscarIdCandidato(candidato);
                                            if(candidato != null){
                                                try{
                                                    for (int i = 0; i < ((JSONArray) jsonRecebido.getFuncao("competenciaExperiencia")).length(); i++) {
                                                        competencia = new Competencia(((JSONArray) jsonRecebido.getFuncao("competenciaExperiencia")).getJSONObject(i).getString("competencia"));
                                                        competencia = jpaCompetencia.buscarIdCompetencia(competencia);
                                                        if(jpaCandidatoCompetencia.buscarIdCandidatoCompetencia(new CandidatoCompetencia(candidato.getIdCandidato(),competencia.getIdCompetencia())) != null){
                                                            throw new EntityExistsException();
                                                        }
                                                        candidatoCompetencia = new CandidatoCompetencia(candidato.getIdCandidato(), competencia.getIdCompetencia(), 
                                                                                        ((JSONArray) jsonRecebido.getFuncao("competenciaExperiencia")).getJSONObject(i).getInt("experiencia"));
                                                        jpaCandidatoCompetencia.cadastrar(candidatoCompetencia);
                                                    }
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competencia/Experiencia cadastrada com sucesso"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 201);
                                                }catch(EntityExistsException e){
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência já encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                }catch(Exception e){
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência não encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                }
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"Candidato não encontrado!"};
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

                                    case "visualizarCompetenciaExperiencia":
                                        jpaToken = new TokenDAO();
                                        jpaCandidato = new CandidatoDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaCandidatoCompetencia = new CandidatoCompetenciaDAO();
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email") + "");
                                            candidato = jpaCandidato.buscarIdCandidato(candidato);
                                            if(candidato != null){
                                                try{
                                                    ArrayList<JSONObject> competenciaExperiencia = new ArrayList<>();
                                                    for (CandidatoCompetencia candidatoCompetenciaFor : jpaCandidatoCompetencia.buscarCompetenciasCandidato(candidato)) {
                                                        competenciaExperiencia.add(new JSONObject(new CompetenciaExperiencia(jpaCompetencia.buscar(
                                                                                    new Competencia(candidatoCompetenciaFor.getIdCompetencia())).getCompetencia(),
                                                                                    candidatoCompetenciaFor.getTempo())));
                                                    }
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao());
                                                    jsonEnviado.adicionarJson("status", 201);
                                                    jsonEnviado.adicionarJson("competenciaExperiencia", competenciaExperiencia);
                                                }catch(Exception e){
                                                    System.out.println(e);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência não encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                    jsonEnviado.montarJson();
                                                }
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"Candidato não encontrado!"};
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

                                    case "apagarCompetenciaExperiencia":
                                        jpaToken = new TokenDAO();
                                        jpaCandidato = new CandidatoDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaCandidatoCompetencia = new CandidatoCompetenciaDAO();
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email") + "");
                                            candidato = jpaCandidato.buscarIdCandidato(candidato);
                                            if(candidato != null){
                                                try{
                                                    for (int i = 0; i < ((JSONArray) jsonRecebido.getFuncao("competenciaExperiencia")).length(); i++) {
                                                        competencia = new Competencia(((JSONArray) jsonRecebido.getFuncao("competenciaExperiencia")).getJSONObject(i).getString("competencia"));
                                                        competencia = jpaCompetencia.buscarIdCompetencia(competencia);
                                                        candidatoCompetencia = new CandidatoCompetencia(candidato.getIdCandidato(), competencia.getIdCompetencia());
                                                        candidatoCompetencia = jpaCandidatoCompetencia.buscarIdCandidatoCompetencia(candidatoCompetencia);
                                                        jpaCandidatoCompetencia.excluir(candidatoCompetencia);
                                                    }
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competencia/Experiencia apagada com sucesso"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 201);
                                                }catch(Exception e){
                                                    System.out.println(e);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência não encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                }
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"Candidato não encontrado!"};
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

                                    case "atualizarCompetenciaExperiencia":
                                        jpaToken = new TokenDAO();
                                        jpaCandidato = new CandidatoDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaCandidatoCompetencia = new CandidatoCompetenciaDAO();
                                        if(tokenClass != null){
                                            candidato = new Candidato(jsonRecebido.getFuncao("email") + "");
                                            candidato = jpaCandidato.buscarIdCandidato(candidato);
                                            if(candidato != null){
                                                try{
                                                    for (int i = 0; i < ((JSONArray) jsonRecebido.getFuncao("competenciaExperiencia")).length(); i++) {
                                                        competencia = new Competencia(((JSONArray) jsonRecebido.getFuncao("competenciaExperiencia")).getJSONObject(i).getString("competencia"));
                                                        competencia = jpaCompetencia.buscarIdCompetencia(competencia);
                                                        candidatoCompetencia = new CandidatoCompetencia(candidato.getIdCandidato(), competencia.getIdCompetencia());
                                                        candidatoCompetencia = jpaCandidatoCompetencia.buscarIdCandidatoCompetencia(candidatoCompetencia);
                                                        candidatoCompetencia.setTempo(((JSONArray) jsonRecebido.getFuncao("competenciaExperiencia")).getJSONObject(i).getInt("experiencia"));
                                                        jpaCandidatoCompetencia.editar(candidatoCompetencia);
                                                    }
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competencia/Experiencia atualizado com sucesso"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 201);
                                                }catch(Exception e){
                                                    System.out.println(e);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência não encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                }
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"Candidato não encontrado!"};
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

                                    case "cadastrarVaga":
                                        jpaToken = new TokenDAO();
                                        jpaEmpresa = new EmpresaDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaVaga = new VagaDAO();
                                        jpaVagaCompetencia = new VagaCompetenciaDAO();
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email") + "");
                                            empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                            if(empresa != null){
                                                try{
                                                    vaga = new Vaga(empresa.getIdEmpresa(), Float.parseFloat(jsonRecebido.getFuncao("faixaSalarial")+""),
                                                                    jsonRecebido.getFuncao("descricao")+"", jsonRecebido.getFuncao("estado")+"",
                                                                    jsonRecebido.getFuncao("nome")+"");
                                                    jpaVaga.cadastrar(vaga);
                                                    for (int i = 0; i < ((JSONArray) jsonRecebido.getFuncao("competencias")).length(); i++) {
                                                        competencia = new Competencia(((JSONArray) jsonRecebido.getFuncao("competencias")).getString(i));
                                                        competencia = jpaCompetencia.buscarIdCompetencia(competencia);
                                                        vagaCompetencia = new VagaCompetencia(vaga.getIdVaga(), competencia.getIdCompetencia());
                                                        jpaVagaCompetencia.cadastrar(vagaCompetencia);
                                                    }
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Vaga cadastrada com sucesso"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 201);
                                                }catch(Exception e){
                                                    System.out.println(e);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência não encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                }
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"Empresa não encontrado!"};
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

                                    case "listarVagas":
                                        jpaToken = new TokenDAO();
                                        jpaVaga = new VagaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaEmpresa = new EmpresaDAO();
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email") + "");
                                            empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                            if(empresa != null){
                                                try{
                                                    vaga = new Vaga(empresa.getIdEmpresa());
                                                    ArrayList<JSONObject> vagaId = new ArrayList<>();
                                                    for (Vaga vagaFor : jpaVaga.buscarVagaEmail(vaga)) {
                                                        vagaId.add(new JSONObject(new VagaId(vagaFor.getNome(),vagaFor.getIdVaga())));
                                                    }
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao());
                                                    jsonEnviado.adicionarJson("vagas", vagaId);
                                                    jsonEnviado.adicionarJson("status", 201);
                                                }catch(Exception e){
                                                    System.out.println(e);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência não encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                    jsonEnviado.montarJson();
                                                }
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"Candidato não encontrado!"};
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

                                    case "visualizarVaga":
                                        jpaToken = new TokenDAO();
                                        jpaEmpresa = new EmpresaDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaVaga = new VagaDAO();
                                        jpaVagaCompetencia = new VagaCompetenciaDAO();
                                        ArrayList<String> competenciaArray = new ArrayList<>();
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email") + "");
                                            empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                            if(empresa != null){
                                                try{
                                                    vaga = new Vaga();
                                                    vaga.setIdVaga(Integer.parseInt(jsonRecebido.getFuncao("idVaga")+""));
                                                    vaga = jpaVaga.buscar(vaga);
                                                    vagaCompetencia = new VagaCompetencia(vaga.getIdVaga());
                                                    for (VagaCompetencia vagaCompetenciaFor : jpaVagaCompetencia.buscarVagaCompetenciaIdVagas(vagaCompetencia)) {
                                                        competenciaArray.add(jpaCompetencia.buscar(new Competencia(vagaCompetenciaFor.getIdCompetencia())).getCompetencia());
                                                    }
                                                    String[] funcoes = {"descricao","estado"};
                                                    String[] valores = {vaga.getDescricao(),vaga.getEstado()};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("faixaSalarial", vaga.getFaixaSalarial());
                                                    jsonEnviado.adicionarJson("competencias", competenciaArray);
                                                    jsonEnviado.adicionarJson("status", 201);
                                                }catch(Exception e){
                                                    System.out.println(e);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência não encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                }
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"Empresa não encontrado!"};
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

                                    case "apagarVaga":
                                        jpaToken = new TokenDAO();
                                        jpaEmpresa = new EmpresaDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaVaga = new VagaDAO();
                                        jpaVagaCompetencia = new VagaCompetenciaDAO();
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email") + "");
                                            empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                            if(empresa != null){
                                                try{
                                                    vaga = new Vaga();
                                                    vaga.setIdVaga(Integer.parseInt(jsonRecebido.getFuncao("idVaga")+""));
                                                    jpaVaga.excluir(vaga);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Vaga apagada com sucesso"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 201);
                                                }catch(Exception e){
                                                    System.out.println(e);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência não encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                }
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"Empresa não encontrado!"};
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

                                    case "atualizarVaga":
                                        jpaToken = new TokenDAO();
                                        jpaEmpresa = new EmpresaDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaVaga = new VagaDAO();
                                        jpaVagaCompetencia = new VagaCompetenciaDAO();
                                        if(tokenClass != null){
                                            empresa = new Empresa(jsonRecebido.getFuncao("email") + "");
                                            empresa = jpaEmpresa.buscarIdEmpresa(empresa);
                                            if(empresa != null){
                                                try{
                                                    vaga = new Vaga();
                                                    vaga.setIdVaga(Integer.parseInt(jsonRecebido.getFuncao("idVaga")+""));
                                                    vaga = jpaVaga.buscar(vaga);
                                                    vaga.setDescricao(jsonRecebido.getFuncao("descricao")+"");
                                                    vaga.setEstado(jsonRecebido.getFuncao("estado")+"");
                                                    vaga.setNome(jsonRecebido.getFuncao("nome")+"");
                                                    vaga.setFaixaSalarial(Float.parseFloat(jsonRecebido.getFuncao("faixaSalarial")+""));
                                                    jpaVaga.editar(vaga);
                                                    for (VagaCompetencia vagaCompetenciaFor : jpaVagaCompetencia.buscarVagaCompetenciaIdVagas(new VagaCompetencia(vaga.getIdVaga()))) {
                                                        int contains = 0;
                                                        String nomeCompetencia = jpaCompetencia.buscar(new Competencia(vagaCompetenciaFor.getIdCompetencia())).getCompetencia();
                                                        for (int i = 0; i < ((JSONArray) jsonRecebido.getFuncao("competencias")).length(); i++) {
                                                            if(((JSONArray) jsonRecebido.getFuncao("competencias")).getString(i) == nomeCompetencia){
                                                                contains = 1;
                                                            }
                                                        }
                                                        if(contains == 0){
                                                            jpaVagaCompetencia.excluir(vagaCompetenciaFor);
                                                            contains = 0;
                                                        }
                                                    }
                                                    for (int i = 0; i < ((JSONArray) jsonRecebido.getFuncao("competencias")).length(); i++) {
                                                        int contains = 0;
                                                        for (VagaCompetencia vagaCompetenciaFor : jpaVagaCompetencia.buscarVagaCompetenciaIdVagas(new VagaCompetencia(vaga.getIdVaga()))) {
                                                            if(((JSONArray) jsonRecebido.getFuncao("competencias")).getString(i) == jpaCompetencia.buscar(new Competencia(vagaCompetenciaFor.getIdCompetencia())).getCompetencia()){
                                                                contains = 1;
                                                            }
                                                        }
                                                        if(contains == 0){
                                                            jpaVagaCompetencia.cadastrar(new VagaCompetencia(vaga.getIdVaga(), jpaCompetencia.buscarIdCompetencia(new Competencia(((JSONArray) jsonRecebido.getFuncao("competencias")).getString(i))).getIdCompetencia()));
                                                        }
                                                    }
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Vaga atualizada com sucesso"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 201);
                                                }catch(Exception e){
                                                    System.out.println(e);
                                                    String[] funcoes = {"mensagem"};
                                                    String[] valores = {"Competência não encontrada!"};
                                                    jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                                    jsonEnviado.adicionarJson("status", 422);
                                                }
                                            }else{
                                                String[] funcoes = {"mensagem"};
                                                String[] valores = {"Empresa não encontrado!"};
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

                                    case "filtrarVagas":
                                        jpaToken = new TokenDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaVaga = new VagaDAO();
                                        jpaVagaCompetencia = new VagaCompetenciaDAO();
                                        jpaEmpresa = new EmpresaDAO();
                                        if(tokenClass != null){
                                            filtro = new Filtro(((JSONObject) jsonRecebido.getFuncao("filtros")).getString("tipo"));
                                            ArrayList<JSONObject> vagaArray = new ArrayList<>();
                                            for (int i = 0; i < ((JSONArray) ((JSONObject) jsonRecebido.getFuncao("filtros")).get("competencias")).length(); i++) {
                                                filtro.setCompetencias(((JSONArray) ((JSONObject) jsonRecebido.getFuncao("filtros")).get("competencias")).getString(i));
                                            }
                                            for (String array : filtro.getCompetencias()) {
                                                vagaCompetencia = new VagaCompetencia();
                                                vagaCompetencia.setIdCompetencia(jpaCompetencia.buscarIdCompetencia(new Competencia(array)).getIdCompetencia());
                                                for (VagaCompetencia vagaCompetenciaFor : jpaVagaCompetencia.buscarVagaCompetenciaIdComeptencia(vagaCompetencia)) {
                                                    vaga = new Vaga();
                                                    vaga.setIdVaga(vagaCompetenciaFor.getIdVaga());
                                                    filtrovaga = new FiltroVaga(jpaVaga.buscar(vaga).getIdVaga(),
                                                                                jpaEmpresa.buscar(new Empresa(jpaVaga.buscar(vaga).getIdEmpresa())).getEmail(),
                                                                                jpaVaga.buscar(vaga).getFaixaSalarial(), jpaVaga.buscar(vaga).getDescricao(),
                                                                                jpaVaga.buscar(vaga).getEstado(), jpaVaga.buscar(vaga).getNome());
                                                    for (VagaCompetencia competenciaNome : jpaVagaCompetencia.buscarVagaCompetenciaIdVagas(new VagaCompetencia(jpaVaga.buscar(vaga).getIdVaga()))) {
                                                        filtrovaga.setCompetencias(jpaCompetencia.buscar(new Competencia(competenciaNome.getIdCompetencia())).getCompetencia());
                                                    }
                                                    if(filtro.getTipo().equalsIgnoreCase("OR")){
                                                        if(vagaArray.isEmpty()){
                                                            vagaArray.add(filtrovaga.getJson());
                                                        }else{
                                                            boolean have = false;
                                                            for (int i = 0; i < vagaArray.size(); i++) {
                                                                if(vagaArray.get(i).getInt("idVaga") == filtrovaga.getIdVaga()){
                                                                    have = true;
                                                                }
                                                            }
                                                            if(!have){
                                                                vagaArray.add(filtrovaga.getJson());
                                                            }
                                                        }
                                                    }else{
                                                        if(vagaArray.isEmpty() && filtrovaga.getCompetencias().containsAll(filtro.getCompetencias())){
                                                            vagaArray.add(filtrovaga.getJson());
                                                        }else{
                                                            boolean have = false;
                                                            for (int i = 0; i < vagaArray.size(); i++) {
                                                                if(vagaArray.get(i).getInt("idVaga") == filtrovaga.getIdVaga()){
                                                                    have = true;
                                                                }
                                                            }
                                                            if(!have && filtrovaga.getCompetencias().containsAll(filtro.getCompetencias())){
                                                                vagaArray.add(filtrovaga.getJson());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao());
                                            jsonEnviado.adicionarJson("status", 201);
                                            jsonEnviado.adicionarJson("vagas", vagaArray);
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
                                    
                                    case "filtrarCandidatos":
                                        jpaToken = new TokenDAO();
                                        jpaCompetencia = new CompetenciaDAO();
                                        tokenClass = new Tokens(jsonRecebido.getFuncao("token") + "");
                                        tokenClass = jpaToken.buscar(tokenClass);
                                        jpaCandidato = new CandidatoDAO();
                                        jpaCandidatoCompetencia = new CandidatoCompetenciaDAO();
                                        jpaEmpresa = new EmpresaDAO();
                                        if(tokenClass != null){
                                            filtro = new Filtro(((JSONObject) jsonRecebido.getFuncao("filtros")).getString("tipo"));
                                            ArrayList<JSONObject> candidatoArray = new ArrayList<>();
                                            for (int i = 0; i < ((JSONArray) ((JSONObject) jsonRecebido.getFuncao("filtros")).get("competenciasExperiencias")).length(); i++) {
                                                filtro.setCompetencias(((JSONArray) ((JSONObject) jsonRecebido.getFuncao("filtros")).get("competenciasExperiencias")).getJSONObject(i).getString("competencia"));
                                                filtro.setExperiencias((((JSONArray) ((JSONObject) jsonRecebido.getFuncao("filtros")).get("competenciasExperiencias")).getJSONObject(i).getInt("experiencia")));
                                            }
                                            for (String array : filtro.getCompetencias()) {
                                                candidatoCompetencia = new CandidatoCompetencia();
                                                candidatoCompetencia.setIdCompetencia(jpaCompetencia.buscarIdCompetencia(new Competencia(array)).getIdCompetencia());
                                                int counter = 0;
                                                for (CandidatoCompetencia candidatoCompetenciaFor : jpaCandidatoCompetencia.buscarCandidatoCompetenciaIdComeptencia(candidatoCompetencia)) {
                                                    if(candidatoCompetenciaFor.getTempo() >= filtro.getExperiencias().get(counter)){
                                                        candidato = new Candidato();
                                                        candidato.setIdCandidato(candidatoCompetenciaFor.getIdCandidato());
                                                        filtroCandidato = new FiltroCandidato(jpaCandidato.buscar(candidato).getNome(),jpaCandidato.buscar(candidato).getEmail());
                                                        for (CandidatoCompetencia forCandidatoCompetencia : jpaCandidatoCompetencia.buscarCompetenciasCandidato(candidato)) {
                                                            filtroCandidato.setCompetenciasExperiencias(new CompetenciaExperiencia(
                                                                                                        jpaCompetencia.buscar(
                                                                                                        new Competencia(forCandidatoCompetencia.getIdCompetencia())).getCompetencia(),
                                                                                                        forCandidatoCompetencia.getTempo()));
                                                        }
                                                        if(filtro.getTipo().equalsIgnoreCase("OR")){
                                                            if(candidatoArray.isEmpty()){
                                                                candidatoArray.add(filtroCandidato.getJson());
                                                            }else{
                                                                boolean have = false;
                                                                for (int i = 0; i < candidatoArray.size(); i++) {
                                                                    if(candidatoArray.get(i).getString("email").equals(filtroCandidato.getEmail())){
                                                                        have = true;
                                                                    }
                                                                }
                                                                if(!have){
                                                                    candidatoArray.add(filtroCandidato.getJson());
                                                                }
                                                            }
                                                        }else{
                                                            if(candidatoArray.isEmpty() && filtroCandidato.getCompetencias().containsAll(filtro.getCompetencias())){
                                                                candidatoArray.add(filtroCandidato.getJson());
                                                            }else{
                                                                boolean have = false;
                                                                for (int i = 0; i < candidatoArray.size(); i++) {
                                                                    if(candidatoArray.get(i).getString("email").equals(filtroCandidato.getEmail())){
                                                                        have = true;
                                                                    }
                                                                }
                                                                if(!have && filtroCandidato.getCompetencias().containsAll(filtro.getCompetencias())){
                                                                    candidatoArray.add(filtroCandidato.getJson());
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                counter++;
                                            }
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao());
                                            jsonEnviado.adicionarJson("status", 201);
                                            jsonEnviado.adicionarJson("candidatos", candidatoArray);
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
                        try {
                            jpaIp.excluir(ipClass);
                        } catch (Exception e) {
                            e.printStackTrace();
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
