package model;

import coppelia.CharWA;
import coppelia.FloatWA;
import coppelia.IntWA;
import util.StateEnum;

import static coppelia.remoteApi.simx_opmode_blocking;
import static coppelia.remoteApi.simx_opmode_oneshot;

public class Simulator implements Runnable{

    private Connection conection;
    private Evolve evolve;
    private Agent agente;

    public Simulator(Connection connection){
        this.evolve = Evolve.getInstance();
        this.conection = connection;
        this.agente = null;

    }

    public void setAgente(Agent agente){
        this.agente = agente;
    }

    public Agent getAgente(){
        return this.agente;
    }


    /**
     * Metodo responsável pelo ação da classe quando executada em thread (comunicação e controle da simulação)
     *
     */
    public void run(){

        this.agente = evolve.hasNextAgent();
        this.agente.setState(StateEnum.DOING);

        CharWA robotParameters = new CharWA(this.agente.getRepresentation().toString());
        FloatWA fitnessResult = new FloatWA(10);
        IntWA cicleCounter = new IntWA(1);

        while(this.agente != null){

            this.conection.getVrep().simxSetStringSignal(this.conection.getClientID(), "robotParameters",
                    robotParameters, simx_opmode_oneshot );

            this.conection.getVrep().simxCallScriptFunction(this.conection.getClientID(),this.evolve.getSceneController(), 1,
                    "startSimulation", null, null, null, null, null,
                    null, null, null, simx_opmode_blocking);

            //wait
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //get fitness
            this.conection.getVrep().simxCallScriptFunction(this.conection.getClientID(),this.evolve.getSceneController(), 1,
                    "getSimulationFitness", null, null, null, null, cicleCounter,
                    fitnessResult, null, null, simx_opmode_blocking);

            System.out.println(cicleCounter.getArray()[0]);

            this.agente.setFitness(fitnessResult.getArray()[0]); // set fitness to agent

            this.agente.setState(StateEnum.DONE);
            this.agente = evolve.hasNextAgent();
            if(this.agente != null){
                this.agente.setState(StateEnum.DOING);
            }else{
                break;
            }
        }

    }
}
