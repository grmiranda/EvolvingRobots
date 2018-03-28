package util;

/**
 *
 * @author Gabriel Reis Miranda
 */
public interface Representation {
    //Método para retornar os dados que representam o agente
    public Object getRepresentation();
    //Método para setar os dados que representam o agente
    public void setRepresentation(Object rep);
    //Método para incializar randomicamente o objeto
    public void randonInit(int margem);
}
