package com.example.trabalhosistemasdistribuidos;

import java.net.*;
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
                        ip = clienteSocket.getInetAddress().getHostAddress();
                        System.out.println("Conexão com: " + ip);
                        jsonRecebido = new ToJson();
                        try{
                            PrintWriter output = new PrintWriter(clienteSocket.getOutputStream(), true);
                            BufferedReader in = new BufferedReader(new InputStreamReader( clienteSocket.getInputStream()));
   
                            String inputLine;

                            while ((inputLine = in.readLine()) != null){
                                System.out.println(ip + " enviou: " + inputLine);
                                jsonRecebido.setJson(inputLine);
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
                                        if((jsonRecebido.getFuncao("email").equals("marcosartemio221@gmail.com")) && 
                                            (jsonRecebido.getFuncao("senha").equals("67890"))){
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
                                    
                                    case "logout":
                                        String logout = "{\"operacao\":\"logout\",\"status\":\"204\"}";
                                        System.out.println("Enviado: " + logout + " para " + ip);
                                        output.println(logout);
                                        break;
                                    
                                    case "buscarCandidato":
                                        if(jsonRecebido.getFuncao("email").equals("marcosartemio221@gmail.com")){
                                            String[] funcoes = {"status","nome","senha"};
                                            String[] valores = {"201","Marcos Artêmio Gomes dos Santos","67890"};
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

                                    case "atualizarCandidato":
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

                                    case "cadastrarCandidato":
                                        if(jsonRecebido.getFuncao("email").equals("marcosartemio221@gmail.com")){
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"422","E-mail já cadastrado"};
                                            jsonEnviado = new ToJson(jsonRecebido.getOperacao(), funcoes, valores);
                                        }else if(jsonRecebido.getFuncao("nome").equals("Marcos")){
                                            String[] funcoes = {"status","mensagem"};
                                            String[] valores = {"404",""};
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
