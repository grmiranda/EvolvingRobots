package model;

import exception.ConnectErrorException;
import coppelia.remoteApi;

/**
 *
 * @author Gabriel Reis Miranda
 */
public class Connection{
    
    private remoteApi vrep; // V-rep object
    private int clientID; // connection client ID
    private final int port; //connection port
    private final String IP; // connection ip
    private boolean connected; // boolean to indicate if the V-rep object is connected or not
    private boolean threadRunning; // boolean to indicate if the Thread is running or not

    public Connection(String IP, int port) {
        this.port = port;
        this.IP = IP;
        this.vrep = new remoteApi();
        this.vrep.simxFinish(-1); // close all active connections (just for safe)
        this.connected = false;
    }
    
    /**
     * Função que retorna a instancia da comunicação com API
     * @return retorna a instancia da comunicação com API externa ou null caso a conexão não exista
     */
    public remoteApi getVrep() {
        if(this.connected == false){
            return null;
        }else{
            return vrep;
        }
    }
    /**
     * Retorna a porta de comunicação do Simulador
     * @return Porta de comunicação da maquina (simulador)
     */
    public int getPort() {
        return port;
    }

    /**
     * Retorna o ID do Simulador
     * @return ID da maquina (simulador)
     */
    public String getId() {
        return IP;
    }

    /**
     * ID conexão
     * @return ID da conexão
     */
    public int getClientID() {
        if(this.connected == false){
            return -1;
        }else{
            return clientID;
        }
    }

    /**
     * Verificar se a conexão existe
     * @return true se existe uma conexão, false se não existir
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Verificar se a thread está em execução
     * @return true se a thread está em execução, false se não
     */
    public boolean isThreadRunning() {
        return threadRunning;
    }
    
    /**
     * Metodo para iniciar a conexão com o Simulador
     * @throws ConnectErrorException
     */
    public void connect() throws ConnectErrorException{
        
        this.clientID = this.vrep.simxStart(IP,port,true,true,5000,5);
        if(this.clientID == -1){
            throw new ConnectErrorException();
        }else{
            connected = true;
        }
    }
    
    /**
     * Metodo para desconetar do simulador
     * @throws ConnectErrorException
     */
    public void disconnect() throws ConnectErrorException{
        if(connected){
            vrep.simxFinish(clientID);
        }else{
            throw new ConnectErrorException("There is no active connection");
        }
    }
    
}
