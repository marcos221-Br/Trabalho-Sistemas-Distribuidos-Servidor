package com.example.trabalhosistemasdistribuidos;

import java.net.*;

import org.json.JSONObject;

import com.example.trabalhosistemasdistribuidos.banco.CandidatoDAO;
import com.example.trabalhosistemasdistribuidos.modelo.Candidato;
import com.example.trabalhosistemasdistribuidos.modelo.Login;

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
                                jsonRecebido.setJson(new JSONObject(inputLine));
                                switch (jsonRecebido.getOperacao()) {
                                    case "loginEmpresa":
                                        if((jsonRecebido.getFuncao("email").equals("marcosartemio221@gmail.com")) && 
                                            (jsonRecebido.getFuncao("senha").equals("12345"))){
                                            String[] funcoes = {"status","token"};
                                            String[] valores = {"200","abcdefghijklmnopqrstuvwxyz"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }else{
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"401","Login ou senha incorretos"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }
                                        System.out.println("Enviado: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;
                                    
                                    case "loginCandidato":
                                        Login login;
                                        login = new Login();
                                        login.setLogin(jsonRecebido.getFuncao("email"));
                                        login.setSenha(jsonRecebido.getFuncao("senha"));
                                        if(login.buscar()){
                                            String[] funcoes = {"status","token"};
                                            String[] valores = {"200",token.createToken(login.getLogin())};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }else{
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"401","Login ou senha incorretos"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }
                                        System.out.println("Enviado: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;
                                    
                                    case "logout":
                                        String[] funcao = {"status"};
                                        String[] valor = {"204"};
                                        jsonEnviado = new ToJson("logout",funcao,valor);
                                        System.out.println("Enviado: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;
                                    
                                    case "visualizarCandidato":
                                        candidato = new Candidato(jsonRecebido.getFuncao("email"));
                                        jpaCandidato = new CandidatoDAO();
                                        candidato = jpaCandidato.buscarIdCandidato(candidato);
                                        if(candidato != null){
                                            String[] funcoes = {"status","nome","senha"};
                                            String[] valores = {"201",candidato.getNome(),candidato.getSenha()+""};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }else{
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"404","E-mail não encontrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "apagarCandidato":
                                        candidato = new Candidato(jsonRecebido.getFuncao("email"));
                                        jpaCandidato = new CandidatoDAO();
                                        candidato = jpaCandidato.buscarIdCandidato(candidato);
                                        try{
                                            jpaCandidato.excluir(candidato);
                                            String[] funcoes = {"status"};
                                            String[] valores = {"201"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }catch(Exception e){
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"404","E-mail não encontrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "atualizarCandidato":
                                        candidato = new Candidato(jsonRecebido.getFuncao("email"));
                                        jpaCandidato = new CandidatoDAO();
                                        candidato = jpaCandidato.buscarIdCandidato(candidato);
                                        candidato.setNome(jsonRecebido.getFuncao("nome"));
                                        candidato.setSenha(Integer.parseInt(jsonRecebido.getFuncao("senha")));
                                        try{
                                            if(!(candidato.getEmail().contains("@")) || candidato.getEmail().length() < 7 || candidato.getEmail().length() > 50 ||
                                                candidato.getSenha() < 100 || candidato.getSenha() > 99999999 || candidato.getNome().length() < 6 ||
                                                candidato.getNome().length() > 30){
                                                    throw new Exception();
                                                }
                                            jpaCandidato.editar(candidato);
                                            String[] funcoes = {"status"};
                                            String[] valores = {"201"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }catch(Exception e){
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"404","E-mail não encontrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "cadastrarCandidato":
                                        candidato = new Candidato(jsonRecebido.getFuncao("email"),Integer.parseInt(jsonRecebido.getFuncao("senha")));
                                        jpaCandidato = new CandidatoDAO();
                                        candidato = jpaCandidato.buscarIdCandidato(candidato);
                                        if(candidato != null){
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"422","E-mail já cadastrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(), funcoes, valores);
                                        }else{
                                            try{
                                                candidato = new Candidato(jsonRecebido.getFuncao("email"), Integer.parseInt(jsonRecebido.getFuncao("senha")));
                                                candidato.setNome(jsonRecebido.getFuncao("nome"));
                                                if(!(candidato.getEmail().contains("@")) || candidato.getEmail().length() < 7 || 
                                                    candidato.getEmail().length() > 50 || candidato.getSenha() < 100 || candidato.getSenha() > 99999999 ||
                                                    candidato.getNome().length() < 6 || candidato.getNome().length() > 30){
                                                    throw new Exception();
                                                }
                                                int totalCandidato = 0;
                                                for(Candidato candidatoFor : jpaCandidato.buscarTodos()){
                                                    if(candidatoFor != null){
                                                        totalCandidato = candidatoFor.getIdCandidato();
                                                    }
                                                }
                                                candidato.setIdCandidato(totalCandidato);
                                                jpaCandidato.cadastrar(candidato);
                                                String[] funcoes = {"status","token"};
                                                String[] valores = {"201",token.createToken(candidato.getEmail())};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                            }catch(Exception e){
                                                System.err.println(e);
                                                String[] funcoes = {"status","mensagem"};
                                                String[] valores = {"404",""};
                                                jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);   
                                            }
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "visualizarEmpresa":
                                        if(jsonRecebido.getFuncao("email").equals("marcosartemio221@gmail.com")){
                                            String[] funcoes = {"status","razaoSocial","cnpj","senha","descricao","ramo"};
                                            String[] valores = {"201","Marcos Artêmio Gomes dos Santos","67890","67890","Teste","Testando","TI"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }else{
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"404","E-mail não encontrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "apagarEmpresa":
                                        if(jsonRecebido.getFuncao("email").equals("marcosartemio221@gmail.com")){
                                            String[] funcoes = {"status"};
                                            String[] valores = {"201"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }else{
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"404","E-mail não encontrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "atualizarEmpresa":
                                        if(jsonRecebido.getFuncao("email").equals("marcosartemio@gmail.com")){
                                            String[] funcoes = {"status"};
                                            String[] valores = {"201"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }else{
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"404","E-mail não encontrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    case "cadastrarEmpresa":
                                        if(jsonRecebido.getFuncao("email").equals("marcosartemio221@gmail.com")){
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"422","E-mail já cadastrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(), funcoes, valores);
                                        }else if(jsonRecebido.getFuncao("cnpj").equals("123")){
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"422","CNPJ já cadastrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }else{
                                            String[] funcoes = {"status","token"};
                                            String[] valores = {"201","abcdefghijklmnopqrstuwxyz"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(),funcoes,valores);
                                        }
                                        System.out.println("Envido: " + jsonEnviado.getJson() + " para " + ip);
                                        output.println(jsonEnviado.getJson());
                                        break;

                                    default:
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
