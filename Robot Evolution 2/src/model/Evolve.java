package model;

import exception.ConnectErrorException;
import representation.IntegerArrayRep;
import util.StateEnum;
import viewController.ConfigurarEvolutionPrincipal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author gabriel reis miranda
 */
public class Evolve {
    
    //SINGLETON instance
    private static Evolve instance = new Evolve();
    
    //Ambient variables (infra)
    private ArrayList<Connection> connections;
    private ArrayList<Simulator> simulators;
    private ArrayList<Thread> threads;
    private ArrayList<Agent> agents;
    private int currentGeneration;
    
    //Evolve configuration variables
    private int maxRobots; // max robots number in each generation 
    private int maxGeneration; // max generation number to be simulated
    private int repDataNumber; // number of data nodes in representation
    private String sceneController; // scene object name who the control script is attached 
    private int initRange; // int number to determine the range values to random init population data (0 -> range) 
    
    
    private Evolve(){}
    public static Evolve getInstance(){
        if(instance == null){
            instance = new Evolve();
        }
        return instance;
    }

    public String getSceneController() {
        return sceneController;
    }

    public void setConnections (String [] dados){
        this.connections = new ArrayList<>();
        // add all recived data to connection array
        for (int i = 0; i < dados.length; i++){
            String[] aux = dados[i].split(";");
            this.connections.add(new Connection(aux[0], Integer.parseInt(aux[1])));
        }

    }
    
    public void setEvolveConfig (int robots, int generations, int repDataNumber, int range, String sceneController){
        this.maxRobots = robots;
        this.maxGeneration = generations;
        this.sceneController = sceneController;
        this.repDataNumber = repDataNumber;
        this.initRange = range;
    }

    public boolean connect() throws ConnectErrorException {
        for ( Connection aux : this.connections ) {
            aux.connect();
        }
        for( int count = 0; count < this.connections.size(); count++){
            if(!this.connections.get(count).isConnected()){
                return false;
            }
        }
        return true;
    }

    public void start(){
        // Variables Setup
        this.simulators = new ArrayList<>();  // initialize simulator runnable list
        this.agents = new ArrayList<>(); // initialize agents

        this.currentGeneration = 0; // set current generation to 0
        ///////////////////////////////////////////////////////////////////////

        // setting up the simulators runnable objects + threads
        for (Connection aux : this.connections) {
            this.simulators.add(new Simulator(aux));
        }
        ConfigurarEvolutionPrincipal.consoleLogPrint("Runnables Ready");
        System.out.println("Runnable Ready");
        System.out.println("---------------------------");
        ////////////////////////////////////////////////////////////////////////

        // initialize population
        for(int i = 0; i < this.maxRobots; i++) {
            IntegerArrayRep aux = new IntegerArrayRep(this.repDataNumber);
            aux.randonInit(this.initRange);
            this.agents.add(new Agent(aux));
        }
        System.out.println("Frist Population is Ready");
        System.out.println("---------------------------");
        System.out.println("Initializing evolution algorithm simulation");
        System.out.println("---------------------------");


        /////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////
        //                            EVOLVE LOOP                              //
        /////////////////////////////////////////////////////////////////////////


        while(this.currentGeneration < this.maxGeneration){
            //setup threads
            this.threads = new ArrayList<>(); // cleaning threads list
            for (Simulator aux: this.simulators) { // attach each thread to respective simulator object
                this.threads.add(new Thread(aux));
            }
            //////////////////////////////////////////////////

            //initilize threads
            for (Thread aux : this.threads) {
                aux.start();
            }
            /////////////////////////////////////////////////////////

            //verify if all agents was simulated
            while(!this.allAgentsDone()){

            }
            /////////////////////////////////////////////////////////

            //order agents by fitness (the top 10 will pass to the next generation)
            Collections.sort(this.agents);
            /////////////////////////////////////////////////////////

            //print
            this.printAllAgentsData(this.agents);
            System.out.println("---------------------------");


            //create new array of agents (next generation)
            ArrayList<Agent> nextGenerationAgents = new ArrayList<>();
            //clone frist 10 agents
            for(int i = 0; i < 10; i++){
                this.agents.get(i).setState(StateEnum.WAIT);
                nextGenerationAgents.add(this.agents.get(i)); // passing elements to a temporary array
            };
            ////////////////////////////////////////////////////////

            //populate the second part of the next generation array
            int[] dad1, dad2;
            dad1 = new int[this.repDataNumber];
            dad2 = new int[this.repDataNumber];
            IntegerArrayRep auxRepresentation = new IntegerArrayRep(this.repDataNumber);
            Random randomGenerator = new Random();
            for(int i = 10; i < this.maxRobots; i++){
                // get representation data from parents
                int number1 = ThreadLocalRandom.current().nextInt(0, 10);
                int number2 = ThreadLocalRandom.current().nextInt(0, 10);
                while(number2 == number1){
                    number2 = ThreadLocalRandom.current().nextInt(0, 10);
                }
                dad1 = (int[])this.agents.get(number1).getRepresentation().getRepresentation();
                dad2 = (int[])this.agents.get(number2).getRepresentation().getRepresentation();
                int[] repData = new int[this.repDataNumber]; // representation data for the new agent
                // Loop to define the new agent representation data
                for(int gene = 0; gene < this.repDataNumber; gene++){
                    int aux = ThreadLocalRandom.current().nextInt(0, 2);
                    if(aux == 0){
                        repData[gene] = dad1[gene]; // if random gets 0 -> dad 1
                    }else{
                        repData[gene] = dad2[gene]; // if random gets 0 -> dad 2
                    }
                }
                //MUTAÇÃO
                repData[randomGenerator.nextInt(this.repDataNumber)] = randomGenerator.nextInt(this.initRange);
                auxRepresentation.setRepresentation(repData);
                nextGenerationAgents.add(new Agent(auxRepresentation)); // put new agent in next generation list
            }
            ////////////////////////////////////////////////////////

            this.agents = nextGenerationAgents;

            this.currentGeneration++; // pass to next generation
        }

    }

    private boolean allAgentsDone(){
        for (Agent aux : this.agents) {
            if(aux.getState() != StateEnum.DONE){
                return false;
            }
        }
        return true;
    }

    public Agent hasNextAgent(){
        for (Agent aux : this.agents) {
            if(aux.getState() == StateEnum.WAIT){
                return aux;
            }
        }
        return null;

    };

    private void printAllAgentsData(ArrayList<Agent> agents){
        for (Agent aux: agents) {
            for (int x :(int[]) aux.getRepresentation().getRepresentation()) {
                if(x < 10){
                    System.out.print(x + "   ");
                }else if(x < 100 && x >= 10){
                    System.out.print(x + "  ");
                }else{
                    System.out.print(x + " ");
                }

            }
            System.out.print("\t | \t");
            System.out.print(aux.getState().toString());
            System.out.print("\t | \t");
            System.out.println(aux.getFitness());
        }
    }
}
