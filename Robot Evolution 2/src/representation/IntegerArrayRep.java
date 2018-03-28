package representation;

import util.Representation;
import java.util.Random;

/**
 *
 * @author Gabriel Reis Miranda
 */
public class IntegerArrayRep implements Representation{
    private int[] representation;
    
    /**
     * Construtor que inicializa obrigatoriamente o numero de posições do array de representação
     * @param lenght inteiro referente ao número de posições do array
     */
    public IntegerArrayRep(int lenght) {
        this.representation = new int[lenght];
    }
    
    /**
     * Retorna o objeto referente a representação
     * @return array de inteiros
     */
    @Override
    public Object getRepresentation() {
        return representation;
    }
    /**
     * Seta o obejto 
     * @param rep Dado a ser armazenado na representação Array de inteiros
     */
    @Override
    public void setRepresentation(Object rep) {
        this.representation = (int[])rep;
    }
    /**
     * Inicializa randomicamente os daos da represntação
     */
    @Override
    public void randonInit(int margem) {
        Random generator = new Random(); // gerador de números aleatórios
        for(int i = 0; i < this.representation.length; i++){
            this.representation[i] = generator.nextInt(margem);
        }
    }

    public String toString() {
        String aux = "";
        for (int i: this.representation) {
            aux = aux + i + ";";
        }
        return aux;
    }
}
